package org.lemon.frames;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.border.Border;

public class ImagePanel extends JLabel {

	/**
	 * default version id
	 */
	private static final long serialVersionUID = 1L;
	
	public BufferedImage img;
	
	public ImagePanel() {
		
	}

	public ImagePanel(BufferedImage img) throws IOException {
		
		this.img = img;
		//layout for panel
		//BoxLayout layout = new BoxLayout(getContentPane(), BoxLayout.Y_AXIS);
				
		Border border = BorderFactory.createLineBorder(Color.GRAY, 1);
		setBorder(border);
		setSize(400, 400);
		
		setIcon(new ImageIcon(img));
	}
	
	public BufferedImage getCurrentImg() {
		return this.img;
	}
	
}
