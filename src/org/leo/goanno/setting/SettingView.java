package org.leo.goanno.setting;

import com.intellij.openapi.project.Project;
import com.intellij.ui.tabs.JBTabs;
import com.intellij.ui.tabs.TabInfo;
import com.intellij.ui.tabs.TabsListener;
import com.intellij.ui.tabs.impl.JBTabsImpl;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class SettingView {

    private Project project;

    private SettingViewDelegate delegate;

    private JCheckBox jCheckBox = new JCheckBox();

    private List<SettingConfig> settingConfigs;

    private SettingConfig selectConfig;

    private boolean selected;

    public SettingView(Project project, List<SettingConfig> settingConfigs, boolean selected) {
        this.project = project;
        this.settingConfigs = settingConfigs;
        this.selectConfig = settingConfigs.get(0);
        this.selected = selected;
    }

    public void setDelegate(SettingViewDelegate delegate) {
        this.delegate = delegate;
    }

    public JPanel initCenter() {
        JPanel center = new JPanel();
        JLabel covertCommentLabel = new JLabel("match original comments");
        covertCommentLabel.setHorizontalAlignment(SwingConstants.LEFT);
        covertCommentLabel.setVerticalAlignment(SwingConstants.CENTER);

        center.add(covertCommentLabel);
        jCheckBox.setSelected(this.selected);

        jCheckBox.setHorizontalAlignment(SwingConstants.LEFT);
        jCheckBox.setVerticalAlignment(SwingConstants.CENTER);
        center.add(jCheckBox);
        return center;
    }

    public JPanel initSouth() {
        JPanel south = new JPanel();
        JButton submit = new JButton("submit");
        submit.setHorizontalAlignment(SwingConstants.CENTER);
        submit.setVerticalAlignment(SwingConstants.CENTER);
        south.add(submit);

        submit.addActionListener(e -> {
            boolean select = jCheckBox.isSelected();
            if (null != this.delegate) {
                this.delegate.submitSetting(this, this.settingConfigs, select);
            }
        });

        return south;
    }

    public JPanel initNorth() {
        JBTabs tabs = new JBTabsImpl(project);
        for (SettingConfig config : this.settingConfigs) {
            JPanel tabPanel = new JPanel();

            TextArea settingTextArea = new TextArea();
            settingTextArea.setBounds(0, 0, 400, 200);
            settingTextArea.setBackground(Color.BLACK);
            settingTextArea.setText(config.getValue());

            settingTextArea.addTextListener((e) -> {
                config.setValue(settingTextArea.getText());
            });

            tabPanel.add(settingTextArea);

            TabInfo tabInfo = new TabInfo(tabPanel);
            tabInfo.setObject(config);
            tabInfo.setText(config.getName());

            tabs.addTab(tabInfo);
        }

        JPanel panel = new JPanel();
        panel.add(tabs.getComponent());
        return panel;
    }
}
