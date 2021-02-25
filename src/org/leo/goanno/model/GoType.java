package org.leo.goanno.model;

public class GoType {
    /**
     * method type with ptr
     */
    private String type;

    /**
     * method name
     */
    private String name;

    /**
     * document info in user comment
     */
    private String document;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDocument() {
        return document;
    }

    public void setDocument(String document) {
        this.document = document;
    }
}
