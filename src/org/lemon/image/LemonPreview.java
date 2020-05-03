package org.lemon.image;

import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;


/**
 * Preview the image after applying filter.
 * */

public class LemonPreview extends JPanel {
	private static final long serialVersionUID = 1L;

	
	private JLabel imgContainer = new JLabel();
	
	
	public LemonPreview() {}
	
	
	public LemonPreview(BufferedImage drawable){
		
		int w = drawable.getWidth();
		int h = drawable.getHeight();
		
		setSize(w, h);
		
		this.imgContainer.setIcon(new ImageIcon(drawable));
		
		add(this.imgContainer);
	}
	
	
}
