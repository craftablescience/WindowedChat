package io.github.craftablescience.windowedchat.swing;

import javax.swing.*;
import java.awt.*;


public class ChatDMCellRenderer extends JLabel implements ListCellRenderer<JLabel> {
    @Override
    public Component getListCellRendererComponent(JList<? extends JLabel> list, JLabel value, int index, boolean isSelected, boolean cellHasFocus) {
        this.setText(value.getText());
        this.setFont(value.getFont());
        this.setIcon(value.getIcon());
        this.setOpaque(true);
        this.setBackground(value.getBackground());
        this.setForeground(value.getForeground());
        return this;
    }
}
