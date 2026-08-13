package com.griddynamics.pbazhko.plugin.services

import com.griddynamics.pbazhko.plugin.ui.AIChatPanel
import com.intellij.openapi.components.Service
import com.intellij.openapi.project.Project

@Service(Service.Level.PROJECT)
class AIChatPanelService {

    private var chatPanel: AIChatPanel? = null

    fun setChatPanel(chatPanel: AIChatPanel) {
        this.chatPanel = chatPanel
    }

    fun setPromptAndSend(prompt: String) {
        chatPanel?.setPromptAndSend(prompt)
    }

    companion object {
        fun getInstance(project: Project): AIChatPanelService {
            return project.getService(AIChatPanelService::class.java)
        }
    }
}
