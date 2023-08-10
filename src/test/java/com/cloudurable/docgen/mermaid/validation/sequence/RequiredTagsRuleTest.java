package com.cloudurable.docgen.mermaid.validation.sequence;

import com.cloudurable.docgen.mermaid.validation.RuleResult;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RequiredTagsRuleTest {

    private RequiredTagsRule rule;

    @BeforeEach
    public void setUp() {
        rule = new RequiredTagsRule();
    }

    // ... Other tests ...

    @Test
    public void testAltWithoutElse() {
        String content = "sequenceDiagram\n" +
                "alt some condition\n" +
                "SomeAction\n" +
                "end";

        RuleResult result = rule.check(content);
        assertEquals("Required Tags", result.getRuleName());
        assertEquals("for `alt` must have at least one `else`", result.getDescription());
    }

    @Test
    public void testCriticalWithoutOption() {
        String content = "sequenceDiagram\n" +
                "critical\n" +
                "SomeAction\n" +
                "end";

        RuleResult result = rule.check(content);
        assertEquals("Required Tags", result.getRuleName());
        assertEquals("for `critical` must have at least one `option`", result.getDescription());
    }

    @Test
    public void testProvidedContent() {
        String content = "---\n" +
                "title: Rerun Images\n" +
                "---\n" +
                "sequenceDiagram\n" +
                "participant RerunImages\n" +
                "participant System\n" +
                "participant Arrays\n" +
                "participant Collectors\n" +
                "participant imagesOutputDir\n" +
                "participant Collections\n" +
                "RerunImages->>imagesOutputDir: Ensure directories exist\n" +
                "alt mermaidFileArray is null\n" +
                "   RerunImages-->>System: Print \"No mermaid files found\" and exit program\n" +
                "else mermaidFileArray is not null\n" +
                "   RerunImages-->>System: Print total number of mermaid files\n" +
                "   RerunImages->>Arrays: Extract mermaid files\n" +
                "   RerunImages->>Arrays: Get file names\n" +
                "   RerunImages->>Arrays: Remove file extensions\n" +
                "   RerunImages->>Collections: Create set of mermaid files\n" +
                "       alt imageFileArray is null\n" +
                "           RerunImages-->>Collections: Create empty set\n" +
                "       else imageFileArray is not null\n" +
                "           RerunImages->>Arrays: Extract image files\n" +
                "           RerunImages->>Arrays: Get file names\n" +
                "           RerunImages->>Arrays: Remove file extensions\n" +
                "           RerunImages->>Collections: Create set of image files\n" +
                "       end\n"+
                "end\n" +
                "alt size of imageFiles is not equal to size of mermaidFiles\n" +
                "   RerunImages-->>System: Print unequal sizes\n" +
                "   RerunImages->>mermaidFiles: Remove image files from mermaid files\n" +
                "else this condition" +
                "   RerunImages-->>System: Print missing image files\n" +
                "   RerunImages-->>File: Rerun missing image files\n" +
                "end";

        RuleResult result = rule.check(content);
        assertEquals(RuleResult.SUCCESS, result);
    }


    @Test
    public void testProvidedContentBad() {
        String content = "---\n" +
                "title: Rerun Images\n" +
                "---\n" +
                "sequenceDiagram\n" +
                "participant RerunImages\n" +
                "participant System\n" +
                "participant Arrays\n" +
                "participant Collectors\n" +
                "participant imagesOutputDir\n" +
                "participant Collections\n" +
                "RerunImages->>imagesOutputDir: Ensure directories exist\n" +
                "alt mermaidFileArray is null\n" +
                "   RerunImages-->>System: Print \"No mermaid files found\" and exit program\n" +
                "else mermaidFileArray is not null\n" +
                "   RerunImages-->>System: Print total number of mermaid files\n" +
                "   RerunImages->>Arrays: Extract mermaid files\n" +
                "   RerunImages->>Arrays: Get file names\n" +
                "   RerunImages->>Arrays: Remove file extensions\n" +
                "   RerunImages->>Collections: Create set of mermaid files\n" +
                "       alt imageFileArray is null\n" +
                "           RerunImages-->>Collections: Create empty set\n" +
                "       else imageFileArray is not null\n" +
                "           RerunImages->>Arrays: Extract image files\n" +
                "           RerunImages->>Arrays: Get file names\n" +
                "           RerunImages->>Arrays: Remove file extensions\n" +
                "           RerunImages->>Collections: Create set of image files\n" +
                "       end\n"+
                "end\n" +
                "alt size of imageFiles is not equal to size of mermaidFiles\n" +
                "   RerunImages-->>System: Print unequal sizes\n" +
                "   RerunImages->>mermaidFiles: Remove image files from mermaid files\n" +
                "end this condition" +
                "   RerunImages-->>System: Print missing image files\n" +
                "   RerunImages-->>File: Rerun missing image files\n" +
                "end";

        RuleResult result = rule.check(content);
        assertEquals("for `alt` must have at least one `else`", result.getDescription());
        assertEquals("Required Tags", result.getRuleName());
    }

}
