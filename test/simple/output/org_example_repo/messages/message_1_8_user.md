# Inputs 

## Broken Mermaid code 
```
---
title: {{Plain English Title}}
---
classDiagram



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
        "description": "mermaid image generator failed \n\nError: Evaluation failed: Error: Parse error on line 5:\nclassDiagram\n------------^\nExpecting 'direction_tb', 'direction_bt', 'direction_rl', 'direction_lr', 'acc_title', 'acc_descr', 'acc_descr_multiline_value', 'NAMESPACE', 'CLASS', 'ANNOTATION_START', 'MEMBER', 'SEPARATOR', 'NOTE_FOR', 'NOTE', 'CALLBACK', 'LINK', 'CLICK', 'CSSCLASS', 'MINUS', 'UNICODE_TEXT', 'NUM', 'ALPHA', 'BQUOTE_STR', got 'EOF'\n   "

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
