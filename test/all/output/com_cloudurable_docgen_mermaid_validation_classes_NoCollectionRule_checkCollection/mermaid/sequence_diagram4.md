----
# FIX RAW RESPONSE 1
# Participants
1. NoCollectionRule
2. Pattern
3. Matcher
4. RuleResult

# Validation Fixes
No validation issues found.

# Interaction After Fix
1. NoCollectionRule->>Pattern: Create Pattern object
2. NoCollectionRule->>Matcher: Create Matcher object
3. NoCollectionRule->>Matcher: Invoke find method
4. Matcher->>NoCollectionRule: Match found
5. alt Match found
    6. Matcher->>NoCollectionRule: Extract collectionType
    7. Matcher->>NoCollectionRule: Extract genericType
    8. NoCollectionRule-->>RuleResult: Build RuleResult object
9. else No match found
    10. NoCollectionRule-->>RuleResult: Build RuleResult object

# Final Participants
1. NoCollectionRule
2. Pattern
3. Matcher
4. RuleResult

# Plain English Title
Check Collection

# Mermaid Sequence Diagram

```mermaid
---
title: Check Collection
---

sequenceDiagram
    participant NoCollectionRule
    participant Pattern
    participant Matcher
    participant RuleResult

    NoCollectionRule->>Pattern: Create Pattern object
    NoCollectionRule->>Matcher: Create Matcher object
    NoCollectionRule->>Matcher: Invoke find method
    Matcher->>NoCollectionRule: Match found
    alt Match found
        Matcher->>NoCollectionRule: Extract collectionType
        Matcher->>NoCollectionRule: Extract genericType
        NoCollectionRule-->>RuleResult: Build RuleResult object
    else No match found
        NoCollectionRule-->>RuleResult: Build RuleResult object
```