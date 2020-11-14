package io.github.craftablescience.windowedchat.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiIngameMenu;
import net.minecraft.client.gui.GuiOptions;


public class GuiOptionsNoChat extends GuiOptions {

    public GuiOptionsNoChat() {
        super(new GuiIngameMenu(), Minecraft.getMinecraft().gameSettings);
    }

    @Override
    public void initGui() {
        super.initGui();
        for (GuiButton button : this.buttonList)
            if (button.id == 103)
                button.enabled = false;
    }
}
