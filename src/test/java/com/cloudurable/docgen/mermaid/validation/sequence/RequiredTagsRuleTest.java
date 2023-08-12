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
        assertEquals(RequiredTagsRule.ALT_TAG_RULES, result.getDescription());
    }

    @Test
    public void testCriticalWithoutOption() {
        String content = "sequenceDiagram\n" +
                "critical\n" +
                "SomeAction\n" +
                "end";

        RuleResult result = rule.check(content);
        assertEquals("Required Tags", result.getRuleName());
        assertEquals(RequiredTagsRule.CRITICAL_TAG_RULES, result.getDescription());
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
        assertEquals(RequiredTagsRule.ALT_TAG_RULES, result.getDescription());
        assertEquals("Required Tags", result.getRuleName());
    }


    @Test
    public void testBugOptWithElse() {
        final var content = "---\n" +
                "title: Run Image Generation Check\n" +
                "---\n" +
                "\n" +
                "sequenceDiagram\n" +
                "    participant RerunImages\n" +
                "    participant Directory\n" +
                "    participant Console\n" +
                "    participant System\n" +
                "    participant Set\n" +
                "    participant MermaidFile\n" +
                "    participant ImageFile\n" +
                "    \n" +
                "    RerunImages->>Directory: Create \"mermaid\" directory\n" +
                "    RerunImages->>Directory: Create \"images\" directory\n" +
                "    Directory->>Directory: List files in \"images\" directory\n" +
                "    Directory->>Directory: List files in \"mermaid\" directory\n" +
                "    opt mermaidFileArray is null\n" +
                "        Directory->>Console: Print \"No mermaid files yet\"\n" +
                "        Console->>System: Exit with status 2\n" +
                "    else mermaidFileArray is not null\n" +
                "        System->>Console: Print \"Total mermaid files\"\n" +
                "        Directory->>Set: Extract file names\n" +
                "        opt imageFileArray is null\n" +
                "            Set->>Set: Create empty imageFiles set\n" +
                "        else imageFileArray is not null\n" +
                "            Directory->>Set: Extract file names\n" +
                "        end\n" +
                "    end\n" +
                "    opt imageFiles size is not equal to mermaidFiles size\n" +
                "        System->>Console: Print \"Total mermaid files\"\n" +
                "        System->>Console: Print \"Sizes not equal\"\n" +
                "        Set->>Set: Remove common files from mermaidFiles\n" +
                "        System->>Console: Print \"Missing images\"\n" +
                "        Set->>Console: Print missing image file names\n" +
                "        Set->>Console: Print \"Re-running files\"\n" +
                "        Set->>MermaidFile: Retrieve the corresponding mermaid file\n" +
                "        Set->>ImageFile: Retrieve the corresponding image file\n" +
                "        ImageFile->>RerunImages: Re-run the mermaid file and save as the image file\n" +
                "    end";

        RuleResult result = rule.check(content);
        assertEquals(RequiredTagsRule.OPT_ELSE_RULE, result.getDescription());
        assertEquals("Required Tags", result.getRuleName());


    }

    @Test
    public void testHasElseButNoEnd() {
        var contents = "---\n" +
                "title: parseFile\n" +
                "---\n" +
                "\n" +
                "sequenceDiagram\n" +
                "    participant ClassVisitorParser\n" +
                "    participant File\n" +
                "    participant Exception\n" +
                "    participant CompilationUnit\n" +
                "\n" +
                "    ClassVisitorParser->>File: run()\n" +
                "    alt Exception is thrown\n" +
                "        File->>ClassVisitorParser: throw Exception\n" +
                "        ClassVisitorParser->>Exception: printStacktrace()\n" +
                "        ClassVisitorParser->>ClassVisitorParser: Handle Exception\n" +
                "        ClassVisitorParser->>CompilationUnit: Indicate Error\n" +
                "        CompilationUnit->>CompilationUnit: Handle Error\n" +
                "        CompilationUnit->>ClassVisitorParser: Report Error\n" +
                "        ClassVisitorParser->>ClassVisitorParser: Cleanup\n" +
                "        ClassVisitorParser->>File: Indicate Failed Parsing\n" +
                "    else Exception is not thrown\n" +
                "        File-->>ClassVisitorParser: Return Result\n" +
                "        ClassVisitorParser->>ClassVisitorParser: Handle Result\n" +
                "        ClassVisitorParser->>CompilationUnit: Process CompilationUnit\n" +
                "        CompilationUnit->>ClassVisitorParser: Return Status\n" +
                "        ClassVisitorParser->>ClassVisitorParser: Indicate Success\n" +
                "\n";

        RuleResult result = rule.check(contents);
        assertEquals("Required Tags", result.getRuleName());

        assertEquals("`alt` without corresponding `end`", result.getDescription());


    }



    @Test
    public void testCriticalHasElse() {
        var contents = "---\n" +
                "title: parseFile\n" +
                "---\n" +
                "\n" +
                "sequenceDiagram\n" +
                "    participant ClassVisitorParser\n" +
                "    participant File\n" +
                "    participant Exception\n" +
                "    participant CompilationUnit\n" +
                "\n" +
                "    ClassVisitorParser->>File: run()\n" +
                "    critical Exception is thrown\n" +
                "        File->>ClassVisitorParser: throw Exception\n" +
                "        ClassVisitorParser->>Exception: printStacktrace()\n" +
                "        ClassVisitorParser->>ClassVisitorParser: Handle Exception\n" +
                "        ClassVisitorParser->>CompilationUnit: Indicate Error\n" +
                "        CompilationUnit->>CompilationUnit: Handle Error\n" +
                "        CompilationUnit->>ClassVisitorParser: Report Error\n" +
                "        ClassVisitorParser->>ClassVisitorParser: Cleanup\n" +
                "        ClassVisitorParser->>File: Indicate Failed Parsing\n" +
                "    else Exception is not thrown\n" +
                "        File-->>ClassVisitorParser: Return Result\n" +
                "        ClassVisitorParser->>ClassVisitorParser: Handle Result\n" +
                "        ClassVisitorParser->>CompilationUnit: Process CompilationUnit\n" +
                "        CompilationUnit->>ClassVisitorParser: Return Status\n" +
                "        ClassVisitorParser->>ClassVisitorParser: Indicate Success\n" +
                "\n";

        RuleResult result = rule.check(contents);
        assertEquals("Required Tags", result.getRuleName());

        assertEquals(RequiredTagsRule.CRITICAL_ELSE_RULE, result.getDescription());


    }


    @Test
    public void testAltHasOption() {
        var contents = "---\n" +
                "title: parseFile\n" +
                "---\n" +
                "\n" +
                "sequenceDiagram\n" +
                "    participant ClassVisitorParser\n" +
                "    participant File\n" +
                "    participant Exception\n" +
                "    participant CompilationUnit\n" +
                "\n" +
                "    ClassVisitorParser->>File: run()\n" +
                "    alt Exception is thrown\n" +
                "        File->>ClassVisitorParser: throw Exception\n" +
                "        ClassVisitorParser->>Exception: printStacktrace()\n" +
                "        ClassVisitorParser->>ClassVisitorParser: Handle Exception\n" +
                "        ClassVisitorParser->>CompilationUnit: Indicate Error\n" +
                "        CompilationUnit->>CompilationUnit: Handle Error\n" +
                "        CompilationUnit->>ClassVisitorParser: Report Error\n" +
                "        ClassVisitorParser->>ClassVisitorParser: Cleanup\n" +
                "        ClassVisitorParser->>File: Indicate Failed Parsing\n" +
                "    option Exception is not thrown\n" +
                "        File-->>ClassVisitorParser: Return Result\n" +
                "        ClassVisitorParser->>ClassVisitorParser: Handle Result\n" +
                "        ClassVisitorParser->>CompilationUnit: Process CompilationUnit\n" +
                "        CompilationUnit->>ClassVisitorParser: Return Status\n" +
                "        ClassVisitorParser->>ClassVisitorParser: Indicate Success\n" +
                "\n";

        RuleResult result = rule.check(contents);
        assertEquals("Required Tags", result.getRuleName());

        assertEquals(RequiredTagsRule.ALT_OPTION_RULE, result.getDescription());


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
