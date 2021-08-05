package org.leo.goanno.utils;

import com.intellij.ide.util.PropertiesComponent;
import org.leo.goanno.generate.Generator;
import org.leo.goanno.generate.impl.FuncCommentGeneratorV2Impl;
import org.leo.goanno.generate.impl.ParamCommentGeneratorImpl;
import org.leo.goanno.model.GenerateInfo;
import org.leo.goanno.setting.SettingConfig;
import org.leo.goanno.setting.SettingConstants;
import org.leo.goanno.template.impl.DefaultTemplateImpl;
import org.leo.goanno.template.impl.GoMethodTemplateImpl;

public class GeneratorUtils {
    public static String generate(GenerateInfo generateInfo, int left, int firstLineLeft) {
        // read template
        String template = readTemplate(generateInfo);

        switch (generateInfo.getGenerateEnums()) {
            case STRUCT:
            case PACKAGE:
            case INTERFACE:
            case STRUCT_FIELD:
            case NONE: {
                Generator generator = new ParamCommentGeneratorImpl(new DefaultTemplateImpl(left, firstLineLeft), template);
                return generator.generate(generateInfo);
            }
            default:{
                Generator generator = new FuncCommentGeneratorV2Impl(new GoMethodTemplateImpl(left, firstLineLeft), template);
                return generator.generate(generateInfo);
            }
        }
    }

    public static String readTemplate(GenerateInfo generateInfo) {
        switch (generateInfo.getGenerateEnums()) {
            case STRUCT: {
                SettingConfig settingConfig = new SettingConfig("Struct", SettingConstants.SETTING_KEY_STRUCT, SettingConstants.STRUCT_TEMPLATE, loadSettingValue(SettingConstants.SETTING_KEY_STRUCT));
                return settingConfig.getValue();
            }
            case PACKAGE: {
                SettingConfig settingConfig = new SettingConfig("Package", SettingConstants.SETTING_KEY_PACKAGE, SettingConstants.PACKAGE_TEMPLATE, loadSettingValue(SettingConstants.SETTING_KEY_PACKAGE));
                return settingConfig.getValue();
            }
            case INTERFACE: {
                SettingConfig settingConfig = new SettingConfig("Interface", SettingConstants.SETTING_KEY_INTERFACE, SettingConstants.INTERFACE_TEMPLATE, loadSettingValue(SettingConstants.SETTING_KEY_INTERFACE));
                return settingConfig.getValue();
            }
            case STRUCT_FIELD: {
                SettingConfig settingConfig = new SettingConfig("Struct Field", SettingConstants.SETTING_KEY_STRUCT_FIELD, SettingConstants.STRUCT_FIELD_TEMPLATE, loadSettingValue(SettingConstants.SETTING_KEY_STRUCT_FIELD));
                return settingConfig.getValue();
            }
            case NONE: {
                SettingConfig settingConfig = new SettingConfig("Other", SettingConstants.SETTING_KEY_NONE, SettingConstants.NONE_TEMPLATE, loadSettingValue(SettingConstants.SETTING_KEY_NONE));
                return settingConfig.getValue();
            }
            case INTERFACE_METHOD: {
                SettingConfig settingConfig = new SettingConfig("Interface Method", SettingConstants.SETTING_KEY_INTERFACE_METHOD, SettingConstants.INTERFACE_METHOD_TEMPLATE, loadSettingValue(SettingConstants.SETTING_KEY_INTERFACE_METHOD));
                return settingConfig.getValue();
            }
            case NORMAL_METHOD: {
                SettingConfig settingConfig = new SettingConfig("Normal Method", SettingConstants.SETTING_KEY, SettingConstants.TEMPLATE, loadSettingValue(SettingConstants.SETTING_KEY));
                return settingConfig.getValue();
            }
            default: {
                return "";
            }
        }
    }

    private static String loadSettingValue(String key) {
        return PropertiesComponent.getInstance().getValue(key);
    }
}
