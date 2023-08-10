package com.cloudurable.docgen.parser.model;

import java.util.List;

/**
 * Represents an item in Java code, such as a class, method, or field.
 */
public class JavaItem {

    /** Import body, the imports for the class. */
    private final String importBody;

    /**  Code body, the whole code for the item. */
    private final String body;

    /**  JavaDoc, the javadoc for the item. */
    private final String javadoc;
    private final String name;
    private final String simpleName;
    private final String definition;
    private final JavaItem parent;
    private final JavaItemType type;

    /**
     * Constructs a new Item.
     *
     * @param builder  Builder
     */
    public JavaItem(Builder builder) {
        this.importBody = orEmptyString(builder.importBody);
        this.body = orEmptyString(builder.body);
        this.javadoc = orEmptyString(builder.javadoc);
        this.name = orEmptyString(builder.name);
        this.simpleName = orEmptyString(builder.simpleName);
        this.definition = orEmptyString(builder.definition);
        this.parent = builder.parent;
        this.type = builder.type;
    }

    /**
     * Returns the column headers for the CSV representation of an Item.
     *
     * @return the column headers
     */
    public static List<String> headers() {
        return List.of("Name", "Type", "FullName", "Definition", "JavaDoc", "Parent", "Imports", "Body");
    }

    /**
     * Returns a builder to construct an Item.
     *
     * @return the Item builder
     */
    public static Builder builder() {
        return new Builder();
    }

    private String orEmptyString(String part) {
        return part == null ? "" : part;
    }

    /**
     * Returns a row representing the Item for the CSV output.
     *
     * @return the row representing the Item
     */
    public List<String> row() {
        return List.of(simpleName, type.toString().toLowerCase(), name, definition, javadoc, parent != null ? parent.getName() : "", importBody, body);
    }

    /**
     * Returns the import statements associated with the item.
     *
     * @return the import statements
     */
    public String getImportBody() {
        return importBody;
    }

    /**
     * Returns the source code body of the item.
     *
     * @return the source code body
     */
    public String getBody() {
        return body;
    }

    /**
     * Returns the Javadoc documentation of the item.
     *
     * @return the Javadoc documentation
     */
    public String getJavadoc() {
        return javadoc;
    }

    /**
     * Returns the fully qualified name of the item.
     *
     * @return the fully qualified name
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the definition of the item.
     *
     * @return the definition
     */
    public String getDefinition() {
        return definition;
    }

    /**
     * Returns the parent item of the current item.
     *
     * @return the parent item
     */
    public JavaItem getParent() {
        return parent;
    }

    /**
     * Returns the type of the item.
     *
     * @return the item type
     */
    public JavaItemType getType() {
        return type;
    }

    public String getSimpleName() {
        return simpleName;
    }

    @Override
    public String toString() {
        return "Item{" +
                "simpleName='" + simpleName + '\'' +
                ", type=" + type +
                ", name='" + name + '\'' +
                ", definition='" + definition + '\'' +
                ", parent=" + parent +
                ", importBody='" + importBody + '\'' +
                ", javadoc='" + javadoc + '\'' +
                ", body='" + body + '\'' +
                '}';
    }

    /**
     * Builder class for constructing an Item.
     */
    public static class Builder {

        // FIELDS ONLY
        public String fieldType;
        public boolean primitive;

        //

        private String body;
        private String javadoc;
        private String name;
        private String definition;
        private JavaItem parent;
        private String importBody;
        private String simpleName;
        private JavaItemType type;

        private Builder() {
        }

        public Builder simpleName(String simpleName) {
            this.simpleName = simpleName;
            return this;
        }

        public Builder type(JavaItemType type) {
            this.type = type;
            return this;
        }

        public Builder importBody(String importBody) {
            this.importBody = importBody;
            return this;
        }

        public Builder body(String body) {
            this.body = body;
            return this;
        }

        public Builder javadoc(String javadoc) {
            this.javadoc = javadoc;
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder definition(String definition) {
            this.definition = definition;
            return this;
        }

        public Builder parent(JavaItem parent) {
            this.parent = parent;
            return this;
        }

        public Builder fieldType(String fieldType) {
            this.fieldType = fieldType;
            return this;
        }

        public Builder primitive(boolean primitive) {
            this.primitive = primitive;
            return this;
        }

        /**
         * Builds and returns the Item.
         *
         * @return the constructed Item
         */
        public JavaItem build() {
            if (type == JavaItemType.FIELD) {
                return new FieldJavaItem(this);
            }else {
                return new JavaItem(this);
            }
        }
    }
}
