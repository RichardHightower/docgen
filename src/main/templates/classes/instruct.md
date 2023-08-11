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

## **Relationship Details List** : List the relationships and describe why you picked that relationship type and its cardinality. Do not include cardinality 1 to 1 in the description or final diagram.
(markdown format)

## Mermaid diagram

With the examples as an example, guidelines to guide, generate a mermaid class diagram
    * use the `Java code`, `Class Details List`, and `Relationship Details List`
    * Do not add any relationships not in `Relationship Details List`. 
    * Do not specify cardinality for 1 to 1 relationships. 
    * Do specify cardinality for 1 to many, 1 to 4, etc. 

```mermaid
---
title: {{Plain English Title}}
---
classDiagram

    {{ClassesWithFields}}
    {{Relationships}}


```
