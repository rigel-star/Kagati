package org.lemon.filters;

import java.awt.Color;
import java.awt.image.BufferedImage;

public class GrayScale {

	BufferedImage img;
	
	public GrayScale() {
		
	}
	
	public GrayScale(BufferedImage img) {
		this.img = img;
		int height = img.getHeight();
		int width = img.getWidth();
		
		for(int y=0;y<height;y++) {
			for(int x=0;x<width;x++) {
				
				Color color = new Color(img.getRGB(x, y));
				
				int R = (int) (color.getRed() * 0.3);
				int G = (int) (color.getGreen() * 0.5);
				int B = (int) (color.getBlue() * 0.1);
				
				Color newColor = new Color(R+G+B, R+G+B, R+G+B);
				
				img.setRGB(x, y, newColor.getRGB());
				
			}
		}
		
	}
	
	public BufferedImage getGrayScaledImg() {
		return img;
	}
	
}
