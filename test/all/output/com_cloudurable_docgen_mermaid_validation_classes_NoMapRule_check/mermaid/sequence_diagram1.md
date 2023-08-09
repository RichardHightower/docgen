----
# ORIGINAL RESPONSE 
# Participants

1. NoMapRule
2. Pattern
3. Relationship
4. RuleResult

# Initial Interactions

- NoMapRule instantiates Pattern with `PATTERN` as the regex pattern.
- NoMapRule overrides the `check` method of the LineRule interface.
- NoMapRule checks if the input line contains a Relationship.
- If a Relationship exists, it checks both the left and right classes using the `checkMap` method.
- If the left class check is successful, it returns the result of the right class check.
- If the left class check fails, it returns the result of the failed check.

# Clean Interactions

- NoMapRule->>Pattern: Instantiate with `PATTERN` regex
- NoMapRule->>Relationship: Check if line contains Relationship
- alt Relationship found
    - NoMapRule->>Relationship: Parse and get Relationship object
    - alt Relationship is valid
        - NoMapRule->>NoMapRule: Check left class
        - alt Left class check is successful
            - NoMapRule->>NoMapRule: Check right class
        - else Left class check fails
            - NoMapRule->>RuleResult: Return failed RuleResult
    - else Relationship is invalid
        - NoMapRule->>RuleResult: Return failed RuleResult
- else Relationship not found
    - NoMapRule-->>NoMapRule: Return success RuleResult

# Final Participants

- NoMapRule
    - Instantiate with `PATTERN` regex
    - Check if line contains Relationship
    - Parse and get Relationship object
    - Check left class
    - Check right class
- Pattern
    - Instantiate with `PATTERN` regex
- Relationship
    - Check if line contains Relationship
    - Parse and get Relationship object
- RuleResult
    - Return failed RuleResult
    - Return success RuleResult

# Plain English Title

Check NoMapRule

# Mermaid Sequence Diagram

```mermaid
---
title: Check NoMapRule
---

sequenceDiagram
    participant NoMapRule
    participant Pattern
    participant Relationship
    participant RuleResult

    NoMapRule->>Pattern: Instantiate with `PATTERN`
    NoMapRule->>Relationship: Check if line contains Relationship
    alt Relationship found
        NoMapRule->>Relationship: Parse and get Relationship object
        alt Relationship is valid
            NoMapRule->>NoMapRule: Check left class
            alt Left class check is successful
                NoMapRule->>NoMapRule: Check right class
            else Left class check fails
                NoMapRule->>RuleResult: Return failed RuleResult
        else Relationship is invalid
            NoMapRule->>RuleResult: Return failed RuleResult
    else Relationship not found
        NoMapRule-->>NoMapRule: Return success RuleResult
```
