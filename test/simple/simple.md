
## DIR ./test/simple/simple


## DIR ./test/simple/simple/gradle


## DIR ./test/simple/simple/gradle/wrapper


## DIR ./test/simple/simple/src


## DIR ./test/simple/simple/src/test


## DIR ./test/simple/simple/src/test/resources


## DIR ./test/simple/simple/src/test/java


## DIR ./test/simple/simple/src/main


## DIR ./test/simple/simple/src/main/resources


## DIR ./test/simple/simple/src/main/java


## DIR ./test/simple/simple/src/main/java/org


## DIR ./test/simple/simple/src/main/java/org/example


## DIR ./test/simple/simple/src/main/java/org/example/model


## FILE ./test/simple/simple/src/main/java/org/example/model/Team.java

```java
package org.example.model;

import java.util.Map;

public class Team {
    private final Map<Department, Employee> teamMembers;


    public Team(Map<Department, Employee> teamMembers) {
        this.teamMembers = teamMembers;
    }

    public Map<Department, Employee> getTeamMembers() {
        return teamMembers;
    }
}

```

## FILE ./test/simple/simple/src/main/java/org/example/model/Manager.java

```java
package org.example.model;

import java.util.List;

public class Manager {

    private final Employee manager;
    private final List<Employee> employees;

    public Manager(Employee manager, List<Employee> employees) {
        this.manager = manager;
        this.employees = employees;
    }


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Manager{");
        sb.append("manager=").append(manager);
        sb.append(", employees=[\n");
        for (var employee : employees) {
            sb.append("\t").append(employee).append(",\n");
        }
        sb.append("]\n");
        sb.append('}');
        return sb.toString();
    }

}

```

## FILE ./test/simple/simple/src/main/java/org/example/model/Customer.java

```java
package org.example.model;

public class Customer extends Person {
    private final boolean elite;

    public Customer(String firstName, String lastName, String workEmail, String email, Address address, boolean elite) {
        super(firstName, lastName, workEmail, email, address);
        this.elite = elite;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "elite=" + elite +
                '}';
    }
}

```

## FILE ./test/simple/simple/src/main/java/org/example/model/Department.java

```java
package org.example.model;

public class Department {
    private final Manager manager;

    public Department(Manager manager) {
        this.manager = manager;
    }

    public Manager getManager() {
        return manager;
    }
}

```

## FILE ./test/simple/simple/src/main/java/org/example/model/Address.java

```java
package org.example.model;

public class Address {

    private final String street;
    private final String town;

    private final String country;

    private final String postalCode;

    public Address(String street, String town, String country, String postalCode) {
        this.street = street;
        this.town = town;
        this.country = country;
        this.postalCode = postalCode;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Address{");
        sb.append("street='").append(street).append('\'');
        sb.append(", town='").append(town).append('\'');
        sb.append(", country='").append(country).append('\'');
        sb.append(", postalCode='").append(postalCode).append('\'');
        sb.append('}');
        return sb.toString();
    }

}

```

## FILE ./test/simple/simple/src/main/java/org/example/model/Person.java

```java
package org.example.model;

import java.util.Objects;

public class Person {

    private final String firstName;
    private final String lastName;

    private final String workEmail;

    private final String email;

    private final Address address;


    public Person(String firstName, String lastName, String workEmail, String email,
                  Address address) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.workEmail = workEmail;
        this.email = email;
        this.address = address;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Person)) return false;
        Person person = (Person) o;
        return Objects.equals(firstName, person.firstName) && Objects.equals(lastName, person.lastName) && Objects.equals(workEmail, person.workEmail) && Objects.equals(email, person.email) && Objects.equals(address, person.address);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName, workEmail, email, address);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Person{");
        sb.append("firstName='").append(firstName).append('\'');
        sb.append(", lastName='").append(lastName).append('\'');
        sb.append(", workEmail='").append(workEmail).append('\'');
        sb.append(", email='").append(email).append('\'');
        sb.append(", address=").append(address);
        sb.append('}');
        return sb.toString();
    }

}

```

