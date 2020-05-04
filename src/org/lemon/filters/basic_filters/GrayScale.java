package org.lemon.filters.basic_filters;

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
				
				//equation is R*0.29 + B*0.58 + B*0.11
				int R = (int) (color.getRed() * 0.29);
				int G = (int) (color.getGreen() * 0.58);
				int B = (int) (color.getBlue() * 0.11);
				
				Color newColor = new Color(R+G+B, R+G+B, R+G+B);
				
				img.setRGB(x, y, newColor.getRGB());
				
			}
		}
		
	}
	
	public BufferedImage getGrayScaledImg() {
		return img;
	}
	
}
