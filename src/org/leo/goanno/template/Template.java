package org.leo.goanno.template;

import java.util.List;
import java.util.Map;

public interface Template {

    /**
     * add template params
     * @param key
     * @param value
     */
    void addParams(String key, String value);

    /**
     * load code template
     * @param template
     * @return
     */
    String load(String template);
}
