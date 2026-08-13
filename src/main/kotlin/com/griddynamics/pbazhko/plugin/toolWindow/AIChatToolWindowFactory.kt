package com.griddynamics.pbazhko.plugin.toolWindow

import com.google.gson.Gson
import com.griddynamics.pbazhko.plugin.action.OpenSettingsAction
import com.griddynamics.pbazhko.plugin.services.AIChatPanelService
import com.griddynamics.pbazhko.plugin.settings.SecureStorage
import com.griddynamics.pbazhko.plugin.state.AppSettingsState
import com.griddynamics.pbazhko.plugin.ui.ChatCellRenderer
import com.intellij.openapi.actionSystem.ActionManager
import com.intellij.openapi.actionSystem.DefaultActionGroup
import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.progress.ProgressIndicator
import com.intellij.openapi.progress.Task
import com.intellij.openapi.project.Project
import com.intellij.openapi.wm.ToolWindow
import com.intellij.openapi.wm.ToolWindowFactory
import com.intellij.ui.content.ContentFactory
import com.intellij.openapi.ui.SimpleToolWindowPanel
import com.intellij.ui.components.JBList
import com.intellij.ui.components.JBScrollPane
import com.intellij.ui.components.JBTextArea
import com.intellij.util.io.HttpRequests
import java.awt.BorderLayout
import java.awt.event.KeyAdapter
import java.awt.event.KeyEvent
import javax.swing.DefaultListModel
import javax.swing.JPanel

class AIChatToolWindowFactory : ToolWindowFactory {

    override fun createToolWindowContent(project: Project, toolWindow: ToolWindow) {
        val chatPanel = AIChatPanel()
        AIChatPanelService.getInstance(project).setChatPanel(chatPanel)

        val contentFactory = ContentFactory.getInstance()
        val content = contentFactory.createContent(chatPanel, "", false)
        toolWindow.contentManager.addContent(content)
    }

    override fun shouldBeAvailable(project: Project) = true

    class AIChatPanel : SimpleToolWindowPanel(true, true) {

        private val listModel = DefaultListModel<String>()

        init {
            val actionGroup = DefaultActionGroup()
            actionGroup.add(OpenSettingsAction())

            val actionToolbar = ActionManager.getInstance().createActionToolbar(
                "AIChatToolbar",
                actionGroup,
                true // true = horizontal toolbar
            )
            actionToolbar.targetComponent = this

            toolbar = actionToolbar.component

            val messageList = JBList<String>()
            messageList.cellRenderer = ChatCellRenderer()
            messageList.model = listModel

            val scrollPane = JBScrollPane(messageList)

            val inputArea = JBTextArea()
            inputArea.rows = 3
            inputArea.emptyText.text = "Ask AI a question..."
            inputArea.addKeyListener(object : KeyAdapter() {
                override fun keyPressed(e: KeyEvent) {
                    if (e.keyCode == KeyEvent.VK_ENTER && !e.isShiftDown) {
                        e.consume() // Prevent adding a newline
                        val text = inputArea.text.trim()
                        if (text.isNotEmpty()) {
                            listModel.addElement("User: $text")
                            inputArea.text = ""
                            sendToAI(text)
                        }
                    }
                }
            })

            val inputScrollPane = JBScrollPane(inputArea)

            val mainContainer = JPanel(BorderLayout())
            mainContainer.add(scrollPane, BorderLayout.CENTER)
            mainContainer.add(inputScrollPane, BorderLayout.SOUTH)

            setContent(mainContainer)
        }

        private fun sendToAI(userMessage: String) {

            // We launch a background task. "Thinking..." will appear in the IDE status bar.
            object : Task.Backgroundable(null, "Thinking...", true) {

                override fun run(indicator: ProgressIndicator) {
                    // WE ARE NOW ON A BACKGROUND THREAD!
                    // Do NOT touch the messageList or inputArea here.

                    val url = AppSettingsState.instance.apiBaseUrl
                    val apiKey = SecureStorage.getApiKey()

                    if (apiKey.isNullOrEmpty()) {
                        updateUI("System: Please configure your API key in settings.")
                        return
                    }

                    try {
                        val payloadMap = mapOf(
                            "model" to AppSettingsState.instance.modelName,
                            "messages" to listOf(
                                mapOf("role" to "user", "content" to userMessage)
                            )
                        )
                        val payload = Gson().toJson(payloadMap)
                        val responseString = HttpRequests.post(url, "application/json")
                            .tuner { connection ->
                                connection.setRequestProperty("Authorization", "Bearer $apiKey")
                            }
                            .connect { request ->
                                request.write(payload)
                                request.readString()
                            }

                        // Parse the JSON response securely.
                        // Assuming an OpenAI-compatible response: {"choices": [{"message": {"content": "..."}}]}
                        val jsonObject = com.google.gson.JsonParser.parseString(responseString).asJsonObject
                        val content = jsonObject.getAsJsonArray("choices")
                            .get(0).asJsonObject
                            .getAsJsonObject("message")
                            .get("content").asString

                        val aiReply = "AI: $content"

                        updateUI(aiReply)
                    } catch (e: Exception) {
                        updateUI("System: Failed to connect. ${e.message}")
                    }
                }
            }.queue()
        }

        private fun updateUI(message: String) {
            ApplicationManager.getApplication().invokeLater {
                // BACK ON THE EDT! Safe to touch UI.
                listModel.addElement(message)
            }
        }

        fun setPromptAndSend(prompt: String) {
            listModel.addElement("User: [Sent Code Selection]")
            sendToAI(prompt)
        }
    }
}
