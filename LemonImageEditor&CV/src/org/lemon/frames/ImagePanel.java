package org.lemon.frames;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.image.BufferedImage;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;

public class ImagePanel extends JPanel {

	/**
	 * default version id
	 */
	private static final long serialVersionUID = 1L;
	
	//img label
	private JLabel imgLabel = new JLabel();

	public ImagePanel(BufferedImage img) {
		
		Border border = BorderFactory.createLineBorder(Color.GRAY, 1);
		
		setLayout(new FlowLayout());
		setBorder(border);
		setSize(500, 500);
		imgLabel.setIcon(new ImageIcon(img));
		add(imgLabel);
	}
	
}
