package com.griddynamics.pbazhko.plugin.ui.components

import com.griddynamics.pbazhko.plugin.config.USER_INPUT_TITLE
import com.intellij.ui.components.JBLabel
import com.intellij.ui.components.JBScrollPane
import com.intellij.util.ui.JBUI
import java.awt.BorderLayout
import java.awt.event.KeyAdapter
import java.awt.event.KeyEvent
import java.util.function.Consumer
import javax.swing.JPanel
import javax.swing.JTextArea

class ChatInput(consumer: Consumer<String>) : JPanel(BorderLayout()) {

    init {
        val inputLabel = JBLabel(USER_INPUT_TITLE).apply {
            border = JBUI.Borders.empty(0, 12)
        }

        var inputArea = JTextArea().apply {
            rows = 3
            border = JBUI.Borders.empty(8, 12)
            addKeyListener(object : KeyAdapter() {
                override fun keyPressed(e: KeyEvent) {
                    if (e.keyCode == KeyEvent.VK_ENTER && !e.isShiftDown) {
                        e.consume()
                        val message = text.trim()
                        if (text.isNotEmpty()) {
                            consumer.accept(message)
                            text = ""
                        }
                    }
                }
            })
        }

        border = JBUI.Borders.empty(8, 0)

        add(inputLabel, BorderLayout.NORTH)
        add(JBScrollPane(inputArea).apply {
            border = JBUI.Borders.empty()
        }, BorderLayout.CENTER)
    }
}
