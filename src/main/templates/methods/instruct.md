
# Guidelines for Mermaid Sequence Diagram Generation 

* Produce documentation with Mermaid sequence diagrams for code functionality.
* Target audience: non-tech savvy. Ensure diagrams are easily understood and in plain English.
* Include relevant business rules/domain knowledge from code comments or logs in diagrams.
* Diagrams must clearly represent critical concepts. Use business domain-specific language when applicable.
* No method calls in descriptions so `Handler -> HRService : lookupEmployeeById()` not ok, but `Handler -> HRService : Lookup Employee By Id` is ok
  * Good: `StringBuilder-->>Person: Return toString result`  | does not use method call
  * BAD: `StringBuilder-->>Person: Return toString() result` | does use method call syntax which is not allowed.
* Participants should not be named Exception or Throwable and should not end in the word Exception
* Avoid participant aliases in diagrams. Use original class/object names from code.
* Exclude notes from diagrams. No `Note` in sequence diagram. Encapsulate all necessary information within interaction sequence.
* Avoid 'activate'/'deactivate' commands in diagrams. Focus on participant interaction and action flow.
* Do not shorten/abbreviate names in diagrams. Use authentic names of classes/objects.
* Participants should be classes/objects with substantial actions/significant interactions.
* The start of the sequence is a client class if the method is public method
* The start of the sequence is the class of the method if it is a private method
* If sequences are long, then avoid data classes as participants: File, ChatRequest, EmbeddingRequest, AudioResponse, Article, Queue, String, StringNode, ObjectNode, etc.
* Do not use primitives as participants. Entities using/generating data are actual participants.
* Participants should include action-oriented classes like NewsService, ArticleSorter, etc.
* Replace Throwable/Exception with "Handled error", "Reported error".
* Do not use `System.out` as a participant
* Do not use primitives as a participant, e.g., do not use `byte[]`, `float`, `int`.
* Do not use colon ":" in YAML `title` for sequence diagram.
* No angle brackets in participant: FAIL=`participant Optional<OfferEntity>`, PASS=`participant Optional~OfferEntity~`
* No angle brackets in message interaction: FAIL=`offerRepository-->>Optional<OfferEntity>: return Optional<OfferEntity>`, PASS=`offerRepository-->>Optional~OfferEntity~: might return an offer`
* No angle brackets in message interaction: FAIL=`offerRepository-->>Optional<OfferEntity>: return Optional<OfferEntity>`, PASS=`offerRepository-->>Optional~OfferEntity~: might return an offer`
* No dots in participant FAIL=`participant FacilityConfigProto.FacilityConfig`, PASS=`FacilityConfig`
* Always add a title to the sequence diagram.


# Instructions
Generate a mermaid sequence diagram based on the above guidelines using the Java code below by 
following the instructions below:
1. Create a list of participants
   * Take out of that list any participants that are exceptions or throwables, and any primitives.
   * Take out of that list any participants that are basic data types like `String`.
   * Ensure there are no angle brackets in the list of participants `<` `>` can be changed to `~`.
2. `Participants`: Show the list of participants.
3. `Initial Interactions` : List all interactions between the participants in markdown and explain in plain English
4. `Clean Interactions`: Now clean up the `Initial Interactions` between the participants 
   * List out each interaction and 
   * List out each `alt`/`else`/`end` for `if`/`else` Java
   * List out `critical`/`option`/`end` for `try`/`catch` Java. 
   * Describe descriptions of `alt`/`else`/`end`, `critical`/`option`/`end` in plain English
   * Describe descriptions of interactions in plain English with no method call syntax: NO `method()`.
   * Ensure there are no angle brackets in the interactions. Replace <> with ~, i.e., List<Employee> to List~Employee~.
   * Ensure there are no method calls in the interaction description, just plain English.
       * Do not allow what looks like method calls in the interaction description, No parens
         * DON't DO THIS: "    Address->>StringBuilder: Call toString() method" No parens
         * INSTEAD DO THIS: "    Address->>StringBuilder: Convert StringBuilder to a String"
5. `Final Participants`: Show the list of participants from `Participants` with participants removed if participant is not used in `Clean Interaction`.
   * Add sub-bullet for each participant with a sub list of the interaction it is mentioned in from `Clean Interaction`.
   * If there are no interactions in the sub-list than it should not be on this list so add an extra sub-bullet with the text `REMOVE`.
6. `Plain English Title`: Create a plain English title based on  "{{TITLE}}".
7. Finally, create the mermaid code for the sequence diagram
    * make sure it uses the `Plain English Title` for the title.
    * make sure it uses participants from the plain `Final Participants` and the participant is not marked `REMOVE`
    * Do not add `Notes`.
    * Make sure it uses interactions from `Clean Interaction`.
      * Ensure that all `alt`/`else` have a corresponding `end`. 
      * Ensure that all `critical`/`option` have a corresponding `end`. 

# Java method 

```java

{{JAVA_METHOD}}

```
