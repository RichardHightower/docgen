package com.cloudurable.docgen.mermaid.validation.sequence;

import com.cloudurable.docgen.mermaid.validation.ContentRule;
import com.cloudurable.docgen.mermaid.validation.RuleResult;

import java.util.Stack;

public class MismatchedTagsRule implements ContentRule {

    @Override
    public RuleResult check(String content) {
        Stack<String> tagStack = new Stack<>();
        String[] lines = content.split("\\n");
        int lineNumber = 0;

        for (String line : lines) {
            lineNumber++;
            line = line.trim();

            if (line.startsWith("alt")) {
                tagStack.push("alt");
            } else if (line.startsWith("critical")) {
                tagStack.push("critical");
            } else if (line.startsWith("loop")) {
                tagStack.push("loop");
            } else if (line.startsWith("opt")) {
                tagStack.push("opt");
            }
            else if (line.startsWith("end")) {
                if (tagStack.isEmpty()) {
                    return createMismatchError(lineNumber, "end without opening tag");
                }
                String openingTag = tagStack.pop();
                if ("end".equals(openingTag)) {
                    return createMismatchError(lineNumber, "nested end tags");
                }
            }
        }

        if (!tagStack.isEmpty()) {
            return createMismatchError(lineNumber, tagStack.peek() + " without corresponding end");
        }

        return RuleResult.SUCCESS;
    }

    private RuleResult createMismatchError(int lineNumber, String description) {
        return RuleResult.builder()
                .ruleName("Mismatched Tags")
                .violatedLine("")
                .lineNumber(lineNumber)
                .description(description)
                .build();
    }
}
