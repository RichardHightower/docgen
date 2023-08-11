package com.cloudurable.docgen.mermaid.validation.classes;

import com.cloudurable.docgen.mermaid.validation.RuleResult;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class InterfaceRuleTest {

    private InterfaceRule interfaceRule;

    @BeforeEach
    public void setup() {
        interfaceRule = new InterfaceRule();
    }

    @Test
    public void testValidInterfaceDeclaration() {
        String testInput = "interface MyInterface {";
        RuleResult result = interfaceRule.check(testInput, 1);

        assertEquals("InterfaceRule", result.getRuleName());
        assertEquals(1, result.getLineNumber());
        assertEquals(testInput, result.getViolatedLine());
        assertEquals("Instead of using 'interface MyInterface {', use 'class MyInterface {\n\t<<interface>>'.", result.getDescription());
    }

    @Test
    public void testInvalidInterfaceDeclaration() {
        String testInput = "class MyInterface {";
        RuleResult result = interfaceRule.check(testInput, 2);

        assertEquals(RuleResult.SUCCESS, result);
    }
}
