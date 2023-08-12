package com.cloudurable.docgen.mermaid.validation.sequence;


import com.cloudurable.docgen.mermaid.validation.LineRule;
import com.cloudurable.docgen.mermaid.validation.RuleResult;

import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class SystemOutRule implements LineRule {
    private static final Pattern PATTERN = Pattern.compile("System\\.out");
    public static final  String RULE_NAME = "System Out Rule";
    public static final String RULE_DESCRIPTION = "Avoid using System.out in your mermaid code use Console.";

    @Override
    public RuleResult check(String line, int lineNumber) {
        Matcher matcher = PATTERN.matcher(line);
        if(matcher.find()) {
            return RuleResult.builder()
                    .lineNumber(lineNumber)
                    .violatedLine(line)
                    .ruleName(RULE_NAME)
                    .description(RULE_DESCRIPTION)
                    .build();
        } else {
            return RuleResult.SUCCESS;
        }
    }
}
