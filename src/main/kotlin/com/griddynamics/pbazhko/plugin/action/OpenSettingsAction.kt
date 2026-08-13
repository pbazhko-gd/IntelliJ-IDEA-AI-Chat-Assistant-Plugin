package com.griddynamics.pbazhko.plugin.action

import com.intellij.icons.AllIcons
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.ui.Messages

class OpenSettingsAction : AnAction("AI Settings", "Configure AI Provider", AllIcons.General.Settings) {

    // Always visible and enabled for our chat window
    override fun update(e: AnActionEvent) {
        e.presentation.isEnabledAndVisible = true
    }

    override fun actionPerformed(e: AnActionEvent) {
        // Retrieve the current project context safely
        val project = e.project

        // Placeholder behavior for now
        Messages.showInfoMessage(
            project,
            "The settings screen will open here (Coming in Module 4!)",
            "AI Chat Settings"
        )
    }
}
