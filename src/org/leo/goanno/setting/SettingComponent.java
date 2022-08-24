package org.leo.goanno.setting;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.intellij.openapi.fileChooser.FileChooser;
import com.intellij.openapi.fileChooser.FileChooserDescriptor;
import com.intellij.openapi.fileChooser.FileChooserFactory;
import com.intellij.openapi.fileChooser.FileSaverDescriptor;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.DialogWrapper;
import com.intellij.openapi.vfs.LocalFileSystem;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.openapi.vfs.VirtualFileWrapper;
import com.intellij.util.io.IOUtil;
import org.jetbrains.annotations.Nullable;
import org.leo.goanno.utils.CommonUtils;

import javax.swing.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class SettingComponent extends DialogWrapper implements SettingViewDelegate {
    private SettingView settingView;
    private SettingDelegate delegate;
    private Project project;

    public SettingComponent(SettingDelegate delegate, @Nullable Project project) {
        super(project);
        this.project = project;
        this.delegate = delegate;
        this.settingView = new SettingView(project, loadSettingConfig(), loadSettingSelected());
        if (null != delegate) {
            settingView.setDelegate(this);
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

    private List<SettingConfig> loadSettingConfig() {
        List<SettingConfig> settingConfigs = new ArrayList<>();
        settingConfigs.add(new SettingConfig("Normal Method", SettingConstants.SETTING_KEY, SettingConstants.TEMPLATE, loadSettingValue(SettingConstants.SETTING_KEY)));
        settingConfigs.add(new SettingConfig("Interface", SettingConstants.SETTING_KEY_INTERFACE, SettingConstants.INTERFACE_TEMPLATE, loadSettingValue(SettingConstants.SETTING_KEY_INTERFACE)));
        settingConfigs.add(new SettingConfig("Interface Method", SettingConstants.SETTING_KEY_INTERFACE_METHOD, SettingConstants.INTERFACE_METHOD_TEMPLATE, loadSettingValue(SettingConstants.SETTING_KEY_INTERFACE_METHOD)));
        settingConfigs.add(new SettingConfig("Struct", SettingConstants.SETTING_KEY_STRUCT, SettingConstants.STRUCT_TEMPLATE, loadSettingValue(SettingConstants.SETTING_KEY_STRUCT)));
        settingConfigs.add(new SettingConfig("Struct Field", SettingConstants.SETTING_KEY_STRUCT_FIELD, SettingConstants.STRUCT_FIELD_TEMPLATE, loadSettingValue(SettingConstants.SETTING_KEY_STRUCT_FIELD)));
        settingConfigs.add(new SettingConfig("Package", SettingConstants.SETTING_KEY_PACKAGE, SettingConstants.PACKAGE_TEMPLATE, loadSettingValue(SettingConstants.SETTING_KEY_PACKAGE)));
        settingConfigs.add(new SettingConfig("Other", SettingConstants.SETTING_KEY_NONE, SettingConstants.NONE_TEMPLATE, loadSettingValue(SettingConstants.SETTING_KEY_NONE)));

        return settingConfigs;
    }

    @Override
    public void submitSetting(SettingView view, List<SettingConfig> settingConfigs, boolean selected) {
        for (SettingConfig settingConfig : settingConfigs) {
            delegate.submitSetting(this, settingConfig.getKey(), settingConfig.getValue());
        }

        delegate.submitSetting(this, SettingConstants.SELECT_KEY, selected);
        this.close(0);
    }

    @Override
    public void exportSetting(SettingView view, List<SettingConfig> settingConfigs, boolean selected) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        SettingExportConfig settingExportConfig = new SettingExportConfig();
        settingExportConfig.setConvertSelected(selected);
        settingExportConfig.setSettingConfigs(settingConfigs);

        String config = gson.toJson(settingExportConfig);
        FileSaverDescriptor fileSaverDescriptor = new FileSaverDescriptor("Goanno", "Please choose a location");
        String homePath = System.getProperty("user.home");
        File parentPath = homePath.length() == 0 ? new File("/") : new File(homePath);

        VirtualFile parent = LocalFileSystem.getInstance().findFileByIoFile(parentPath);
        String filename = "goanno.json";
        VirtualFileWrapper fileWrapper = FileChooserFactory.getInstance().createSaveFileDialog(fileSaverDescriptor, this.project).save(parent, filename);
        File file = fileWrapper.getFile();
        try {
            if (!file.exists()) {
                file.createNewFile();
            }

            file.setWritable(true);
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            fileOutputStream.write(config.getBytes());
            fileOutputStream.flush();;
            fileOutputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void importSetting(SettingView view) {
        FileChooserDescriptor descriptor = new FileChooserDescriptor(true, false, false, false, false, false);
        VirtualFile[] virtualFiles = FileChooser.chooseFiles(descriptor, this.project, null);
        if(null == virtualFiles || virtualFiles.length == 0) {
            return;
        }

        VirtualFile configFile = virtualFiles[0];
        StringBuilder buffer = new StringBuilder();
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(configFile.getInputStream()));
            String line;
            while ((line = bufferedReader.readLine()) != null){
                buffer.append(line);
                buffer.append("\n");
            }
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }

        String config = buffer.toString();
        if (config.length() == 0) {
            return;
        }

        Gson gson = new Gson();
        SettingExportConfig settingExportConfig = gson.fromJson(config, SettingExportConfig.class);
        this.settingView.refresh(settingExportConfig.getSettingConfigs(), settingExportConfig.isConvertSelected());
    }

    private boolean loadSettingSelected() {
        return CommonUtils.toValue(Boolean.class, this.delegate.loadSetting(this, SettingConstants.SELECT_KEY), false);
    }

    private String loadSettingValue(String key) {
        return CommonUtils.toValue(String.class, this.delegate.loadSetting(this, key), "");
    }
}
