package org.lemon.filters;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import org.lemon.image.ImageView;

public class Opacity {
	
	public Opacity(ImageView bg, BufferedImage img, float opacity) {
		img = this.apply(bg, img, opacity);
	}
	
	private BufferedImage apply(ImageView bg, BufferedImage img, float opacity) {
		
		if(img == null)
			throw new NullPointerException("Image can't be null.");
		if(opacity < 0.0 || opacity > 1.0)
			throw new IllegalArgumentException("Invalid opacity value. Must be between 0.0f to 1.0f.");
		
		Graphics2D g = (Graphics2D) bg.getGraphics();
		g.setComposite(AlphaComposite.SrcOver.derive(opacity));
		g.drawImage(img, 0, 0, null);
		g.dispose();
		System.out.println("from opacity class: " + opacity);
		
		return img;
	}
	
}
