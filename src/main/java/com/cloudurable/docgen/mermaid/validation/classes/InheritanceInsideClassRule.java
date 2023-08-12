package com.cloudurable.docgen.mermaid.validation.classes;

import com.cloudurable.docgen.mermaid.validation.ContentRule;
import com.cloudurable.docgen.mermaid.validation.RuleResult;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class InheritanceInsideClassRule implements ContentRule {

    private final Pattern PATTERN = Pattern.compile("^(class\\s+([A-Za-z0-9_]+)\\s*\\{)([^{]*?<< extends ([A-Za-z0-9_]+) >>)", Pattern.DOTALL);

    @Override
    public RuleResult check(String content) {
        Matcher matcher = PATTERN.matcher(content);
        if (matcher.find()) {
            String className = matcher.group(2);
            String baseClass = matcher.group(4);
            String description = "Instead of embedding the inheritance inside the class, format it as:\n" +
                    "class " + className + " {\n" +
                    "...\n" +
                    "}\n" +
                    className + " —|> " + baseClass;
            return RuleResult.builder()
                    .ruleName("InheritanceInsideClassRule")
                    .lineNumber(0)
                    .violatedLine(content)
                    .description(description)
                    .build();
        }
        return RuleResult.SUCCESS;
    }

}
