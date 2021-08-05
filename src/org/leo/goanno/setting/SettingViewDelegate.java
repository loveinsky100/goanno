package org.leo.goanno.setting;

import java.util.List;

public interface SettingViewDelegate {
    /**
     *
     * @param view
     * @param settingConfigs
     * @param selected
     */
    void submitSetting(SettingView view, List<SettingConfig> settingConfigs, boolean selected);
}