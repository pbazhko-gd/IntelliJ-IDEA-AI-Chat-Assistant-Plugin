package com.griddynamics.pbazhko.plugin.ui

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

        val textArea = JTextArea(message).apply {
            lineWrap = true
            wrapStyleWord = true
            isOpaque = true
            isEditable = false
            border = JBUI.Borders.empty(8)
            val listWidth = if (list != null && list.width > 0) list.width else 400
            setSize(listWidth - 40, Int.MAX_VALUE)
        }

        if (message.startsWith("User:")) {
            textArea.background = JBColor(0xE0F7FA, 0x2b3d42) // Light Theme / Dark Theme Colors
            panel.add(textArea, BorderLayout.EAST) // Right-align user
        } else {
            textArea.background = JBColor(0xF1F8E9, 0x33402e)
            panel.add(textArea, BorderLayout.WEST) // Left-align AI
        }

        panel.isOpaque = false

        return panel
    }
}
