package org.leo.goanno.action;

import com.intellij.ide.util.PropertiesComponent;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import org.apache.commons.lang.StringUtils;
import org.leo.goanno.setting.SettingComponent;
import org.leo.goanno.setting.SettingDelegate;

public class GoAnnoSettingAction extends AnAction {

    @Override
    public void actionPerformed(AnActionEvent e) {
        SettingComponent settingComponent = new SettingComponent(new SettingDelegate() {
            @Override
            public Object loadSetting(SettingComponent component, String settingKey) {
                String data = PropertiesComponent.getInstance().getValue(settingKey);
                if (StringUtils.isBlank(data)) {
                    data = "";
                }

                return data;
            }

            @Override
            public void submitSetting(SettingComponent component, String settingKey, Object value) {
                String data = "";
                if (value instanceof String) {
                    data = (String)value;
                } else if (value instanceof Boolean) {
                    data = (Boolean)value ? "true" : "false";
                }

                PropertiesComponent.getInstance().setValue(settingKey, data);
            }
        }, e.getProject());

        settingComponent.setResizable(true);
        settingComponent.show();
    }
}