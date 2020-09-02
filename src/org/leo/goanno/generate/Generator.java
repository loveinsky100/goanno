package org.leo.goanno.generate;

import org.leo.goanno.template.Template;

public interface Generator {
    /**
     * generate func comment
     * @param code
     * @return
     */
    String generate(String code);
}
