----
# ORIGINAL RESPONSE 
# Participants
* NoPrimitiveOrBasicTypesRule
* Pattern
* Matcher
* RuleResult

# Initial Interactions
The `NoPrimitiveOrBasicTypesRule` class defines a method called `checkCollection` that takes in parameters `side`, `input`, `line`, `lineNumber`. 
In this method, the `PATTERN` `Pattern` object is created with the regex pattern to match basic or primitive types. 
Then, a `Matcher` object is created using the `PATTERN` and the `input` parameter. The `Matcher` object is used to find a match for the regex pattern in the `input` string.
If a match is found, the `type` is extracted from the matched group and used to construct a `RuleResult` object with the relevant information.
If no match is found, a `RuleResult.SUCCESS` object is returned.

# Clean Interactions
The `NoPrimitiveOrBasicTypesRule` class defines a method called `checkCollection` that takes in parameters `side`, `input`, `line`, `lineNumber`. 

* `NoPrimitiveOrBasicTypesRule->>Pattern: Create PATTERN`
* `NoPrimitiveOrBasicTypesRule->>Matcher: Create Matcher`
* `Matcher->>Matcher: Find match in input`
* `alt Match found`
    * `Matcher->>Matcher: Get matched type`
    * `NoPrimitiveOrBasicTypesRule-->>RuleResult: Create RuleResult with type`
* `else No match found`
    * `NoPrimitiveOrBasicTypesRule-->>RuleResult: Create RuleResult SUCCESS`

# Final Participants
* NoPrimitiveOrBasicTypesRule
  * `NoPrimitiveOrBasicTypesRule->>Pattern: Create PATTERN`
  * `NoPrimitiveOrBasicTypesRule->>Matcher: Create Matcher`
  * `Matcher->>Matcher: Find match in input`
  * `alt Match found`
    * `Matcher->>Matcher: Get matched type`
    * `NoPrimitiveOrBasicTypesRule-->>RuleResult: Create RuleResult with type`
  * `else No match found`
    * `NoPrimitiveOrBasicTypesRule-->>RuleResult: Create RuleResult SUCCESS`

# Plain English Title
Sequence Diagram for NoPrimitiveOrBasicTypesRule.checkCollection method

# Mermaid Sequence Diagram
```mermaid
---
title: Sequence Diagram for NoPrimitiveOrBasicTypesRule.checkCollection method
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
