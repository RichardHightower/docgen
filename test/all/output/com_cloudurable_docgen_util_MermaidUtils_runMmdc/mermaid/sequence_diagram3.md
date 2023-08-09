----
# FIX RAW RESPONSE 2
# Participants

* MermaidUtils
* StringBuilder
* Reference
* Latch
* Service
* Reader
* Task
* Result
* FileUtils
* CountDownLatch
* BufferedReader
* Process
* Runtime
* InputStreamReader
* String
* IOException
* IllegalStateException
* AtomicReference
* ExecutorService
* TimeUnit

# Validation Fixes

1. Fix `IllegalStateException`. Change participant name from `IllegalStateException` to "Handled Error" since it is an error condition in the code.
2. Fix `AtomicReference`. Change participant name from `AtomicReference` to "Reference".
3. Fix `ExecutorService`. Change participant name from `ExecutorService` to "Service".

# Interaction After Fix

1. MermaidUtils ->> StringBuilder: Create output and error StringBuilders
2. MermaidUtils ->> Latch: Initialize with count 2
3. MermaidUtils ->> Service: Create new Service
4. MermaidUtils ->> Task: Execute command
5. Service ->> Reader: Submit read output task
6. Service ->> Reader: Submit read error task
7. MermaidUtils ->> Task: Wait for process to complete
8. alt Timeout
    - MermaidUtils ->> StringBuilder: Append timeout message to error StringBuilder
9. MermaidUtils ->> Service: Shut down Service
10. MermaidUtils ->> Task: Get exit code
11. alt Exit code not zero
    - MermaidUtils ->> FileUtils: Read input file
    - MermaidUtils ->> Result: Create Result with exit code, output StringBuilder, error StringBuilder, and null exception
    - Result -->> MermaidUtils: Return Result
12. else Exit code zero
13. end

# Final Participants

* MermaidUtils
* StringBuilder
* Latch
* Service
* Reader
* Task
* Result
* FileUtils

# Plain English Title

"Execute Mermaid Command (MermaidUtils)"

# Mermaid Sequence Diagram

```mermaid
---
title: Execute Mermaid Command (MermaidUtils)
---

sequenceDiagram
    participant MermaidUtils
    participant StringBuilder
    participant Latch
    participant Service
    participant Reader
    participant Task
    participant Result
    participant FileUtils

    note right of MermaidUtils: Executes the mermaid command
    MermaidUtils ->> StringBuilder: Create output and error StringBuilders
    MermaidUtils ->> Latch: Initialize with count 2
    MermaidUtils ->> Service: Create new Service
    MermaidUtils ->> Task: Execute command
    Service ->> Reader: Submit read output task
    Service ->> Reader: Submit read error task
    MermaidUtils ->> Task: Wait for process to complete
    alt Timeout
        MermaidUtils ->> StringBuilder: Append timeout message to error StringBuilder
    MermaidUtils ->> Service: Shut down Service
    MermaidUtils ->> Task: Get exit code
    alt Exit code not zero
        MermaidUtils ->> FileUtils: Read input file
        MermaidUtils ->> Result: Create Result with exit code, output StringBuilder, error StringBuilder, and null exception
        Result -->> MermaidUtils: Return Result
    else Exit code zero
    end
```