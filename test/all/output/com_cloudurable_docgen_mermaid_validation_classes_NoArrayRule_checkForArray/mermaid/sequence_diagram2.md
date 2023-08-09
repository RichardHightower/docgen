----
# FIX RAW RESPONSE 3
# Participants
 
* NoArrayRule
* Pattern 
* Matcher 
* RuleResult 

# Validation Fixes

* Remove "Note over NoArrayRule: PATTERN" because notes are not allowed in the diagram.

# Interaction After Fix

NoArrayRule->>Pattern: Invoke matcher method passing input
Pattern-->>Matcher: Find match

alt Match found
    Matcher-->>NoArrayRule: Retrieve array declaration and assign to arrayDeclaration
    Matcher-->>NoArrayRule: Retrieve class name and assign to className
    alt "side" is "Right"
        NoArrayRule-->>NoArrayRule: Generate error description replacing input in line with "*" before class name
    else "side" is "Left"
        NoArrayRule-->>NoArrayRule: Generate error description replacing input in line with "*" after class name
    NoArrayRule-->>NoArrayRule: Build and return RuleResult with error description
else Match not found
    NoArrayRule-->>NoArrayRule: Return success RuleResult

# Final Participants

* NoArrayRule
* Pattern 
* Matcher 
* RuleResult 

# Plain English Title

Check for Array

# Mermaid Sequence Diagram

```mermaid
---
title: Check for Array
---

sequenceDiagram
    participant NoArrayRule
    participant Pattern
    participant Matcher
    participant RuleResult

    NoArrayRule->>Pattern: Invoke matcher method passing input
    Pattern-->>Matcher: Find match

    alt Match found
        Matcher-->>NoArrayRule: Retrieve array declaration and assign to arrayDeclaration
        Matcher-->>NoArrayRule: Retrieve class name and assign to className
        alt "side" is "Right"
            NoArrayRule-->>NoArrayRule: Generate error description replacing input in line with "*" before class name
        else "side" is "Left"
            NoArrayRule-->>NoArrayRule: Generate error description replacing input in line with "*" after class name
        NoArrayRule-->>NoArrayRule: Build and return RuleResult with error description
    else Match not found
        NoArrayRule-->>NoArrayRule: Return success RuleResult
```

