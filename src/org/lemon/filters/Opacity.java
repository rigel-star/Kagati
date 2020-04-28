package org.lemon.filters;

import java.awt.Color;
import java.awt.image.BufferedImage;

import org.rampcv.color.Range;

public class Opacity {
	
	public Opacity(BufferedImage img, float opacity) {
		img = this.apply(img, opacity);
	}
	
	private BufferedImage apply(BufferedImage img, float opacity) {
		
		if(img == null)
			throw new NullPointerException("Image can't be null.");
		if(opacity < 0.0 || opacity > 1.0)
			throw new IllegalArgumentException("Invalid opacity value. Must be between 0.0f to 1.0f.");
		
		Color col;
		Color res;
		
		for(int x=0; x<img.getWidth(); x++) {
			for(int y=0; y<img.getHeight(); y++) {
				col = new Color(img.getRGB(x, y));
				int alpha = (int) Range.constrain(col.getAlpha() - (opacity*10), 0, 255);
				res = new Color(col.getRed(), col.getGreen(), col.getBlue(), alpha);
				img.setRGB(x, y, res.getRGB());
			}
		}
		
		return img;
	}
	
}
