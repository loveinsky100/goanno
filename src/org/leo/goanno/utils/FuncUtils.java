package org.leo.goanno.utils;

import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class FuncUtils {
    public static String findFuncLine(String code, int line) {
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
            }

            break;
        }

        String func = funcLine.toString();
        func = removeLast(func, '{');
        if (StringUtils.isEmpty(func)) {
            return findInterfaceFuncLine(code, line);
        }

        return func;
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
