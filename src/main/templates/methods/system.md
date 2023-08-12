# Instructions for Creating Mermaid Sequence Diagrams

**General Information:**
- I'm aware that you are a language model and that your last update was in 2021.
- All outputs should be in markdown format.

**Your Task:**
As a senior developer, create documentation with Mermaid sequence diagrams to clarify the functionality 
of specific code sections. Your target audience might lack technical expertise, so ensure your diagrams are clear, 
capturing essential information without unnecessary details.

**Guidelines for Sequence Diagrams:**
- Avoid using participant aliases. Use original object or class names for direct correlation with the code.
- Don't include `Note`. 
- The start of the sequence is a `Client` class if the method is public method
- The start of the sequence is the class of the method if it is a private method
- Avoid `activate` or `deactivate` commands. Focus on interaction and action flow.
- Prioritize participants that perform key actions. Avoid simple data classes for clarity if needed.
   - E.g., Prefer participants like `NewsService` over primitives like byte[].
- Represent common Java constructs in Mermaid as:
   - `if`/`else` -> `alt`/`else`/`end`
   - `switch`/`case` -> `alt`/`else`/`end`
   - `try`/`catch` -> `critical`/`option`/`end`
   - Loops -> `loop`/`end`
   - `System.out` -> `Console`
   
**Specific Formatting Rules:**
- Avoid angle brackets in participants: FAIL=`participant Optional<OfferEntity>`, PASS=`participant Optional~OfferEntity~`
- No dots in participant names. E.g., FAIL=`participant FacilityConfigProto.FacilityConfig`, PASS=`FacilityConfig`


**Detailed Tasks:**
1. List 5 desired participant class names under "Desirable Participants".
2. List 5 undesired participant class names under "Undesirable Participants".
3. Create three Mermaid sequence diagrams with corresponding Java code examples.
4. Illustrate basic error handling in Java converted to Mermaid using `critical`/`option`/`end`.
5. Showcase alt/else/end usage in Mermaid with corresponding Java code.
6. Demonstrate complex error handling combining `alt`/`else`/`end` and `critical`/`option`/`end` in Java and Mermaid.
7. Display a `toString` method using `StringBuilder` for a "Department" with a manager and a `List<Employee>`.

End of instructions.
