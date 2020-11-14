package io.github.craftablescience.windowedchat.swing;

import javax.swing.JLabel;

import net.minecraft.util.text.ITextComponent;


public class ChatLink extends JLabel {

	private static final long serialVersionUID = -2146486386137706249L;
	private ITextComponent link;


    public ChatLink(String name, ITextComponent link) {
        super(name);
        this.link = link;
    }

    public ITextComponent getLink() {
        return this.link;
    }
}
