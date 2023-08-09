package com.cloudurable.docgen.mermaid.validation.sequence;

import static org.junit.jupiter.api.Assertions.*;


import com.cloudurable.docgen.mermaid.validation.LineRule;
import com.cloudurable.docgen.mermaid.validation.RuleResult;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DataClassesAndPrimitiveRuleTest {
    private LineRule rule;
    private int lineNumber;

    @BeforeEach
    void setUp() {
        rule = new DataClassesAndPrimitiveRule();
        lineNumber = 1;
    }

    @Test
    void testCheckReturnsSuccessWhenLineDoesNotContainRestrictedWords() {
        String line = "participant \"Customer\"";
        RuleResult result = rule.check(line, lineNumber);
        assertEquals(RuleResult.SUCCESS, result);
    }

    @Test
    void testCheckReturnsFailureWhenLineContainsRestrictedWords() {
        String line = "participant \"Customer\" as int";
        RuleResult result = rule.check(line, lineNumber);
        assertNotEquals(RuleResult.SUCCESS, result);
    }

    @Test
    void testCheckReturnsFailureWhenLineContainsDataClass() {
        String line = "participant String";
        RuleResult result = rule.check(line, lineNumber);
        assertNotEquals(RuleResult.SUCCESS, result);
    }

    @Test
    void makeSureListIsOkIfPartOfAnotherWord() {
        String line = "participant \"Customer\" as CustomerList";
        RuleResult result = rule.check(line, lineNumber);
        assertEquals(RuleResult.SUCCESS, result);
    }

    @Test
    void testCheckReturnsFailureWhenLineContainsSquareBrackets() {
        String line = "participant \"Customer\" as []";
        RuleResult result = rule.check(line, lineNumber);
        assertNotEquals(RuleResult.SUCCESS, result);
    }

    @Test
    void testCheckReturnsSuccessWhenLineContainsRestrictedWordsCaseInsensitive() {
        String line = "participant \"Customer\" as Byte";
        RuleResult result = rule.check(line, lineNumber);
        assertNotEquals(RuleResult.SUCCESS, result);
    }

    @Test
    void noBrackets() {
        String line = "participant Foo[]";
        RuleResult result = rule.check(line, lineNumber);
        assertNotEquals(RuleResult.SUCCESS, result);
    }

    @Test
    void bracketsOkInString() {
        String line = "foo-->bar: \"Foo[]\"";
        RuleResult result = rule.check(line, lineNumber);
        assertEquals(RuleResult.SUCCESS, result);
    }
}
