package io.github.craftablescience.windowedchat;

import io.github.craftablescience.windowedchat.event.WindowedChatEvents;
import io.github.craftablescience.windowedchat.swing.ChatWindow;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiOptions;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.Logger;

import javax.swing.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Mod(modid   = io.github.craftablescience.windowedchat.WindowedChat.MODID,
     name    = io.github.craftablescience.windowedchat.WindowedChat.NAME,
     version = io.github.craftablescience.windowedchat.WindowedChat.VERSION)
public class WindowedChat {
    public static final String MODID = "windowedchat";
    public static final String NAME = "Windowed Chat";
    public static final String VERSION = "1.0";

    public static ChatWindow chatWindow;
    public static Logger logger;


    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        logger = event.getModLog();
        MinecraftForge.EVENT_BUS.register(new WindowedChatEvents());
    }

    @EventHandler
    public void init(FMLInitializationEvent event) {
        chatWindow = new ChatWindow();
    }

    public static void setChatEnabled() {
        Minecraft.getMinecraft().gameSettings.chatVisibility = EntityPlayer.EnumChatVisibility.FULL;
        Minecraft.getMinecraft().gameSettings.saveOptions();
    }
}
