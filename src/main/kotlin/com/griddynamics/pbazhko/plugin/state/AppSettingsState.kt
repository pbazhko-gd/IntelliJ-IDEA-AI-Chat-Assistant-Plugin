package com.griddynamics.pbazhko.plugin.state

import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.components.PersistentStateComponent
import com.intellij.openapi.components.State
import com.intellij.openapi.components.Storage

@State(
    name = "com.griddynamics.pbazhko.plugin.state.AppSettingsState",
    storages = [Storage("AIChatSettings.xml")]
)
class AppSettingsState : PersistentStateComponent<AppSettingsState> {

    var apiBaseUrl: String = "https://api.openai.com/v1/chat/completions"
    var modelName: String = "gpt-5.4-mini"

    override fun getState(): AppSettingsState = this

    override fun loadState(state: AppSettingsState) {
        this.apiBaseUrl = state.apiBaseUrl
        this.modelName = state.modelName
    }

    companion object {
        val instance: AppSettingsState
            get() = ApplicationManager.getApplication().getService(AppSettingsState::class.java)
    }
}
