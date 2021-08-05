package org.leo.goanno.model;

public class GenerateInfo {
    // generateEnums
    private GenerateEnums generateEnums;

    // interface name
    private String interfaceName;

    // struct name
    private String structName;

    // struct field name
    private String structFieldName;

    // package name
    private String packageName;

    // func
    private String func;

    public GenerateEnums getGenerateEnums() {
        return generateEnums;
    }

    public void setGenerateEnums(GenerateEnums generateEnums) {
        this.generateEnums = generateEnums;
    }

    public String getInterfaceName() {
        return interfaceName;
    }

    public void setInterfaceName(String interfaceName) {
        this.interfaceName = interfaceName;
    }

    public String getStructName() {
        return structName;
    }

    public void setStructName(String structName) {
        this.structName = structName;
    }

    public String getStructFieldName() {
        return structFieldName;
    }

    public void setStructFieldName(String structFieldName) {
        this.structFieldName = structFieldName;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getFunc() {
        return func;
    }

    public void setFunc(String func) {
        this.func = func;
    }
}

