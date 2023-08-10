package com.cloudurable.docgen.generators;

import com.cloudurable.docgen.Result;
import com.cloudurable.docgen.TestUtil;
import com.cloudurable.docgen.parser.model.FieldJavaItem;
import com.cloudurable.docgen.util.FileUtils;
import com.cloudurable.docgen.parser.model.JavaItem;
import com.cloudurable.docgen.parser.model.JavaItemType;
import com.cloudurable.docgen.util.MermaidUtils;
import org.junit.jupiter.api.Test;
import org.opentest4j.AssertionFailedError;

import java.io.File;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class MethodMermaidSequenceGenTest {


    @Test
    public void testAddressToString() {
        String mermaidCode = testSequenceDiagram("Address", "toString");
        Pattern appendPattern = Pattern.compile("\\.append\\([^)]*\\)");
        assertFalse(appendPattern.matcher(mermaidCode).find(), "The mermaidCode should not have any .append(...) calls");
    }

    @Test
    public void testPersonToString() {
        String mermaidCode = testSequenceDiagram("Person", "toString");
        //Pattern appendPattern = Pattern.compile("\\.append\\([^)]*\\)");
        //assertFalse(appendPattern.matcher(mermaidCode).find(), "The mermaidCode should not have any .append(...) calls");
    }

    @Test
    public void testManagerToString() {
        String mermaidCode = testSequenceDiagram("Manager", "toString");
        Pattern appendPattern = Pattern.compile("\\.append\\([^)]*\\)");
        assertFalse(appendPattern.matcher(mermaidCode).find(), "The mermaidCode should not have any .append(...) calls");
    }

    @Test
    public void testHRServiceUpdateEmployeesManager() {
        String mermaidCode = testSequenceDiagram("HRService", "updateEmployeesManager");
        System.out.println(mermaidCode);
    }

    @Test
    public void changeEmployeeDepartment() {
        String mermaidCode = testSequenceDiagram("HRService", "changeEmployeeDepartment");
        System.out.println(mermaidCode);
    }


    public void readAllFromHereOLD() {
        List<JavaItem> javaItems = TestUtil.loadProject("./src/main/java");

        List<JavaItem> methods = javaItems.stream().filter(item -> item.getType() == JavaItemType.METHOD).collect(Collectors.toList());
        final int size = methods.size();
        final AtomicInteger counter = new AtomicInteger();
        System.out.println(size);


        javaItems.stream().filter(item -> item.getType() == JavaItemType.METHOD).forEach(method ->
        {
            String simpleClassName = method.getParent().getSimpleName();
            String methodName = method.getSimpleName();
            String mermaidCode = testSequenceDiagram(10, true, simpleClassName,
                    methodName, "all", javaItems);

            int currentCount = counter.incrementAndGet();
            System.out.println("######################## MERMAID CODE");
            System.out.println(mermaidCode);
            System.out.printf("Total %d, \t Count Done %d, \t Remaining %d\n", size, currentCount, size - currentCount);
        });

    }

    @Test
    public void readAllFromHere() {
        List<JavaItem> javaItems = TestUtil.loadProject("./src/main/java");

        List<JavaItem> methods = javaItems.stream().filter(item -> item.getType() == JavaItemType.METHOD).collect(Collectors.toList());
        final int size = methods.size();
        final AtomicInteger counter = new AtomicInteger();
        System.out.println(size);

        long startTotalTime = System.nanoTime(); // Track the start time of the entire loop

        methods.forEach(method -> {
            long startIterationTime = System.nanoTime(); // Track the start time of the current iteration

            String simpleClassName = method.getParent().getSimpleName();
            String methodName = method.getSimpleName();
            String mermaidCode = testSequenceDiagram(10, true, simpleClassName, methodName, "all", javaItems);

            int currentCount = counter.incrementAndGet();
            System.out.println("######################## MERMAID CODE");
            System.out.println(mermaidCode);
            System.out.printf("Total %d, \t Count Done %d, \t Remaining %d\n", size, currentCount, size - currentCount);

            long endIterationTime = System.nanoTime(); // Track the end time of the current iteration
            long elapsedIterationTime = endIterationTime - startIterationTime;
            System.out.printf("Time taken for this iteration: %d minutes, %d seconds\n",
                    TimeUnit.NANOSECONDS.toMinutes(elapsedIterationTime),
                    TimeUnit.NANOSECONDS.toSeconds(elapsedIterationTime) - TimeUnit.MINUTES.toSeconds(TimeUnit.NANOSECONDS.toMinutes(elapsedIterationTime))
            );
            long endTotalTime = System.nanoTime(); // Track the end time of the entire loop
            long elapsedTotalTime = endTotalTime - startTotalTime;
            System.out.printf("Total time taken for all iterations so far: %d minutes, %d seconds\n",
                    TimeUnit.NANOSECONDS.toMinutes(elapsedTotalTime),
                    TimeUnit.NANOSECONDS.toSeconds(elapsedTotalTime) - TimeUnit.MINUTES.toSeconds(TimeUnit.NANOSECONDS.toMinutes(elapsedTotalTime))
            );
        });

        long endTotalTime = System.nanoTime(); // Track the end time of the entire loop
        long elapsedTotalTime = endTotalTime - startTotalTime;
        System.out.printf("Total time taken for all iterations: %d minutes, %d seconds\n",
                TimeUnit.NANOSECONDS.toMinutes(elapsedTotalTime),
                TimeUnit.NANOSECONDS.toSeconds(elapsedTotalTime) - TimeUnit.MINUTES.toSeconds(TimeUnit.NANOSECONDS.toMinutes(elapsedTotalTime))
        );
    }


    public String testSequenceDiagram(String simpleClassName, String methodName) {
        return testSequenceDiagram(3, false, simpleClassName, methodName, "simple", TestUtil.loadSimpleProject());
    }

    public String testSequenceDiagram(int count, boolean ignore, String simpleClassName,
                                      String methodName, String prefix, List<JavaItem> javaItems) {

        final File rootDir = new File("test/"+ prefix+"/output");
        rootDir.mkdirs();


        Optional<JavaItem> classOptional = javaItems.stream().filter(javaItem -> javaItem.getType() == JavaItemType.CLASS
                        || javaItem.getType() == JavaItemType.INTERFACE  || javaItem.getType() == JavaItemType.ENUM)
                .filter(clazz -> clazz.getName().endsWith(simpleClassName)).findFirst();

        if (!classOptional.isPresent()) {
            if (!ignore) {
                throw new IllegalStateException("Not found " + simpleClassName);
            } else {
                System.err.println("Not found " + simpleClassName + "###################");
                return "Not found " + simpleClassName + "###################";
            }
        }

        JavaItem parentClass = classOptional.get();

        List<JavaItem> personMethods = javaItems.stream().filter(javaItem -> javaItem.getType() == JavaItemType.METHOD)
                .filter(method -> method.getParent().equals(parentClass)).collect(Collectors.toList());

        List<JavaItem> fields  = javaItems.stream().filter(javaItem -> javaItem.getType() == JavaItemType.FIELD)
                .filter(field -> field.getParent().equals(parentClass))
                .map(field -> ((FieldJavaItem)  field))
                .filter(field -> !field.isPrimitive())
                .collect(Collectors.toList());

        Optional<JavaItem> methodOpt = personMethods.stream().filter(m -> m.getSimpleName().equals(methodName)).findFirst();

        assertTrue(methodOpt.isPresent());

        JavaItem method = methodOpt.get();

        StringBuilder body = new StringBuilder();
        body.append("Parent class:\n").append(method.getParent().getDefinition()).append("\n");

        body.append("Parent Class Fields:\n");
        for (var field : fields) {
            body.append("\t").append(field.getDefinition()).append("\n");
        }
        body.append("Method Body:\n");
        body.append(method.getBody());

        File methodDir = new File(rootDir, method.getName().replace('.', '_'));
        methodDir.mkdirs();
        File promptDir = new File(methodDir, "prompts");
        File mermaidDir = new File(methodDir, "mermaid");

        promptDir.mkdirs();
        mermaidDir.mkdirs();

        final var gen = MethodMermaidSequenceGen.builder()
                .setModel("gpt-3.5-turbo-16k-0613")
                .setTemperature(0.0f)
                .setMaxTokens(2000)
                .setValidateJson(false).build();

        final var counter = new AtomicInteger();
        final var promptBuilder = new StringBuilder();
        final var responseBuilder = new StringBuilder();
        String mermaidCode = gen.generateSequenceFromMethod(
                body.toString(), method.getSimpleName(), method.getParent().getName(), "org.example.model", prompt -> {
                    promptBuilder.append(prompt);
                    counter.incrementAndGet();
                    File outputFile = new File(promptDir, "prompt_" + counter.get() + ".md");
                    FileUtils.writeFile(outputFile, prompt);
                    System.out.println("##########################################################################");
                    System.out.println(prompt);
                    System.out.println("##########################################################################");
                }, output -> {
                    File outputFile = new File(mermaidDir, "sequence_diagram" + counter.get() + ".md");
                    FileUtils.writeFile(outputFile, output);
                });

        File outputFile = new File(mermaidDir, "sequence_diagram_final.mmd");
        File outputFile2 = new File(rootDir, "mermaid/sequence_diagram_final_" + method.getName().replace('.', '_') + ".mmd");
        outputFile2.getParentFile().mkdirs();
        FileUtils.writeFile(outputFile, mermaidCode);
        FileUtils.writeFile(outputFile2, mermaidCode);

        try {
            Pattern titlePattern = Pattern.compile("^---\\s*title:\\s*[^-\\s][^\\n]*\\s*---");
            assertTrue(titlePattern.matcher(mermaidCode).find(),"The mermaidCode should have a title");
            Pattern sequenceDiagramPattern = Pattern.compile("sequenceDiagram");
            assertTrue(sequenceDiagramPattern.matcher(mermaidCode).find(), "The mermaidCode should have a sequenceDiagram");
            Pattern addressPattern = Pattern.compile("participant " + simpleClassName);
            assertTrue(addressPattern.matcher(mermaidCode).find(), "The mermaidCode should have a participant");
            validateMermaid(mermaidCode);
            System.out.println("COUNTER " + counter);
            assertTrue(counter.get() < count, "We did not retry more than");
        }catch (AssertionFailedError ex) {
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

    private static void validateMermaid(String mermaidCode) {

        File rootDir = new File("./temp" + System.currentTimeMillis());
        File output = new File(rootDir, "test.png");
        File input = new File(rootDir, "test.mmd");

        try {
            rootDir.mkdirs();
            FileUtils.writeFile(input, mermaidCode);
            Result result = MermaidUtils.runMmdc(input, output);
            assertEquals(0, result.getResult());
            assertTrue(output.exists());
        } finally {
            input.delete();
            output.delete();
            rootDir.delete();
        }
    }

}