## FILE ./test/simple/simple/src/main/java/org/example/model/Employee.java

```java
package org.example.model;

public class Employee extends Person {

    private final boolean newHire;

    public Employee(String firstName, String lastName, String workEmail, String email,
                    boolean newHire, Address address) {
        super(firstName, lastName, workEmail, email, address);
        this.newHire = newHire;
    }

    public boolean isNewHire() {
        return newHire;
    }
}

```

## DIR ./test/simple/simple/src/main/java/org/example/service


## FILE ./test/simple/simple/src/main/java/org/example/service/HRService.java

```java
package org.example.service;

import org.example.model.Department;
import org.example.model.Employee;
import org.example.model.Manager;
import org.example.repo.HRRepo;
import org.example.repo.HRRepoException;

import java.util.logging.Level;
import java.util.logging.Logger;


@Service
public class HRService {
    private final HRRepo repo;
    private final WorkflowService workflow;

    private final Logger logger = Logger.getLogger(HRService.class.getName());

    public HRService(HRRepo repo, WorkflowService workflow) {
        this.repo = repo;
        this.workflow = workflow;
    }


    public void updateEmployeesManager(final Employee employee, final Manager manager) {
        if (!employee.isNewHire()) {
            workflow.initiateManagerChangeWorkflow(employee, manager);
        } else {
            try {
                repo.updateManager(employee, manager);
            } catch (HRRepoException hrRepoException) {
                logger.log(Level.SEVERE, "Unable to handle update employee operations",
                        hrRepoException);
            }
        }
    }

    public void changeEmployeeDepartment(final Employee employee, final Department department) {
        if (!employee.isNewHire()) {
            workflow.initiateChangeDepartmentWorkflow(employee, department);
        } else {
            try {
                repo.updateManager(employee, department.getManager());
                repo.updateDepartment(employee, department);
            } catch (HRRepoException hrRepoException) {
                logger.log(Level.SEVERE, "Unable to handle update employee operations",
                        hrRepoException);
            }
        }
    }



}

```

## FILE ./test/simple/simple/src/main/java/org/example/service/WorkflowService.java

```java
package org.example.service;

import org.example.model.Department;
import org.example.model.Employee;
import org.example.model.Manager;

public class WorkflowService {

    public void initiateManagerChangeWorkflow(Employee employee, Manager manager) {

    }

    public void initiateChangeDepartmentWorkflow(Employee employee, Department department) {

    }
}

```

## FILE ./test/simple/simple/src/main/java/org/example/service/Service.java

```java
package org.example.service;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface Service {
}

```

## DIR ./test/simple/simple/src/main/java/org/example/repo


## FILE ./test/simple/simple/src/main/java/org/example/repo/HRRepo.java

```java
package org.example.repo;

import org.example.model.Department;
import org.example.model.Employee;
import org.example.model.Manager;

public interface HRRepo extends Repo<Employee, String> {

    @Override
    default void save(Employee employee) {

    }

    @Override
    default void lookup(String s) {

    }

    void updateManager(Employee employee, Manager manager);

    void updateDepartment(Employee employee, Department department);
}

```

## FILE ./test/simple/simple/src/main/java/org/example/repo/Repo.java

```java
package org.example.repo;

public interface Repo<T, ID> {

    void save(T t);
    void lookup(ID id);



}

```

## FILE ./test/simple/simple/src/main/java/org/example/repo/HRRepoException.java

```java
package org.example.repo;

public class HRRepoException extends RuntimeException{
}

```

## FILE ./test/simple/simple/src/main/java/org/example/Main.java

```java
package org.example;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) {
        // Press Opt+Enter with your caret at the highlighted text to see how
        // IntelliJ IDEA suggests fixing it.
        System.out.printf("Hello and welcome!");

        // Press Ctrl+R or click the green arrow button in the gutter to run the code.
        for (int i = 1; i <= 5; i++) {

            // Press Ctrl+D to start debugging your code. We have set one breakpoint
            // for you, but you can always add more by pressing Cmd+F8.
            System.out.println("i = " + i);
        }
    }
}
```
