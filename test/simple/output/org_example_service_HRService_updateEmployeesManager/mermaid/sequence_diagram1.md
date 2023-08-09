----
# ORIGINAL RESPONSE 
# Participants

* HRService
* Employee
* Manager
* WorkflowService
* Logger
* HRRepo
* HRRepoException

# Initial Interactions

1. HRService receives a request to update an employee's manager.
2. HRService checks if the employee is a new hire.
3. If the employee is not a new hire:
   - HRService initiates a manager change workflow using the WorkflowService.
4. If the employee is a new hire:
   - HRService updates the manager of the employee in the HRRepo.
   - If an HRRepoException occurs during the update:
     - HRService logs the exception using the Logger.

# Clean Interactions

1. HRService --> Employee: Check if new hire
2. alt Employee is not a new hire
   - HRService ->> WorkflowService: Initiate Manager Change Workflow
3. else Employee is a new hire
   - HRService ->> HRRepo: Update Manager
   - alt Update successful
     - HRService --> HRRepo: Log Update Employee Success
   - else Update failed
     - HRService --> HRRepo: Log Update Employee Failure
   - HRService ->> Logger: Log HRRepoException
4. HRService -->> HRRepo: Update Employee Result

# Final Participants

* HRService
   - alt Employee is not a new hire (Interaction 2)
      - WorkflowService (Interaction 2)
   - else Employee is a new hire (Interaction 3)
      - HRRepo (Interaction 3)
      - Logger (Interaction 3)

# Plain English Title

HRService: Update Employee's Manager

# Mermaid Sequence Diagram

```mermaid
---
title: HRService: Update Employee's Manager
---

sequenceDiagram
    participant HRService
    participant Employee
    participant Manager
    participant WorkflowService
    participant HRRepo
    participant Logger

    HRService->>Employee: Check if new hire
    alt Employee is not a new hire
        HRService ->> WorkflowService: Initiate Manager Change Workflow
    else Employee is a new hire
        HRService ->> HRRepo: Update Manager
        alt Update successful
            HRService --> HRRepo: Log Update Employee Success
        else Update failed
            HRService --> HRRepo: Log Update Employee Failure
        end
        HRService ->> Logger: Log HRRepoException
    end
    HRService -->> HRRepo: Update Employee Result
```
