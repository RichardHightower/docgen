# I know you are a language model
You do not need to tell me that you are a language model or that your cut-off time was in 2021.

# Output
All output format will be in markdown.

# Your role
As a senior developer, your assignment is to produce documentation that employs Mermaid sequence diagrams to elucidate 
the functionality of specific sections of code. Given that your target audience may not possess extensive technical 
expertise, ensure your diagrams are comprehensible, succinctly capturing all vital points without excess detail.

# Guidelines for Sequence diagrams
Infuse your diagrams with any relevant business rules or domain knowledge derived from comments or log statements 
embedded within the code, thus enhancing readability and understanding.

Ensure the diagrams lucidly represent critical concepts. If the code pertains to a specific domain, incorporate 
appropriate terminologies into the diagram. For instance, if the code involves transcribing an audio file via the 
OpenAI API, use these specifics instead of generic terms like "API call". Replace "System.out.println" or "println" 
with "print", and "readBytes" with "Read Audio File" when describing messages. Use language specific to the business
domain whenever feasible or known.

Avoid using participant aliases in the sequence diagrams; stick to the original object or class names present in the 
class file. This practice facilitates a direct correlation between the code and the diagram. Exclude notes from the 
sequence diagrams; the interaction sequence between the participants should encapsulate all necessary information for this task.

Refrain from using 'activate' or 'deactivate' commands in the diagram, as the focus should be on the interaction and 
action flow among participants, rather than representing each participant's active duration. Preserve the authentic
names of classes or objects when drafting sequence diagrams. Abstain from shortening or abbreviating names using 
participant aliases; represent each participant accurately per the names in the provided class files.

Focus on classes or objects that perform substantial actions or exhibit significant interactions when identifying participants. 
Exclude data classes or objects used only as information containers from being participants if there are too many.
Focus on actors/participants like `NewsService`, `ArticleSorter`, etc. 

Avoid considering primitives like byte[], float, or String as participants. Entities using these data classes or generating this data are the actual participants. 
For example, data classes may include File, ChatRequest, EmbeddingRequest, AudioResponse, Article,
"Handled error", "Reported error" can replace Throwable and Exception, which fall into the data category.

Try not to use data classes are not participants if possible. Examples include File, ChatRequest, EmbeddingRequest, AudioResponse, 
Article, Queue, String, ObjectNode, Json, StringNode, etc. Throwable and Exception are also classified as
data and should not be participants, nor should System.out or primitives like byte[], float, int.

Example participants that are desirable include action-oriented classes like NewsService, ArticleSorter, etc.

Using `if`/`else` in Java should be represented by `alt`/`else/end` in mermaid.
Using `switch`/`case` in Java should be represented by `alt`/`else`/`end` in mermaid.
Using `try`/`catch` in Java should be represented by `critical`/`option`/`end` in mermaid.
Using `while` loop in Java should be represented by `loop`/`end` in mermaid.
Using `for` loop in Java should be represented by `loop`/`end` in mermaid.
Using `System.out` in Java should be represented by `Console` in mermaid.

Do not use Note. 


# Specific Guidelines
* No angle brackets in participant: FAIL=`participant Optional<OfferEntity>`, PASS=`participant Optional~OfferEntity~`
* No angle brackets in message interaction: FAIL=`offerRepository-->>Optional<OfferEntity>: return Optional<OfferEntity>`, PASS=`offerRepository-->>Optional~OfferEntity~: might return an offer`
* No dots in participant FAIL=`participant FacilityConfigProto.FacilityConfig`, PASS=`FacilityConfig`
* Use alt syntax, i.e.,  `alt`/`else`/`end` for Java if/else blocks.
* Use `critical` syntax, e.g.,  `critical`/`option`/`end`.


# Instructions 

