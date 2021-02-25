package org.leo.goanno.template;

import org.leo.goanno.model.GoMethod;

public interface GoMethodTemplate extends Template {
    /**
     * go method
     * @param method
     */
    void setGoMethod(GoMethod method);
}
