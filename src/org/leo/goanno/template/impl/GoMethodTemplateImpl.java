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
            Templates.RET,
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

        return generate(templateLines, templateLine2CodesMap);
    }

    private String generate(String []templateLines, Map<String, List<String>> templateLine2CodesMap) {
        StringBuilder codeGenerator = new StringBuilder();
        int line = 0;
        for (String templateLine : templateLines) {
            List<String> codes = templateLine2CodesMap.get(templateLine);
            if (null != codes && !codes.isEmpty()) {
                for (int index = 0; index < codes.size(); index ++) {
                    if (line == 0) {
                        codeGenerator.append(FuncUtils.blank(firstLineLeft));
                    } else {
                        codeGenerator.append(FuncUtils.blank(left));
                    }

                    codeGenerator.append(codes.get(index));
                    codeGenerator.append("\n");
                }
            } else if (!FuncUtils.containsAnyArgs(templateLine, TEMPLATE_DEFINES)) {
                if (line == 0) {
                    codeGenerator.append(FuncUtils.blank(firstLineLeft));
                } else {
                    codeGenerator.append(FuncUtils.blank(left));
                }

                codeGenerator.append(templateLine).append("\n");
            }

            line ++;
        }

        // remove last break line
        String templateCode = codeGenerator.toString();
        if (StringUtils.isBlank(templateCode) || templateCode.length() < 1) {
            return templateCode;
        }

        templateCode = templateCode.substring(0, templateCode.length() - 1);
        return templateCode;
    }

    private List<String> generateMethodArgs(String template, List<String> args) {
        if (null == args || args.size() == 0) {
            return null;
        }

        if (null == this.method && !FuncUtils.containsOnly(template, Templates.COMMENT, TEMPLATE_DEFINES)) {
            return null;
        }

        List<GoType> judgeTypes = judgeGoTypeContext(method, args);
        if (null == judgeTypes || judgeTypes.size() == 0) {
            if (isGoTypeContext(args)) {
                return null;
            }

            return generateValueTemplate(template, method, args);
        }

        return generateTypeTemplate(template, method, judgeTypes, args);
    }

    private List<String> generateTypeTemplate(String template, GoMethod method, List<GoType> types, List<String> args) {
        List<String> generates = new ArrayList<>();
        for (GoType type : types) {
            String code = template;
            for (String arg : args) {
                code = code.replace(arg, calculateArgValue(type, method, arg));
            }

            generates.add(code);
        }

        return generates;
    }

    private List<String> generateValueTemplate(String template, GoMethod method, List<String> args) {
        List<String> generates = new ArrayList<>();
        String code = template;
        for (String arg : args) {
            code = code.replace(arg, calculateArgValue(null, method, arg));
        }

        generates.add(code);
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
                if (null == type) {
                    return "";
                }

                String value = type.getName();
                if (StringUtils.isBlank(value)) {
                    value = type.getType();
                }

                return value;
            }
            case Templates.RET_NAME:
            case Templates.RECEIVER_NAME:
            case Templates.PARAM_NAME: {
                if (null == type) {
                    return "";
                }

                return type.getName();
            }
            case Templates.RET_TYPE:
            case Templates.RECEIVER_TYPE:
            case Templates.PARAM_TYPE: {
                if (null == type) {
                    return "";
                }

                return type.getType();
            }
            case Templates.RET_NAME_TYPE:
            case Templates.RECEIVER_NAME_TYPE:
            case Templates.PARAM_NAME_TYPE: {
                if (null == type) {
                    return "";
                }

                if (!StringUtils.isBlank(type.getName())) {
                    if (StringUtils.isEmpty(type.getType())) {
                        return type.getName();
                    }

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
                if (null == method.getReceiver()) {
                    return null;
                }

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

    private boolean isGoTypeContext(List<String> args) {
        for (String arg : args) {
            if (arg.contains("ret") || arg.contains("receiver") || arg.contains("param")) {
                return true;
            }
        }

        return false;
    }
}