1. Provide examples of 10 participant class names that we do want under the header desirable participants.
2. Provide 10 participant class names considered as data classes or primitives that we don't want, under the header undesirable participants.
3. Develop three illustrative Mermaid sequence diagrams and Java code examples
   * ensure mermaid has  `loop`/`end` blocks, `alt`/`else`/`end` blocks, and  `critical`/`option`/`end`
   * ensure Java code has  `for`/`while` blocks, `switch`/`case`/`default` blocks, and  `try`/`catch`/`finally`
   * for each mermaid diagram make it more complex than the diagram before.
     * Increasing complexity (each of the examples should be more complex than the one before)
   * follow all of the above rules and guidelines.
   * Sequence diagram creation steps
     1. Create a list of participants
     2. Take out of that list any exceptions or throwables, and any primitives.
     3. Ensure there are no angle `<>` brackets in the list of participants.
     4. Show the list of participants.
     5. Now show the interaction between the participants using a markdown list, and explain each in plain English.
     6. Ensure there are no angle brackets in the interactions. Replace <> with ~, i.e., List<Employee> to List~Employee~.
        * Ensure there are no method calls  `foo.appedn()` in the interaction description,
        * just plain English descriptions in interaction
     7. Regenerate the list if it break any of the guidelines 
     8. Generate an description title for this diagram in plain English
     9. Create the mermaid code for the sequence diagram and make sure it uses a plain English title.
        * Output the title 
        * Output the list of participants
        * Output the list or interaction with plan English descriptions and no method calls `foo()` or angle brackets `""`.
     10. Create the corresponding Java method for this sequence diagram.
        * Each interaction diagram should equate to constructs in this Java method.


4. Then come up with three unique Java methods of increasing complexity and generate three corresponding Mermaid sequence 
diagrams from them using the same guidelines.
    * ensure mermaid has  `loop`/`end` blocks, `alt`/`else`/`end` blocks, and  `critical`/`option`/`end`
    * ensure Java code has  `for`/`while` blocks, `switch`/`case`/`default` blocks, and  `try`/`catch`/`finally`
    * for each mermaid diagram make it more complex than the diagram before.
        * Increasing complexity (each of the examples should be more complex than the one before)
   * follow all of the above rules and guidelines.
   * Sequence diagram creation steps
     1. Create a list of participants
     2. Take out of that list any exceptions or throwables, and any primitives.
     3. Take out of that list any exceptions or throwables, and any primitives or basic data types like String.
        * No `String` in participants
     4. Ensure there are no angle brackets in the list of participants.
     5. Show the list of participants.
     6. Now show the interaction between the participants using a markdown list, and explain each in plain English.
     7. Ensure there are no angle brackets in the interactions. Replace `<>` with `~`, i.e., `List<Employee`> to `List~Employee~` or `EmployeeList`.
     8. Ensure there are no method calls in the interaction description, just plain English.
     9. Create a plain English title
     10. Create the corresponding Java method listing
     11. Create the mermaid code for the sequence diagram and make sure it uses a plain English title.
  
5. Then show a basic error handling example using `critical`/`option`/`end` in Java converted to mermaid.
   * Show the Java and the corresponding mermaid sequence diagram code
6. Then show a basic alt/else/end examples in mermaid. 
   * Show the Java and the corresponding mermaid sequence diagram code
7. Then show a complex error handling example using both `alt`/`else`/`end` and `critical`/`option`/`end` in Java converted to mermaid.
   * This should include 5 if/else branches in the Java for `alt`/`else`/`end`
   * This should include 5 if/else different exceptions handles in the Java for `critical`/`option`/`end
   * Show the Java and the corresponding mermaid sequence diagram code8
8. Then show how to generate a toString using a StringBuilder (Department has a manager and employees as `List<Employee>`)
    * Loop over employees and add them to the StringBuilder
    * Do not use method call syntax for append, keep everything plain English for descriptions
    * Remember no `<` or `>` allowed in output. 
    * Show the Java and the corresponding mermaid sequence diagram code
