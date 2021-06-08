package me.belewis.windowedchat.events;

import me.belewis.windowedchat.WindowedChat;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.io.IOException;
import java.nio.file.Paths;

public class WindowedChatEvents {
    public static boolean loadedApplication = false;

    @SubscribeEvent
    @OnlyIn(Dist.CLIENT)
    public static void onPlayerLogin(WorldEvent.Load event) {
        // todo: connect to the chat client, register "password" as argument
        try {
            if (event.getWorld().isRemote() && !loadedApplication) {
                Runtime.getRuntime().exec(Paths.get("windowedchat/windowedchatclient.exe").toAbsolutePath().toString());
                loadedApplication = true;
            }
        } catch (IOException e) {
            WindowedChat.logError(e.getMessage());
        }
    }

    @SubscribeEvent
    @OnlyIn(Dist.CLIENT)
    public static void onPlayerLogout(WorldEvent.Unload event) {
        // todo: trigger chat client to kill itself
    }
}
