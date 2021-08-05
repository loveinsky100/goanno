package org.leo.goanno.setting;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.DialogWrapper;
import org.jetbrains.annotations.Nullable;
import org.leo.goanno.utils.CommonUtils;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class SettingComponent extends DialogWrapper {
    private SettingView settingView;
    private SettingDelegate delegate;

    public SettingComponent(SettingDelegate delegate, @Nullable Project project) {
        super(project);
        this.delegate = delegate;
        this.settingView = new SettingView(project, loadSettingConfig(), loadSettingSelected());
        if (null != delegate) {
            settingView.setDelegate((SettingView view, List<SettingConfig> settingConfigs, boolean selected) -> {
                for (SettingConfig settingConfig : settingConfigs) {
                    delegate.submitSetting(this, settingConfig.getKey(), settingConfig.getValue());
                }

                delegate.submitSetting(this, SettingConstants.SELECT_KEY, selected);
                this.close(0);
            });
        }

        setTitle("Edit Goanno Template");
        init();
    }

    @Override
    protected JComponent createNorthPanel() {
        return settingView.initNorth();
    }

    @Override
    protected JComponent createSouthPanel() {
        return settingView.initSouth();
    }

    @Override
    protected JComponent createCenterPanel() {
        return settingView.initCenter();
    }

    private List<SettingConfig> loadSettingConfig() {
        List<SettingConfig> settingConfigs = new ArrayList<>();
        settingConfigs.add(new SettingConfig("Normal Method", SettingConstants.SETTING_KEY, SettingConstants.TEMPLATE, loadSettingValue(SettingConstants.SETTING_KEY)));
        settingConfigs.add(new SettingConfig("Interface", SettingConstants.SETTING_KEY_INTERFACE, SettingConstants.INTERFACE_TEMPLATE, loadSettingValue(SettingConstants.SETTING_KEY_INTERFACE)));
        settingConfigs.add(new SettingConfig("Interface Method", SettingConstants.SETTING_KEY_INTERFACE_METHOD, SettingConstants.INTERFACE_METHOD_TEMPLATE, loadSettingValue(SettingConstants.SETTING_KEY_INTERFACE_METHOD)));
        settingConfigs.add(new SettingConfig("Struct", SettingConstants.SETTING_KEY_STRUCT, SettingConstants.STRUCT_TEMPLATE, loadSettingValue(SettingConstants.SETTING_KEY_STRUCT)));
        settingConfigs.add(new SettingConfig("Struct Field", SettingConstants.SETTING_KEY_STRUCT_FIELD, SettingConstants.STRUCT_FIELD_TEMPLATE, loadSettingValue(SettingConstants.SETTING_KEY_STRUCT_FIELD)));
        settingConfigs.add(new SettingConfig("Package", SettingConstants.SETTING_KEY_PACKAGE, SettingConstants.PACKAGE_TEMPLATE, loadSettingValue(SettingConstants.SETTING_KEY_PACKAGE)));
        settingConfigs.add(new SettingConfig("Other", SettingConstants.SETTING_KEY_NONE, SettingConstants.NONE_TEMPLATE, loadSettingValue(SettingConstants.SETTING_KEY_NONE)));

        return settingConfigs;
    }

    private boolean loadSettingSelected() {
        return CommonUtils.toValue(Boolean.class, this.delegate.loadSetting(this, SettingConstants.SELECT_KEY), false);
    }

    private String loadSettingValue(String key) {
        return CommonUtils.toValue(String.class, this.delegate.loadSetting(this, key), "");
    }
}
