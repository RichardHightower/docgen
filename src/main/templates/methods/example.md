
## Desirable Participants:
1. NewsService
2. ArticleSorter
3. AudioTranscriber
4. ChatBot
5. PaymentProcessor
6. OrderManager
7. EmailSender
8. NotificationService
9. DatabaseManager
10. EmployeeRepo

## Undesirable Participants:
1. File
2. ChatRequest
3. AudioResponse
4. Queue
5. String
6. StoreException
7. Json 
8. EmployeeStoreException 
9. Object
10. int

# Here are five Mermaid sequence diagrams and their associated Java methods as example

## Sequence Diagram 1

```mermaid
---
title: Retrieve and Sort Articles
---
sequenceDiagram
    participant User
    participant NewsService
    participant ArticleSorter
    participant DatabaseManager
    
    User->>NewsService: Request news articles
    NewsService->>ArticleSorter: Sort articles by relevance
    ArticleSorter->>DatabaseManager: Retrieve articles from database
    DatabaseManager-->>ArticleSorter: Return articles
    ArticleSorter-->>NewsService: Return sorted articles
    NewsService-->>User: Return sorted articles
```

Java Method:

```java
public List<Article> getSortedArticles() {
    List<Article> articles = databaseManager.retrieveArticles();
    List<Article> sortedArticles = articleSorter.sortArticles(articles);
    return sortedArticles;
}
```

## Sequence Diagram 2

```mermaid
---
title: Manage Chat Conversation
---
sequenceDiagram
    participant User
    participant ChatBot
    participant ChatService
    participant DatabaseManager
    
    User->>ChatBot: Send chat message
    ChatBot->>ChatService: Process chat message
    ChatService->>DatabaseManager: Save chat message
    DatabaseManager-->>ChatService: Return success status
    ChatService-->>ChatBot: Return success status
    ChatBot-->>User: Return success status
```

Java Method:

```java
public boolean saveChatMessage(String message) {
    boolean success = chatService.processMessage(message);
    if (success) {
        boolean saved = databaseManager.saveMessage(message);
        return saved;
    }
    return false;
}
```

## Sequence Diagram 3

```mermaid
---
title: Manage Payment From User
---
sequenceDiagram
    participant User
    participant PaymentProcessor
    participant OrderManager
    participant DatabaseManager
    
    User->>PaymentProcessor: Make payment
    PaymentProcessor->>OrderManager: Process payment
    OrderManager->>DatabaseManager: Update order status
    DatabaseManager-->>OrderManager: Return success status
    OrderManager-->>PaymentProcessor: Return success status
    PaymentProcessor-->>User: Return success status
```

Java Method:

```java
public boolean processPayment(double amount) {
    boolean success = paymentProcessor.makePayment(amount);
    if (success) {
        boolean updated = orderManager.updateOrderStatus();
        return updated;
    }
    return false;
}
```

## Sequence Diagram 4

```mermaid
---
title: Find user details by their email
---
sequenceDiagram
    participant User
    participant EmailSender
    participant NotificationService
    participant UserService
    
    User->>EmailSender: Send email
    EmailSender->>NotificationService: Process email
    NotificationService->>UserService: Get user details
    UserService-->>NotificationService: Return user details
    NotificationService-->>EmailSender: Return user details
    EmailSender-->>User: Return user details
```

Java Method:

```java
public User getUserDetailsForEmail(String email) {
    User user = userService.getUserByEmail(email);
    if (user != null) {
        boolean sent = emailSender.sendEmail(user.getEmail());
        if (sent) {
            return user;
        }
    }
    return null;
}
```

## Sequence Diagram 5

```mermaid
---
title: Register User
---
sequenceDiagram
    participant User
    participant LoggingService
    participant UserService
    participant DatabaseManager
    
    User->>UserService: Register user
    UserService->>DatabaseManager: Save user details
    DatabaseManager-->>UserService: Return success status
    UserService-->>LoggingService: Log user registration
    LoggingService-->>User: Return success status
```

Java Method:

```java
public boolean registerUser(User user) {
    boolean saved = databaseManager.saveUser(user);
    if (saved) {
        boolean logged = userService.logUserRegistration(user);
        return logged;
    }
    return false;
}
```

# Now let's generate five Mermaid sequence diagrams from different Java methods.

## Java Method 1

```java
public void processOrder(Order order) {
    boolean validated = validationService.validateOrder(order);
    if (validated) {
        boolean processed = orderProcessor.processOrder(order);
        if (processed) {
            emailService.sendOrderConfirmation(order);
        }
    }
}
```

Sequence Diagram:

```mermaid
---
title: Process Order
---
sequenceDiagram
    participant Order
    participant ValidationService
    participant OrderProcessor
    participant EmailService
    
    Order->>ValidationService: Validate order
    ValidationService->>OrderProcessor: Process order
    OrderProcessor->>EmailService: Send order confirmation
```

## Java Method 2

```java
public void generateReport(String reportType) {
    Report report = reportGenerator.generate(reportType);
    if (report != null) {
        fileUploader.upload(report);
        emailService.sendReport(report);
    }
}
```

