package org.leo.goanno.test;

import org.apache.commons.lang.StringUtils;
import org.leo.goanno.generate.Generator;
import org.leo.goanno.generate.impl.FuncCommentGeneratorV2Impl;
import org.leo.goanno.template.impl.GoMethodTemplateImpl;
import org.leo.goanno.utils.CommentUtils;
import org.leo.goanno.utils.FuncUtils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class TestCommon {
    static class GoFuncInfo {
        // func info
        private String func;

        // except comment
        private String comment;
    }

    protected static void assertTrue(boolean flag, String message) {
        if (!flag) {
            throw new RuntimeException(message);
        }
    }

    protected static void assertGenerateCode(String template, GoFuncInfo goFuncInfo, boolean covert) {
        // Generator generator = new DefaultFuncCommentGeneratorImpl(new DefaultTemplateImpl(0, 0), template);
        Generator generator = new FuncCommentGeneratorV2Impl(new GoMethodTemplateImpl(0, 0), template);
        String code = generator.generate(FuncUtils.findGenerateInfo(goFuncInfo.func, 0));
        if (covert) {
            String []comments = StringUtils.split(goFuncInfo.comment, "\n");
            List<String> commentList = new ArrayList<>();
            for (String comment : comments) {
                commentList.add(comment);
            }

            code = CommentUtils.mergeComment(code, commentList);
        }

        assertTrue(code.equals(goFuncInfo.comment), String.format(
                "\n\nfunc:\n----------------------------\n%s----------------------------\n" +
                "comment:\n----------------------------\n%s\n----------------------------\n" +
                "except:\n----------------------------\n%s\n----------------------------\n", goFuncInfo.func, code, goFuncInfo.comment));
    }

    protected static List<String> readFile(String file) {
        InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(file);
        if (null == inputStream) {
            return null;
        }

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        List<String> lines = bufferedReader.lines().collect(Collectors.toList());
        return lines;
    }

    protected static List<GoFuncInfo> readGoFuncInfoList(String file) {
        List<String> lines = readFile(file);
        if (null == lines || lines.isEmpty()) {
            return null;
        }

        List<GoFuncInfo> goFuncInfoList = new ArrayList<>();
        boolean inComment = true;
        boolean inTestMethodArea = false;
        StringBuilder commentBuilder = new StringBuilder();
        StringBuilder functionBuilder = new StringBuilder();
        for (String line : lines) {
            if (StringUtils.isEmpty(line)) {
                continue;
            }

            if (line.startsWith("// Methods Declare")) {
                inTestMethodArea = true;
                continue;
            }

            if (!inTestMethodArea) {
                continue;
            }

            if (line.startsWith("func") || line.startsWith("// start_interface_method")) {
                inComment = false;
            }

            if (inComment) {
                commentBuilder.append(line).append("\n");
            } else {
                functionBuilder.append(line).append("\n");
            }

            if (line.startsWith("}") || line.startsWith("// end_interface_method")) {
                GoFuncInfo goFuncInfo = new GoFuncInfo();
                String func = functionBuilder.toString();
                String comment = commentBuilder.toString();

                // remove last break line
                if (comment.endsWith("\n")) {
                    comment = comment.substring(0, comment.length() - 1);
                }

                goFuncInfo.func = func;
                goFuncInfo.comment = comment;

                goFuncInfoList.add(goFuncInfo);
                inComment = true;
                commentBuilder = new StringBuilder();
                functionBuilder = new StringBuilder();
            }
        }

        return goFuncInfoList;
    }

    protected static String readGoFuncTemplate(String file) {
        List<String> lines = readFile(file);
        if (null == lines || lines.isEmpty()) {
            return null;
        }

        boolean inTemplateArea = false;
        StringBuilder templateBuilder = new StringBuilder();
        for (String line : lines) {
            if (StringUtils.isEmpty(line)) {
                continue;
            }

            if (line.startsWith("// Template Declare Start")) {
                inTemplateArea = true;
                continue;
            }

            if (line.startsWith("// Template Declare End")) {
                break;
            }

            if (inTemplateArea) {
                templateBuilder.append(line).append('\n');
            }
        }

        if (templateBuilder.length() == 0) {
            return templateBuilder.toString();
        }

        // remove last break line
        return templateBuilder.substring(0, templateBuilder.length() - 1);
    }
}
