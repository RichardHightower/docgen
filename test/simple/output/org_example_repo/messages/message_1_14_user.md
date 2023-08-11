# Inputs 

## Broken Mermaid code 
```
---
title: Class Diagram for org.example.repo Package
---
classDiagram
  class HRRepo {
    <<interface>>
  }
  class Repo {
    <<interface>>
  }
  class HRRepoException extends RuntimeException {
  }
  HRRepo -->|extends| Repo
  HRRepoException -->|extends| RuntimeException

```

## Java code

```java

public interface HRRepo extends Repo<Employee, String> 


fields:
	

---

public interface Repo<T, ID> 


fields:
	

---

public class HRRepoException extends RuntimeException 


fields:
	

```



## Validation JSON

```javascript 

[
    {
        "lineNumber": 0
,
        "violatedLine": ""
,
        "ruleName": "MermaidImageGen"
,
        "description": "mermaid image generator failed \n\nError: Evaluation failed: Error: Lexical error on line 10. Unrecognized text.\n...n {  }  HRRepo -->|extends| Repo  HRR\n---------------------^\n   "

    }
]

```


# Instructions


## ***Description of Validation Issues***: Describe Validation Issues From `Validation JSON` in plain English
(markdown list)

## ***Description of Fixes Validation Issues***: Describe Validation Issues From `Validation JSON` in plain English
(markdown list)

## Create a ***Plain English Title*** for the diagram based on Package org.example.repo and overview of classes
(markdown)

## **Class Details List** : List the classes, abstract classes, interfaces and enums with a sublist of their annotations and fields
(markdown format)
* Class Name
  * Annotations
    * Annotation 1
    * Annotation 2
  * Fields
    * Field 1
    * Field 2
    * Field 3

## **Relationship Details List** : List the relationships and describe why you picked that relationship type and its cardinality. Do not include cardinality 1 to 1 in the description or final diagram.
(markdown format)

## Mermaid diagram

With the examples as an example, guidelines to guide, regenerate a mermaid class diagram and fix validation issues
    * use the `Java code`, `Class Details List`, `Description of Fixes Validation Issues` and `Relationship Details List`
    * Do not use any classes not mentioned in the `Java code` section. Do not make up classes not in the diagram.
    * Do not specify cardinality for 1 to 1 relationships.
    * Do specify cardinality for 1 to many, 1 to 4, etc.

```mermaid
---
title: {{Plain English Title}}
---
classDiagram


```
