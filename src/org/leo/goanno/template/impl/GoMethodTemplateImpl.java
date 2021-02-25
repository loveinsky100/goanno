package org.leo.goanno.template.impl;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.leo.goanno.model.GoMethod;
import org.leo.goanno.model.GoType;
import org.leo.goanno.template.GoMethodTemplate;
import org.leo.goanno.template.constants.Templates;
import org.leo.goanno.utils.FuncUtils;

import java.util.*;

public class GoMethodTemplateImpl implements GoMethodTemplate {
    private int left = 0;
    private int firstLineLeft = 0;
    private GoMethod method;

    private String []TEMPLATE_DEFINES = new String[]{
            Templates.COMMENT,
            Templates.PARAMS,
            Templates.RETS,
            Templates.RECEIVER,
            Templates.DATE,
            Templates.FUNCTION_NAME,
            Templates.PARAM_NAME,
            Templates.PARAM_TYPE,
            Templates.PARAM_NAME_TYPE,
            Templates.RECEIVER_NAME,
            Templates.RECEIVER_TYPE,
            Templates.RECEIVER_NAME_TYPE,
            Templates.RET_NAME,
            Templates.RET_TYPE,
            Templates.RET_NAME_TYPE,
            Templates.NOTE
    };

    public GoMethodTemplateImpl(int left, int firstLineLeft) {
        this.left = left;
        this.firstLineLeft = firstLineLeft;
    }

    @Override
    public void setGoMethod(GoMethod method) {
        this.method = method;
    }

    @Override
    public String load(String template) {
        if (null == template || template.length() == 0) {
            return template;
        }

        String []templateLines = template.split("\n");
        Map<String, List<String>> templateLine2CodesMap = new HashMap<>();
        for (String templateLine : templateLines) {
            List<String> containsArgs = FuncUtils.getContainsAnyArgs(templateLine, TEMPLATE_DEFINES);
            if (null == containsArgs || containsArgs.isEmpty()) {
                continue;
            }

            templateLine2CodesMap.put(templateLine, generateMethodArgs(templateLine, containsArgs));
        }

        return "";
    }

    private List<String> generateMethodArgs(String template, List<String> args) {
        if (null == this.method || null == args || args.size() == 0) {
            return null;
        }

        List<GoType> judgeTypes = judgeGoTypeContext(method, args);
        if (null == judgeTypes || judgeTypes.size() == 0) {
            return generateValueTemplate(template, method, args);
        }

        return generateTypeTemplate(template, method, judgeTypes, args);
    }

    private List<String> generateTypeTemplate(String template, GoMethod method, List<GoType> types, List<String> args) {
        List<String> generates = new ArrayList<>();
        for (GoType type : types) {
            for (String arg : args) {
                template = template.replace(arg, calculateArgValue(type, method, arg));
                generates.add(template);
            }
        }

        return generates;
    }

    private List<String> generateValueTemplate(String template, GoMethod method, List<String> args) {
        List<String> generates = new ArrayList<>();
        for (String arg : args) {
            template = template.replace(arg, calculateArgValue(null, method, arg));
            generates.add(template);
        }

        return generates;
    }

    private String calculateArgValue(GoType type, GoMethod method, String arg) {
        switch (arg) {
            case Templates.NOTE: // TODO: user customer note
            case Templates.COMMENT: {
                return "";
            }
            case Templates.DATE: {
                return DateFormatUtils.format(method.getGenerateDate(), "yyyy-MM-dd HH:mm:ss");
            }
            case Templates.FUNCTION_NAME: {
                return method.getName();
            }
            case Templates.RETS:
            case Templates.RECEIVER:
            case Templates.PARAMS: {
                String value = type.getName();
                if (StringUtils.isBlank(value)) {
                    value = type.getType();
                }

                return value;
            }
            case Templates.RET_NAME:
            case Templates.RECEIVER_NAME:
            case Templates.PARAM_NAME: {
                return type.getName();
            }
            case Templates.RET_TYPE:
            case Templates.RECEIVER_TYPE:
            case Templates.PARAM_TYPE: {
                return type.getType();
            }
            case Templates.RET_NAME_TYPE:
            case Templates.RECEIVER_NAME_TYPE:
            case Templates.PARAM_NAME_TYPE: {
                if (!StringUtils.isBlank(type.getName())) {
                    return type.getName() + " " + type.getType();
                }

                return type.getType();
            }
            default:{
                return "__Not Support__";
            }
        }
    }

    private List<GoType> judgeGoTypeContext(GoMethod method, List<String> args) {
        for (String arg : args) {
            if (arg.contains("ret")) {
                return method.getOutputs();
            }

            if (arg.contains("receiver")) {
                List<GoType> types = new ArrayList<>();
                types.add(method.getReceiver());
                return types;
            }

            if (arg.contains("param")) {
                return method.getInputs();
            }
        }

        return null;
    }


}
