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

    /**
     *
     * @param view
     * @param settingConfigs
     * @param selected
     */
    void exportSetting(SettingView view, List<SettingConfig> settingConfigs, boolean selected);

    /**
     *
     * @param view
     */
    void importSetting(SettingView view);
}