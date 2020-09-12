package org.lemon.filter.basic;

import java.awt.image.BufferedImage;

public class GrayScale {

	BufferedImage img;
	
	
	public GrayScale(BufferedImage img) {
		this.img = img;
		int height = img.getHeight();
		int width = img.getWidth();
		
		for(int y=0; y<height; y++) {
			for(int x=0; x<width; x++) {
				
				int color = img.getRGB(x, y);
				
				int alpha = (color >> 24) & 255;
				int red = (color >> 16) & 255;
				int green = (color >> 8) & 255;
				int blue = (color) & 255;
				
				final int lum = (int)(0.2126 * red + 0.7152 * green + 0.0722 * blue);
				 
	            alpha = (alpha << 24);
	            red = (lum << 16);
	            green = (lum << 8);
	            blue = lum;
	 
	            color = alpha + red + green + blue;
				
				img.setRGB(x, y, color);
				
			}
		}
		
	}
	
	public BufferedImage getGrayScaledImg() {
		return img;
	}
	
}
