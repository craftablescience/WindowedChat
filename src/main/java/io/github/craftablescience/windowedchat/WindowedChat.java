package io.github.craftablescience.windowedchat;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.logging.log4j.Logger;

import io.github.craftablescience.windowedchat.event.WindowedChatEvents;
import io.github.craftablescience.windowedchat.helpers.ImageAssets;
import io.github.craftablescience.windowedchat.swing.ChatWindow;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;


@Mod(modid = WindowedChat.MODID, name = WindowedChat.NAME, version = WindowedChat.VERSION)
public class WindowedChat {
	public static final String MODID = "windowedchat";
	public static final String NAME = "Windowed Chat";
	public static final String VERSION = "1.1";

	private static Path imagesPath = Paths.get(Minecraft.getMinecraft().mcDataDir + "/cachedImages");
	public static String imgPath = imagesPath.toFile() + File.separator;
	public static ChatWindow chatWindow;
	public static Logger logger;


	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		logger = event.getModLog();

		try {
			if(!Files.exists(imagesPath)) {
				Files.createDirectory(imagesPath);
			}
		} catch (IOException e) {
			WindowedChat.logger.error("Failed to create ImageCache Directory");
		}
		
		MinecraftForge.EVENT_BUS.register(new WindowedChatEvents());
	}

	@EventHandler
	public void init(FMLInitializationEvent event) {
		chatWindow = new ChatWindow();
		
		ImageAssets.addTexture("steve", "assets/windowedchat/textures/steve.png");
		ImageAssets.addTexture("gameinfo", "assets/windowedchat/textures/info.png");
		ImageAssets.addTexture("system", "assets/windowedchat/textures/system.png");
	}

	public static void setChatEnabled() {
		Minecraft.getMinecraft().gameSettings.chatVisibility = EntityPlayer.EnumChatVisibility.FULL;
		Minecraft.getMinecraft().gameSettings.saveOptions();
	}

	@SubscribeEvent
	public void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent event) {
		if (event.getModID().equals(WindowedChat.MODID)) {
			ConfigManager.sync(WindowedChat.MODID, Config.Type.INSTANCE);
		}
	}
}