Sequence Diagram:

```mermaid
---
title: Generate Report based on Report Type
---
sequenceDiagram
    participant Report
    participant ReportGenerator
    participant FileUploader
    participant EmailService
    
    Report->>ReportGenerator: Generate report
    ReportGenerator->>FileUploader: Upload report
    FileUploader->>EmailService: Send report
```

## Java Method 3

```java
public void processPayment(double amount) {
    boolean authorized = paymentService.authorizePayment(amount);
    if (authorized) {
        boolean processed = paymentProcessor.processPayment(amount);
        if (processed) {
            notificationService.sendPaymentConfirmation(amount);
        }
    }
}
```

Sequence Diagram:

```mermaid
---
title: Process Payment
---
sequenceDiagram
    participant Payment
    participant PaymentService
    participant PaymentProcessor
    participant NotificationService
    
    Payment->>PaymentService: Authorize payment
    PaymentService->>PaymentProcessor: Process payment
    PaymentProcessor->>NotificationService: Send payment confirmation
```

## Java Method 4

```java
public void processChatMessage(ChatMessage message) {
    boolean filtered = chatFilter.filterMessage(message);
    if (filtered) {
        boolean processed = chatProcessor.processMessage(message);
        if (processed) {
            chatHistory.saveMessage(message);
        }
    }
}
```

Sequence Diagram:

```mermaid
---
title: Manage Chat Message
---
sequenceDiagram
    participant ChatMessage
    participant ChatFilter
    participant ChatProcessor
    participant ChatHistory
    
    ChatMessage->>ChatFilter: Filter message
    ChatFilter->>ChatProcessor: Process message
    ChatProcessor->>ChatHistory: Save message
```

## Java Method 5

```java
public void processImage(Image image) {
    Image processedImage = imageProcessor.process(image);
    if (processedImage != null) {
        fileUploader.upload(processedImage);
        notificationService.sendImageProcessedNotification(processedImage);
    }
}
```

Sequence Diagram:

```mermaid
---
title: Process the Image
---
sequenceDiagram
    participant Image
    participant ImageProcessor
    participant FileUploader
    participant NotificationService
    
    Image->>ImageProcessor: Process image
    ImageProcessor->>FileUploader: Upload processed image
    FileUploader->>NotificationService: Send image processed notification
```

# Error Handling Example:

```mermaid 
---
title: Handle an error
---
sequenceDiagram
    critical Establish a connection to the DB
        Service-->DB: connect
    option Network timeout
        Service-->Service: Log error
    option Credentials rejected
        Service-->Service: Log different error
    end
```

# Show critical alt and opt example 


## Java example for  critical alt and opt example
```java
class HRService {
    ...

    public void updateEmployeesManager(final Employee employee, final Manager manager) {
        if (!employee.isNew()) {
            hRWorkflow.initiateManagerChangeWorkflow(employee, manager);
        } else {
            try {
                repo.updateManager(employee, manager);
            } catch (EmployeeRepoException hrRepoException) {
                logger.log(Level.SEVERE, "Unable to handle update employee operations",
                        hrRepoException);
            }
        }
    }
}
```

## Mermaid example for critical, alt and option example


```mermaid
---
title: Update an employees' manager 
---
sequenceDiagram
    participant EmployeeDirectoryService
    participant Employee
    participant Manager
    participant HRWorkflow
    participant EmployeeRepo
    participant EmployeeRepoException
    participant Logger

    EmployeeDirectoryService->>Employee: Check if employee is new
    alt Not a new hire
        EmployeeDirectoryService->>HRWorkflow: Initiate manager change in HR Workflow
    else New hire
        EmployeeDirectoryService->>EmployeeRepo: Update manager
        critical Update successful
            EmployeeRepo-->>EmployeeDirectoryService: Return success
        option Update failed
            EmployeeRepo-->>EmployeeDirectoryService: Throw EmployeeRepoException
            EmployeeDirectoryService->>Logger: Log exception
        end    
    end
```

Note alt syntax is alt/else/end.
Note that critical syntax is critical/option/end.



---


```mermaid
---
title: Book a cruise
---

sequenceDiagram
    participant MemberDateService
    participant RulesEngine

    alt Check member has valid membership
         MemberDateService->>RulesEngine: check member date and id
         MemberDateService-->>MemberDateService: return we were unable to proceed
    else Check to see if appointment fits in schedule
        MemberDateService->>CruiseShip: parse the date string
        alt See if vaction fits
            MemberDateService-->>CruiseShip: comparing available dates
            CruiseShip-->>MemberDateService: Return comparison result
        else No Match
            MemberDateService-->>RefundService: Issue refund
        end
    end

```



# Example toString

## Input Java 

```java

public class Person {

    ...

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
```mermaid 
---
title: Convert Person to a String
---
sequenceDiagram
    participant Person
    participant StringBuilder

    Person->>StringBuilder: Append firstName
    Person->>StringBuilder: Append lastName
    Person->>StringBuilder: Append workEmail
    Person->>StringBuilder: Append email
    Person->>StringBuilder: Append address
    StringBuilder-->>Person: Return toString result

```


