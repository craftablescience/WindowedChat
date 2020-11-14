package io.github.craftablescience.windowedchat.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.ChatOptionsScreen;
import net.minecraft.client.gui.screen.SettingsScreen;
import net.minecraft.client.gui.widget.button.AbstractButton;


public class GuiOptionsNoChat extends ChatOptionsScreen {

    public GuiOptionsNoChat() {
        super(, Minecraft.getInstance().gameSettings);
    }

    @Override
    public void init() {
        super.init();
        for (AbstractButton button : this.buttonList)
            if (button.x == 103)
                button.enabled = false;
    }
}
