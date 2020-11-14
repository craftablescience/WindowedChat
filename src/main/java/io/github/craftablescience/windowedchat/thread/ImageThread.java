package io.github.craftablescience.windowedchat.thread;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;

import io.github.craftablescience.windowedchat.helpers.ImageAssets;


public class ImageThread extends Thread {
	
	private String file;
	private URL url;


	public ImageThread(String file, URL url) {
		this.file = file;
		this.url = url;
		setName("Image Thread");
		setDaemon(true);
		start();
	}
	
	@Override
	public void run () {
		save();
	}
	
	private void save() {
		try {
		BufferedImage image = resize(url, new Dimension(32, 32));
		ImageIO.write(image, "png", new File(ImageAssets.imgPath + file + ".png"));
		} catch (Exception e) {}
	}
	
	private BufferedImage resize(final URL url, final Dimension size) throws IOException{
	    final BufferedImage image = ImageIO.read(url);
	    final BufferedImage resized = new BufferedImage(size.width, size.height, BufferedImage.TYPE_INT_ARGB);
	    final Graphics2D g = resized.createGraphics();
	    g.drawImage(image, 0, 0, size.width, size.height, null);
	    g.dispose();
	    return resized;
	}
}
