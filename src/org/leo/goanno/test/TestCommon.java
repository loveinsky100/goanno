package org.leo.goanno.test;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.leo.goanno.generate.Generator;
import org.leo.goanno.generate.impl.DefaultFuncCommentGeneratorImpl;
import org.leo.goanno.template.impl.DefaultTemplateImpl;
import org.leo.goanno.utils.FuncUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class TestCommon {
    static final String TEST_TEMPLATE = "//\n" +
            "// ${function_name}\n" +
            "// @Description:${todo}\n" +
            "// @receiver ${receiver}\n" +
            "// @param ${params}\n" +
            "// @return ${return_types}\n" +
            "//";

    static final String TEST_FILE = "test.go";

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

    protected static void assertGenerateCode(GoFuncInfo goFuncInfo) {
        Generator generator = new DefaultFuncCommentGeneratorImpl(new DefaultTemplateImpl(0, 0), TEST_TEMPLATE);
        String code = generator.generate(FuncUtils.findFuncLine(goFuncInfo.func, 0));

        assertTrue(code.equals(goFuncInfo.comment), String.format("func: \n%s\ncomment:\n\n%s\n\nexcept:\n\n%s\n\n", goFuncInfo.func, code, goFuncInfo.comment));
    }

    protected static List<String> readFile(String file) {
        List<String> lines = null;
        InputStream in = GenerateTest.class.getClassLoader().getResourceAsStream(file);
        try {
            lines = IOUtils.readLines(in, "utf-8");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return lines;
    }

    protected static List<GoFuncInfo> readGoFuncInfoList(String file) {
        List<String> lines = readFile(file);
        if (null == lines || lines.isEmpty()) {
            return null;
        }

        List<GoFuncInfo> goFuncInfoList = new ArrayList<>();
        boolean inComment = true;
        StringBuilder commentBuilder = new StringBuilder();
        StringBuilder functionBuilder = new StringBuilder();
        for (String line : lines) {
            if (StringUtils.isEmpty(line)) {
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
}
