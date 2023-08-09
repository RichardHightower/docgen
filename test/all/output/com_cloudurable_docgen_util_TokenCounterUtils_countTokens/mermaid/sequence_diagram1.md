----
# ORIGINAL RESPONSE 
# Participants

- TokenCounterUtils
- Analyzer
- StandardAnalyzer
- TokenStream
- StringReader
- CharTermAttribute
- IOException

# Initial Interactions

1. TokenCounterUtils calls Analyzer
2. Analyzer creates a new instance of StandardAnalyzer
3. TokenCounterUtils calls TokenStream with analyzer and a new instance of StringReader with the text parameter
4. TokenStream creates a new instance of CharTermAttribute
5. TokenStream calls reset
6. TokenStream increments tokens while tokenStream.incrementToken() is true
7. TokenStream calls end
8. If an IOException is thrown, it is caught and prints the stack trace
9. TokenCounterUtils returns the tokenCount multiplied by 0.3 plus the tokenCount

# Clean Interactions

1. TokenCounterUtils -> Analyzer: Calls StandardAnalyzer
2. TokenCounterUtils -> TokenStream: Calls reset
3. TokenStream -> TokenCounterUtils: IncrementToken is false
4. TokenCounterUtils -> TokenStream: Returns tokenCount multiplied by 0.3 plus tokenCount
5. (alt) IOException is thrown
    - TokenCounterUtils -> IOException: Catch IOException
    - IOException -> TokenCounterUtils: Print Stack Trace
6. TokenCounterUtils -> TokenStream: Close tokenStream
7. TokenCounterUtils -> Analyzer: Close analyzer

# Final Participants

- TokenCounterUtils
   - `StandardAnalyzer`
   - `TokenStream`
   - `CharTermAttribute`
   - `IOException`

# Plain English Title

Token Count

# Mermaid Sequence Diagram

```mermaid
---
title: Token Count
---

sequenceDiagram
    participant TokenCounterUtils
    participant Analyzer
    participant TokenStream
    participant CharTermAttribute
    participant IOException

    TokenCounterUtils -> Analyzer: Calls StandardAnalyzer
    TokenCounterUtils -> TokenStream: Calls reset
    TokenStream -> TokenCounterUtils: IncrementToken is false
    TokenCounterUtils -> TokenStream: Returns tokenCount multiplied by 0.3 plus tokenCount
    alt IOException is thrown
        TokenCounterUtils -> IOException: Catch IOException
        IOException -> TokenCounterUtils: Print Stack Trace
    TokenCounterUtils -> TokenStream: Close tokenStream
    TokenCounterUtils -> Analyzer: Close analyzer
```
