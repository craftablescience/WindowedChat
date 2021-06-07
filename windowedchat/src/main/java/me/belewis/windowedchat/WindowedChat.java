package me.belewis.windowedchat;

import me.belewis.windowedchat.events.WindowedChatEvents;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(WindowedChat.MODID)
public class WindowedChat {
    public static final String MODID = "windowedchat";
    public static final String NAME = "Windowed Chat";
    public static final String VERSION = "2.0";
    public static final Logger LOGGER = LogManager.getLogger();

    public WindowedChat() {
        MinecraftForge.EVENT_BUS.register(WindowedChatEvents.class);
    }
}
