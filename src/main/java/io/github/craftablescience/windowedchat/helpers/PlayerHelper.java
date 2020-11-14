package io.github.craftablescience.windowedchat.helpers;

import java.net.URL;

import javax.swing.ImageIcon;

import com.mojang.authlib.GameProfile;

import io.github.craftablescience.windowedchat.WDConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;

public class PlayerHelper {

	public static String getPlayerHead(EntityPlayer player) {
		GameProfile gameProfile = player.getGameProfile();
		return WDConfig.avatarURL.replace("%uuid%", gameProfile.getId().toString().replace("-", ""));
	}
	
    private static EntityPlayer player(String name) {
    	for (EntityPlayer player : Minecraft.getMinecraft().world.playerEntities) {
    		if (player.getName().equals(name)) {
    			return player;
    		}
    	}
		return null;
    }
	
	public static ImageIcon loadImage(String playerName) {
		ImageIcon steve = ImageAssets.getTexture("steve");
		String uuid = player(playerName).getGameProfile().getId().toString().replace("-", "");
		if(WDConfig.avatarURL.equalsIgnoreCase("steve")) {
			return steve;
		} else {
			if(ImageAssets.isCached(uuid)) {
				return ImageAssets.getTextureAsIcon(uuid);
			} else {
				try {
					ImageAssets.addTextureFromURL(uuid, new URL(WDConfig.avatarURL.replace("%uuid%", uuid)));
					return ImageAssets.getTextureAsIcon(uuid);
				} catch (Exception e) {
					return steve;
				}
			}
		}
	}
}
