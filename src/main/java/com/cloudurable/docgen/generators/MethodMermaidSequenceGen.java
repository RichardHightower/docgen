package com.cloudurable.docgen.generators;

import com.cloudurable.docgen.util.FileUtils;
import com.cloudurable.docgen.mermaid.validation.*;
import com.cloudurable.docgen.mermaid.validation.sequence.*;
import com.cloudurable.docgen.util.MermaidUtils;
import com.cloudurable.jai.OpenAIClient;
import com.cloudurable.jai.model.text.completion.chat.ChatRequest;
import com.cloudurable.jai.model.text.completion.chat.Message;
import com.cloudurable.jai.model.text.completion.chat.Role;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import static com.cloudurable.docgen.util.Env.getOpenaiApiKey;

public class MethodMermaidSequenceGen {

    private final OpenAIClient client;
    private final List<Message> context = new ArrayList<>();
    private final int  maxTokens;
    private final String  model; //
    private final float temperature;
    private final boolean validateJson;

    private final File templateDir = new File("src/main/templates/methods/");

    public  MethodMermaidSequenceGen(Builder builder){
        this.maxTokens = builder.maxTokens;
        this.model = builder.model;
        this.temperature = builder.temperature;
        this.validateJson = builder.validateJson;

        client = builder.client != null ? builder.client :
                OpenAIClient.builder().validateJson(this.validateJson).setApiKey(getOpenaiApiKey()).build();

        final var systemMessage = Message.builder().role(Role.SYSTEM)
                .content(FileUtils.readFile(new File(templateDir, "system.md")))
                .build();
        context.add(systemMessage);

        final var assistantMessage = Message.builder().role(Role.ASSISTANT)
                .content(FileUtils.readFile(new File(templateDir, "example.md")))
                .build();
        context.add(assistantMessage);

    }


    private  ChatRequest.Builder requesatBuilder(List<Message> context) {
        return ChatRequest.builder().messages(new ArrayList<>(context))
                .maxTokens(maxTokens).temperature(temperature).model(model);
    }

    private static RuleRunner buildRuleRunner() {
        RuleRunner ruleRunner;
        List<LineRule> rules = new ArrayList<>();
        rules.add(new AvoidNotesRule());
        rules.add(new NoMethodCallsInDescriptionsRule());
        rules.add(new AvoidActivateDeactivateRule());
        rules.add(new ParticipantAliasRule());
        rules.add(new SystemOutRule());
        rules.add(new DataClassesAndPrimitiveRule());

        ruleRunner = RuleRunner.builder().contentRules(List.of(new TitleRequiredRule(), new MismatchedTagsRule(), MermaidUtils.createRule()))
                .rules(rules).build();
        return ruleRunner;
    }

    public static String extractSequenceDiagram(String mermaidCode) {

        final var lines = mermaidCode.split("\n");
        final var extractedCode = new StringBuilder();

        boolean foundStart = false;

        for (String line : lines) {

            if (line.startsWith("```mermaid")) {
                foundStart = true;
            } else if (foundStart) {
                if (line.startsWith("```")) {
                    break;
                }
                extractedCode.append(line).append("\n");
            }
        }

        return extractedCode.toString();
    }

    public String generateSequenceFromMethod(String javaMethodSource, String methodName, String className, String packageName,
                                             Consumer<String> promptConsumer, Consumer<String> responseConsumer) {

        final var ruleRunner = buildRuleRunner();
        final var title = methodName + " (" + className + ")";
        final var builder = requesatBuilder(context);

        final var promptBuilder = new StringBuilder();

        context.forEach(message -> {
            promptBuilder.append(convertMessageToMarkdown(message));
        });

        final var template = FileUtils.readFile(new File(this.templateDir, "instruct.md"));
        final var instruction = template.replace("{{JAVA_METHOD}}", javaMethodSource)
                .replace("{{TITLE}}", title);

        final var instructionMessage = Message.builder().role(Role.USER).content(instruction).build();

        promptBuilder.append(convertMessageToMarkdown(instructionMessage));
        promptConsumer.accept(promptBuilder.toString());

        final var request = builder.addMessage(instructionMessage).build();


        return runValidationFeedbackLoop(javaMethodSource, title, instruction, request, ruleRunner,
                promptConsumer, responseConsumer);
    }


    private static String convertMessageToMarkdown(Message message) {
        StringBuilder messageBuilder = new StringBuilder();
        messageBuilder.append("\n# ").append(message.getRole()).append("\n").append(message.getContent()).append("\n---\n");
        return messageBuilder.toString();
    }

