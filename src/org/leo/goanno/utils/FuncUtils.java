package org.leo.goanno.utils;

import org.apache.commons.lang.StringUtils;
import org.leo.goanno.model.GenerateEnums;
import org.leo.goanno.model.GenerateInfo;

import java.util.ArrayList;
import java.util.List;

public class FuncUtils {
    public static GenerateInfo findGenerateInfo(String code, int line) {
        String []lines = code.split("\n");
        StringBuilder funcLine = new StringBuilder();
        boolean findFirst = false;
        for (int index = line; index < lines.length; index ++) {
            String lineCode = lines[index];
            if (StringUtils.isBlank(lineCode)) {
                continue;
            }

            String lineCodeInfo = StringUtils.trim(lineCode);
            if (lineCodeInfo.startsWith("//") || lineCodeInfo.startsWith("*") || lineCodeInfo.startsWith("/*")) {
                continue;
            }

            if (findFirst) {
                funcLine.append(" ").append(lineCode);
                if (lineCodeInfo.contains("{")) {
                    break;
                }

                continue;
            }

            if (lineCodeInfo.startsWith("func")) {
                funcLine.append(lineCode);
                findFirst = true;
                // find it
                if (lineCodeInfo.contains("{")) {
                    break;
                }

                continue;
            } else if (lineCodeInfo.startsWith("package")) {
                // package
                lineCodeInfo = lineCodeInfo.replace("package", "");
                lineCodeInfo = lineCodeInfo.replaceAll(" ", "");
                GenerateInfo generateInfo = new GenerateInfo();
                generateInfo.setGenerateEnums(GenerateEnums.PACKAGE);
                generateInfo.setPackageName(lineCodeInfo);

                return generateInfo;
            } else if (lineCodeInfo.startsWith("type")) {
                // interface or struct or typedef
                lineCodeInfo = lineCodeInfo.replace("type", "");
                lineCodeInfo = lineCodeInfo.replaceAll(" ", "");

                if (lineCodeInfo.endsWith("interface") || lineCodeInfo.endsWith("interface{")) {
                    lineCodeInfo = lineCodeInfo.substring(0, lineCodeInfo.length() - (lineCodeInfo.endsWith("interface") ? "interface".length() : "interface{".length()));
                    GenerateInfo generateInfo = new GenerateInfo();
                    generateInfo.setGenerateEnums(GenerateEnums.INTERFACE);
                    generateInfo.setInterfaceName(lineCodeInfo);

                    return generateInfo;
                } else if (lineCodeInfo.endsWith("struct") || lineCodeInfo.endsWith("struct{")) {
                    lineCodeInfo = lineCodeInfo.substring(0, lineCodeInfo.length() - (lineCodeInfo.endsWith("struct") ? "struct".length() : "struct{".length()));
                    GenerateInfo generateInfo = new GenerateInfo();
                    generateInfo.setGenerateEnums(GenerateEnums.STRUCT);
                    generateInfo.setStructName(lineCodeInfo);

                    return generateInfo;
                }
            }

            break;
        }

        GenerateInfo generateInfo = new GenerateInfo();
        generateInfo.setGenerateEnums(GenerateEnums.NONE);

        String func = funcLine.toString();
        func = removeLast(func, '{');
        if (StringUtils.isEmpty(func)) {
            String interfaceFunc = findInterfaceFuncLine(code, line);
            if (null != interfaceFunc && interfaceFunc.length() > 0) {
                generateInfo.setFunc(interfaceFunc);

                String interfaceName = findInterfaceName(code, line);
                if (!StringUtils.isEmpty(interfaceName)) {
                    generateInfo.setInterfaceName(interfaceName);
                    generateInfo.setGenerateEnums(GenerateEnums.INTERFACE_METHOD);

                    return generateInfo;
                }

                String structName = findStructName(code, line);
                if (!StringUtils.isEmpty(structName)) {
                    generateInfo.setStructName(structName);
                    generateInfo.setGenerateEnums(GenerateEnums.STRUCT_FIELD);

                    interfaceFunc = interfaceFunc.replaceFirst("func", "");
                    // remove last type info
                    interfaceFunc = interfaceFunc.trim();

                    String[] interfaceFuncs = interfaceFunc.split(" ");
                    if (null == interfaceFuncs || interfaceFuncs.length <= 1) {
                        generateInfo.setStructFieldName(interfaceFunc.trim());
                    } else {
                        generateInfo.setStructFieldName(interfaceFuncs[0].trim());
                    }

                    return generateInfo;
                }
            }
        } else {
            generateInfo.setGenerateEnums(GenerateEnums.NORMAL_METHOD);
            generateInfo.setFunc(func);
        }

        return generateInfo;
    }

    public static String findInterfaceFuncLine(String code, int line) {
        String []lines = code.split("\n");
        StringBuilder funcLine = new StringBuilder();
        for (int index = line; index < lines.length; index ++) {
            String lineCode = lines[index];
            if (StringUtils.isBlank(lineCode)) {
                continue;
            }

            String lineCodeInfo = StringUtils.trim(lineCode);
            if (lineCodeInfo.startsWith("//") || lineCodeInfo.startsWith("*") || lineCodeInfo.startsWith("/*")) {
                continue;
            }

            funcLine.append(lineCode);
            break;
        }

        String func = funcLine.toString();
        int blankLength = firstBlankLength(func);
        return blank(blankLength) + "func " + func;
    }

