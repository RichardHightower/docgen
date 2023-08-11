package com.cloudurable.docgen;

import com.cloudurable.docgen.parser.model.JavaItem;
import com.cloudurable.docgen.parser.model.JavaItemType;
import com.cloudurable.docgen.parser.util.ClassVisitorParser;
import com.cloudurable.docgen.util.FileUtils;
import com.cloudurable.docgen.util.MermaidUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.cloudurable.docgen.parser.model.JavaItemType.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestUtil {

    public static List<JavaItem> loadSimpleProject() {
        final var parser = new ClassVisitorParser();
        List<JavaItem> javaItems = null;
        try {
            javaItems = parser.scanDirectory(new File("test/simple/simple/src/main"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return javaItems;
    }

    public static List<JavaItem> loadProject(String path) {
        final var parser = new ClassVisitorParser();
        List<JavaItem> javaItems = null;
        try {
            javaItems = parser.scanDirectory(new File(path));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return javaItems;
    }

    public static Map<String, String> mapPackageToClassDefs(List<JavaItem> javaItems) {
        final var inputMap = doMapPackageToClassDefs(javaItems);
        return inputMap.entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        entry -> String.join("\n\n---\n\n", entry.getValue())
                ));
    }

    private static Map<String, List<String>> doMapPackageToClassDefs(List<JavaItem> javaItems) {
        Map<String, List<String>> classBasicDefByPackageName = new HashMap<>();
        javaItems.stream()
                .filter(javaItem -> javaItem.getType() == CLASS ||
                        javaItem.getType() == INTERFACE || javaItem.getType() == ENUM)
                .filter(javaItem -> javaItem.getParent() == null)
                .filter(javaItem -> !javaItem.getSimpleName().endsWith("Test"))
                .filter(javaItem -> !javaItem.getSimpleName().endsWith("Tests"))
                .filter(javaItem -> !javaItem.getSimpleName().endsWith("TestBase"))
                .forEach(

                        classItem -> {
                            String fullyQualifiedName = classItem.getName();
                            String packageName = getPackageName(fullyQualifiedName);
                            List<String> classDefs = classBasicDefByPackageName.getOrDefault(packageName, new ArrayList<>());

                            classDefs.add(classItem.getDefinition() + "\n\n"
                                    + fieldsFromClass(classItem, javaItems) //+ "\n\n"
                                    //+ methodsFromClass(classItem, javaItems)
                            );

                            classBasicDefByPackageName.put(packageName, classDefs);
                        }
                );
        return classBasicDefByPackageName;
    }

    public static String getPackageName(String fullyQualifiedName) {
        int lastDotIndex = fullyQualifiedName.lastIndexOf('.');
        return fullyQualifiedName.substring(0, lastDotIndex);
    }

    public static String fieldsFromClass(final JavaItem classItem, List<JavaItem> javaItems) {
        return componentFromClass(classItem, javaItems, FIELD);
    }
    public static String methodsFromClass(final JavaItem classItem, List<JavaItem> javaItems) {
        return componentFromClass(classItem, javaItems, METHOD);
    }

    public static String componentFromClass(final JavaItem classItem, List<JavaItem> javaItems, JavaItemType type) {
        return  "\n" + type.toString().toLowerCase()  +"s:\n\t"

                + String.join("\n\t", javaItems.stream().filter(field -> field.getType() == type)
                .filter(field -> field.getParent() == classItem)

                .map(field -> {
                    String definition = field.getDefinition();
                    int index = definition.indexOf('=');
                    if (index == -1) {
                        return definition;
                    } else {
                        return definition.substring(0, index);
                    }
                }).collect(Collectors.toList()).toArray(new String[0]));
    }


    public static void validateMermaid(String mermaidCode) {

        File rootDir = new File("./temp" + System.currentTimeMillis());
        File output = new File(rootDir, "/test.png");
        File input = new File(rootDir, "/test.mmd");

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
