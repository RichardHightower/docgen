----
# FIX RAW RESPONSE 3
# Participants

1. NoArrayRule
2. Pattern
3. Relationship
4. RuleResult

# Validation Fixes

1. RuleResult is a desirable participant representing the result of a rule check.
2. The `RuleResult.SUCCESS` return value represents a successful rule check.
3. The `RuleResult` object is created when a rule violation is found.
4. The `RuleResult` object is returned from the `check` method.
5. The `RuleResult` object contains information about the violated rule, the line number, the violated line, and a description of the violation.

# Interaction After Fix

1. NoArrayRule creates a Pattern to match a specific pattern in a line.
2. NoArrayRule checks if the line has a relationship.
   - If the relationship is NOT_FOUND, NoArrayRule creates a RuleResult object and returns it.
   - If the relationship is a valid relationship, NoArrayRule parses the relationship.
   - NoArrayRule checks if there are arrays in the left class.
     - If there are arrays in the left class, NoArrayRule creates a RuleResult object and returns it.
     - If there are no arrays in the left class, NoArrayRule checks for arrays in the right class.
       - If there are arrays in the right class, NoArrayRule creates a RuleResult object and returns it.
       - If there are no arrays in the right class, NoArrayRule returns RuleResult.SUCCESS.

# Final Participants

1. NoArrayRule
2. Relationship
3. RuleResult

# Plain English Title

Check No Array Rule

# Mermaid Sequence Diagram

```mermaid
---
title: Check No Array Rule
---

sequenceDiagram
    participant NoArrayRule
    participant Relationship
    participant RuleResult

    NoArrayRule->>Relationship: Create Pattern
    NoArrayRule->>Relationship: Check if line has relationship
    alt Parsed relationship is NOT_FOUND
        NoArrayRule->>RuleResult: Create RuleResult
        RuleResult-->>NoArrayRule: Returns RuleResult
    else Parsed relationship is a valid relationship
        NoArrayRule->>Relationship: Parse relationship
        NoArrayRule->>Relationship: Check for arrays in class
        alt There are arrays in the left class
            NoArrayRule->>RuleResult: Create RuleResult
            RuleResult-->>NoArrayRule: Returns RuleResult
        else There are no arrays in the left class
            NoArrayRule->>Relationship: Check for arrays in class
            alt There are arrays in the right class
                NoArrayRule->>RuleResult: Create RuleResult
                RuleResult-->>NoArrayRule: Returns RuleResult
            else There are no arrays in the right class
                NoArrayRule->>RuleResult: Return RuleResult.SUCCESS
    end
```
