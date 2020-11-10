package io.github.craftablescience.windowedchat.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.*;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.world.EnumDifficulty;


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
