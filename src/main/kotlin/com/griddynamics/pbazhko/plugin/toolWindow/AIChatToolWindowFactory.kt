package com.griddynamics.pbazhko.plugin.toolWindow

import com.intellij.openapi.project.Project
import com.intellij.openapi.wm.ToolWindow
import com.intellij.openapi.wm.ToolWindowFactory
import com.intellij.ui.content.ContentFactory
import com.intellij.openapi.ui.SimpleToolWindowPanel
import com.intellij.ui.components.JBList
import com.intellij.ui.components.JBScrollPane
import com.intellij.ui.components.JBTextArea
import java.awt.BorderLayout
import javax.swing.JPanel

class AIChatToolWindowFactory : ToolWindowFactory {

    override fun createToolWindowContent(project: Project, toolWindow: ToolWindow) {
        val chatPanel = AIChatPanel()
        val contentFactory = ContentFactory.getInstance()
        val content = contentFactory.createContent(chatPanel, "", false)
        toolWindow.contentManager.addContent(content)
    }

    override fun shouldBeAvailable(project: Project) = true

    class AIChatPanel : SimpleToolWindowPanel(true, true) {

        init {
            val messageList = JBList<String>()
            val scrollPane = JBScrollPane(messageList)

            val inputArea = JBTextArea()
            inputArea.rows = 3
            inputArea.emptyText.text = "Ask AI a question..."

            val inputScrollPane = JBScrollPane(inputArea)

            val mainContainer = JPanel(BorderLayout())
            mainContainer.add(scrollPane, BorderLayout.CENTER)
            mainContainer.add(inputScrollPane, BorderLayout.SOUTH)

            setContent(mainContainer)
        }
    }
}
