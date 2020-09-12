package org.lemon.filter.basic;

import java.awt.image.BufferedImage;

public class BlurImage {

	private BufferedImage src;
	private int iterate;
	
	public BlurImage(BufferedImage src, int iterate) {
		this.src = src;
		this.iterate = iterate;
		blur();
	}
	
	private void blur() {
		
		int width = this.src.getWidth();
		int height = this.src.getHeight();
		
		for(int i=0;i<this.iterate;i++) {
			for (int x = 1; x < width-1; x++)
		        for (int y = 1; y < height-1; y++) {
	
		            int redAmount = 0;
		            int greenAmount = 0;
		            int blueAmount = 0;
	
		            int pixel1 = this.src.getRGB(x - 1, y - 1);
		            redAmount += (pixel1 >> 16) & 0xff;
		            greenAmount += (pixel1 >> 8) & 0xff;
		            blueAmount += (pixel1 >> 0) & 0xff;
	
		            int pixel2 = this.src.getRGB(x, y - 1);
		            redAmount += (pixel2 >> 16) & 0xff;
		            greenAmount += (pixel2 >> 8) & 0xff;
		            blueAmount += (pixel2 >> 0) & 0xff;
	
		            int pixel3 = this.src.getRGB(x + 1, y - 1);
		            redAmount += (pixel3 >> 16) & 0xff;
		            greenAmount += (pixel3 >> 8) & 0xff;
		            blueAmount += (pixel3 >> 0) & 0xff;
	
		            int pixel4 = this.src.getRGB(x - 1, y);
		            redAmount += (pixel4 >> 16) & 0xff;
		            greenAmount += (pixel4 >> 8) & 0xff;
		            blueAmount += (pixel4 >> 0) & 0xff;
	
		            int pixel5 = this.src.getRGB(x, y);
		            redAmount += (pixel5 >> 16) & 0xff;
		            greenAmount += (pixel5 >> 8) & 0xff;
		            blueAmount += (pixel5 >> 0) & 0xff;
	
		            int pixel6 = this.src.getRGB(x + 1, y);
		            redAmount += (pixel6 >> 16) & 0xff;
		            greenAmount += (pixel6 >> 8) & 0xff;
		            blueAmount += (pixel6 >> 0) & 0xff;
	
		            int pixel7 = this.src.getRGB(x - 1, y + 1);
		            redAmount += (pixel7 >> 16) & 0xff;
		            greenAmount += (pixel7 >> 8) & 0xff;
		            blueAmount += (pixel7 >> 0) & 0xff;
	
		            int pixel8 = this.src.getRGB(x, y + 1);
		            redAmount += (pixel8 >> 16) & 0xff;
		            greenAmount += (pixel8 >> 8) & 0xff;
		            blueAmount += (pixel8 >> 0) & 0xff;
	
		            int pixel9 = this.src.getRGB(x + 1, y + 1);
		            redAmount += (pixel9 >> 16) & 0xff;
		            greenAmount += (pixel9 >> 8) & 0xff;
		            blueAmount += (pixel9 >> 0) & 0xff;
	
		            redAmount /= 9;
		            greenAmount /= 9;
		            blueAmount /= 9;
	
		            int newPixel = (redAmount << 16) | (greenAmount << 8) | blueAmount;
		            this.src.setRGB(x, y, newPixel);
		        }
		}
		
	}
	
	public BufferedImage getBlurredImg() {
		return this.src;
	}
}
