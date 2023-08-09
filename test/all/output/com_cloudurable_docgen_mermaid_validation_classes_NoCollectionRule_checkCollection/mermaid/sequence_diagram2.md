----
# FIX RAW RESPONSE 3
# Participants

1. NoCollectionRule
2. Pattern
3. Matcher
4. RuleResult

# Validation Fixes

1. "No Method Calls In Descriptions Rule"
   - Replace ```Invoke find() method``` with ```Invoke find method```

# Interaction After Fix

- NoCollectionRule->>Pattern: Create Pattern object
- NoCollectionRule->>Matcher: Create Matcher object
- NoCollectionRule->>Matcher: Invoke find method
- Matcher->>NoCollectionRule: Match found
  - alt Match found
    - Matcher->>NoCollectionRule: Extract collectionType
    - Matcher->>NoCollectionRule: Extract genericType
    - NoCollectionRule-->>RuleResult: Build RuleResult object
  - else No match found
    - NoCollectionRule-->>RuleResult: Build RuleResult object

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

Note: The mermaid image generator failed and was unable to generate an image for the sequence diagram.