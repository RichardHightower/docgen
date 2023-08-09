----
# FIX RAW RESPONSE 3
# Participants
Desirable participants:
1. MermaidUtils
2. File
3. StringBuilder
4. AtomicReference
5. CountDownLatch
6. ExecutorService
7. BufferedReader
8. InputStreamReader
9. InputStream
10. Process

# Validation Fixes
1. Remove the participant "File" because it is considered a primitive.
2. Replace "AtomicReference" with "Reference" to adhere to the guideline.
3. Replace "CountDownLatch" with "Latch" to adhere to the guideline.
4. Replace "ExecutorService" with "Service" to adhere to the guideline.
5. Replace "BufferedReader" with "Reader" to adhere to the guideline.
6. Replace "InputStreamReader" with "Reader" to adhere to the guideline.
7. Replace "InputStream" with "Input" to adhere to the guideline.
8. Replace "Process" with "Task" to adhere to the guideline.
9. Remove "FileUtils" as it is not used in the interaction.
10. Replace angle brackets "<>" with "~" for generic classes.

# Interaction After Fix
1. MermaidUtils creates and initializes output and error StringBuilders.
2. MermaidUtils creates a new Latch and ExecutorService.
3. MermaidUtils executes a command by creating a new Task.
4. ExecutorService submits read output and read error tasks to BufferedReader.
5. MermaidUtils waits for the process to complete.
6. If there is a Timeout, MermaidUtils appends a timeout message to the error StringBuilder.
7. MermaidUtils shuts down the ExecutorService.
8. MermaidUtils gets the exit code of the process.
9. If the exit code is not zero, MermaidUtils reads the input file using FileUtils and creates a Result with the exit code, output StringBuilder, error StringBuilder, and null exception.
10. If the exit code is zero, the sequence ends.
11. Execution continues to the end of the sequence.

# Final Participants
Participants:
1. MermaidUtils
2. StringBuilder
3. Reference
4. Latch
5. Service
6. Reader
7. Task

# Plain English Title
runMmdc - Execute Mermaid Command

# Mermaid Sequence Diagram
```mermaid
---
title: runMmdc - Execute Mermaid Command
---

sequenceDiagram
    participant MermaidUtils
    participant StringBuilder
    participant Reference
    participant Latch
    participant Service
    participant Reader
    participant Task

    MermaidUtils ->> StringBuilder: Create output and error StringBuilders
    MermaidUtils ->> Latch: Initialize with count 2
    MermaidUtils ->> Service: Create new ExecutorService
    MermaidUtils ->> Task: Execute command
    Service ->> Reader: Submit read output task
    Service ->> Reader: Submit read error task
    MermaidUtils ->> Task: Wait for process to complete
    alt Timeout
        MermaidUtils ->> StringBuilder: Append timeout message to error StringBuilder
    MermaidUtils ->> Service: Shut down ExecutorService
    MermaidUtils ->> Task: Get exit code
    alt Exit code not zero
        MermaidUtils ->> FileUtils: Read input file
        MermaidUtils ->> Result: Create Result with exit code, output StringBuilder, error StringBuilder, and null exception
        Result -->> MermaidUtils: Return Result
    else Exit code zero
    end
```
