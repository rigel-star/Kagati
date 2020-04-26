package org.lemon.colors;

import java.awt.Color;
import java.awt.image.BufferedImage;

public class ColorRemover {

	private Color removeCol;
	private BufferedImage img;
	private int h, w;
	private ColorRange colRange;
	
	public ColorRemover(BufferedImage img, Color removeCol) {
		this.img = img;
		this.h = img.getHeight();
		this.w = img.getWidth();
		this.removeCol = removeCol;
		
		this.colRange = new ColorRange();
		
		this.remove();
		
	}
	
	private void remove() {
		
		Color currentCol ;
		double diff;
		
		for(int y=0;y<h;y++) {
			for(int x=0;x<w;x++) {
				
				currentCol = new Color(this.img.getRGB(x, y));
				
				diff = dist(removeCol.getRed(), removeCol.getGreen(), removeCol.getBlue(),
						currentCol.getRed(), currentCol.getGreen(), currentCol.getBlue());
				
				if(this.colRange.is(diff).inRange(0, 100)) {
					img.setRGB(x, y, 0);
				}
				else
					img.setRGB(x, y, img.getRGB(x, y));
				
			}
		}
	}
	
	//euclidian distance of two RGB_color_space
	static double dist(double r1, double g1, double b1, double r2, double g2, double b2) {	
		return Math.sqrt(Math.pow((r1-r2), 2) + Math.pow((b1-b2), 2) + Math.pow((g1-g2), 2));
	}
	
}
