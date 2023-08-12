package com.cloudurable.docgen.mermaid.validation.classes;

import com.cloudurable.docgen.mermaid.validation.RuleResult;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ClassBracketsRuleTest {

    private ClassBracketsRule rule;

    @BeforeEach
    public void setUp() {
        rule = new ClassBracketsRule();
    }

    @Test
    public void testClassWithBracketsOnSameLine() {
        String line = "class WorkflowService {}";
        RuleResult result = rule.check(line, 1);

        assertEquals("ClassBracketsRule", result.getRuleName());
        assertEquals(1, result.getLineNumber());
        assertEquals(line, result.getViolatedLine());
        assertTrue(result.getDescription().contains("should not be on the same line"));
    }

    @Test
    public void testClassWithBracketsOnSeparateLines() {
        String line = "class WorkflowService {";
        RuleResult result = rule.check(line, 1);

        // Expecting success because brackets are on separate lines
        assertEquals(RuleResult.SUCCESS, result);
    }

    @Test
    public void testNonClassLine() {
        String line = "This is not a class declaration.";
        RuleResult result = rule.check(line, 1);

        // Expecting success because it's not a class declaration
        assertEquals(RuleResult.SUCCESS, result);
    }
}