    private String runValidationFeedbackLoop(String javaMethodSource, String title, String instruction,
                                             ChatRequest request, RuleRunner ruleRunner, Consumer<String> promptConsumer,
                                             Consumer<String> responseConsumer) {
        for (int i = 0; i < 5; i++) {
            final var chatResponse = client.chat(request);
            if (chatResponse.getException().isPresent()) {
                System.out.printf("%s\n", instruction);
                chatResponse.getException().ifPresent(Throwable::printStackTrace);
            } else if (chatResponse.getStatusMessage().isPresent()) {
                chatResponse.getStatusMessage().ifPresent(status -> {
                    System.out.printf("%s %s %d\n", instruction, status, chatResponse.getStatusCode().orElse(666));
                });
            } else if (chatResponse.getResponse().isPresent()) {
                final var response = chatResponse.getResponse().get();
                final var chatChoice = response.getChoices().get(0);
                final var original = chatChoice.getMessage().getContent();
                responseConsumer.accept("----\n# ORIGINAL RESPONSE \n" + original);
                final var mermaidDiagram = extractSequenceDiagram(original);
                return validateMermaid(javaMethodSource, mermaidDiagram, title, ruleRunner,
                        promptConsumer, responseConsumer);
            }
        }
        return "";
    }

    private String validateMermaid(String javaMethodSource, String mermaidDiagram, String title,
                                   RuleRunner ruleRunner,  Consumer<String> promptConsumer, Consumer<String> responseConsumer) {
        return validateMermaid(javaMethodSource, mermaidDiagram, title, ruleRunner,3, promptConsumer, responseConsumer);
    }

    private String validateMermaid(String javaMethodSource, final String originalMermaidDiagram, String title,
                                   RuleRunner ruleRunner, int count, Consumer<String> promptConsumer,
                                   Consumer<String> responseConsumer) {

        if (count <= 0) {
            return originalMermaidDiagram;
        }

        ChatRequest.Builder builder = requesatBuilder(context);


        final var promptBuilder = new StringBuilder();
        promptBuilder.append("\n# Fix for ").append(count).append(" ").append(title).append("\n\n");

        context.forEach(message -> {
            promptBuilder.append(convertMessageToMarkdown(message));
        });

        final var templateMermaid = FileUtils.readFile(new File(this.templateDir,"fix.md"));

        final var checks = ruleRunner.checkContent(originalMermaidDiagram);

        if (!checks.isEmpty()) {
            final var fixInstruction = "----\n# FIX PROMPT TRY " + count + "\n" +
                    (templateMermaid
                    .replace("{{JAVA_METHOD}}", javaMethodSource)
                    .replace("{{JSON}}", RuleRunner.serializeRuleResults(checks))
                    .replace("{{MERMAID}}", originalMermaidDiagram)
                    .replace("{{TITLE}}", title));

           final var fixMessage = Message.builder().role(Role.USER)
                    .content(fixInstruction).build();


            promptBuilder.append(convertMessageToMarkdown(fixMessage));
            promptConsumer.accept(promptBuilder.toString());

            final var fixRequest = builder.addMessage(fixMessage).build();

            final var fixMermaidResponse = client.chat(fixRequest);


            if (fixMermaidResponse.getException().isPresent()) {
                System.out.printf("%s\n", fixRequest);
                fixMermaidResponse.getException().ifPresent(Throwable::printStackTrace);
            } else if (fixMermaidResponse.getStatusMessage().isPresent()) {
                fixMermaidResponse.getException().ifPresent(status -> {
                    System.out.printf("%s %s %d\n", fixInstruction, status, fixMermaidResponse.getStatusCode().orElse(666));
                });
            }
            if (fixMermaidResponse.getResponse().isPresent()) {
                final var response = fixMermaidResponse.getResponse().get();
                final var chatChoice = response.getChoices().get(0);
                final var original = chatChoice.getMessage().getContent();
                responseConsumer.accept("----\n# FIX RAW RESPONSE " + count + "\n" + original);
                final var mermaidDiagram = extractSequenceDiagram(original);
                return validateMermaid(javaMethodSource, mermaidDiagram, title, ruleRunner, count - 1,
                        promptConsumer, responseConsumer);
            } else {
                return originalMermaidDiagram;
            }

        } else {
            return originalMermaidDiagram;
        }
    }

    public static Builder builder() {
        return new Builder();
    }
    public static class Builder {
        private  OpenAIClient client;
        private  int  maxTokens = 2000;
        private  String  model = "gpt-3.5-turbo-16k-0613";
        private  float temperature = 0.0f;
        private  boolean validateJson;

        public Builder setClient(OpenAIClient client) {
            this.client = client;
            return this;
        }

        public Builder setMaxTokens(int maxTokens) {
            this.maxTokens = maxTokens;
            return this;
        }

        public Builder setModel(String model) {
            this.model = model;
            return this;
        }

        public Builder setTemperature(float temperature) {
            this.temperature = temperature;
            return this;
        }

        public Builder setValidateJson(boolean validateJson) {
            this.validateJson = validateJson;
            return this;
        }

        public MethodMermaidSequenceGen build() {
            return  new MethodMermaidSequenceGen(this);
        }
    }

}
