package org.lemon.image;

import java.awt.Color;
import java.awt.image.BufferedImage;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.border.Border;

public class MiniImageView extends JFrame {
	private static final long serialVersionUID = 1L;

	
	public MiniImageView(BufferedImage img, int x, int y, int w, int h) {
		
		int iw = img.getWidth();
		int ih = img.getHeight();
		
		setSize(w, h);
		setUndecorated(true);
		setVisible(true);
		setLocation(x - (w/2), y - (h/2));
		
		Border b = BorderFactory.createLineBorder(Color.black, 2);
		
		if(x > 0 && y > 0) {
			if(x < (iw - w) && y < (ih - h)) {
				var mini = img.getSubimage(x - (iw/2), y - (ih/2), w, h);
				var pan = new ImagePanel(mini);
				pan.setBorder(b);
				add(pan);
			}
		}
	}
	
}
