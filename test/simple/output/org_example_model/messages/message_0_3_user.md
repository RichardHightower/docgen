# Input

## Java code

```java

public class Manager 


fields:
	private final Employee manager;
	private final List<Employee> employees;

---

public class Customer extends Person 


fields:
	private final boolean elite;

---

public class Department 


fields:
	private final Manager manager;

---

public class Address 


fields:
	private final String street;
	private final String town;
	private final String country;
	private final String postalCode;

---

public class Person 


fields:
	private final String firstName;
	private final String lastName;
	private final String workEmail;
	private final String email;
	private final Address address;

---

public class Employee extends Person 


fields:
	private final boolean newHire;

```


# Instruction

## Create a ***Plain English Title*** for the diagram based on Package org.example.model and overview of classes
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
