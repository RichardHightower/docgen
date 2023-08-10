package com.cloudurable.docgen.parser.model;

public class FieldJavaItem extends JavaItem {
    private final String fieldType;
    private final boolean primitive;

    /**
     */
    public FieldJavaItem(final Builder builder) {
        super(builder);
        this.fieldType = builder.fieldType;
        this.primitive = builder.primitive;
    }

    public String getFieldType() {
        return fieldType;
    }

    public boolean isPrimitive() {
        return primitive;
    }
}
