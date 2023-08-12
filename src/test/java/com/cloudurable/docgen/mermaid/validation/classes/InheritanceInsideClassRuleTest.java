package com.cloudurable.docgen.mermaid.validation.classes;

import com.cloudurable.docgen.mermaid.validation.RuleResult;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class InheritanceInsideClassRuleTest {

    private InheritanceInsideClassRule rule;

    @BeforeEach
    public void setUp() {
        rule = new InheritanceInsideClassRule();
    }

    @Test
    public void testClassWithInheritanceInside() {
        String content = "class Customer {\n" +
                "<< extends Person >>" +
                "\nboolean elite " +
                "\nString foo\n" +
                "}";
        RuleResult result = rule.check(content);

        assertEquals("InheritanceInsideClassRule", result.getRuleName());
        assertTrue(result.getDescription().contains("Instead of embedding the inheritance inside the class"));
    }


    @Test
    public void testWithMoreAnnotations() {
        String content = "class Customer {\n" +
                " << interface >> \n" +
                " << Service >> \n" +
                "<< extends Person >>\n" +
                " << Json >> \n" +
                "boolean elite\n" +
                "String foo\n" +
                "}";
        RuleResult result = rule.check(content);

        assertEquals("InheritanceInsideClassRule", result.getRuleName());
        assertTrue(result.getDescription().contains("Instead of embedding the inheritance inside the class"));
    }

    @Test
    public void testClassWithoutInheritanceInside() {
        String content = "class Customer { boolean elite }";
        RuleResult result = rule.check(content);

        // Expecting success because no embedded inheritance
        assertEquals(RuleResult.SUCCESS, result);
    }
}
