package io.github.craftablescience.windowedchat.swing;

import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;


public class ChatCellRenderer extends JLabel implements ListCellRenderer<JLabel> {

	private static final long serialVersionUID = 301106395433128089L;

	@Override
    public Component getListCellRendererComponent(JList<? extends JLabel> list, JLabel value, int index, boolean isSelected, boolean cellHasFocus) {
        this.setText(value.getText());
        this.setFont(value.getFont());
        this.setIcon(value.getIcon());
        this.setOpaque(true);
        this.setBackground(value.getBackground());
        return this;
    }
}
