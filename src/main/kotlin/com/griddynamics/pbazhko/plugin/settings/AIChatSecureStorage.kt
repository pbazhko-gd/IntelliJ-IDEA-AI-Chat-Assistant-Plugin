package com.griddynamics.pbazhko.plugin.settings

import com.griddynamics.pbazhko.plugin.config.AI_SECURE_API_KEY
import com.griddynamics.pbazhko.plugin.config.AI_SECURE_SUBSYSTEM
import com.intellij.credentialStore.CredentialAttributes
import com.intellij.credentialStore.generateServiceName
import com.intellij.ide.passwordSafe.PasswordSafe

object AIChatSecureStorage {

    private val CREDENTIAL_ATTRIBUTES = CredentialAttributes(
        generateServiceName(AI_SECURE_SUBSYSTEM, AI_SECURE_API_KEY)
    )

    fun getApiKey(): String? {
        return PasswordSafe.instance.getPassword(CREDENTIAL_ATTRIBUTES)
    }

    fun setApiKey(apiKey: String) {
        PasswordSafe.instance.setPassword(CREDENTIAL_ATTRIBUTES, apiKey)
    }
}
