package org.leo.goanno.action;

import com.intellij.ide.util.PropertiesComponent;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.editor.*;
import com.intellij.openapi.project.Project;
import org.apache.commons.lang.StringUtils;
import org.leo.goanno.model.GenerateResult;
import org.leo.goanno.utils.GeneratorUtils;
import org.leo.goanno.model.GenerateInfo;
import org.leo.goanno.setting.SettingConstants;
import org.leo.goanno.utils.CommentUtils;
import org.leo.goanno.utils.FuncUtils;

import java.util.List;

/**
 * generate go comment template
 */
public class GoAnnoAction extends AnAction {
    @Override
    public void actionPerformed(AnActionEvent e) {
        // TODO: insert action logic here
        Project project = e.getData(PlatformDataKeys.PROJECT);
        if (null == project) {
            return;
        }

        Editor editor = e.getData(PlatformDataKeys.EDITOR);
        if (null == editor) {
            return;
        }

        CaretModel caretModel = editor.getCaretModel();
        if (null == caretModel) {
            return;
        }

        LogicalPosition logicalPosition = caretModel.getLogicalPosition();
        if (null == logicalPosition) {
            return;
        }

        int line = logicalPosition.line;
        // find first func
        Document document = editor.getDocument();
        if (null == document) {
            return;
        }

        String code = document.getText();
        GenerateInfo generateInfo = FuncUtils.findGenerateInfo(code, line);
        int blankLength = FuncUtils.firstBlankLength(generateInfo.getFunc());

        GenerateResult result = GeneratorUtils.generate(generateInfo, blankLength, Math.max(0, blankLength - logicalPosition.column));
        String template = null != result ? result.getResult() : "";
        int todoIndex = null != result ? result.getTodoIndex() : 0;
        if (StringUtils.isBlank(template)) {
            return;
        }

        String select = PropertiesComponent.getInstance().getValue(SettingConstants.SELECT_KEY);
        if (StringUtils.isEmpty(select) || StringUtils.equals("true", select)) {
            List<String> comments = CommentUtils.findComment(code, line);
            template = CommentUtils.mergeComment(template, comments);

            int start = editor.getSelectionModel().getSelectionStart();
            int end = editor.getSelectionModel().getSelectionEnd();

            String current = template;
            WriteCommandAction.runWriteCommandAction(project, () -> {
                document.insertString(end, current);
                document.deleteString(start, end);
                caretModel.moveToOffset(caretModel.getOffset() + todoIndex);
            });

        } else {
            String current = template;
            int offset = document.getLineEndOffset(line);
            WriteCommandAction.runWriteCommandAction(project, () -> {
                document.insertString(offset, current);
                caretModel.moveToOffset(caretModel.getOffset() + todoIndex);
            });
        }
    }
}
