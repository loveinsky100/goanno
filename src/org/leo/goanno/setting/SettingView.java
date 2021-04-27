package org.leo.goanno.setting;

import javax.swing.*;
import java.awt.*;

public class SettingView {
    private SettingDelegate delegate;

    private JPanel north = new JPanel();

    private JPanel center = new JPanel();

    private JPanel south = new JPanel();

    private TextArea settingTestArea = new TextArea();

    private JCheckBox jCheckBox = new JCheckBox();

    public void setDelegate(SettingDelegate delegate) {
        this.delegate = delegate;
    }

    public JPanel initNorth() {
        north.setLayout(new GridLayout(1, 1));

        settingTestArea = new TextArea();
        settingTestArea.setBounds(0, 0, 400, 500);
        if (null != this.delegate) {
            settingTestArea.setText(this.delegate.loadSetting());
        }

        north.add(settingTestArea);
        return north;
    }

    public JPanel initCenter() {
        JLabel covertCommentLabel = new JLabel("match original comments");
        covertCommentLabel.setHorizontalAlignment(SwingConstants.LEFT);
        covertCommentLabel.setVerticalAlignment(SwingConstants.CENTER);

        center.add(covertCommentLabel);

        if (null != this.delegate) {
            jCheckBox.setSelected(this.delegate.loadSelect());
        }

        jCheckBox.setHorizontalAlignment(SwingConstants.LEFT);
        jCheckBox.setVerticalAlignment(SwingConstants.CENTER);
        center.add(jCheckBox);
        return center;
    }

    public JPanel initSouth() {
        JButton submit = new JButton("submit");
        submit.setHorizontalAlignment(SwingConstants.CENTER);
        submit.setVerticalAlignment(SwingConstants.CENTER);
        south.add(submit);

        submit.addActionListener(e -> {
            String setting = settingTestArea.getText();
            boolean select = jCheckBox.isSelected();
            if (null != this.delegate) {
                this.delegate.submitSetting(setting, select);
            }
        });

        return south;
    }
}
