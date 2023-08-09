package com.cloudurable.docgen.util;

import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

class FileUtilsTest {

    @Test
    public void readFile() {
        try {
            FileUtils.readFile(new File("./DON'TEXISTFILE1q2w3e"));
            fail("Should not get here");
        } catch (Exception e) {
            assertTrue(true, "Threw Exception");
        }
    }

    @Test
    public void writeFile() {
        try {
            FileUtils.writeFile(new File("./foo/bar/data/DON'TEXISTFILE1q2w3e"), "crap");
            fail("Should not get here");
        } catch (Exception e) {
            assertTrue(true, "Threw Exception");
        }
    }

    @Test
    public void readWrite() {
        final var testString = "1q21w3e4r5t6y7u8i9o0p";
        final var file = new File(".build/" + System.currentTimeMillis() + "/test.txt");
        file.getParentFile().mkdirs();
        FileUtils.writeFile(file, testString);
        String contents = FileUtils.readFile(file);
        assertEquals(testString, contents);
    }

}
