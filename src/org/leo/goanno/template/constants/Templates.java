package org.leo.goanno.template.constants;

public interface Templates {

    String SETTING_KEY = "ganno.setting.config";

    // comment area
    String COMMENT = "${todo}";

    // param info
    String PARAMS = "${params}";

    // return info
    String RETS = "${return_types}";

    // golang receiver
    String RECEIVER = "${receiver}";

    // default template code
    String TEMPLATE = "/** \n" +
            " * @Description: ${todo}\n" +
            " * @receiver ${receiver}\n" +
            " * @param ${params}\n" +
            " * @return ${return_types}\n" +
            " */";

    // current date in "yyyy-MM-dd HH:mm:ss"
    String DATE = "${date}";

    // current method name
    String FUNCTION_NAME = "${function_name}";
}
