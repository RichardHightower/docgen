package com.cloudurable.docgen.mermaid.validation.classes;

import com.cloudurable.docgen.mermaid.validation.LineRule;
import com.cloudurable.docgen.mermaid.validation.RuleResult;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NoInlineInheritanceRule implements LineRule {

    private final Pattern PATTERN = Pattern.compile("^class\\s+([A-Za-z0-9_]+)\\s+extends\\s+([A-Za-z0-9_]+)\\s*\\{");

    @Override
    public RuleResult check(String line, int lineNumber) {
        Matcher matcher = PATTERN.matcher(line);
        if (matcher.find()) {
            String className = matcher.group(1);
            String baseClass = matcher.group(2);
            String description = "Instead of using inline inheritance, format it as:\n" +
                    "class " + className + " {\n" +
                    "...\n" +
                    "}\n" +
                    className + " —|> " + baseClass;
            return RuleResult.builder()
                    .ruleName("NoInlineInheritanceRule")
                    .lineNumber(lineNumber)
                    .violatedLine(line)
                    .description(description)
                    .build();
        }
        return RuleResult.SUCCESS;
    }
}
