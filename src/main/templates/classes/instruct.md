# Input

## Java code

```java

{{JAVA_CODE}}

```


# Instruction

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

## Mermaid diagram

With the examples, guidelines, `Java code`, `Class Details List`, and `Relationship Details List`,
generate a mermaid class diagram.


```mermaid
---
title: {{Plain English Title}}
---
classDiagram

    {{ClassesWithFields}}
    {{Relationships}}


```
