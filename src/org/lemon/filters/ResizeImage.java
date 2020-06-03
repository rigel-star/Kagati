package org.lemon.filters;

import java.awt.image.BufferedImage;

public class ResizeImage {

	private BufferedImage img;
	private int h, w;
	
	
	public ResizeImage(BufferedImage img, int w, int h) {
		this.img = img;
		this.w = w;
		this.h = h;
	}
	
	public BufferedImage getScaledImage() {
		
		var iimg = new BufferedImage(w, h, img.getType());
		
		var g = iimg.createGraphics();
		g.drawImage(img, 0, 0, w, h, null);
		g.dispose();
		
		return iimg;
	}
}
