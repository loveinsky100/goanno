package org.leo.goanno.action;

import com.intellij.ide.util.PropertiesComponent;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import org.apache.commons.lang.StringUtils;
import org.leo.goanno.setting.SettingComponent;
import org.leo.goanno.setting.SettingDelegate;
import org.leo.goanno.template.constants.Templates;

public class GoAnnoSettingAction extends AnAction {

    @Override
    public void actionPerformed(AnActionEvent e) {
        SettingComponent settingComponent = new SettingComponent(new SettingDelegate() {
            @Override
            public String loadSetting() {
                String data = PropertiesComponent.getInstance().getValue(Templates.SETTING_KEY);
                if (StringUtils.isBlank(data)) {
                    data = Templates.TEMPLATE;
                }

                return data;
            }

            @Override
            public void submitSetting(String setting) {
                if (StringUtils.isBlank(setting)) {
                    return;
                }

                PropertiesComponent.getInstance().setValue(Templates.SETTING_KEY, setting);
            }
        }, e.getProject());

        settingComponent.setResizable(true);
        settingComponent.show();
    }
}