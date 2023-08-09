package com.cloudurable.docgen.util;

import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

class TokenCounterUtilsTest {

    @Test
    public void testSequence() {
        String s = FileUtils.readFile(new File("./src/main/templates/methods/system.md"));
        System.out.println("System    \t\t" + TokenCounterUtils.countTokens(s));
        System.out.println("Fix       \t\t" + TokenCounterUtils.countTokens(
                FileUtils.readFile(new File("./src/main/templates/methods/fix.md"))));
        System.out.println("Instruct\t\t" + TokenCounterUtils.countTokens(
                FileUtils.readFile(new File("./src/main/templates/methods/instruct.md"))));
        System.out.println("Example \t\t" + TokenCounterUtils.countTokens(
                FileUtils.readFile(new File("./src/main/templates/methods/example.md"))));

        System.out.println(TokenCounterUtils.countTokens(s));

        assertTrue(TokenCounterUtils.countTokens(s) > 500);

    }


    @Test
    public void testClass() {
        String s = FileUtils.readFile(new File("./src/main/templates/classes/system.md"));
        System.out.println("System    \t\t" + TokenCounterUtils.countTokens(s));
        System.out.println("Fix       \t\t" + TokenCounterUtils.countTokens(
                FileUtils.readFile(new File("./src/main/templates/classes/fix.md"))));
        System.out.println("Instruct\t\t" + TokenCounterUtils.countTokens(
                FileUtils.readFile(new File("./src/main/templates/classes/instruct.md"))));
        System.out.println("Example \t\t" + TokenCounterUtils.countTokens(
                FileUtils.readFile(new File("./src/main/templates/classes/examples.md"))));

        System.out.println(TokenCounterUtils.countTokens(s));

        assertTrue(TokenCounterUtils.countTokens(s) > 500);

    }
}
