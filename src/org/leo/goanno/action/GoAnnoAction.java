package org.leo.goanno.action;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.editor.*;
import com.intellij.openapi.project.Project;
import org.apache.commons.lang.StringUtils;
import org.leo.goanno.generate.Generator;
import org.leo.goanno.generate.impl.DefaultFuncCommentGeneratorImpl;
import org.leo.goanno.template.impl.DefaultTemplateImpl;
import org.leo.goanno.utils.FuncUtils;

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

        Generator generator = new DefaultFuncCommentGeneratorImpl(new DefaultTemplateImpl(logicalPosition.column));
        String code = document.getText();
        String func = FuncUtils.findFuncLine(code, line);
        String template = generator.generate(func);
        if (StringUtils.isBlank(template)) {
            return;
        }

        int offset = document.getLineEndOffset(line);
        WriteCommandAction.runWriteCommandAction(project, () -> document.insertString(offset, template));
    }
}
