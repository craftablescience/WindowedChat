package io.github.craftablescience.windowedchat.config;

import io.github.craftablescience.windowedchat.WindowedChat;
import net.minecraftforge.common.config.Config;


@Config(modid = WindowedChat.MODID)
public class WindowedChatConfig {
	
	@Config.Name("url")
	@Config.Comment("Defines the URL used to obtain a players Skin for rendering their Head in the Chat Window\nAcceptable Options:\n"
			+ "\t3D:   https://crafatar.com/renders/head/%uuid%?overlay\n"
			+ "\t2D:   https://crafatar.com/avatars/%uuid%?overlay\n"
			+ "\tNone: steve")
	@Config.RequiresWorldRestart
	public static String avatarURL = "https://crafatar.com/renders/head/%uuid%?overlay?size=32";
}
