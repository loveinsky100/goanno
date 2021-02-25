package org.leo.goanno.generate.impl;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.leo.goanno.generate.Generator;
import org.leo.goanno.model.GoMethod;
import org.leo.goanno.model.GoType;
import org.leo.goanno.template.GoMethodTemplate;
import org.leo.goanno.template.Template;
import org.leo.goanno.template.constants.Templates;
import org.leo.goanno.utils.FuncUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class FuncCommentGeneratorV2Impl implements Generator {
    private GoMethodTemplate template;

    private String commentTemplate;

    public FuncCommentGeneratorV2Impl(GoMethodTemplate template, String commentTemplate) {
        this.template = template;
        this.commentTemplate = commentTemplate;
    }

    /**
     * do something
     * @param code
     * @return
     */
    @Override
    public String generate(String code) {
        GoMethod method = new GoMethod();
        method.setGenerateDate(new Date());

        String funcLine = code;
        // func (receiver) method_name(params) (returns)
        if (StringUtils.isBlank(funcLine)) {
            return template.load(this.commentTemplate);
        }

        // find receiver
        String receiverOrMethod = StringUtils.substringBetween(funcLine, "func", "(");
        if (null == receiverOrMethod) {
            return template.load(this.commentTemplate);
        }

        boolean hasReceiver = false;
        if (StringUtils.trim(receiverOrMethod).length() == 0) {
            hasReceiver = true;
        }

        String returnLine = null;
        String receiver = null;
        String functionName = null;
        if (hasReceiver) {
            String receiverInfo = FuncUtils.betweenString(funcLine, '(', ')');
            method.setReceiver(readGoType(receiverInfo));

            String leftLine = funcLine.substring(funcLine.indexOf(receiverInfo) + receiverInfo.length() + 1);
            String argsLine = FuncUtils.betweenString(leftLine, '(', ')');
            String anchorLine = "(" + argsLine + ")";

            if (leftLine.startsWith(" ")) {
                functionName = FuncUtils.betweenString(leftLine, ' ', '(');
            } else {
                functionName = leftLine.substring(0, leftLine.indexOf("("));
            }

            method.setName(functionName);
            leftLine = StringUtils.substring(funcLine, funcLine.indexOf(anchorLine) + anchorLine.length());
            returnLine = leftLine;
            funcLine = argsLine;
        } else {
            String argsLine = FuncUtils.betweenString(funcLine, '(', ')');
            String anchorLine = "(" + argsLine + ")";

            String leftLine = StringUtils.substring(funcLine, funcLine.indexOf(anchorLine) + anchorLine.length());
            returnLine = leftLine;
            funcLine = argsLine;
            functionName = StringUtils.trimToEmpty(receiverOrMethod);
            method.setName(functionName);
        }

        String funcInfo = funcLine;
        List<GoType> params = new ArrayList<>();
        if (!StringUtils.isBlank(funcInfo)) {
            String []funcArgs = funcInfo.split(",");
            for (String funcArg : funcArgs) {
                GoType arg = readGoType(funcArg);
                if (null == arg) {
                    continue;
                }

                params.add(arg);
            }
        }

        method.setInputs(params);
        if (returnLine.trim().startsWith("(")) {
            returnLine = FuncUtils.betweenString(returnLine, '(', ')');
        }

        List<GoType> rets = new ArrayList<>();
        if (!StringUtils.isBlank(returnLine)) {
            String []returnArgs = returnLine.split(",");
            for (String retArg : returnArgs) {
                GoType arg = readGoType(retArg);
                if (null == arg) {
                    continue;
                }

                rets.add(arg);
            }
        }

        method.setOutputs(rets);
        return generateTemplate(method);
    }

    private String generateTemplate(GoMethod method) {
        if (null == method) {
            return null;
        }

        template.setGoMethod(method);
        return template.load(this.commentTemplate);
    }

    /**
     * read go type
     * @param code xxx *Type
     * @return
     */
    private GoType readGoType(String code) {
        code = FuncUtils.removeStartBlank(code);
        if (null == code || code.length() == 0) {
            return null;
        }

        String []args = null;
        if (code.contains("\\*")) {
            args = code.split("\\*");
        } else {
            args = code.split(" ");
        }

        GoType type = new GoType();
        if (null != args && args.length >= 2) {
            type.setName(StringUtils.trim(args[0]));
            type.setType(StringUtils.trim(args[1]));
        } else {
            type.setType(StringUtils.trim(args[0]));
        }

        return type;
    }
}
