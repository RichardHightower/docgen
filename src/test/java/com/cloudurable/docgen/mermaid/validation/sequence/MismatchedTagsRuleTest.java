package com.cloudurable.docgen.mermaid.validation.sequence;



import com.cloudurable.docgen.mermaid.validation.RuleResult;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MismatchedTagsRuleTest {

    private MismatchedTagsRule rule;

    @BeforeEach
    public void setUp() {
        rule = new MismatchedTagsRule();
    }

    @Test
    public void testValidContent() {
        String content = "sequenceDiagram\n" +
                "alt some condition\n" +
                "SomeAction\n" +
                "end";

        RuleResult result = rule.check(content);
        assertEquals(RuleResult.SUCCESS, result);
    }

    @Test
    public void testMissingEnd() {
        String content = "sequenceDiagram\n" +
                "alt some condition\n" +
                "SomeAction\n";

        RuleResult result = rule.check(content);
        assertEquals("Mismatched Tags", result.getRuleName());
        assertEquals("alt without corresponding end", result.getDescription());
    }

    @Test
    public void testUnexpectedEnd() {
        String content = "sequenceDiagram\n" +
                "end";

        RuleResult result = rule.check(content);
        assertEquals("Mismatched Tags", result.getRuleName());
        assertEquals("end without opening tag", result.getDescription());
    }

    @Test
    public void testNestedEnds() {
        String content = "sequenceDiagram\n" +
                "alt some condition\n" +
                "SomeAction\n" +
                "end\n" +
                "end";

        RuleResult result = rule.check(content);
        assertEquals("Mismatched Tags", result.getRuleName());
        assertEquals("end without opening tag", result.getDescription());
    }

    @Test
    public void testMissingEndInLoop() {
        String content = "sequenceDiagram\n" +
                "loop EveryMinute\n" +
                "CheckSomething\n";

        RuleResult result = rule.check(content);
        assertEquals("Mismatched Tags", result.getRuleName());
        assertEquals("loop without corresponding end", result.getDescription());
    }

    @Test
    public void testDeeplyNestedStructure() {
        String content = "sequenceDiagram\n" +
                "alt SomeCondition\n" +
                "loop EveryMinute\n" +
                "critical\n" +
                "SomeAction\n" +
                "end\n" + // End of critical
                "end\n" + // End of loop
                "else\n" +
                "AnotherAction\n" +
                "end"; // End of alt

        RuleResult result = rule.check(content);
        assertEquals(RuleResult.SUCCESS, result);
    }

    @Test
    public void testMismatchInDeeplyNestedStructure() {
        String content = "sequenceDiagram\n" +
                "alt SomeCondition\n" +
                "loop EveryMinute\n" +
                "critical\n" +
                "SomeAction\n" +
                "end\n" + // End of critical
                "end\n" + // Missing end for loop
                "else\n" +
                "AnotherAction\n"; // End of alt

        RuleResult result = rule.check(content);
        assertEquals("Mismatched Tags", result.getRuleName());
        assertEquals("alt without corresponding end", result.getDescription());
    }

    @Test
    public void testCase() {
        String content = "---\n" +
                "title: Remove Extension in RerunImages\n" +
                "---\n" +
                "\n" +
                "sequenceDiagram\n" +
                "    participant RerunImages\n" +
                "\n" +
                "    RerunImages->>RerunImages: Call removeExt method\n" +
                "    alt Check if \".\" exists in name\n" +
                "        RerunImages->>RerunImages: Get index of \".\"\n" +
                "        alt If \".\" exists\n" +
                "            RerunImages->>RerunImages: Get substring from 0 to index\n" +
                "        else If \".\" does not exist\n" +
                "            RerunImages-->>RerunImages: Return name\n" +
                "        end\n" +
                "    RerunImages-->>RerunImages: Return result"; // End of alt

        RuleResult result = rule.check(content);
        assertEquals("Mismatched Tags", result.getRuleName());
        assertEquals("alt without corresponding end", result.getDescription());
    }

    @Test
    public void testCaseFixed() {
        String content = "---\n" +
                "title: Remove Extension in RerunImages\n" +
                "---\n" +
                "\n" +
                "sequenceDiagram\n" +
                "    participant RerunImages\n" +
                "\n" +
                "    RerunImages->>RerunImages: Call removeExt method\n" +
                "    alt Check if \".\" exists in name\n" +
                "        RerunImages->>RerunImages: Get index of \".\"\n" +
                "        alt If \".\" exists\n" +
                "            RerunImages->>RerunImages: Get substring from 0 to index\n" +
                "        else If \".\" does not exist\n" +
                "            RerunImages-->>RerunImages: Return name\n" +
                "        end\n" +
                "    end\n" +
                "    RerunImages-->>RerunImages: Return result"; // End of alt

        RuleResult result = rule.check(content);
        assertEquals("pass", result.getRuleName());
    }

    @Test
    public void test() {
        final var content = "---\n" +
                "title: Generating toString\n" +
                "---\n" +
                "sequenceDiagram\n" +
                "\n" +
                "\n" +
                "    participant Department\n" +
                "    participant StringBuilder\n" +
                "    \n" +
                "    Department->>StringBuilder: Append Department Name\n" +
                "    Department->>StringBuilder: Append Manager\n" +
                "    Department->>StringBuilder: Append Employees\n" +
                "    loop Append Employees\n" +
                "        Department->>StringBuilder: Append Employee\n" +
                "    end\n" +
                "    Department->>StringBuilder: Convert to String";

        RuleResult result = rule.check(content);
        assertEquals(RuleResult.SUCCESS, result);

    }
}
