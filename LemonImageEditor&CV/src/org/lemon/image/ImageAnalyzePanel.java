package org.lemon.image;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class ImageAnalyzePanel extends JPanel {

	private static final long serialVersionUID = 1L;
	
	private JLabel imgLabel;
	
	public ImageAnalyzePanel(BufferedImage img) {
		
		ResizeImg rimg = new ResizeImg(img, 0.4f);
		this.imgLabel = new JLabel(new ImageIcon(rimg.getScaledImage()));
		
		JInternalFrame iframe = new JInternalFrame("Image Info", true);
		iframe.setDefaultCloseOperation(JInternalFrame.DISPOSE_ON_CLOSE);
		iframe.setLayout(new BorderLayout());
		iframe.setVisible(true);
		iframe.setBackground(Color.white);
		iframe.add(this.imgLabel, BorderLayout.CENTER);
	}

}
