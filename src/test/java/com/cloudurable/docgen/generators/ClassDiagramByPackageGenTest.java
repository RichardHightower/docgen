package com.cloudurable.docgen.generators;

import com.cloudurable.docgen.util.FileUtils;
import org.junit.jupiter.api.Test;
import org.opentest4j.AssertionFailedError;

import java.io.File;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Pattern;

import static com.cloudurable.docgen.TestUtil.getClassDefsByPackage;
import static com.cloudurable.docgen.TestUtil.validateMermaid;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ClassDiagramByPackageGenTest {


    @Test
    public void testModel() {

        var diagram = doTest("org.example.model");
        System.out.println(diagram);
    }

    @Test
    public void testRepo() {
        var diagram = doTest("org.example.repo");
        System.out.println(diagram);
    }

    @Test
    public void testService() {
        var diagram = doTest("org.example.service");
        System.out.println(diagram);
    }

    public String doTest(final String packageName) {
        return testClassDiagram(3, false, packageName, "simple", getClassDefsByPackage());
    }

    public String testClassDiagram(int count, boolean ignore,
                                   String packageName, String prefix,
                                   Map<String, String> classDefsByPackage) {

        final File rootDir = new File("test/" + prefix + "/output/packages");
        rootDir.mkdirs();


        File packageDir = new File(rootDir, packageName.replace('.', '_'));
        packageDir.mkdirs();
        File promptDir = new File(packageDir, "prompts");
        File messageDir = new File(packageDir, "messages");
        messageDir.mkdirs();
        File mermaidDir = new File(packageDir, "mermaid");
        promptDir.mkdirs();
        mermaidDir.mkdirs();

        final var gen = ClassDiagramByPackageGen.builder()
                //.setModel("gpt-3.5-turbo-16k-0613")
                .setModel("gpt-3.5-turbo-16k")
                .setTemperature(0.0f)
                .setMaxTokens(2000)
                .setValidateJson(false).build();

        final var counter = new AtomicInteger();
        final var promptBuilder = new StringBuilder();
        final var responseBuilder = new StringBuilder();
        final AtomicInteger messageSequence = new AtomicInteger();
        String mermaidCode = gen.generateClassDiagramFromPackage(packageName,
                classDefsByPackage.get(packageName), message -> {
                    final var msgNum  = messageSequence.incrementAndGet();
                    final var tryCount = counter.get();
                    final var msgRole = message.getRole().toString().toLowerCase();
                    final var content = message.getContent();
                    File outputFile = new File(messageDir,
                            String.format("message_%d_%d_%s.md", tryCount, msgNum, msgRole ));
                    FileUtils.writeFile(outputFile, content);
                },
                prompt -> {
                    promptBuilder.append(prompt);
                    counter.incrementAndGet();
                    File outputFile = new File(promptDir, "prompt_" + counter.get() + ".md");
                    FileUtils.writeFile(outputFile, prompt);
                    System.out.println("##########################################################################");
                    System.out.println(prompt);
                    System.out.println("##########################################################################");
                }, output -> {
                    File outputFile = new File(mermaidDir, "class_diagram" + counter.get() + ".md");
                    FileUtils.writeFile(outputFile, output);
                });

        File outputFile = new File(mermaidDir, "class_diagram_final.mmd");
        File outputFile2 = new File(rootDir, "mermaid/class_diagram_final_" + packageName.replace('.', '_') + ".mmd");
        outputFile2.getParentFile().mkdirs();
        FileUtils.writeFile(outputFile, mermaidCode);
        FileUtils.writeFile(outputFile2, mermaidCode);

        try {
            Pattern classDiagramPattern = Pattern.compile("classDiagram");
            assertTrue(classDiagramPattern.matcher(mermaidCode).find(), "The mermaidCode should have a classDiagram");
            Pattern classPattern = Pattern.compile("class ");
            assertTrue(classPattern.matcher(mermaidCode).find(), "The mermaidCode should have a class");
            validateMermaid(mermaidCode);
            if (!ignore) {
                System.out.println("COUNTER " + counter);
                assertTrue(counter.get() < count, "We did not retry more than " + count);
            }
        } catch (AssertionFailedError ex) {
            System.out.println("Mermaid Code -------------- ");
            System.out.println(mermaidCode);
            System.out.println("PROMPTS -------------- ");
            System.out.println(promptBuilder);
            System.out.println("RESPONSES -------------- ");
            System.out.println(responseBuilder);
            if (ignore) {
                ex.printStackTrace();
                return "";
            } else {
                throw ex;
            }
        }
        return mermaidCode;
    }

}
