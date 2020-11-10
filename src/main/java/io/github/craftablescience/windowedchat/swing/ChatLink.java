package io.github.craftablescience.windowedchat.swing;

import net.minecraft.util.text.ITextComponent;

import javax.swing.*;


public class ChatLink extends JLabel {

    private ITextComponent link;


    public ChatLink(String name, ITextComponent link) {
        super(name);
        this.link = link;
    }

    public ITextComponent getLink() {
        return this.link;
    }
}
