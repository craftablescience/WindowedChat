package me.belewis.windowedchat;

import me.belewis.windowedchat.events.WindowedChatEvents;
import me.belewis.windowedchat.helpers.UnzipFile;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.attribute.FileAttribute;
import java.util.Objects;
import java.util.zip.ZipInputStream;

@Mod(WindowedChat.MODID)
public class WindowedChat {
    public static final String MODID = "windowedchat";
    public static final String NAME = "Windowed Chat";
    public static final String VERSION = "2.0.0";
    public static final Logger LOGGER = LogManager.getLogger();

    public WindowedChat() {
        MinecraftForge.EVENT_BUS.register(WindowedChatEvents.class);
        log("Scanning for client...");

        if (Files.exists(Paths.get("windowedchat/version.txt"))) {
            log("Client found, setup complete.");
        } else {
            log("Client not found. Extracting client to external directory...");
            InputStream is = this.getClass().getResourceAsStream("/windowedchatclient.zip");
            if (is == null) {
                logError("Could not find client zip in mod resources.");
            }
            try {
                UnzipFile.unzip(new ZipInputStream(Objects.requireNonNull(is)), Paths.get("windowedchat"));
                Files.createFile(Paths.get("windowedchat/version.txt"));
                Files.write(Paths.get("windowedchat/version.txt"), VERSION.getBytes(StandardCharsets.UTF_8));
            } catch (IOException e) {
                logError(e.getMessage());
            }
            log("Finished extracting client, setup complete.");
        }
    }

    public static void log(String msg) {
        LOGGER.info("[" + NAME + "] " + msg);
    }

    public static void logError(String msg) {
        log("Error: " + msg + "\nTell a developer at https://discord.com/invite/ASgHFkX");
    }
}
