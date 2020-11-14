package io.github.craftablescience.windowedchat;

import io.github.craftablescience.windowedchat.event.WindowedChatEvents;
import io.github.craftablescience.windowedchat.swing.ChatWindow;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.ChatVisibility;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


@Mod("windowedchat")
public class WindowedChat {
    public static final String MODID = "windowedchat";
    public static final String NAME = "Windowed Chat";
    public static final String VERSION = "1.0";

    public static ChatWindow chatWindow;
    public static final Logger logger = LogManager.getLogger();


    public WindowedChat() {
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        MinecraftForge.EVENT_BUS.register(WindowedChatEvents.class);
    }

    public void setup(final FMLClientSetupEvent event) {
        chatWindow = new ChatWindow();
    }

    public static void setChatEnabled() {
        Minecraft.getInstance().gameSettings.chatVisibility = ChatVisibility.FULL;
        Minecraft.getInstance().gameSettings.saveOptions();
    }
}
