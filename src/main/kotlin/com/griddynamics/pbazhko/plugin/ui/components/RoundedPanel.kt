package com.griddynamics.pbazhko.plugin.ui.components

import com.intellij.util.ui.JBUI
import java.awt.BorderLayout
import java.awt.Color
import java.awt.Graphics
import java.awt.Graphics2D
import java.awt.RenderingHints
import javax.swing.JPanel

class RoundedPanel(
    private val backgroundColor: Color,
    private val radius: Int
) : JPanel() {

    init {
        isOpaque = false
        layout = BorderLayout()
        border = JBUI.Borders.empty(8, 12)
    }

    override fun paintComponent(g: Graphics) {
        val g2 = g.create() as Graphics2D
        g2.setRenderingHint(
            RenderingHints.KEY_ANTIALIASING,
            RenderingHints.VALUE_ANTIALIAS_ON
        )

        g2.color = backgroundColor
        g2.fillRoundRect(
            0,
            0,
            width - 1,
            height - 1,
            radius,
            radius
        )

        g2.dispose()
        super.paintComponent(g)
    }
}
