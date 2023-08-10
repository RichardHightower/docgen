package com.cloudurable.docgen.mermaid.validation.sequence;

import com.cloudurable.docgen.mermaid.validation.ContentRule;
import com.cloudurable.docgen.mermaid.validation.RuleResult;

import java.util.Stack;
import java.util.concurrent.atomic.AtomicInteger;

public class RequiredTagsRule implements ContentRule {

    @Override
    public RuleResult check(String content) {
        Stack<String> tagStack = new Stack<>();
        String[] lines = content.split("\\n");
        int lineNumber = 0;

        Stack<AtomicInteger> optionElseCountStack = new Stack<>();

        for (String line : lines) {
            lineNumber++;
            line = line.trim();

            if (line.startsWith("alt")) {
                tagStack.push("alt");
                optionElseCountStack.push(new AtomicInteger());
            } else if (line.startsWith("critical")) {
                tagStack.push("critical");
                optionElseCountStack.push(new AtomicInteger());
            } else if (line.startsWith("loop")) {
                tagStack.push("loop");
            } else if (line.startsWith("opt")) {
                tagStack.push("opt");
            } else if (line.startsWith("else")) {
                if (!tagStack.isEmpty() && "alt".equals(tagStack.peek())) {
                    optionElseCountStack.peek().incrementAndGet();
                    //tagStack.pop(); do I need this?
                } else {
                    return createMismatchError(lineNumber, "unexpected else", line);
                }
            } else if (line.startsWith("option")) {
                if (!tagStack.isEmpty() && "critical".equals(tagStack.peek())) {
                    optionElseCountStack.peek().incrementAndGet();
                    //tagStack.pop(); do I need this?
                } else {
                    return createMismatchError(lineNumber, "unexpected option", line);
                }
            } else if (line.startsWith("end")) {
                if (tagStack.isEmpty()) {
                    return createMismatchError(lineNumber, "end without opening tag", line);
                }
                String openingTag = tagStack.pop();
                if (optionElseCountStack.pop().get() < 1) {
                    if (openingTag.equals("alt")) {
                        return createMismatchError(lineNumber, "for `alt` must have at least one `else`", line);
                    } else if (openingTag.equals("critical")) {
                        return createMismatchError(lineNumber, "for `critical` must have at least one `option`", line);
                    }
                }
                if ("end".equals(openingTag)) {
                    return createMismatchError(lineNumber, "nested end tags", line);
                }
            }
        }

        if (!tagStack.isEmpty()) {
            if ("alt".equals(tagStack.peek())) {
                return createMismatchError(lineNumber, "alt without corresponding else", "");
            } else if ("critical".equals(tagStack.peek())) {
                return createMismatchError(lineNumber, "critical without corresponding option", "");
            } else {
                return createMismatchError(lineNumber, tagStack.peek() + " without corresponding end", "");
            }
        }

        return RuleResult.SUCCESS;
    }


    private RuleResult createMismatchError(int lineNumber, String description, String line) {
        return RuleResult.builder()
                .ruleName("Required Tags")
                .violatedLine("")
                .lineNumber(lineNumber)
                .description(description)
                .build();
    }
}
