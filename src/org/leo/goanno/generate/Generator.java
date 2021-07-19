package org.leo.goanno.generate;

import org.leo.goanno.model.GenerateInfo;

public interface Generator {
    /**
     * generate func comment
     * @param generateInfo
     * @return
     */
    String generate(GenerateInfo generateInfo);
}
