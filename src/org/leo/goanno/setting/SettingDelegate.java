package org.leo.goanno.setting;

public interface SettingDelegate {
    /**
     * query setting
     * @return
     */
    String loadSetting();

    /**
     * query loadSelect
     * @return
     */
    boolean loadSelect();

    /**
     * setting
     * @param setting
     * @param select
     */
    void submitSetting(String setting, boolean select);
}
