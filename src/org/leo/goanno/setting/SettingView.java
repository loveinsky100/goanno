package org.leo.goanno.setting;

import com.intellij.openapi.project.Project;
import com.intellij.ui.tabs.JBTabs;
import com.intellij.ui.tabs.TabInfo;
import com.intellij.ui.tabs.TabsListener;
import com.intellij.ui.tabs.impl.JBTabsImpl;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SettingView {

    private Project project;

    private SettingViewDelegate delegate;

    private JCheckBox jCheckBox = new JCheckBox();

    private List<SettingConfig> settingConfigs;

    private Map<String, TextArea> config2TextArea;

    private boolean selected;

    public SettingView(Project project, List<SettingConfig> settingConfigs, boolean selected) {
        this.project = project;
        this.settingConfigs = settingConfigs;
        this.selected = selected;
        this.config2TextArea = new HashMap<>();
    }

    public void refresh(List<SettingConfig> settingConfigs, boolean selected) {
        this.settingConfigs = settingConfigs;
        this.selected = selected;
        this.jCheckBox.setSelected(selected);
        for (SettingConfig config : settingConfigs) {
            TextArea textArea = config2TextArea.get(config.getKey());
            if (null == textArea) {
                continue;
            }

            textArea.setText(config.getValue());
        }
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
        jCheckBox.addActionListener(e -> {
            this.selected = jCheckBox.isSelected();
        });

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
            this.config2TextArea.put(config.getKey(), settingTextArea);
            settingTextArea.setBounds(0, 0, 400, 180);
            settingTextArea.setBackground(Color.gray);
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
        panel.setLayout(new BorderLayout());
        panel.add(tabs.getComponent(), BorderLayout.CENTER);

        JButton exportConfigButton = new JButton("export");
        JButton importConfigButton = new JButton("import");
        exportConfigButton.addActionListener(e -> {
            if (null != this.delegate) {
                this.delegate.exportSetting(this, this.settingConfigs, this.selected);
            }
        });

        importConfigButton.addActionListener(e -> {
            if (null != this.delegate) {
                this.delegate.importSetting(this);
            }
        });

        JPanel actionLayout = new JPanel();
        exportConfigButton.setHorizontalAlignment(SwingConstants.CENTER);
        exportConfigButton.setVerticalAlignment(SwingConstants.CENTER);
        importConfigButton.setHorizontalAlignment(SwingConstants.CENTER);
        importConfigButton.setVerticalAlignment(SwingConstants.CENTER);

        actionLayout.add(importConfigButton);
        actionLayout.add(exportConfigButton);

        panel.add(actionLayout, BorderLayout.SOUTH);
        return panel;
    }
}
