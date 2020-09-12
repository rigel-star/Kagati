package org.lemon.filter.basic;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;

public class ResizeImage {

	private BufferedImage img;
	private boolean done = false;
	
	
	public ResizeImage(BufferedImage img) {
		this.img = img;
	}
	
	
	public BufferedImage getImageSizeOf(int w, int h) {
		var iimg = new BufferedImage(w, h, img.getType());
		
		var g = iimg.createGraphics();
		done = g.drawImage(img, 0, 0, w, h, new ImageObserver() {
			
			@Override
			public boolean imageUpdate(Image img, int infoflags, int x, int y, int width, int height) {
				return false;
			}
		});
		
		if(done)
			g.dispose();
		
		return iimg;
	}
	
	
	/**
	 * @return {@code true} if image is successfully resized else {@code false}.
	 * */
	public boolean isDone() {
		return done;
	}
	
	
}
