package com.cloudurable.docgen.mermaid.validation.classes;

import com.cloudurable.docgen.mermaid.validation.LineRule;
import com.cloudurable.docgen.mermaid.validation.RuleResult;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class InterfaceRule implements LineRule {

    private final Pattern PATTERN = Pattern.compile("^interface\\s+([A-Za-z0-9_]+)\\s*\\{$");

    @Override
    public RuleResult check(String line, int lineNumber) {
        Matcher matcher = PATTERN.matcher(line);
        if (matcher.find()) {
            String interfaceName = matcher.group(1);
            String description = "Instead of using 'interface " + interfaceName + " {', " +
                    "use 'class " + interfaceName + " {\n\t<<interface>>'.";
            return RuleResult.builder()
                    .ruleName("InterfaceRule")
                    .lineNumber(lineNumber)
                    .violatedLine(line)
                    .description(description)
                    .build();
        }

        return RuleResult.SUCCESS;
    }
}
