----
# ORIGINAL RESPONSE 
# Participants

1. NoCollectionRule
2. LineRule
3. Pattern
4. Matcher
5. RuleResult

# Initial Interactions

- NoCollectionRule creates a Pattern object.
- NoCollectionRule creates a Matcher object.
- NoCollectionRule invokes the find() method on Matcher.
- Matcher finds a match in the input string.

# Clean Interactions

- NoCollectionRule->>Pattern: Create Pattern object
- NoCollectionRule->>Matcher: Create Matcher object
- NoCollectionRule->>Matcher: Invoke find() method
- Matcher->>NoCollectionRule: Match found
- alt Match found
    - Matcher->>NoCollectionRule: Extract collectionType
    - Matcher->>NoCollectionRule: Extract genericType
    - NoCollectionRule-->>RuleResult: Build RuleResult object
- else No match found
    - NoCollectionRule-->>RuleResult: Build RuleResult object

# Final Participants

- NoCollectionRule
  - Interactions: NoCollectionRule->>Pattern, NoCollectionRule->>Matcher, Matcher->>NoCollectionRule, NoCollectionRule-->>RuleResult
- Pattern
  - Interactions: NoCollectionRule->>Pattern
- Matcher
  - Interactions: NoCollectionRule->>Matcher, Matcher->>NoCollectionRule
- RuleResult
  - Interactions: NoCollectionRule-->>RuleResult

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
    NoCollectionRule->>Matcher: Invoke find() method
    Matcher->>NoCollectionRule: Match found
    alt Match found
        Matcher->>NoCollectionRule: Extract collectionType
        Matcher->>NoCollectionRule: Extract genericType
        NoCollectionRule-->>RuleResult: Build RuleResult object
    else No match found
        NoCollectionRule-->>RuleResult: Build RuleResult object
```
