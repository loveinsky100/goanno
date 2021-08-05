package org.leo.goanno.setting;

import java.util.List;

public interface SettingDelegate {
    /**
     *
     * @param settingComponent
     * @param settingKey
     * @return
     */
    Object loadSetting(SettingComponent settingComponent, String settingKey);

    /**
     *
     * @param settingComponent
     * @param settingKey
     * @param value
     */
    void submitSetting(SettingComponent settingComponent, String settingKey, Object value);
}