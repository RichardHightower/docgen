----
# FIX RAW RESPONSE 3
## Participants

- ClassVisitorParser
- Object
- Pattern
- JavaItem
- CompilationUnit
- StringBuilder

## Validation Fixes

- In line 7, remove `String` as a participant and replace it with `ClassVisitorParser`.
- In line 15, remove `String` as a participant and replace it with `ClassVisitorParser`.
- In line 21, remove `String` as a participant and replace it with `ClassVisitorParser`.

## Interaction After Fix

- ClassVisitorParser creates JAVA_DOC_REGEX.
- ClassVisitorParser splits n by newline.
- ClassVisitorParser checks length of lines.
  - If lines length is greater than numLinesMax, append each line to a StringBuilder, convert the builder to a string, and return the string.
  - If lines length is less than or equal to numLinesMax, return n.toString().

## Final Participants

- ClassVisitorParser
- Object
- Pattern
- JavaItem
- CompilationUnit
- StringBuilder

## Plain English Title

getBodyDefinition

## Mermaid Sequence Diagram

```mermaid
---
title: getBodyDefinition (com.cloudurable.docgen.parser.util.ClassVisitorParser)
---

sequenceDiagram
    participant ClassVisitorParser
    participant Object
    participant Pattern
    participant JavaItem
    participant CompilationUnit
    participant StringBuilder

    ClassVisitorParser->>Pattern: Creates JAVA_DOC_REGEX
    ClassVisitorParser->>Object: Splits n by newline
    ClassVisitorParser->>ClassVisitorParser: Checks length of lines
    alt lines length > numLinesMax
        loop Append each line to builder
            ClassVisitorParser->>StringBuilder: Append line
        end
        ClassVisitorParser->>StringBuilder: Convert builder to string
        ClassVisitorParser->>ClassVisitorParser: Returns string
    else lines length <= numLinesMax
        ClassVisitorParser->>Object: Returns string
    end
```

