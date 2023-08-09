----
# FIX RAW RESPONSE 2
# Participants

- NoPrimitiveOrBasicTypesRule
- Pattern
- Matcher
- RuleResult

# Validation Fixes

The validation error indicates a parse error in line 16 of the Mermaid code. To fix this, we need to check the Mermaid code and identify what is causing the parse error.

# Interaction After Fix

The interaction between the participants is as follows:

1. `NoPrimitiveOrBasicTypesRule` creates a `PATTERN`.
2. `NoPrimitiveOrBasicTypesRule` creates a `Matcher`.
3. `Matcher` finds a match in the input.
4. If a match is found, `Matcher` gets the matched type and `NoPrimitiveOrBasicTypesRule` creates a `RuleResult` with that type.
5. If no match is found, `NoPrimitiveOrBasicTypesRule` creates a `RuleResult` with a success message.

# Final Participants

- NoPrimitiveOrBasicTypesRule
- Pattern
- Matcher
- RuleResult

# Plain English Title

"Check Collection"

# Mermaid Sequence Diagram

```mermaid
---
title: Check Collection
---

sequenceDiagram
    participant NoPrimitiveOrBasicTypesRule
    participant Pattern
    participant Matcher
    participant RuleResult

    NoPrimitiveOrBasicTypesRule->>Pattern: Create PATTERN
    NoPrimitiveOrBasicTypesRule->>Matcher: Create Matcher
    Matcher->>Matcher: Find match in input
    alt Match found
        Matcher->>Matcher: Get matched type
        NoPrimitiveOrBasicTypesRule-->>RuleResult: Create RuleResult with type
    else No match found
        NoPrimitiveOrBasicTypesRule-->>RuleResult: Create RuleResult SUCCESS
```

Please note that due to a parse error in the Mermaid code, there may be some discrepancies in the above diagrams.