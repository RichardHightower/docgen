----
# ORIGINAL RESPONSE 
# Participants
- NoArrayRule
- Pattern
- Relationship
- RuleResult

# Initial Interactions
- NoArrayRule creates a Pattern object using `Pattern.compile()`
- NoArrayRule checks if the line contains a relationship using `Relationship.hasRelationship()`
- NoArrayRule parses the relationship using `Relationship.parseRelationship()`
- NoArrayRule checks if the parsed relationship is NOT_FOUND
- NoArrayRule checks for arrays in the left class of the relationship using `checkForArray()`
- NoArrayRule checks for arrays in the right class of the relationship using `checkForArray()`

# Clean Interactions
- NoArrayRule creates a Pattern object using `Pattern.compile()`
- NoArrayRule checks if the line contains a relationship
- alt The parsed relationship is NOT_FOUND
  - NoArrayRule returns a RuleResult with the violated line, rule name, and description
- else The parsed relationship is a valid relationship
  - NoArrayRule checks for arrays in the left class of the relationship
  - alt There are arrays in the left class
    - NoArrayRule returns a RuleResult with the violated line, rule name, and description
  - else There are no arrays in the left class
    - NoArrayRule checks for arrays in the right class of the relationship
    - alt There are arrays in the right class
      - NoArrayRule returns a RuleResult with the violated line, rule name, and description
    - else There are no arrays in the right class
      - NoArrayRule returns RuleResult.SUCCESS

# Final Participants
- NoArrayRule
  - [checkForArray(), inside alt, desc="Check for arrays in class"]
- Pattern
  - [pattern, inside alt, desc="Create Pattern"]
- Relationship
  - [hasRelationship(), inside NoArrayRule, desc="Check if line has relationship"]
  - [parseRelationship(), inside NoArrayRule, desc="Parse relationship"]
- RuleResult
  - [SUCCESS, inside alt, desc="Returned when a check is successful"]
  - [builder(), inside alt, desc="Create RuleResult builder"]
  - [ruleName(), inside alt, desc="Set rule name"]
  - [lineNumber, inside alt, desc="Set line number"]
  - [violatedLine, inside alt, desc="Set violated line"]
  - [description(), inside alt, desc="Set description"]

# Plain English Title
Check No Array Rule

# Mermaid Sequence Diagram
```mermaid
---
title: Check No Array Rule
---

sequenceDiagram
    participant NoArrayRule
    participant Pattern
    participant Relationship
    participant RuleResult

    NoArrayRule->>Pattern: Create Pattern
    NoArrayRule->>Relationship: Check if line has relationship
    alt Parsed relationship is NOT_FOUND
        NoArrayRule->>RuleResult: Create RuleResult
        RuleResult-->>NoArrayRule: Returns RuleResult
    else Parsed relationship is a valid relationship
        NoArrayRule->>Relationship: Parse relationship
        alt There are arrays in the left class
            NoArrayRule->>RuleResult: Create RuleResult
            RuleResult-->>NoArrayRule: Returns RuleResult
        else There are no arrays in the left class
            NoArrayRule->>Relationship: Check for arrays in class
            alt There are arrays in the right class
                NoArrayRule->>RuleResult: Create RuleResult
                RuleResult-->>NoArrayRule: Returns RuleResult
            else There are no arrays in the right class
                NoArrayRule->>RuleResult: Return RuleResult.SUCCESS
    end
```
