package com.cloudurable.docgen.mermaid.validation.sequence;

import com.cloudurable.docgen.mermaid.validation.LineRule;
import com.cloudurable.docgen.mermaid.validation.RuleResult;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DataClassesAndPrimitiveRule implements LineRule {
    private final String DATA_VALUES = "\\b(String|Map|Queue|byte|float|int|double" +
            "long|boolean|char|File|Byte|Bytes|bytes|:|<|>)\\b";

    //private final Pattern BRACKETS =  Pattern.compile("[\\[\\]]");
    //private final Pattern BRACKETS = Pattern.compile("(?<![\"])\\[|\\](?![\"])");

    private final Pattern BRACKETS_OR_QUOTES = Pattern.compile("\"[^\"]*\"|[\\[\\]]");

    public boolean containsUnquotedBrackets(String input) {
        Matcher matcher = BRACKETS_OR_QUOTES.matcher(input);
        while (matcher.find()) {
            String match = matcher.group();
            if (!match.startsWith("\"") && (match.equals("[") || match.equals("]"))) {
                return true;  // Found an unquoted bracket
            }
        }
        return false;  // No unquoted brackets found
    }


    private final Pattern PATTERN = Pattern.compile(DATA_VALUES);

    private final String RULE_NAME = "Primitive Rule";
    private final String RULE_DESCRIPTION = "Do not include primitive or basic data types as participants in the sequence diagram. Disallow Specific Words: The line cannot contain any of the specific words: \"byte\", \"float\", \"int\", \"double\", \"long\", \"boolean\", \"char\", \"List\", \"File\", and \"bytes\".\n" +
            "\n" +
            "Disallow Square Brackets: The line cannot contain a square bracket character, either opening \"[\" or closing \"]\". Do not include \":\". Do not include angle brackets \"<\" \">\". Use ~ instead of angle brackets \n" ;

    @Override
    public RuleResult check(String line, int lineNumber) {

        if (line.startsWith("title:")) {
            return RuleResult.SUCCESS;
        }

        String[] split = line.split(":");
        String interaction = split.length > 1 ? split[0] : line;

        final  Matcher matcher = PATTERN.matcher(interaction);



        if(matcher.find() || containsUnquotedBrackets(line)) {
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
