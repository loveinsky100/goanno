package org.leo.goanno.template.impl;

import org.apache.commons.lang.StringUtils;
import org.leo.goanno.template.ParamTemplate;
import org.leo.goanno.template.constants.Templates;
import org.leo.goanno.utils.FuncUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DefaultTemplateImpl implements ParamTemplate {

    private Map<String, List<String>> params;
    private int left = 0;
    private int firstLineLeft = 0;

    public DefaultTemplateImpl(int left, int firstLineLeft) {
        this.left = left;
        this.firstLineLeft = firstLineLeft;
    }

    @Override
    public void addParams(String key, String value) {
        if (null == params) {
            params = new HashMap<>();
        }

        List<String> valueList = params.get(key);
        if (null == valueList) {
            valueList = new ArrayList<>();
            params.put(key, valueList);
        }

        valueList.add(value);
    }

    @Override
    public String load(String template) {
        if (null == params || params.isEmpty()) {
            return template;
        }

        if (null == template || template.length() == 0) {
            return template;
        }

        String []templateLines = template.split("\n");
        Map<String, List<String>> templateLine2CodesMap = new HashMap<>();
        for (String templateLine : templateLines) {
            String currentTemplateLine = templateLine;
            List<String> generateCodes = new ArrayList<>();
            String generateCode = templateLine;
            for (Map.Entry<String, List<String>> entry : params.entrySet()) {
                List<String> values = entry.getValue();
                if (null == values || values.isEmpty()) {
                    continue;
                }

                if (!templateLine.contains(entry.getKey())) {
                    continue;
                }

                for (String value : values) {
                    generateCode = generateCode.replace(entry.getKey(), value);
                }
            }
            generateCodes.add(generateCode);
            templateLine2CodesMap.put(currentTemplateLine, generateCodes);
        }

        StringBuilder codeGenerator = new StringBuilder();
        int line = 0;
        for (String templateLine : templateLines) {
            List<String> codes = templateLine2CodesMap.get(templateLine);
            if (null != codes && !codes.isEmpty()) {
                for (int index = 0; index < codes.size(); index ++) {
                    if (line == 0) {
                        codeGenerator.append(blank(firstLineLeft));
                    } else {
                        codeGenerator.append(blank(left));
                    }

                    codeGenerator.append(codes.get(index));
                    codeGenerator.append("\n");
                }
            } else if (!FuncUtils.containsAnyArgs(templateLine, Templates.COMMENT,  Templates.PARAMS, Templates.RECEIVER, Templates.RETS, Templates.DATE, Templates.FUNCTION_NAME)) {
                if (line == 0) {
                    codeGenerator.append(blank(firstLineLeft));
                } else {
                    codeGenerator.append(blank(left));
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

    private String blank(int left) {
        return FuncUtils.blank(left);
    }
}
