package org.leo.goanno.generate.impl;

import org.apache.commons.lang.time.DateFormatUtils;
import org.leo.goanno.generate.Generator;
import org.leo.goanno.model.GenerateInfo;
import org.leo.goanno.template.ParamTemplate;
import org.leo.goanno.template.constants.Templates;
import java.util.Date;

public class ParamCommentGeneratorImpl implements Generator {
    private ParamTemplate template;

    private String commentTemplate;

    public ParamCommentGeneratorImpl(ParamTemplate template, String commentTemplate) {
        this.template = template;
        this.commentTemplate = commentTemplate;
    }

    /**
     * do something
     * @param generateInfo
     * @return
     */
    @Override
    public String generate(GenerateInfo generateInfo) {
        template.addParams(Templates.COMMENT, Templates.COMMENT);
        template.addParams(Templates.DATE, DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss"));

        switch (generateInfo.getGenerateEnums()) {
            case STRUCT: {
                template.addParams(Templates.STRUCT_NAME, generateInfo.getStructName());
                break;
            }
            case PACKAGE: {
                template.addParams(Templates.PACKAGE_NAME, generateInfo.getPackageName());
                break;
            }
            case INTERFACE: {
                template.addParams(Templates.INTERFACE_NAME, generateInfo.getInterfaceName());
                break;
            }
            case STRUCT_FIELD: {
                template.addParams(Templates.STRUCT_NAME, generateInfo.getStructName());
                template.addParams(Templates.STRUCT_FIELD_NAME, generateInfo.getStructFieldName());
                break;
            }
            default:{

            }
        }

        String comment = template.load(commentTemplate);
        return comment;
    }
}
