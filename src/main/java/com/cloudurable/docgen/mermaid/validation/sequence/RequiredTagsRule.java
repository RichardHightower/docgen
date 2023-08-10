package com.cloudurable.docgen.mermaid.validation.sequence;

import com.cloudurable.docgen.mermaid.validation.ContentRule;
import com.cloudurable.docgen.mermaid.validation.RuleResult;

import java.util.Stack;
import java.util.concurrent.atomic.AtomicInteger;

public class RequiredTagsRule implements ContentRule {
    public static final String ALT_TAG_RULES ="The `alt` tag must have at least one `else` and an `end`.\nIf you do not have at lease one `else` then use `opt`/`end` instead.";
    public static final String CRITICAL_TAG_RULES = "The `critical` tag must have at least one `option` and an `end`.";

    public static final String LOOP_ELSE_RULE = "The `loop` tag cannot have an `else` tag";
    public static final String OPT_ELSE_RULE = "The `opt` tag cannot have an `else` tag." +
            "\nIf you need an `else`, then use `alt`/`else`/`end` tags instead.";

    public static final String CRITICAL_ELSE_RULE = "The `critical` tag cannot have an `else` tag. " +
            "Try using `option` with `critical` as in  `critical`/`option`/`end` ";

    public static final String LOOP_OPTION_RULE = "The `loop` tag cannot have an `option` tag";
    public static final String OPT_OPTION_RULE =  "The `opt` tag cannot have an `option` tag." +
            "\nIf you need an `option`, then use `critical`/`option`/`end` tags instead.";

    public static final String ALT_OPTION_RULE = "The `alt` tag cannot have an `option` tag. " +
            "Try using `else` with `alt` as in  `alt`/`else`/`end` ";

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
                optionElseCountStack.push(new AtomicInteger());
            } else if (line.startsWith("else")) {
                final var parentTage = !tagStack.isEmpty() ? tagStack.peek() : "";

                if ("alt".equals(parentTage)) {
                    if (!optionElseCountStack.isEmpty()) {
                        AtomicInteger peek = optionElseCountStack.peek();
                        if (peek != null) {
                            peek.incrementAndGet();
                        } else {
                            System.out.println(content);
                        }
                    }
                    //tagStack.pop(); do I need this?
                } else {
                    switch (parentTage) {
                        case "opt":
                            return createMismatchError(lineNumber, OPT_ELSE_RULE, line);
                        case "loop":
                            return createMismatchError(lineNumber, LOOP_ELSE_RULE, line);
                        case "critical":
                            return createMismatchError(lineNumber, CRITICAL_ELSE_RULE, line);
                        default:
                            return createMismatchError(lineNumber, "unexpected `else`", line);
                    }

                }
            } else if (line.startsWith("option")) {
                final var parentTag = !tagStack.isEmpty() ? tagStack.peek() : "";
                if ("critical".equals(parentTag)) {
                    if (!optionElseCountStack.isEmpty()) {
                        AtomicInteger peek = optionElseCountStack.peek();
                        if (peek != null) {
                            peek.incrementAndGet();
                        } else {
                            System.out.println(content);
                        }
                    }
                    //tagStack.pop(); do I need this?
                } else {
                    switch (parentTag) {
                        case "opt":
                            return createMismatchError(lineNumber, OPT_OPTION_RULE, line);
                        case "loop":
                            return createMismatchError(lineNumber, LOOP_OPTION_RULE, line);
                        case "alt":
                            return createMismatchError(lineNumber, ALT_OPTION_RULE, line);
                        default:
                            return createMismatchError(lineNumber, "unexpected `option`", line);
                    }
                }
            } else if (line.startsWith("opt")) {
                tagStack.push("opt");
                optionElseCountStack.push(new AtomicInteger());
            } else if (line.startsWith("end")) {
                if (tagStack.isEmpty()) {
                    return createMismatchError(lineNumber, "end without opening tag", line);
                }
                String openingTag = tagStack.pop();
                if (optionElseCountStack.isEmpty()) {
                    if (openingTag.equals("alt")) {
                        return createMismatchError(lineNumber, ALT_TAG_RULES, line);
                    } else if (openingTag.equals("critical")) {
                        return createMismatchError(lineNumber,CRITICAL_TAG_RULES , line);
                    }
                }
                else if (optionElseCountStack.pop().get() < 1) {
                    if (openingTag.equals("alt")) {
                        return createMismatchError(lineNumber, ALT_TAG_RULES, line);
                    } else if (openingTag.equals("critical")) {
                        return createMismatchError(lineNumber,CRITICAL_TAG_RULES , line);
                    }
                }
                if ("end".equals(openingTag)) {
                    return createMismatchError(lineNumber, "nested end tags", line);
                }
            }
        }

        if (!tagStack.isEmpty()) {
            if ("alt".equals(tagStack.peek())) {
                return createMismatchError(lineNumber, "`alt` without corresponding `end`", "");
            } else if ("critical".equals(tagStack.peek())) {
                return createMismatchError(lineNumber, "`critical` without corresponding `end`", "");
            } else {
                return createMismatchError(lineNumber, "`" +tagStack.peek() + "` without corresponding `end`", "");
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
