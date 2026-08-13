package com.griddynamics.pbazhko.plugin.ui

import com.griddynamics.pbazhko.plugin.config.USER_MESSAGE_PREFIX
import com.griddynamics.pbazhko.plugin.ui.components.RoundedPanel
import com.intellij.ui.JBColor
import com.intellij.util.ui.JBUI
import java.awt.BorderLayout
import java.awt.Component
import javax.swing.JList
import javax.swing.JPanel
import javax.swing.JTextArea
import javax.swing.ListCellRenderer

class AIChatCellRenderer : ListCellRenderer<String> {

    override fun getListCellRendererComponent(
        list: JList<out String?>?,
        value: String?,
        index: Int,
        isSelected: Boolean,
        cellHasFocus: Boolean
    ): Component {
        val message = value ?: ""

        val panel = JPanel(BorderLayout())
        panel.border = JBUI.Borders.empty(4, 8)

        val messageContainer = RoundedPanel(
            backgroundColor = if (message.isUserMessage()) {
                JBColor(0xE0F7FA, 0x2b3d42)
            } else {
                JBColor(0xF1F8E9, 0x33402e)
            },
            radius = 20
        )

        val messageTextArea = JTextArea(message).apply {
            lineWrap = true
            wrapStyleWord = true
            isOpaque = false
            isEditable = false
            border = null
            background = null

            val listWidth = if (list != null && list.width > 0) {
                list.width
            } else {
                400
            }
            setSize(listWidth - 40, Int.MAX_VALUE)
        }

        messageContainer.add(messageTextArea, BorderLayout.CENTER)

        if (message.isUserMessage()) {
            panel.add(messageContainer, BorderLayout.EAST)
        } else {
            panel.add(messageContainer, BorderLayout.WEST)
        }

        panel.isOpaque = false

        return panel
    }
}

private fun String.isUserMessage() = this.startsWith(USER_MESSAGE_PREFIX)
