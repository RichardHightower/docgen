# Desirable Participants

1. NewsService
2. ArticleSorter
3. PaymentProcessor
4. EmailService
5. UserService

# Undesirable Participants

1. String
2. Integer
3. float
4. boolean
5. byte[]

# Java Method 1: Fibonacci Series

## Java Code
```java
public class Fibonacci {
    public void generateFibonacciSeries(int limit) {
        int first = 0;
        int second = 1;
        int next;

        System.out.print("Fibonacci Series: " + first + ", " + second);

        for (int i = 2; i < limit; i++) {
            next = first + second;
            System.out.print(", " + next);
            first = second;
            second = next;
        }
    }
}
```

## Mermaid Sequence Diagram
```mermaid
---
title: Fibonacci Series
---
sequenceDiagram


    participant Fibonacci
    participant Console

    Fibonacci->>Console: Print Fibonacci Series
    loop Generate Fibonacci Numbers
        Fibonacci->>Console: Print Fibonacci Number
    end
```

# Java Method 2: File Processing

## Java Code
```java
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class FileProcessor {
    public void processFile(String inputFilePath, String outputFilePath) {
        BufferedReader reader = null;
        BufferedWriter writer = null;

        try {
            reader = new BufferedReader(new FileReader(inputFilePath));
            writer = new BufferedWriter(new FileWriter(outputFilePath));

            String line;
            while ((line = reader.readLine()) != null) {
                // Process the line
                String processedLine = processLine(line);
                writer.write(processedLine);
                writer.newLine();
            }
        } catch (IOException e) {
            // Handle file I/O exception
            e.printStackTrace();
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
                if (writer != null) {
                    writer.close();
                }
            } catch (IOException e) {
                // Handle file close exception
                e.printStackTrace();
            }
        }
    }
//...
}
```

## Mermaid Sequence Diagram
```mermaid
---
title: File Processing
---
sequenceDiagram
    participant FileProcessor
    participant BufferedReader
    participant BufferedWriter
    participant FileReader
    participant FileWriter

    critical read input file, process them a line at a time to output file

        BufferedReader ->> FileReader: Open the input file
        FileProcessor ->> BufferedReader: Create a new BufferedReader
        BufferedWriter ->> FileWriter: Open the output file
        FileProcessor ->> BufferedWriter: Create a new BufferedWriter

        loop While line is not null read another line from the input file
            BufferedReader ->> FileProcessor: Read a line
            FileProcessor ->> BufferedWriter: Write the line to the output file
        end
    option IOException Handle I/O Exception
        FileProcessor->>FileProcessor: Handle file I/O exception
    option finally Clean up
        FileProcessor->>BufferedReader: Close
        FileProcessor->>BufferedWriter: Close
    end

```

# Java Method 3: Employee Management

## Java Code
```java
import java.util.ArrayList;
import java.util.List;

public class EmployeeManagement {
    private List<Employee> employees;
   //..
    public void performOperation(int operation) {
        switch (operation) {
            case 1:
                // Add employee
                Employee employee = createEmployee();
                addEmployee(employee);
                break;
            case 2:
                // Remove employee
                Employee employeeToRemove = selectEmployee();
                removeEmployee(employeeToRemove);
                break;
            case 3:
                // Get employee by ID
                int id = enterEmployeeId();
                Employee employeeById = getEmployeeById(id);
                displayEmployee(employeeById);
                break;
            default:
                System.out.println("Invalid operation");
                break;
        }
    }
    //..
}
```

## Mermaid Sequence Diagram
```mermaid
---
title: Employee Management
---
sequenceDiagram
    
    participant EmployeeManagement
    participant Employee
    participant Console

    alt Add Employee operation 1
        EmployeeManagement->>Employee: Create Employee

    else Remove Employee operation 2

        EmployeeManagement->>Employee: Remove Employee
    else Get Employee By ID operation 3
        EmployeeManagement->>Employee: Get Employee by ID
        EmployeeManagement->>Console: Display Employee
    else default Invalid operation
        EmployeeManagement->>Console: Invalid operation
    end
```

# Error Handling Example

## Java Code
```java
public class Calculator {
    public void divideNumbers(int dividend, int divisor) {
        try {
            int result = dividend / divisor;
            System.out.println("Result: " + result);
        } catch (ArithmeticException e) {
            System.out.println("Error: Division by zero");
        }
    }
}
```

## Mermaid Sequence Diagram
```mermaid
---
title: Error Handling
---
sequenceDiagram
    participant Calculator
    participant Console

    critical divide dividend by divisor
        Calculator->>Console: Print Result    
    option
        Calculator->>Console: Print Error
    end
```

# alt/else/end Example

## Java Code
```java
public class NumberProcessor {
    public void processNumber(int number) {
        if (number > 0) {
            System.out.println("Number is positive");
        } else if (number < 0) {
            System.out.println("Number is negative");
        } else {
            System.out.println("Number is zero");
        }
    }
}
```

## Mermaid Sequence Diagram
```mermaid
---
title: Number Processing
---
sequenceDiagram
    participant NumberProcessor
    participant Console
    alt Number is positive
        NumberProcessor->>Console: Print Positive Number
        
    else Number is negative
        NumberProcessor->>Console: Print Negative Number
    else  Number is zero
        NumberProcessor->>Console: Print Zero Number
    end
```

# Complex Error Handling Example

## Java Code
```java
public class ComplexErrorHandling {
    public void processRequest(Request request) {
        try {
            validateRequest(request);
            process(request);
        } catch (InvalidRequestException e) {
            System.out.println("Invalid request: " + e.getMessage());
        } catch (UnauthorizedAccessException e) {
            System.out.println("Unauthorized access: " + e.getMessage());
        } catch (DatabaseException e) {
            System.out.println("Database error: " + e.getMessage());
        } catch (NetworkException e) {
            System.out.println("Network error: " + e.getMessage());
        } finally {
            cleanup();
        }
    }
}

```

## Mermaid Sequence Diagram
```mermaid
---
title: Complex Error Handling
---
sequenceDiagram


    participant ComplexErrorHandling
    participant Console

    ComplexErrorHandling->>Console: Print Invalid Request
    critical  Invalid request
        ComplexErrorHandling->>Console: Print Unauthorized Access
    option Unauthorized access
        ComplexErrorHandling->>Console: Print Database Error
    option Database error
        ComplexErrorHandling->>Console: Print Network Error
    option Network error
    end
    ComplexErrorHandling->>Console: Cleanup
```

# Generating toString using StringBuilder

## Java Code
```java
import java.util.List;

public class Department {
    private String name;
    private Employee manager;
    private List<Employee> employees;

    // Getters and setters

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Department: ").append(name).append("\n");
        sb.append("Manager: ").append(manager).append("\n");
        sb.append("Employees: ").append("\n");
        for (Employee employee : employees) {
            sb.append(employee).append("\n");
        }
        return sb.toString();
    }
}
//...
```

## Mermaid Sequence Diagram
```mermaid
---
title: Generating toString
---
sequenceDiagram


    participant Department
    participant StringBuilder
    
    Department->>StringBuilder: Append Department Name
    Department->>StringBuilder: Append Manager
    Department->>StringBuilder: Append Employees
    loop Append Employees
        Department->>StringBuilder: Append Employee
    end
    Department->>StringBuilder: Convert to String
```
