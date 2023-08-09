----
# FIX RAW RESPONSE 3
# Participants

1. NoMapRule
2. Pattern
3. Relationship
4. RuleResult

# Validation Fixes

The following fixes will be applied to the validation issues in the JSON:

1. Fix `lineNumber`
2. Remove spaces and new lines from `violatedLine`
3. Replace `"` with `'` in `ruleName`
4. Replace `"\n"` with `' '` in `description`

# Interaction After Fix

1. NoMapRule instantiates `Pattern` with the `PATTERN` constant
2. NoMapRule checks if the line contains a `Relationship`
   - If a `Relationship` is found, it is parsed and the `Relationship` object is obtained
   - If the `Relationship` is valid:
     - NoMapRule checks the left class
       - If the left class check is successful, NoMapRule checks the right class
       - If the left class check fails, NoMapRule returns a failed `RuleResult`
   - If the `Relationship` is invalid, NoMapRule returns a failed `RuleResult`
3. If no `Relationship` is found, NoMapRule returns a success `RuleResult`

# Final Participants

1. NoMapRule
2. Pattern
3. Relationship
4. RuleResult

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

    NoMapRule->>Pattern: Instantiate with 'PATTERN'
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