    public static String findInterfaceName(String code, int line) {
        String []lines = code.split("\n");
        String interfaceLine = null;
        for (int index = line; index >= 0; index --) {
            String lineCode = lines[index];
            if (StringUtils.isBlank(lineCode)) {
                continue;
            }

            String lineCodeAll = lineCode.replaceAll(" ", "");
            if (lineCodeAll.equals("}")) {
                break;
            }

            String lineCodeInfo = lineCode.replaceAll(" ", "");
            if (lineCodeInfo.startsWith("type") && (lineCodeInfo.endsWith("interface{") || lineCodeInfo.endsWith("interface"))) {
                // find interface define
                interfaceLine = lineCode;
                break;
            }
        }

        if (null == interfaceLine || interfaceLine.length() == 0) {
            return null;
        }

        String lineCodeInfo = interfaceLine.replaceAll(" ", "");
        lineCodeInfo = lineCodeInfo.replaceFirst("type", "");
        if (lineCodeInfo.endsWith("interface{")) {
            lineCodeInfo = lineCodeInfo.substring(0, lineCodeInfo.length() - "interface{".length());
        } else if (lineCodeInfo.endsWith("interface")) {
            lineCodeInfo = lineCodeInfo.substring(0, lineCodeInfo.length() - "interface".length());
        }

        String interfaceName = lineCodeInfo;
        return interfaceName;
    }

    public static String findStructName(String code, int line) {
        String []lines = code.split("\n");
        String interfaceLine = null;
        for (int index = line; index >= 0; index --) {
            String lineCode = lines[index];
            if (StringUtils.isBlank(lineCode)) {
                continue;
            }

            String lineCodeAll = lineCode.replaceAll(" ", "");
            if (lineCodeAll.equals("}")) {
                break;
            }

            String lineCodeInfo = lineCode.replaceAll(" ", "");
            if (lineCodeInfo.startsWith("type") && (lineCodeInfo.endsWith("struct{") || lineCodeInfo.endsWith("struct"))) {
                // find interface define
                interfaceLine = lineCode;
                break;
            }
        }

        if (null == interfaceLine || interfaceLine.length() == 0) {
            return null;
        }

        String lineCodeInfo = interfaceLine.replaceAll(" ", "");
        lineCodeInfo = lineCodeInfo.replaceFirst("type", "");
        if (lineCodeInfo.endsWith("struct{")) {
            lineCodeInfo = lineCodeInfo.substring(0, lineCodeInfo.length() - "struct{".length());
        } else if (lineCodeInfo.endsWith("struct")) {
            lineCodeInfo = lineCodeInfo.substring(0, lineCodeInfo.length() - "struct".length());
        }

        String interfaceName = lineCodeInfo;
        return interfaceName;
    }

    public static String betweenString(String value, char left, char right) {
        char []values = value.toCharArray();
        int begin = 0;
        int end = 0;
        int leftCount = 0;
        int rightCount = 0;

        for (int index = 0; index < values.length; index++) {
            char v = values[index];
            if (v == left) {
                if (begin == 0) {
                    begin = index;
                }

                leftCount ++;
            } else if (v == right) {
                end = index;
                rightCount ++;
            }

            if (end != 0 && leftCount == rightCount) {
                break;
            }
        }

        return StringUtils.substring(value, begin + 1, end);
    }

    public static String removeStartBlank(String value) {
        if (null == value) {
            return null;
        }

        char []values = value.toCharArray();
        StringBuilder stringBuilder = new StringBuilder();
        boolean isStart = true;
        for (char v : values) {
            if (v == ' ' && isStart) {
                continue;
            }

            isStart = false;
            stringBuilder.append(v);
        }

        return stringBuilder.toString();
    }

    public static String removeLast(String value, char remove) {
        if (StringUtils.isBlank(value)) {
            return value;
        }

        char []values = value.toCharArray();
        int removeIndex = -1;
        for (int index = values.length - 1; index >= 0; index --) {
            char v = values[index];
            if (v == ' ') {
                continue;
            }

            if (v == remove) {
                removeIndex = index;
            }

            break;
        }

        // fix: https://github.com/loveinsky100/goanno/issues/10
        if (-1 == removeIndex) {
            return value;
        }

        return value.substring(0, removeIndex);
    }

    public static int firstBlankLength(String value) {
        if (StringUtils.isEmpty(value)) {
            return 0;
        }

        char []cs = value.toCharArray();
        int count = 0;
        for (char c : cs) {
            if (c == '\t') {
                count += 4;
            } else if (c == ' ') {
                count += 1;
            } else {
                break;
            }
        }

        return count;
    }

    public static String blank(int left) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int index = 0; index < left; index++) {
            stringBuilder.append(" ");
        }

        return stringBuilder.toString();
    }

    public static boolean containsAnyArgs(String template, String ...args) {
        for (String arg : args) {
            if (template.contains(arg)) {
                return true;
            }
        }

        return false;
    }

    public static boolean containsOnly(String template, String onlyArg, String ...args) {
        List<String> contains = getContainsAnyArgs(template, args);
        if (null == contains || contains.size() == 0) {
            return false;
        }

        for (String contain : contains) {
            if (contain != onlyArg) {
                return false;
            }
        }

        return true;
    }

    public static List<String> getContainsAnyArgs(String template, String ...args) {
        List<String> contains = new ArrayList<>();
        for (String arg : args) {
            if (template.contains(arg)) {
                contains.add(arg);
            }
        }

        return contains;
    }
}
