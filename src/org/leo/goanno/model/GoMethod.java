package org.leo.goanno.model;

import java.util.Date;
import java.util.List;

public class GoMethod {
    /**
     * interface method
     */
    private boolean interfaze;

    /**
     * method args
     */
    private List<GoType> inputs;

    /**
     * method output
     */
    private List<GoType> outputs;

    /**
     * method receiver
     */
    private GoType receiver;

    /**
     * method name
     */
    private String name;

    /**
     * method document
     */
    private String document;

    /**
     * current date
     */
    private Date generateDate;

    public boolean isInterfaze() {
        return interfaze;
    }

    public void setInterfaze(boolean interfaze) {
        this.interfaze = interfaze;
    }

    public List<GoType> getInputs() {
        return inputs;
    }

    public void setInputs(List<GoType> inputs) {
        this.inputs = inputs;
    }

    public List<GoType> getOutputs() {
        return outputs;
    }

    public void setOutputs(List<GoType> outputs) {
        this.outputs = outputs;
    }

    public GoType getReceiver() {
        return receiver;
    }

    public void setReceiver(GoType receiver) {
        this.receiver = receiver;
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

    public Date getGenerateDate() {
        return generateDate;
    }

    public void setGenerateDate(Date generateDate) {
        this.generateDate = generateDate;
    }
}
