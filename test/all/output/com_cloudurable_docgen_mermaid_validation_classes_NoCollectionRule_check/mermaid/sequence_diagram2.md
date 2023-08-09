----
# FIX RAW RESPONSE 3
# Participants
1. NoCollectionRule
2. Relationship
3. RuleResult
4. Console

# Validation Fixes
- [ ] Fix mermaid image generator issue

# Interaction After Fix
1. NoCollectionRule checks if the line contains a relationship.
2. If a relationship is found in the line:
   - Relationship parses the relationship.
   - If the relationship is not found, NoCollectionRule prints an error message.
   - If the relationship is valid:
     - NoCollectionRule invokes RuleResult to check the left class collection.
     - If the left check is successful, NoCollectionRule invokes RuleResult to check the right class collection.
     - If the left check fails, NoCollectionRule returns the left check result.
3. If a relationship is not found in the line, the process is skipped.

# Final Participants
- NoCollectionRule
- Relationship
- RuleResult
- Console

# Plain English Title
Check No Collection Rule

# Mermaid Sequence Diagram
```mermaid
---
title: Check No Collection Rule
---

sequenceDiagram
    participant NoCollectionRule
    participant Relationship
    participant RuleResult
    participant Console
    
    NoCollectionRule->>Relationship: Check if line contains a relationship
    alt Relationship found in the line
        Relationship->>NoCollectionRule: Parse the relationship
        alt Relationship is not found
            NoCollectionRule->>Console: Print error message
        else Relationship is valid
            NoCollectionRule->>RuleResult: Invoke checkCollection for left class
            alt Left check is successful
                NoCollectionRule->>RuleResult: Invoke checkCollection for right class
            else Left check failed
                NoCollectionRule->>RuleResult: Return left check result
        end
    else Relationship not found in the line
```
