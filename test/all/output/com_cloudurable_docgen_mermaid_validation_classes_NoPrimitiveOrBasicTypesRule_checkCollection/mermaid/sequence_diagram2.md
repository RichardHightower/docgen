----
# FIX RAW RESPONSE 3
# Participants
- NoPrimitiveOrBasicTypesRule
- Pattern
- Matcher
- RuleResult

# Validation Fixes
- No validation issues to fix.

# Interaction After Fix

1. **NoPrimitiveOrBasicTypesRule** creates Pattern.
2. **NoPrimitiveOrBasicTypesRule** creates Matcher.
3. **Matcher** finds a match in the input.
4. If a match is found:
   - **Matcher** gets the matched type.
   - **NoPrimitiveOrBasicTypesRule** creates a RuleResult with the type and sends it to RuleResult.
5. If no match is found:
   - **NoPrimitiveOrBasicTypesRule** creates a RuleResult SUCCESS and sends it to RuleResult.

# Final Participants
- NoPrimitiveOrBasicTypesRule
- Pattern
- Matcher
- RuleResult

# Plain English Title
Check Collection

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
