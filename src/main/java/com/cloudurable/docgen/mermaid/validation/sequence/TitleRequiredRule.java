package com.cloudurable.docgen.mermaid.validation.sequence;

import com.cloudurable.docgen.mermaid.validation.ContentRule;
import com.cloudurable.docgen.mermaid.validation.RuleResult;

import java.util.regex.Pattern;

public class TitleRequiredRule implements ContentRule {

    Pattern TITLE_PATTERN = Pattern.compile("^---\\s*title:\\s*[^-\\s][^\\n]*\\s*---");

    //            Pattern titlePattern = Pattern.compile("^---\\s*title:\\s*[^-\\s][^\\n]*\\s*---");
    //            assertTrue(titlePattern.matcher(mermaidCode).find(),"The mermaidCode should have a title");
    @Override
    public RuleResult check(String content) {
        if (!TITLE_PATTERN.matcher(content).find()) {
            return RuleResult.builder().ruleName("Require Title").violatedLine("").lineNumber(0)
                    .violatedLine("").description("Must have a title, e.g., \n```---\n" +
                            "title: Generating toString\n" +
                            "---\n" +
                            "sequenceDiagram\n```\n" ).build();
        } else {
            return RuleResult.SUCCESS;
        }
    }
}
