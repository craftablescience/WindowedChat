package io.github.craftablescience.windowedchat.swing;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ItemEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.ListSelectionModel;
import javax.swing.WindowConstants;

import org.lwjgl.opengl.Display;

import io.github.craftablescience.windowedchat.WindowedChat;
import net.minecraft.client.Minecraft;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.event.ClickEvent;


public class ChatWindow extends JFrame {

	private static final long serialVersionUID = -8877036744251716979L;
	private final DefaultListModel<JLabel> items;
    private final JPanel dmList;
    private final JPanel settingsPanel;
    private final JTextField textbox;
    private final Font font;

    public static HashMap<String,ChatDM> DM_HASHMAP = new HashMap<>();

    public static boolean SETTING_MCCHATHISTORY = false;
    public static boolean SETTING_AUTOSCROLL = true;


    public ChatWindow() {
        super(Display.getTitle() + " Chat Window");
        this.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        this.setAutoRequestFocus(false);

        this.font = new Font("Tahoma", Font.PLAIN, 20);

        JPanel messages = new JPanel();
        GridBagLayout layout = new GridBagLayout();
        messages.setLayout(layout);

        this.items = new DefaultListModel<>();

        JList<JLabel> list = new JList<>(this.items);
        list.setCellRenderer(new ChatCellRenderer());
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.setSelectedIndex(0);
        list.setLayoutOrientation(JList.VERTICAL);
        list.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && list.getSelectedIndex() != -1 && items.elementAt(list.getSelectedIndex()) instanceof ChatLink)
                handleComponentClick(((ChatLink) items.elementAt(list.getSelectedIndex())).getLink());
        });

        JScrollPane scrollPane = new JScrollPane(list);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.getVerticalScrollBar().addAdjustmentListener(e -> {
            if (ChatWindow.SETTING_AUTOSCROLL)
                e.getAdjustable().setValue(e.getAdjustable().getMaximum());
        });
        GridBagConstraints c = new GridBagConstraints();
        c.anchor = GridBagConstraints.PAGE_START;
        c.fill = GridBagConstraints.BOTH;
        c.gridy = 0;
        c.weightx = 1;
        c.weighty = 1;
        layout.setConstraints(scrollPane, c);
        messages.add(scrollPane);

        this.textbox = new JTextField();
        this.textbox.addActionListener(e -> {
            if (this.textbox.getText().length() > 0) {
                Minecraft.getMinecraft().player.sendChatMessage(this.textbox.getText());
                this.textbox.setText("");
            }
        });
        this.textbox.setHorizontalAlignment(JTextField.LEFT);
        this.textbox.setFont(this.font);
        this.textbox.setForeground(Color.BLUE);
        c = new GridBagConstraints();
        c.anchor = GridBagConstraints.PAGE_END;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridy = 1;
        layout.setConstraints(this.textbox, c);
        messages.add(this.textbox);

        this.dmList = new JPanel();
        this.dmList.setLayout(new GridBagLayout());
        JScrollPane dms = new JScrollPane(this.dmList);
        dms.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        dms.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        this.settingsPanel = new JPanel();
        this.settingsPanel.setLayout(new BoxLayout(this.settingsPanel, BoxLayout.Y_AXIS));

        JToggleButton mcTextEnabled = new JToggleButton("Show/Hide Chat in Main Window");
        mcTextEnabled.setSelected(SETTING_MCCHATHISTORY);
        mcTextEnabled.addItemListener(e -> SETTING_MCCHATHISTORY = (e.getStateChange() == ItemEvent.SELECTED));
        try {
            InputStream stream = getClass().getClassLoader().getResourceAsStream("assets/windowedchat/textures/mcchathistory.png");
            mcTextEnabled.setIcon(new ImageIcon(ImageIO.read(stream)));
            stream.close();
        } catch (IOException ignored) {}
        this.settingsPanel.add(mcTextEnabled);

        JToggleButton autoscroll = new JToggleButton("Autocroll Lists in This Window");
        autoscroll.setSelected(SETTING_AUTOSCROLL);
        autoscroll.addItemListener(e -> SETTING_AUTOSCROLL = (e.getStateChange() == ItemEvent.SELECTED));
        try {
            InputStream stream = getClass().getClassLoader().getResourceAsStream("assets/windowedchat/textures/autoscroll.png");
            autoscroll.setIcon(new ImageIcon(ImageIO.read(stream)));
            stream.close();
        } catch (IOException ignored) {}
        this.settingsPanel.add(autoscroll);

        JTabbedPane main = new JTabbedPane();
        main.addTab("Messages", messages);
        try {
            InputStream stream = getClass().getClassLoader().getResourceAsStream("assets/windowedchat/textures/messages.png");
            main.setIconAt(0, new ImageIcon(ImageIO.read(stream)));
            stream.close();
        } catch (IOException ignored) {}

        main.addTab("DMs", dms);
        try {
            InputStream stream = getClass().getClassLoader().getResourceAsStream("assets/windowedchat/textures/dm.png");
            main.setIconAt(1, new ImageIcon(ImageIO.read(stream)));
            stream.close();
        } catch (IOException ignored) {}

        main.addTab("Settings", this.settingsPanel);
        try {
            InputStream stream = getClass().getClassLoader().getResourceAsStream("assets/windowedchat/textures/settings.png");
            main.setIconAt(2, new ImageIcon(ImageIO.read(stream)));
            stream.close();
        } catch (IOException ignored) {}

        this.add(main, BorderLayout.CENTER);

        try {
            InputStream stream = getClass().getClassLoader().getResourceAsStream("assets/windowedchat/textures/icon.png");
            this.setIconImage(new ImageIcon(ImageIO.read(stream)).getImage());
            stream.close();
        } catch (IOException ignored) {}
        this.setSize(500, 700);
        this.setVisible(false);

        this.addInitMessage(false);
        list.setPrototypeCellValue(this.items.elementAt(0));
    }

    public void addInitMessage(boolean loggedIn) {
        this.items.removeAllElements();
        this.dmList.removeAll();
        DM_HASHMAP = new HashMap<>();
        if (loggedIn)
            this.items.addElement(new JLabel("Logged in as " + Minecraft.getMinecraft().getSession().getProfile().getName()));
        else
            this.items.addElement(new JLabel("Started Windowed Chat"));
        this.items.addElement(new JLabel("-----------------------------------"));
        //try {
        //    InputStream DejaVuSans = this.getClass().getResourceAsStream("fonts/DejaVuSans.ttf");
        //    this.font = Font.createFont(Font.TRUETYPE_FONT, DejaVuSans).deriveFont(Font.PLAIN, 20);
        //} catch (IOException | FontFormatException e) {
        //}
        this.items.elementAt(0).setFont(this.font);
        this.items.elementAt(1).setFont(this.font);
        try {
            InputStream stream = getClass().getClassLoader().getResourceAsStream("assets/windowedchat/textures/info.png");
            this.items.elementAt(0).setIcon(new ImageIcon(ImageIO.read(stream)));
            stream.close();
        } catch (IOException ignored) {}
    }

    public void addItem(String item, Icon icon) {
        JLabel label = new JLabel(item);
        label.setFont(this.font);
        label.setBackground(new Color(0xEE, 0xEE, 0xEE));
        if (icon != null)
            label.setIcon(icon);
        this.items.addElement(label);
    }

    public void addLinkItem(String item, Icon icon, ITextComponent link) {
        ChatLink label = new ChatLink(item, link);
        label.setFont(this.font);
        label.setBackground(new Color(0xAA, 0xAA, 0xFF));
        label.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        if (icon != null)
            label.setIcon(icon);
        label.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                handleComponentClick(link);
            }
            @Override
            public void mousePressed(MouseEvent e) {}
            @Override
            public void mouseReleased(MouseEvent e) {}
            @Override
            public void mouseEntered(MouseEvent e) {
                label.setBackground(new Color(0x00, 0x00, 0xAA));
            }
            @Override
            public void mouseExited(MouseEvent e) {
                label.setBackground(new Color(0xAA, 0xAA, 0xFF));
            }
        });
        this.items.addElement(label);
    }

    public void addDM(String name, String firstMessage, boolean selfInitiated) {
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 1.0;
        c.weighty = 0.0;
        c.anchor = GridBagConstraints.PAGE_START;
        c.gridy = this.dmList.getComponentCount();
        ChatDM chat = new ChatDM(name, firstMessage, selfInitiated);
        DM_HASHMAP.put(name, chat);
        this.dmList.add(chat, c);
    }

    public void activate(boolean loggedIn) {
        this.setVisible(true);
        WindowedChat.setChatEnabled();
        this.addInitMessage(loggedIn);
    }
    public void deactivate() {
        this.setVisible(false);
    }

    // From net.minecraft.client.gui.GuiScreen with LOTS of changes
    private boolean handleComponentClick(ITextComponent component) {
        ClickEvent clickevent = component.getStyle().getClickEvent();

        if (clickevent.getAction() == ClickEvent.Action.OPEN_URL) {
            if (!Minecraft.getMinecraft().gameSettings.chatLinks)
                return false;
            try {
                URI uri = new URI(clickevent.getValue());
                String s = uri.getScheme();
                if (s == null) {
                    throw new URISyntaxException(clickevent.getValue(), "Missing protocol");
                }
                try {
                    Class<?> oclass = Class.forName("java.awt.Desktop");
                    Object object = oclass.getMethod("getDesktop").invoke(null);
                    oclass.getMethod("browse", URI.class).invoke(object, uri);
                } catch (Throwable throwable1) {
                    Throwable throwable = throwable1.getCause();
                    WindowedChat.logger.error("Couldn't open link: {}", (throwable == null ? "<UNKNOWN>" : throwable.getMessage()));
                }
            } catch (URISyntaxException urisyntaxexception) {
                WindowedChat.logger.error("Can't open url for {}", clickevent, urisyntaxexception);
            }
        } else if (clickevent.getAction() == ClickEvent.Action.SUGGEST_COMMAND) {
            this.textbox.setText(clickevent.getValue());
        } else if (clickevent.getAction() == ClickEvent.Action.RUN_COMMAND) {
            Minecraft.getMinecraft().player.sendChatMessage(clickevent.getValue());
        } else {
            WindowedChat.logger.error("Don't know how to handle {}", clickevent);
        }
        return true;
    }
}