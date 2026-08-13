package com.griddynamics.pbazhko.plugin.state

import com.griddynamics.pbazhko.plugin.config.AI_DEFAULT_MODEL
import com.griddynamics.pbazhko.plugin.config.AI_DEFAULT_URL
import com.griddynamics.pbazhko.plugin.config.CONFIG_FILENAME
import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.components.PersistentStateComponent
import com.intellij.openapi.components.State
import com.intellij.openapi.components.Storage

@State(
    name = "com.griddynamics.pbazhko.plugin.state.AppSettingsState",
    storages = [Storage(CONFIG_FILENAME)]
)
class AppSettingsState : PersistentStateComponent<AppSettingsState> {

    var apiBaseUrl: String = AI_DEFAULT_URL
    var modelName: String = AI_DEFAULT_MODEL

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
