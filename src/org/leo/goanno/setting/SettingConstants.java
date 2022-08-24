package org.leo.goanno.setting;

public interface SettingConstants {
    // rewrite comment config
    String SELECT_KEY = "ganno.setting.config.covert";

    // default template code
    String TEMPLATE = "//\n" +
            "// ${function_name}\n" +
            "//  @Description: ${todo}\n" +
            "//  @receiver ${receiver}\n" +
            "//  @param ${params}\n" +
            "//  @return ${return_types}\n" +
            "//";

    // default method template
    String SETTING_KEY = "ganno.setting.config";

    // default template code
    String INTERFACE_TEMPLATE = "//\n" +
            "//  ${interface_name}\n" +
            "//  @Description: ${todo}\n" +
            "//";

    // interface template
    String SETTING_KEY_INTERFACE = "ganno.setting.config.interface";

    // default template code
    String INTERFACE_METHOD_TEMPLATE = "//\n" +
            "// ${function_name}\n" +
            "//  @Description: ${todo}\n" +
            "//  @receiver ${receiver}\n" +
            "//  @param ${params}\n" +
            "//  @return ${return_types}\n" +
            "//";

//
//  ${function_name}
//  @Description: ${todo}
//  @receiver ${receiver}
//  @param ${params}
//  @return ${return_types}
//



    // interface method template
    String SETTING_KEY_INTERFACE_METHOD = "ganno.setting.config.interface.method";

    // default template code
    String STRUCT_TEMPLATE = "//\n" +
            "//  ${struct_name}\n" +
            "//  @Description: ${todo}\n" +
            "//";

    // struct template
    String SETTING_KEY_STRUCT = "ganno.setting.config.struct";

    // default template code
    String STRUCT_FIELD_TEMPLATE = "//\n" +
            "//  ${struct_field_name}\n" +
            "//  @Description: ${todo}\n" +
            "//";

    // struct field
    String SETTING_KEY_STRUCT_FIELD = "ganno.setting.config.struct.field";

    // default template code
    String PACKAGE_TEMPLATE = "// Package ${package_name}\n" +
            "// @Description: ${todo}";

    // package
    String SETTING_KEY_PACKAGE = "ganno.setting.config.package";

    // default template code
    String NONE_TEMPLATE = "//  ${todo}";

    // none
    String SETTING_KEY_NONE = "ganno.setting.config.none";
}
