package com.cloudurable.docgen.mermaid.validation.classes;

import com.cloudurable.docgen.mermaid.validation.RuleResult;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class NoInlineInheritanceRuleTest {

    private NoInlineInheritanceRule rule;

    @BeforeEach
    public void setUp() {
        rule = new NoInlineInheritanceRule();
    }

    @Test
    public void testClassWithInlineInheritance() {
        String line = "class Employee extends Person {\n" +
                "newHire: boolean" +
                "\n}";
        RuleResult result = rule.check(line, 1);

        assertEquals("NoInlineInheritanceRule", result.getRuleName());
        assertEquals(1, result.getLineNumber());
        assertEquals(line, result.getViolatedLine());
        assertTrue(result.getDescription().contains("Instead of using inline inheritance"));
    }

    @Test
    public void testClassWithoutInlineInheritance() {
        String line = "class Employee {\n" +
                "newHire: boolean" +
                "\n}";
        RuleResult result = rule.check(line, 1);

        // Expecting success because no inline inheritance
        assertEquals(RuleResult.SUCCESS, result);
    }

    @Test
    public void testNonClassLine() {
        String line = "Employee —|> Person";
        RuleResult result = rule.check(line, 1);

        // Expecting success because it's not a class declaration with inline inheritance
        assertEquals(RuleResult.SUCCESS, result);
    }
}
