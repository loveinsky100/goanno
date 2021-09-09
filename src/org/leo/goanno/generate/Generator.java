package org.leo.goanno.generate;

import org.leo.goanno.model.GenerateInfo;
import org.leo.goanno.model.GenerateResult;
import org.leo.goanno.template.constants.Templates;

public interface Generator {
    /**
     * generate func comment
     * @param generateInfo
     * @return
     */
    @Deprecated
    String generate(GenerateInfo generateInfo);

    default GenerateResult generateResult(GenerateInfo generateInfo) {
        // replace all ${todo}
        GenerateResult result = new GenerateResult();
        String template = generate(generateInfo);
        result.setResult(template);
        if (template == null || template.length() == 0) {
            return result;
        }

        // find first
        int index = template.indexOf(Templates.COMMENT);
        result.setTodoIndex(index);

        template = template.replaceAll("\\$\\{todo\\}", "");
        result.setResult(template);
        return result;
    }
}
