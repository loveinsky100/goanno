package org.leo.goanno.setting;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.DialogWrapper;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

public class SettingComponent extends DialogWrapper {
    private SettingView settingView = new SettingView();
    public SettingComponent(SettingDelegate delegate, @Nullable Project project) {
        super(project);
        if (null != delegate) {
            SettingComponent thiz = this;
            settingView.setDelegate(new SettingDelegate() {
                @Override
                public String loadSetting() {
                    return delegate.loadSetting();
                }

                @Override
                public boolean loadSelect() {
                    return delegate.loadSelect();
                }

                @Override
                public void submitSetting(String setting, boolean select) {
                    delegate.submitSetting(setting, select);
                    thiz.close(0);
                }
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
}
