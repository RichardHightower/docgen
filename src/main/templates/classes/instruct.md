# Instruction

Let's walk through process of creating a mermaid class diagram and then create a mermaid class diagram.

## Input - Use this ***Java code*** if present

```java

{{JAVA_CODE}}

```

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
    * Do not use any classes not mentioned in the `Java code` section. Do not make up classes not in the diagram.
    * Do not specify cardinality for 1 to 1 relationships. 
    * Specify cardinality for 1 to many, 1 to 4, etc. 

```mermaid
---
title: {{Plain English Title}}
---
classDiagram


```
