package com.cloudurable.docgen;

import com.cloudurable.docgen.parser.model.JavaItem;
import com.cloudurable.docgen.parser.util.ClassVisitorParser;

import java.io.File;
import java.io.IOException;
import java.util.List;

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
}
