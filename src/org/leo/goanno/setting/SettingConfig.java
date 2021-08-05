package org.leo.goanno.setting;

public class SettingConfig {
    private String name;
    private String key;
    private String defaultValue;
    private String value;

    public SettingConfig(String name, String key, String defaultValue, String value) {
        this.name = name;
        this.key = key;
        this.defaultValue = defaultValue;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    public String getValue() {
        if (null == value || value.length() == 0) {
            return defaultValue;
        }

        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
