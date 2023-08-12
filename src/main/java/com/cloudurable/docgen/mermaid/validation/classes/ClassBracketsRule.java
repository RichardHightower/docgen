package com.cloudurable.docgen.mermaid.validation.classes;

import com.cloudurable.docgen.mermaid.validation.LineRule;
import com.cloudurable.docgen.mermaid.validation.RuleResult;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ClassBracketsRule implements LineRule {

    // Pattern checks for a class name followed by {} on the same line.
    private final Pattern PATTERN = Pattern.compile("^class\\s+([A-Za-z0-9_]+)\\s*\\{\\}$");

    @Override
    public RuleResult check(String line, int lineNumber) {
        Matcher matcher = PATTERN.matcher(line);
        if (matcher.find()) {
            String className = matcher.group(1);
            String description = "The opening and closing brackets for class '" + className +
                    "' should not be on the same line. Place them on separate lines.";
            return RuleResult.builder()
                    .ruleName("ClassBracketsRule")
                    .lineNumber(lineNumber)
                    .violatedLine(line)
                    .description(description)
                    .build();
        }

        return RuleResult.SUCCESS;
    }
}
