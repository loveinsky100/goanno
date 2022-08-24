package org.leo.goanno.setting;

import java.util.List;

public class SettingExportConfig {
    private boolean convertSelected;
    private List<SettingConfig> settingConfigs;

    public SettingExportConfig() {
    }

    public SettingExportConfig(boolean convertSelected, List<SettingConfig> settingConfigs) {
        this.convertSelected = convertSelected;
        this.settingConfigs = settingConfigs;
    }

    public boolean isConvertSelected() {
        return convertSelected;
    }

    public void setConvertSelected(boolean convertSelected) {
        this.convertSelected = convertSelected;
    }

    public List<SettingConfig> getSettingConfigs() {
        return settingConfigs;
    }

    public void setSettingConfigs(List<SettingConfig> settingConfigs) {
        this.settingConfigs = settingConfigs;
    }
}
