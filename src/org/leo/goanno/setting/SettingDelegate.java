package org.leo.goanno.setting;

public interface SettingDelegate {
    /**
     * query setting
     * @return
     */
    String loadSetting();

    /**
     * update setting
     * @param setting
     */
    void submitSetting(String setting);
}
