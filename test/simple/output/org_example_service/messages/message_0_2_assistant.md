# Part 1: Generating examples 

## Java Input 1:

```java
public interface Computable { }

@Product 
public class Computer {
    private Processor processor;
    private List<MemoryModule> ramModules;
}

@Component
public class Processor { }

@Component
public class MemoryModule { }

```

## Mermaid Output 1:

```mermaid
---
title: Computer Components Relationship
---
classDiagram

    class Computable{
        <<interface>>
    }

    class Computer{
        <<Product>>
        Processor processor
        List<MemoryModule> ramModules
    }

    class Processor {
      <<Component>>
      MemoryModule l1Cache
      MemoryModule l2Cache
      MemoryModule l3Cache
    }

    class MemoryModule {
      <<Component>>
      Processor processor
    }

    Processor ..|> Computable: implements
    Processor *-- MemoryModule: l1Cache
    Processor *-- MemoryModule: l2Cache
    Processor *-- MemoryModule: l3Cache
    MemoryModule --> Processor: processor
    Computer *-- Processor: has one processor
    Computer *-- MemoryModule: has multiple RAM modules

```

## Java Input 2:

```java
@Component
public interface Task { }

public abstract class RegularTask implements Task {
    private List<Item> items;
}

public class UrgentTask extends RegularTask implements Deadline {
    private Clock clock;
}

@ManagedBean
public class Clock {
    private Time time;
}

public class Time {
}

public class Item {
}

public interface Deadline {
}

```

## Mermaid Output 2:

```mermaid
---
title: Most Complex Example
---
classDiagram
    
    class TaskPriority {
        <<enumeration>>
        HIGH
        LOW
        URGENT
        IMPORTANT
    }
    class Task{
        <<interface>>
        <<Component>>
    }
    class RegularTask{
        <<abstract>>
        List<Item> items
    }
    class UrgentTask{
        Clock clock
    }
    class Clock{
        <<ManagedBean>>
        Time time
    }
    class Time{
    }
    class Item{
    }
    class Deadline{
        <<interface>>
    }

    RegularTask ..|> Task: implements
    UrgentTask --|> RegularTask: extends
    UrgentTask ..|> Deadline: implements
    UrgentTask --> Clock: uses
    Clock --> Time: shows

```


# Company Building Example 

## Company Infrastructure and Relationships

- **Building Types**:
    - `Skyscrapers`, `Warehouses`, and `Factories` are all types of `Buildings`.  (inheritance). 
    - While a company owns buildings, buildings can exist even without being associated with a company.

- **Ownership**:
    - A `Company` owns multiple types of `Buildings` such as `Skyscrapers`, `Warehouses`, and `Factories`.

- **Company and Departments**:
    - A `Company` consists of multiple `Departments`. This relationship is a composition since departments are intrinsic to the company.

- **Department and Employees**:
    - Each `Department` includes multiple `Employees`. This is also a composition relationship.

- **Employee's Resources**:
    - An `Employee` might use a `Desk` and a `Laptop`. This is an association since they can exist independently of the employee.

- **Vendors**:
    - A `Company` might have associations with multiple `Vendors` to provide services or goods.


## Input Mermaid Class Diagram



```mermaid
---
title: Company Infrastructure and Relationships
---
classDiagram

    class Company{
    }
    class Building{
    }
    class Skyscraper{
    }
    class Warehouse{
    }
    class Factory{
    }
    class Department{
    }
    class Employee{
        Desk desk
        Laptop laptop
    }
    class Vendor{
    }
    class Desk{
    }
    class Laptop{
    }

    Company "one" o-- "many" Building: owns
    Company "1" *-- "*" Department: has
    Department *-- Employee: includes
    Employee "1" --> "1" Desk: uses
    Employee "1" --> "1" Laptop: uses
    Company "1" --> "*" Vendor: associated with

    Skyscraper --|> Building
    Warehouse --|> Building
    Factory --|> Building

```

## Output Java 

```java 

public abstract class Building {
    private Company owner;
    // ... other attributes and methods
}

public class Skyscraper extends Building {
    // ... attributes and methods specific to Skyscraper
}

public class Warehouse extends Building {
    // ... attributes and methods specific to Warehouse
}

public class Factory extends Building {
    // ... attributes and methods specific to Factory
}

public class Company {
    private List<Building> buildings;
    private List<Department> departments;
    private List<Vendor> vendors;
    // ... other attributes and methods
}

public class Department {
    private List<Employee> employees;
    // ... other attributes and methods
}

public class Employee {
    private Desk desk;
    private Laptop laptop;
    // ... other attributes and methods
}

public class Vendor {
    // ... attributes and methods related to vendor
}

public class Desk {
    // ... attributes and methods related to the desk
}

public class Laptop {
    // ... attributes and methods related to the laptop
}

```
