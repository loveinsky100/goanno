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

    // current date in "yyyy-MM-dd HH:mm:ss"
    String DATE = "${date}";

    // current method name
    String FUNCTION_NAME = "${function_name}";

    // param name info
    String PARAM_NAME = "${param_name}";

    // param info
    String PARAM_TYPE = "${param_type}";

    // param name & type
    String PARAM_NAME_TYPE = "${param_name_type}";

    // golang receiver name
    String RECEIVER_NAME = "${receiver_name}";

    // golang receiver type
    String RECEIVER_TYPE = "${receiver_type}";

    // golang receiver name & type
    String RECEIVER_NAME_TYPE = "${receiver_name_type}";

    // return info
    String RET = "${ret}";

    // return name
    String RET_NAME = "${ret_name}";

    // return type
    String RET_TYPE = "${ret_type}";

    // return type & name
    String RET_NAME_TYPE = "${ret_name_type}";

    // note info
    String NOTE = "${note}";

    // name of interface
    String INTERFACE_NAME = "${interface_name}";

    // name of package
    String PACKAGE_NAME = "${package_name}";

    // name of struct
    String STRUCT_NAME = "${struct_name}";

    // name of struct field
    String STRUCT_FIELD_NAME = "${struct_field_name}";

    // break line
    String BREAK_LINE = "${break_line}";
}
