package me.belewis.windowedchat;

import me.belewis.windowedchat.events.WindowedChatEvents;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Mod(WindowedChat.MODID)
public class WindowedChat {
    public static final String MODID = "windowedchat";
    public static final String NAME = "Windowed Chat";
    public static final String VERSION = "2.0";
    public static final Logger LOGGER = LogManager.getLogger();

    public WindowedChat() {
        MinecraftForge.EVENT_BUS.register(WindowedChatEvents.class);
        log("Copying client to external directory...");

        Path dir = Paths.get("/windowedchat");
        if (!Files.exists(dir)) {
            try {
                Files.createDirectory(dir);
            } catch (IOException e) {
                logError(e.getMessage());
            }
        }
    }

    public static void log(String msg) {
        LOGGER.info("[" + NAME + "] " + msg);
    }

    public static void logError(String msg) {
        log("[" + NAME + "] Error: " + msg + "\n\nTell a developer at https://discord.com/invite/ASgHFkX");
    }
}
