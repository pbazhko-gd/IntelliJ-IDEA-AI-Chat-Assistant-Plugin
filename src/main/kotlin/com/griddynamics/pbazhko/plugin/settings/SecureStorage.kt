package com.griddynamics.pbazhko.plugin.settings

import com.intellij.credentialStore.CredentialAttributes
import com.intellij.credentialStore.generateServiceName
import com.intellij.ide.passwordSafe.PasswordSafe

object SecureStorage {

    private val CREDENTIAL_ATTRIBUTES = CredentialAttributes(
        generateServiceName("MyAIChatPlugin", "ApiKey")
    )

    fun getApiKey(): String? {
        return PasswordSafe.instance.getPassword(CREDENTIAL_ATTRIBUTES)
    }

    fun setApiKey(apiKey: String) {
        PasswordSafe.instance.setPassword(CREDENTIAL_ATTRIBUTES, apiKey)
    }
}
