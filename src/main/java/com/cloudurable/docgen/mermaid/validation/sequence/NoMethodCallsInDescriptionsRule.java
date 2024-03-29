package com.cloudurable.docgen.mermaid.validation.sequence;


import com.cloudurable.docgen.mermaid.validation.LineRule;
import com.cloudurable.docgen.mermaid.validation.RuleResult;

import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class NoMethodCallsInDescriptionsRule implements LineRule {
    private final Pattern PATTERN = Pattern.compile("(?<=:).*\\b\\w+\\s*\\(.*\\)"); // matches "methodName(...)" anywhere after a colon
    private final String RULE_NAME = "No Method Calls In Descriptions Rule";
    private final String RULE_DESCRIPTION = "No method calls in descriptions. " +
            "instead of ```StringBuilder-->>Person: Return toString() result``` use ```StringBuilder-->>Person: Return toString result``` \n" +
            "instead of NO ```EmployeeService->>EmployeeRepo: updateEmployeeManager()``` use  YES ```EmployeeService->>EmployeeRepo: update Employee's Manager``` \n";

    @Override
    public RuleResult check(String line, int lineNumber) {
        if (line.startsWith("title:")) {
            return RuleResult.SUCCESS;
        }
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
