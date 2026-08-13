package com.griddynamics.pbazhko.plugin.ui.components

import com.griddynamics.pbazhko.plugin.config.USER_MESSAGE_PLACEHOLDER
import com.intellij.ui.components.JBTextArea
import com.intellij.util.ui.JBUI
import java.awt.event.KeyAdapter
import java.awt.event.KeyEvent
import java.util.function.Consumer

class ChatInputArea(consumer: Consumer<String>) : JBTextArea() {

    init {
        text = USER_MESSAGE_PLACEHOLDER
        rows = 3
        border = JBUI.Borders.empty(8, 12)
        addKeyListener(object : KeyAdapter() {
            override fun keyPressed(e: KeyEvent) {
                if (e.keyCode == KeyEvent.VK_ENTER && !e.isShiftDown) {
                    e.consume()
                    val message = text.trim()
                    if (text.isNotEmpty()) {
                        consumer.accept(message)
                        text = USER_MESSAGE_PLACEHOLDER
                    }
                }
            }
        })
    }
}
