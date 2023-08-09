package com.cloudurable.docgen.mermaid.validation;

import static org.junit.jupiter.api.Assertions.*;


import org.junit.jupiter.api.Test;

class RuleResultTest {

    @Test
    void testBuilder() {
        RuleResult ruleResult = RuleResult.builder()
                .lineNumber(10)
                .violatedLine("Line violated")
                .ruleName("Rule 1")
                .description("Violation Description")
                .build();

        assertEquals(10, ruleResult.getLineNumber());
        assertEquals("Line violated", ruleResult.getViolatedLine());
        assertEquals("Rule 1", ruleResult.getRuleName());
        assertEquals("Violation Description", ruleResult.getDescription());
    }

    @Test
    void testSerialize() {
        RuleResult ruleResult = RuleResult.builder()
                .lineNumber(10)
                .violatedLine("Line violated")
                .ruleName("Rule 1")
                .description("Violation Description")
                .build();

        String expectedJson = "{\n" +
                "    \"lineNumber\": 10\n" +
                ",\n" +
                "    \"violatedLine\": \"Line violated\"\n" +
                ",\n" +
                "    \"ruleName\": \"Rule 1\"\n" +
                ",\n" +
                "    \"description\": \"Violation Description\"\n" +
                "\n" +
                "}";
        assertEquals(expectedJson, ruleResult.serialize());
    }
}
