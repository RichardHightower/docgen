# Inputs 

## Broken Mermaid code 
```
{{MERMAID}}
```

## Java code

```java

{{JAVA_CODE}}

```



## Validation JSON

```javascript 

{{JSON}}

```


# Instructions


## ***Description of Validation Issues***: Describe Validation Issues From `Validation JSON` in plain English
(markdown list)

## ***Description of Fixes Validation Issues***: Describe Validation Issues From `Validation JSON` in plain English
(markdown list)

## Create a ***Plain English Title*** for the diagram based on {{TITLE}} and overview of classes
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


## **Relationship Details List** : List the relationships and describe why you picked that relationship type and its cardinality
(markdown format)

## Fixed Mermaid diagram 

With the `Java code`, `Description of Fixes Validation Issues`, `Class Details List`, and `Relationship Details List`, 
regenerate a mermaid class diagram.

```mermaid
---
title: {{Plain English Title}}
---
classDiagram

    {{ClassesWithFields}}

    {{Relationships}}

}
```
