----
# FIX RAW RESPONSE 2
# Participants
* NoCollectionRule
* Relationship
* RuleResult
* Console

# Validation Fixes
The mermaid code has a syntax error on line 21. The participant declaration should be `participant 1NoCollectionRule`, not `participant NoCollectionRule`. 

# Interaction After Fix

1NoCollectionRule->>Relationship: Check if line contains a relationship\
alt Relationship found in the line\
    Relationship->>1NoCollectionRule: Parse the relationship\
    alt Relationship is not found\
        1NoCollectionRule->>Console: Print error message\
    else Relationship is valid\
        1NoCollectionRule->>RuleResult: Invoke checkCollection for left class\
        alt Left check is successful\
            1NoCollectionRule->>RuleResult: Invoke checkCollection for right class\
        else Left check failed\
            1NoCollectionRule->>RuleResult: Return left check result\
    end\
else Relationship not found in the line

# Final Participants
* 1NoCollectionRule
* Relationship
* RuleResult
* Console

# Plain English Title
Check No Collection Rule

# Mermaid Sequence Diagram

```mermaid
---
title: Check No Collection Rule
---

sequenceDiagram
    participant 1NoCollectionRule
    participant Relationship
    participant RuleResult
    participant Console
    
    1NoCollectionRule->>Relationship: Check if line contains a relationship
    alt Relationship found in the line
        Relationship->>1NoCollectionRule: Parse the relationship
        alt Relationship is not found
            1NoCollectionRule->>Console: Print error message
        else Relationship is valid
            1NoCollectionRule->>RuleResult: Invoke checkCollection for left class
            alt Left check is successful
                1NoCollectionRule->>RuleResult: Invoke checkCollection for right class
            else Left check failed
                1NoCollectionRule->>RuleResult: Return left check result
        end
    else Relationship not found in the line
```

