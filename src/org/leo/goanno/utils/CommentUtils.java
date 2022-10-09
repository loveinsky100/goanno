package org.leo.goanno.utils;

import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CommentUtils {
    public static List<String> findComment(String code, int line) {
        String []lines = code.split("\n");
        int commentFuncLine = line;
        for (int index = line; index < lines.length; index ++) {
            String lineCode = lines[index];
            if (StringUtils.isBlank(lineCode)) {
                continue;
            }

            String lineCodeInfo = StringUtils.trim(lineCode);
            if (lineCodeInfo.startsWith("//") || lineCodeInfo.startsWith("*") || lineCodeInfo.startsWith("/*")) {
                continue;
            }

            commentFuncLine = index;
            break;
        }

        List<String > comments = new ArrayList<>();
        for (int index = commentFuncLine - 1; index >= 0; index --) {
            String lineCode = lines[index];
            if (StringUtils.isBlank(lineCode)) {
                continue;
            }

            String lineCodeInfo = StringUtils.trim(lineCode);
            if (!lineCodeInfo.startsWith("//") && !lineCodeInfo.startsWith("*") && !lineCodeInfo.startsWith("/*")) {
                break;
            }

            if (lineCode.startsWith("\t")) {
                // user 4 blank instant
                lineCode = lineCode.replace("\t", "    ");
            }

            comments.add(lineCode);
        }

        // revert array
        Collections.reverse(comments);
        return comments;
    }

    public static String mergeComment(String current, List<String> lasts) {
        String []currents = StringUtils.split(current, "\n");
        if (null == currents || currents.length == 0) {
            return current;
        }

        List<String> comments = new ArrayList<>();
        for (String currentLine : currents) {
            // find first
            String matchLine = "";

            String lineCodeInfo = StringUtils.trim(currentLine);
            if (lineCodeInfo.equals("//") || lineCodeInfo.equals("*") || lineCodeInfo.equals("/*") || lineCodeInfo.equals("/**") || lineCodeInfo.equals("/")) {
                comments.add(currentLine);
                continue;
            }

            for (String last : lasts) {
                if (last.startsWith(currentLine)) {
                    matchLine = last;
                    break;
                }
            }

            if (StringUtils.isEmpty(matchLine)) {
                comments.add(currentLine);
                continue;
            }

            lasts.remove(matchLine);
            comments.add(matchLine);
        }

        return StringUtils.join(comments, "\n");
    }
}
