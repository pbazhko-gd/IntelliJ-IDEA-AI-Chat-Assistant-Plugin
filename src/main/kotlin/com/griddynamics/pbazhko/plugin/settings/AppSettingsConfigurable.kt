package com.griddynamics.pbazhko.plugin.settings

import com.griddynamics.pbazhko.plugin.state.AppSettingsState
import com.intellij.openapi.options.Configurable
import com.intellij.ui.components.JBPasswordField
import com.intellij.ui.components.JBTextField
import com.intellij.util.ui.FormBuilder
import javax.swing.JComponent
import javax.swing.JPanel

class AppSettingsConfigurable : Configurable {

    private var myMainPanel: JPanel? = null

    private val urlField = JBTextField()
    private val modelField = JBTextField()
    private val apiKeyField = JBPasswordField()

    override fun getDisplayName(): String = "AI Chat Settings"

    override fun createComponent(): JComponent? {
        myMainPanel = FormBuilder.createFormBuilder()
            .addLabeledComponent("API Base URL", urlField, 1, true)
            .addLabeledComponent("Model Name", modelField, 1, true)
            .addLabeledComponent("API Key", apiKeyField, 1, true)
            .addComponentFillVertically(JPanel(), 0)
            .panel

        return myMainPanel
    }

    override fun isModified(): Boolean {
        val state = AppSettingsState.instance
        return urlField.text != state.apiBaseUrl ||
                modelField.text != state.modelName ||
                String(apiKeyField.password) != (SecureStorage.getApiKey() ?: "")
    }

    override fun apply() {
        val state = AppSettingsState.instance
        state.apiBaseUrl = urlField.text
        state.modelName = modelField.text
        SecureStorage.setApiKey(String(apiKeyField.password))
    }

    override fun reset() {
        val state = AppSettingsState.instance
        urlField.text = state.apiBaseUrl
        modelField.text = state.modelName
        apiKeyField.text = SecureStorage.getApiKey() ?: ""
    }

    override fun disposeUIResources() {
        myMainPanel = null
    }
}
