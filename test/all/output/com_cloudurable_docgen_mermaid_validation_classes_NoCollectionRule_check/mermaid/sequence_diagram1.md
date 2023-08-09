----
# ORIGINAL RESPONSE 
# Participants

* NoCollectionRule
* Relationship
* RuleResult

# Initial Interactions

* NoCollectionRule checks if the line contains a relationship.
* If the line contains a relationship, parse the relationship.
* If the relationship is not found, return an error result.
* Call `checkCollection` method for the left class of the relationship.
* If the left check is successful, call `checkCollection` method for the right class of the relationship.

# Clean Interactions

* NoCollectionRule->>Relationship: Check if line contains a relationship
* alt Relationship found in the line
    * Relationship->>NoCollectionRule: Parse the relationship
    * alt Relationship is not found
        * NoCollectionRule->>Console: Print error message
    * else Relationship is valid
        * NoCollectionRule->>RuleResult: Invoke checkCollection for left class
        * alt Left check is successful
            * NoCollectionRule->>RuleResult: Invoke checkCollection for right class
        * else Left check failed
            * NoCollectionRule->>RuleResult: Return left check result
    * end
* else Relationship not found in the line

# Final Participants

* NoCollectionRule
  * checkCollection
  * Relationship.hasRelationship
* Relationship
  * parseRelationship
* Console
* RuleResult

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
