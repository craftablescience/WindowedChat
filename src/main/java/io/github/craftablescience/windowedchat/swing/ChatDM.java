package io.github.craftablescience.windowedchat.swing;

import net.minecraft.client.Minecraft;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.io.IOException;
import java.io.InputStream;


public class ChatDM extends JPanel {

    public final String player;
    private final DefaultListModel<JLabel> items;
    private final Font font;


    public ChatDM(String player, String firstMessage, boolean selfInitiated) {
        this.setBackground(new Color(0xEE, 0xEE, 0xEE));
        this.setBorder(new CompoundBorder(new CompoundBorder(
                new EmptyBorder(4, 4, 4, 4),
                BorderFactory.createLineBorder(new Color(0x55, 0x55, 0x55), 4)),
                new EmptyBorder(4, 4, 4, 4)));
        GridBagLayout layout = new GridBagLayout();
        this.setLayout(layout);
        this.setMaximumSize(new Dimension(Integer.MAX_VALUE, 300));

        this.player = player;
        this.font = new Font("Tahoma", Font.PLAIN, 20);
        Font fontBold = new Font("Tahoma", Font.BOLD, 20);

        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0D;
        c.weighty = 0D;
        c.gridx = 0;
        c.gridy = 0;

        JLabel title = new JLabel(player);
        try {
            InputStream stream = getClass().getClassLoader().getResourceAsStream("assets/windowedchat/textures/steve.png");
            title.setIcon(new ImageIcon(ImageIO.read(stream)));
            stream.close();
        } catch (IOException ignored) {}
        title.setFont(fontBold);
        layout.setConstraints(title, c);
        this.add(title);

        this.items = new DefaultListModel<>();
        JList<JLabel> list = new JList<>(items);
        list.setCellRenderer(new ChatDMCellRenderer());
        c.fill = GridBagConstraints.BOTH;
        c.weightx = 1D;
        c.weighty = 1D;
        c.gridx = 0;
        c.gridy = 1;
        JScrollPane p = new JScrollPane(list);
        p.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        p.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        p.getVerticalScrollBar().addAdjustmentListener(e -> {
            if (ChatWindow.SETTING_AUTOSCROLL)
                e.getAdjustable().setValue(e.getAdjustable().getMaximum());
        });
        layout.setConstraints(p, c);
        this.add(p);

        if (selfInitiated)
            this.addItemSelf(firstMessage);
        else
            this.addItemOther(firstMessage);

        JTextField textbox = new JTextField();
        textbox.addActionListener(e -> {
            if (textbox.getText().length() > 0) {
                Minecraft.getMinecraft().player.sendChatMessage("/msg " + player + " " + textbox.getText());
                textbox.setText("");
            }
        });
        textbox.setHorizontalAlignment(JTextField.LEFT);
        textbox.setFont(font);
        textbox.setForeground(Color.BLUE);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 1D;
        c.weighty = 1D;
        c.gridx = 0;
        c.gridy = 2;
        layout.setConstraints(textbox, c);
        this.add(textbox);
    }

    public void addItemSelf(String message) {
        JLabel label = new JLabel(message, SwingConstants.RIGHT);
        label.setFont(this.font);
        label.setForeground(new Color(0x00, 0x00, 0xFF));

        this.items.addElement(label);
    }

    public void addItemOther(String message) {
        JLabel label = new JLabel(message, SwingConstants.LEFT);
        label.setFont(this.font);
        this.items.addElement(label);
    }
}
