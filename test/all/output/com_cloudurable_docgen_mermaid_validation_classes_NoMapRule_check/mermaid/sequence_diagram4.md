----
# FIX RAW RESPONSE 1
# Participants

- NoMapRule
- Pattern
- Relationship
- RuleResult

# Validation Fixes
There seems to be an error while generating the mermaid image. The error message states: "Parse error on line 22: ...Expecting 'SPACE', 'NEWLINE', 'box', 'end', 'autonumber', 'activate', 'deactivate', 'title', 'legacy_title', 'acc_title', 'acc_descr', 'acc_descr_multiline_value', 'loop', 'rect', 'opt', 'alt', 'par', 'par_over', 'critical', 'break', 'else', 'participant', 'participant_actor', 'note', 'links', 'link', 'properties', 'details', 'ACTOR', 'open_directive', got '1'".

To fix this issue, we need to investigate the mermaid code and identify the source of the error on line 22. 

# Interaction After Fix
The mermaid code includes interactions between the participants that represent the flow of actions and messages in the code. However, the current code contains an error that needs to be fixed. Once the error is resolved, we will update the interactions based on the fixed code.

# Final Participants
- NoMapRule
- Pattern
- Relationship
- RuleResult

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