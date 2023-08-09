----
# ORIGINAL RESPONSE 
# Participants

* NoArrayRule
* LineRule
* Pattern
* Matcher

# Initial Interactions

1. `NoArrayRule` has a private field `PATTERN` of type `Pattern` that is assigned a compiled regular expression.
2. `NoArrayRule` has a private method `checkForArray` that takes four parameters: `side` (a `String`), `input` (a `String`), `line` (a `String`), and `lineNumber` (an `int`).
3. Within the `checkForArray` method, a `Matcher` object is created by invoking the `matcher` method on the `PATTERN` field and passing in the `input` parameter.
4. If the matcher finds a match in the input, the method retrieves the matched string and assigns it to the `arrayDeclaration` variable.
5. The method also retrieves the captured group 1 (the class name) and assigns it to the `className` variable.
6. Depending on the value of the `side` parameter, the method generates a custom error message stating that arrays are not allowed in the relationship and suggests an alternative syntax.
7. The method returns a `RuleResult` object with the relevant information if an array is found; otherwise, it returns a success `RuleResult`.

# Clean Interactions

1. `NoArrayRule`-->>`Pattern`: Invoke `matcher` method passing `input`
2. `Matcher`-->>`NoArrayRule`: Find match
3. alt Match found
    4. `Matcher`-->>`NoArrayRule`: Retrieve array declaration and assign to `arrayDeclaration`
    5. `Matcher`-->>`NoArrayRule`: Retrieve class name and assign to `className`
    6. alt `side` is "Right"
        7. `NoArrayRule`-->>`NoArrayRule`: Generate error description replacing input in line with `"*"` before class name
    8. else `side` is "Left"
        9. `NoArrayRule`-->>`NoArrayRule`: Generate error description replacing input in line with `"*"` after class name
    10. `NoArrayRule`-->>`NoArrayRule`: Build and return `RuleResult` with error description
else Match not found
    11. `NoArrayRule`-->>`NoArrayRule`: Return success `RuleResult`
    
# Final Participants

* NoArrayRule
    * Check For Array
    * PATTERN
    * Side
    * Input
    * Line
    * Line Number
* Matcher
* Pattern
* RuleResult

# Plain English Title

Check for Array (NoArrayRule)

# Mermaid Sequence Diagram

```mermaid
---
title: Check for Array (NoArrayRule)
---

sequenceDiagram
    participant NoArrayRule
    participant Pattern
    participant Matcher
    participant RuleResult
    
    Note over NoArrayRule: PATTERN
    NoArrayRule->>Pattern: Invoke matcher method\npassing input
    Pattern-->>Matcher: Find match
    alt Match found
        Matcher-->>NoArrayRule:\nRetrieve array declaration\nand assign to arrayDeclaration
        Matcher-->>NoArrayRule: Retrieve class name\nand assign to className
        alt "side" is "Right"
            NoArrayRule-->>NoArrayRule: Generate error description\nreplacing input in line with "*"\nbefore class name
        else "side" is "Left"
            NoArrayRule-->>NoArrayRule: Generate error description\nreplacing input in line with "*"\nafter class name
        NoArrayRule-->>NoArrayRule: Build and return RuleResult\nwith error description
    else Match not found
        NoArrayRule-->>NoArrayRule: Return success RuleResult
```
