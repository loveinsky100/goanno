package org.leo.goanno.generate.impl;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.leo.goanno.generate.Generator;
import org.leo.goanno.template.Template;
import org.leo.goanno.template.constants.Templates;
import org.leo.goanno.utils.FuncUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DefaultFuncCommentGeneratorImpl implements Generator {
    private Template template;

    private String commentTemplate;

    public DefaultFuncCommentGeneratorImpl(Template template, String commentTemplate) {
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
        String funcLine = code;
        // func (receiver) method_name(params) (returns)
        if (StringUtils.isBlank(funcLine)) {
            template.addParams(Templates.COMMENT, "");
            template.addParams(Templates.FUNCTION_NAME, "");
            template.addParams(Templates.DATE, DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
            return template.load(this.commentTemplate);
        }

        // find receiver
        String receiverOrMethod = StringUtils.substringBetween(funcLine, "func", "(");
        if (null == receiverOrMethod) {
            template.addParams(Templates.COMMENT, "");
            template.addParams(Templates.FUNCTION_NAME, "");
            template.addParams(Templates.DATE, DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
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
            String []args = receiverInfo.split("\\*");
            String arg = args[0];
            arg = StringUtils.trim(arg);
            receiver = arg;

            String leftLine = funcLine.substring(funcLine.indexOf(receiverInfo) + receiverInfo.length() + 1);
            String argsLine = FuncUtils.betweenString(leftLine, '(', ')');
            String anchorLine = argsLine;
            if (StringUtils.isEmpty(anchorLine)) {
                anchorLine = "()";
            }

            functionName = FuncUtils.betweenString(leftLine, ' ', '(');
            leftLine = funcLine.substring(funcLine.indexOf(anchorLine) + anchorLine.length() + 1);
            returnLine = leftLine;
            funcLine = argsLine;
        } else {
            String argsLine = FuncUtils.betweenString(funcLine, '(', ')');
            String anchorLine = argsLine;
            if (StringUtils.isEmpty(anchorLine)) {
                anchorLine = "()";
            }

            String leftLine = funcLine.substring(funcLine.indexOf(anchorLine) + anchorLine.length() + 1);
            returnLine = leftLine;
            funcLine = argsLine;
            functionName = StringUtils.trimToEmpty(receiverOrMethod);
        }

        String funcInfo = funcLine;
        List<String> params = new ArrayList<>();
        if (!StringUtils.isBlank(funcInfo)) {
            String []funcArgs = funcInfo.split(",");
            for (String funcArg : funcArgs) {
                String []argType = funcArg.split(" ");

                String argName = null;
                for (String arg : argType) {
                    if (StringUtils.isBlank(arg)) {
                        continue;
                    }

                    argName = StringUtils.trim(arg);
                    break;
                }

                params.add(argName);
            }
        }

        if (returnLine.trim().startsWith("(")) {
            returnLine = FuncUtils.betweenString(returnLine, '(', ')');
        }

        List<String> rets = new ArrayList<>();
        if (!StringUtils.isBlank(returnLine)) {
            String []returnArgs = returnLine.split(",");
            for (String retArg : returnArgs) {
                if (retArg.trim().startsWith("func(")) {
                    rets.add(FuncUtils.removeStartBlank(retArg));
                    continue;
                }

                String []argType = retArg.split(" ");
                String argName = null;
                for (String arg : argType) {
                    if (StringUtils.isBlank(arg)) {
                        continue;
                    }

                    argName = StringUtils.trim(arg);
                    break;
                }

                rets.add(argName);
            }
        }

        template.addParams(Templates.COMMENT, "");
        template.addParams(Templates.DATE, DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
        template.addParams(Templates.FUNCTION_NAME, functionName);
        if (null != receiver) {
            template.addParams(Templates.RECEIVER, receiver);
        }

        for (String param : params) {
            template.addParams(Templates.PARAMS, param);
        }

        for (String ret : rets) {
            template.addParams(Templates.RETS, ret);
        }

        String commentTemplate = Templates.TEMPLATE;
        if (!StringUtils.isEmpty(this.commentTemplate)) {
            commentTemplate = this.commentTemplate;
        }

        String comment = template.load(commentTemplate);
        return comment;
    }

    public static void main(String[] args) {
        System.out.println(FuncUtils.betweenString("Foreach(consumer func(key interface{}) bool, ss aa)", '(', ')'));
    }
}
