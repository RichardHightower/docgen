package com.cloudurable.docgen.extract;

import com.cloudurable.docgen.util.FileUtils;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

class MarkdownCreatorTest {

    @Test
    public void run() {

        final File outputFile = new File("./test/simple/simple.md");
        outputFile.delete();

        final var creator =  MarkdownCreator.builder().setOutputFile(outputFile)
                .setRootDir(new File("./test/simple/simple/")).build();

        creator.run();

        assertTrue(outputFile.exists(), "Output File Should exist");
        assertTrue(FileUtils.readFile(outputFile).length() > 1000, "Output File Should exist");

    }

}
