package org.leo.goanno.template.constants;

public interface Templates {
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

    // default template code
    String TEMPLATE_DEFAULT = "/** \n" +
            " * @Description: \n" +
            " */";
}
