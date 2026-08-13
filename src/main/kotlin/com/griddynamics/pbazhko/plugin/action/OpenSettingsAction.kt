package com.griddynamics.pbazhko.plugin.action

import com.griddynamics.pbazhko.plugin.settings.AppSettingsConfigurable
import com.intellij.icons.AllIcons
import com.intellij.openapi.actionSystem.ActionUpdateThread
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.options.ShowSettingsUtil

class OpenSettingsAction : AnAction("AI Settings", "Configure AI Provider", AllIcons.General.Settings) {

    override fun update(e: AnActionEvent) {
        e.presentation.isEnabledAndVisible = true
    }

    override fun actionPerformed(e: AnActionEvent) {
        ShowSettingsUtil.getInstance()
            .showSettingsDialog(e.project, AppSettingsConfigurable::class.java)
    }

    override fun getActionUpdateThread(): ActionUpdateThread {
        return super.getActionUpdateThread()
    }
}
