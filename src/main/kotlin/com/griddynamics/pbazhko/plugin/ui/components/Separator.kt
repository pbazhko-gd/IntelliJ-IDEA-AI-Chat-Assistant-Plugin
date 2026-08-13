package com.griddynamics.pbazhko.plugin.ui.components

import com.intellij.ui.JBColor
import java.awt.Dimension
import javax.swing.JPanel

class Separator(height: Int) : JPanel() {
    init {
        background = JBColor.border()
        preferredSize = Dimension(0, height)
    }
}