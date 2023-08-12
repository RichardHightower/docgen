package com.cloudurable.docgen.parser.util;

import com.cloudurable.docgen.parser.model.JavaItem;
import com.cloudurable.docgen.parser.model.JavaItemType;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class ClassVisitorParserTest {

    @Test
    void run() throws IOException {

        final var parser = new ClassVisitorParser();
        List<JavaItem> javaItems = parser.scanDirectory(new File("test/simple/simple/src/main"));
        Set<String> classes = javaItems.stream().filter(item -> item.getType() == JavaItemType.CLASS).map(item -> item.getName()).collect(Collectors.toSet());

        System.out.println(classes);
        assertTrue(classes.contains("org.example.model.Customer"));
        assertTrue(classes.size() > 10);

    }
}
