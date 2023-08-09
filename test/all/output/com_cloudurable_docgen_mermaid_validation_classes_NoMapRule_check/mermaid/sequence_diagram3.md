----
# FIX RAW RESPONSE 2
# Participants

1. NoMapRule
2. Pattern
3. Relationship
4. RuleResult

# Validation Fixes
This diagram has a validation error that needs to be fixed. The error is: `Expecting 'SPACE', 'NEWLINE', 'box', 'end', 'autonumber', 'activate', 'deactivate', 'title', 'legacy_title', 'acc_title', 'acc_descr', 'acc_descr_multiline_value', 'loop', 'rect', 'opt', 'alt', 'par', 'par_over', 'critical', 'break', 'else', 'participant', 'participant_actor', 'note', 'links', 'link', 'properties', 'details', 'ACTOR', 'open_directive', got '1'`. To fix this, the incorrect `"lineNumber": 0` and `"violatedLine": ""` entries should be removed from the validation JSON.

# Interaction After Fix
There is no interaction description provided in the broken Mermaid code, so it cannot be fixed.

# Final Participants

1. NoMapRule
2. Pattern
3. Relationship
4. RuleResult

# Plain English Title
"Check NoMapRule"

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

