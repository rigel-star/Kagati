package org.lemon.gui.image;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.image.BufferedImage;

import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class ImageInfoPanel extends JPanel {
	
	private static final long serialVersionUID = 1L;
	
	private JLabel imgHW;
	
	public ImageInfoPanel(BufferedImage img) {
		this.init(img);
		this.setSize(150, 50);
		
		JInternalFrame iframe = new JInternalFrame("Image Info", true);
		iframe.setDefaultCloseOperation(JInternalFrame.DISPOSE_ON_CLOSE);
		iframe.setLayout(new BorderLayout());
		iframe.setVisible(true);
		iframe.setBackground(Color.white);
		iframe.add(this.imgHW, BorderLayout.SOUTH);
		iframe.setSize(150, 50);
		this.add(iframe);
		
	}
	
	//init widgets
	private void init(BufferedImage img) {
		this.imgHW = new JLabel();
		
		if(img != null)
			this.imgHW.setText("  Height: "+ img.getHeight() + "                Width: " + img.getWidth() + "  \n");
		else
			this.imgHW.setText("  Height: " + "                Width: " + "  \n");
	}
}
