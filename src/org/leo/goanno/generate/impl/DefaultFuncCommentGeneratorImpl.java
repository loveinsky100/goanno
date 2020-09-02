package org.leo.goanno.generate.impl;

import org.apache.commons.lang.StringUtils;
import org.leo.goanno.generate.Generator;
import org.leo.goanno.template.Template;
import org.leo.goanno.template.constants.Templates;
import org.leo.goanno.utils.FuncUtils;

import java.util.ArrayList;
import java.util.List;

public class DefaultFuncCommentGeneratorImpl implements Generator {
    private Template template;

    public DefaultFuncCommentGeneratorImpl(Template template) {
        this.template = template;
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
            return Templates.TEMPLATE_DEFAULT;
        }

        // find receiver
        String receiverOrMethod = StringUtils.substringBetween(funcLine, "func", "(");
        if (StringUtils.isBlank(receiverOrMethod)) {
            return Templates.TEMPLATE_DEFAULT;
        }

        boolean hasReceiver = false;
        if (StringUtils.trim(receiverOrMethod).length() == 0) {
            hasReceiver = true;
        }

        String returnLine = null;
        String receiver = null;
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
        if (null != receiver) {
            template.addParams(Templates.RECEIVER, receiver);
        }

        for (String param : params) {
            template.addParams(Templates.PARAMS, param);
        }

        for (String ret : rets) {
            template.addParams(Templates.RETS, ret);
        }

        String comment = template.load(Templates.TEMPLATE);
        if (StringUtils.isBlank(comment)) {
            comment = Templates.TEMPLATE_DEFAULT;
        }

        return comment;
    }

    public static void main(String[] args) {
        System.out.println(FuncUtils.betweenString("Foreach(consumer func(key interface{}) bool, ss aa)", '(', ')'));
    }
}
