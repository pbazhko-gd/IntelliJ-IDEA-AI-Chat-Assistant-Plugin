package com.griddynamics.pbazhko.plugin.action

import com.griddynamics.pbazhko.plugin.config.ASK_AI_ABOUT_SELECTION_TITLE
import com.griddynamics.pbazhko.plugin.services.AIChatPanelService
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.CommonDataKeys
import com.intellij.openapi.wm.ToolWindowManager
import com.intellij.psi.PsiDocumentManager
import com.intellij.psi.util.PsiTreeUtil
import com.intellij.psi.PsiClass

class AskAIContextAction : AnAction(ASK_AI_ABOUT_SELECTION_TITLE) {

    override fun update(e: AnActionEvent) {

        // We need an active Project and an active Editor
        val project = e.project
        val editor = e.getData(CommonDataKeys.EDITOR)

        // Only enable if the user actually has text highlighted
        val hasSelection = editor?.selectionModel?.hasSelection() == true

        e.presentation.isEnabledAndVisible = project != null && hasSelection
    }

    override fun actionPerformed(e: AnActionEvent) {
        val project = e.project ?: return
        val editor = e.getData(CommonDataKeys.EDITOR) ?: return

        // Extract the plain text the user highlighted
        val selectedText = editor.selectionModel.selectedText ?: return
        val selectionStartOffset = editor.selectionModel.selectionStart

        // 1. Get the PSI File that corresponds to the open Document
        val psiFile = PsiDocumentManager.getInstance(project).getPsiFile(editor.document)

        // 2. Find the exact AST node at the start of the user's selection
        val elementAtCaret = psiFile?.findElementAt(selectionStartOffset)

        // 3. Walk UP the syntax tree until we find a "class" declaration!
        val parentClass = PsiTreeUtil.getParentOfType(elementAtCaret, PsiClass::class.java)
        val className = parentClass?.name ?: "Unknown Class"

        // Let's formulate our prompt
        val enhancedPrompt = """
            You are analyzing code from a class named `$className`.
            Please explain the following snippet:
            ```
            $selectedText
            ```
        """.trimIndent()

        val toolWindow = ToolWindowManager.getInstance(project).getToolWindow("AI Chat")
        toolWindow?.show {
            AIChatPanelService.getInstance(project).setPromptAndSend(enhancedPrompt)
        }
    }
}
