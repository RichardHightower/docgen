package com.cloudurable.docgen.generators;

import com.cloudurable.docgen.mermaid.validation.classes.*;
import com.cloudurable.docgen.mermaid.validation.sequence.TitleRequiredRule;
import com.cloudurable.docgen.util.FileUtils;
import com.cloudurable.docgen.mermaid.validation.*;
import com.cloudurable.docgen.util.Env;
import com.cloudurable.docgen.util.MermaidUtils;
import com.cloudurable.jai.OpenAIClient;
import com.cloudurable.jai.model.ClientResponse;
import com.cloudurable.jai.model.text.completion.chat.*;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import static com.cloudurable.docgen.generators.GenUtils.convertMessageToMarkdown;

public class ClassDiagramByPackageGen {

    private final OpenAIClient client;
    private final List<Message> context = new ArrayList<>();

    private final int  maxTokens;
    private final String  model; //
    private final float temperature;
    private final boolean validateJson;
    private final int numChoices;

    private final File templateDir = new File("src/main/templates/methods/");

    public ClassDiagramByPackageGen(final Builder builder){

        this.maxTokens = builder.maxTokens;
        this.model = builder.model;
        this.temperature = builder.temperature;
        this.validateJson = builder.validateJson;
        this.numChoices = builder.numChoices;


        client = builder.client == null ?
                OpenAIClient.builder().validateJson(validateJson)
                        .setApiKey(Env.getOpenaiApiKey()).build()
        : builder.client;

        final var systemMessage = Message.builder().role(Role.SYSTEM)
                .content(FileUtils.readFile(new File("src/main/templates/classes/system.md")))
                .build();
        context.add(systemMessage);

        final var assistantMessage = Message.builder().role(Role.ASSISTANT)
                .content(FileUtils.readFile(new File("src/main/templates/classes/examples.md")))
                .build();
        context.add(assistantMessage);

    }



    private ChatRequest.Builder requesatBuilder() {
        return ChatRequest.builder().messages(context)
                .maxTokens(maxTokens).temperature(temperature)
                .completionCount(numChoices).model(model);

        //"gpt-3.5-turbo-16k-0613"
    }

    private static RuleRunner buildRuleRunner() {
        RuleRunner ruleRunner;
        ruleRunner = RuleRunner.builder().contentRules(List.of(new TitleRequiredRule(), new InheritanceInsideClassRule(),
                        MermaidUtils.createRule()))
                .rules(List.of(new NoCollectionRule(), new NoPrimitiveOrBasicTypesRule(),
                        new NoInlineInheritanceRule(), new NoArrayRule(), new InterfaceRule(),
                        new ClassBracketsRule()))
                .build();
        return ruleRunner;
    }

    public static String extractMermaidDiagram(String mermaidCode) {

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

        return extractedCode.toString().trim();
    }

    public String generateClassDiagramFromPackage(String packageName, String source,
                                                  Consumer<Message> messageListener,
                                                  Consumer<String> promptConsumer,
                                                  Consumer<String> responseConsumer) {

        final var title = "Package " + packageName;
        final var builder = requesatBuilder();
        final var template = FileUtils.readFile(new File("src/main/templates/classes/instruct.md"));
        final var instruction = template.replace("{{JAVA_CODE}}", source)
                .replace("{{TITLE}}", title);
        final var instructionMessage = Message.builder().role(Role.USER).content(instruction).build();
        context.forEach(messageListener);
        messageListener.accept(instructionMessage);
        promptConsumer.accept(promptFromMessages(instructionMessage, context));
        final var request = builder.addMessage(instructionMessage).build();
        return runMermaidValidationFeedbackLoop(source, title, instruction, request, messageListener, promptConsumer, responseConsumer);
    }

    public static String promptFromMessages(Message instructionMessage, List<Message> context) {

        final var messages = new StringBuilder();
        String base = String.join("\n----\n",
                context.stream().map(GenUtils::convertMessageToMarkdown).toArray(String[]::new));

        messages.append(base).append("\n----\n").append(convertMessageToMarkdown(instructionMessage));
        return messages.toString();
    }

    private String runMermaidValidationFeedbackLoop(String source, String title, String instruction,
                                                    ChatRequest request,
                                                    Consumer<Message> messageListener,
                                                    Consumer<String> promptConsumer,
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
                String mermaidDiagram = processResposne(messageListener, responseConsumer, chatResponse);
                return validateMermaid(source, mermaidDiagram, title, messageListener, promptConsumer, responseConsumer);
            }
        }
        return "";
    }

    private String processResposne(Consumer<Message> messageListener, Consumer<String> responseConsumer, ClientResponse<ChatRequest, ChatResponse> chatResponse) {
        final var response = chatResponse.getResponse().get();
        response.getChoices().stream().map(ChatChoice::getMessage).forEach(messageListener);
        final var chatChoice = response.getChoices().get(0);
        final var original = chatChoice.getMessage().getContent();
        responseConsumer.accept(original);
        return extractMermaidDiagram(original);
    }


    private String validateMermaid(String source, String mermaidDiagram, String title,
                                   Consumer<Message> messageListener, Consumer<String> promptConsumer,
                                   Consumer<String> responseConsumer) {
        return validateMermaid(source, mermaidDiagram, title, 5, messageListener, promptConsumer, responseConsumer);
    }

    private String validateMermaid(String source, String existingMermaidDiagram, String title, int count,
                                   Consumer<Message> messageListener, Consumer<String> promptConsumer,
                                   Consumer<String> responseConsumer) {

        if (count <= 0) {
            return existingMermaidDiagram;
        }

        ChatRequest.Builder builder = requesatBuilder();

        final var ruleRunner = buildRuleRunner();

        final var templateMermaid = FileUtils.readFile(new File("src/main/templates/classes/fix.md"));

        final var checks = ruleRunner.checkContent(existingMermaidDiagram);

        if (!checks.isEmpty()) {
            final var fixInstruction = templateMermaid
                    .replace("{{JAVA_CODE}}", source)
                    .replace("{{JSON}}", RuleRunner.serializeRuleResults(checks))
                    .replace("{{MERMAID}}", existingMermaidDiagram)
                    .replace("{{TITLE}}", title);

            final var fixMessage = Message.builder().role(Role.USER)
                    .content(fixInstruction).build();

            context.forEach(messageListener);
            messageListener.accept(fixMessage);
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
                final var newMermaidDiagram = processResposne(messageListener, responseConsumer, fixMermaidResponse);
                return validateMermaid(source, newMermaidDiagram, title, count - 1,
                        messageListener, promptConsumer, responseConsumer);
            } else {
                return existingMermaidDiagram;
            }

        } else {
            return existingMermaidDiagram;
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
        private int numChoices = 1;


        public Builder setNumChoices(int numChoices) {
            this.numChoices = numChoices;
            return this;
        }

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

        public ClassDiagramByPackageGen build() {
            return  new ClassDiagramByPackageGen(this);
        }
    }

}
