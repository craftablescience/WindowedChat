package io.github.craftablescience.windowedchat.helpers;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.imageio.stream.FileImageInputStream;
import javax.swing.ImageIcon;

import io.github.craftablescience.windowedchat.WindowedChat;
import io.github.craftablescience.windowedchat.thread.ImageThread;


public class ImageAssets {

	public static Map<String, ImageIcon> textures = new HashMap<>();
	public static String imgPath = WindowedChat.imgPath;


	public static void addTexture(String name, String path) {
		name = name.toLowerCase();
		InputStream stream = ImageAssets.class.getClassLoader().getResourceAsStream(path);
		try {
			textures.put(name, new ImageIcon(ImageIO.read(stream)));
			stream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}	
	
	public static ImageIcon getTexture(String name) {
		name = name.toLowerCase();
		if (!textures.containsKey(name))
			return null;
		return textures.get(name);
	}
	
	public static boolean containsImg(String name) {
		name = name.toLowerCase();
		return textures.containsKey(name);
	}
	
	public static void addTextureFromURL(String uuid, URL path) {
		uuid = uuid.toLowerCase();
		try {
			new ImageThread(uuid, path);
		} catch (Exception e1) {
			WindowedChat.logger.error("ImageThread failed while saving Image from URL!");
		}
	}
	
	public static ImageIcon getTextureAsIcon(String uuid) {
		File file = new File(imgPath + uuid + ".png");
		try {
			return new ImageIcon(ImageIO.read(new FileImageInputStream(file)));
		} catch (IOException e) {
			WindowedChat.logger.error("Failed to obtain ImageIcon from Image File!");
			return null;
		}
	}
	
	public static boolean isCached(String uuid) {
		File file = new File(imgPath + uuid + ".png");
		return file.exists();
	}
}
