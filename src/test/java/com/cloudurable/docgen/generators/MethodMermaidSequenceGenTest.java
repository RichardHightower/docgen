package com.cloudurable.docgen.generators;

import com.cloudurable.docgen.Result;
import com.cloudurable.docgen.TestUtil;
import com.cloudurable.docgen.util.FileUtils;
import com.cloudurable.docgen.parser.model.JavaItem;
import com.cloudurable.docgen.parser.model.JavaItemType;
import com.cloudurable.docgen.util.MermaidUtils;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.List;
import java.util.Optional;
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


    public String testSequenceDiagram(String simpleClassName, String methodName) {


        List<JavaItem> javaItems = TestUtil.loadSimpleProject();

        Optional<JavaItem> classOptional = javaItems.stream().filter(javaItem -> javaItem.getType() == JavaItemType.CLASS)
                .filter(clazz -> clazz.getName().endsWith(simpleClassName)).findFirst();

        if (!classOptional.isPresent()) {
            throw new IllegalStateException("Not found " + simpleClassName);
        }

        JavaItem parentClass = classOptional.get();

        List<JavaItem> personMethods = javaItems.stream().filter(javaItem -> javaItem.getType() == JavaItemType.METHOD)
                .filter(method -> method.getParent().equals(parentClass)).collect(Collectors.toList());

        List<JavaItem> fields  = javaItems.stream().filter(javaItem -> javaItem.getType() == JavaItemType.FIELD)
                .filter(field -> field.getParent().equals(parentClass))
                //.filter(field -> Character.isLowerCase(field.getDefinition().charAt(0)))
                .collect(Collectors.toList());

        Optional<JavaItem> toStringOpt = personMethods.stream().filter(m -> m.getSimpleName().equals(methodName)).findFirst();

        assertTrue(toStringOpt.isPresent());

        JavaItem method = toStringOpt.get();

        StringBuilder body = new StringBuilder();
        body.append("Parent class: ").append(method.getParent().getDefinition()).append("\n");

        body.append("Parent Class Fields:\n");
        for (var field : fields) {
            body.append(field.getDefinition()).append("\n");
        }
        body.append("Method Body:\n");
        body.append(method.getBody());;

        final var gen = new MethodMermaidSequenceGen();
        final var counter = new AtomicInteger();
        final var builder = new StringBuilder();
        String mermaidCode = gen.generateSequenceFromMethod(
                body.toString(), method.getSimpleName(), method.getParent().getName(), "org.example.model", s -> {
                    builder.append(s);
                    counter.incrementAndGet();
                    System.out.println(s);
                });

        System.out.println(builder);
        System.out.println(mermaidCode);



        Pattern titlePattern = Pattern.compile("^---\\s*title:\\s*[^-\\s][^\\n]*\\s*---");
        assertTrue(titlePattern.matcher(mermaidCode).find(),"The mermaidCode should have a title");


        // Validate that the mermaidCode has a sequenceDiagram
        Pattern sequenceDiagramPattern = Pattern.compile("sequenceDiagram");
        assertTrue(sequenceDiagramPattern.matcher(mermaidCode).find(), "The mermaidCode should have a sequenceDiagram");

        Pattern addressPattern = Pattern.compile("participant " + simpleClassName);
        assertTrue(addressPattern.matcher(mermaidCode).find(), "The mermaidCode should have a participant");

        System.out.println("COUNTER " + counter);

        assertTrue(counter.get() < 4, "We did not retry more than twice");

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


        return mermaidCode;
    }

}
