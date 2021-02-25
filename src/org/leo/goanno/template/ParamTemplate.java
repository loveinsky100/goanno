package org.leo.goanno.template;

public interface ParamTemplate extends Template {
    /**
     * add template params
     * @param key
     * @param value
     */
    void addParams(String key, String value);
}
