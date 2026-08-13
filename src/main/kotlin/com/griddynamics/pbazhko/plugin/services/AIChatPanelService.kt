package com.griddynamics.pbazhko.plugin.services

import com.intellij.openapi.components.Service
import com.intellij.openapi.project.Project
import com.griddynamics.pbazhko.plugin.toolWindow.AIChatToolWindowFactory

@Service(Service.Level.PROJECT)
class AIChatPanelService {

    private var chatPanel: AIChatToolWindowFactory.AIChatPanel? = null

    fun setChatPanel(chatPanel: AIChatToolWindowFactory.AIChatPanel) {
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
