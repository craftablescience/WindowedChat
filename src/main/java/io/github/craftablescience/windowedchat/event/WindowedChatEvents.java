package io.github.craftablescience.windowedchat.event;

import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class WindowedChatEvents {

    @SubscribeEvent
    public void onPlayerLogin(WorldEvent.Load event) {
        // todo: connect to the chat client
    }

    @SubscribeEvent
    public void onPlayerLogout(WorldEvent.Unload event) {
        // todo: disconnect from the chat client
    }
}
