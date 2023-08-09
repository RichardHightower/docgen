package com.cloudurable.docgen.extract;

import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

class ProjectCreatorTest {

    @Test
    void createProject() {

        final File markdownFile = new File("./test/simple/simple.md");
        final File outputDir = new File("./build/markdown/gen/" + System.currentTimeMillis() + "/");
        if (!markdownFile.exists()) {
            MarkdownCreator.builder().setOutputFile(markdownFile)
                    .setRootDir(new File("./test/simple/simple/")).build().run();
        }

        ProjectCreator.builder().setOutputDir(outputDir).setInputFile(markdownFile).build().createProject();


        File addressFile = new File(outputDir, "test/simple/simple/src/main/java/org/example/model/Address.java");
        assertTrue(addressFile.exists());
    }
}
