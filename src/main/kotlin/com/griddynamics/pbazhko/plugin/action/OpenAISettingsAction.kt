package com.griddynamics.pbazhko.plugin.action

import com.griddynamics.pbazhko.plugin.config.AI_SETTINGS_DESCRIPTION
import com.griddynamics.pbazhko.plugin.config.AI_SETTINGS_TITLE
import com.griddynamics.pbazhko.plugin.settings.AppSettingsConfigurable
import com.intellij.icons.AllIcons
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.options.ShowSettingsUtil

class OpenAISettingsAction : AnAction(AI_SETTINGS_TITLE, AI_SETTINGS_DESCRIPTION, AllIcons.General.Settings) {

    override fun update(e: AnActionEvent) {
        e.presentation.isEnabledAndVisible = true
    }

    override fun actionPerformed(e: AnActionEvent) {
        ShowSettingsUtil.getInstance()
            .showSettingsDialog(e.project, AppSettingsConfigurable::class.java)
    }
}
