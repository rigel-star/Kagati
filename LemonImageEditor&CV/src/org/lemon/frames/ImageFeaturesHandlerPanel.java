package org.lemon.frames;

import java.awt.Color;
import java.awt.Container;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JPanel;

public class ImageFeaturesHandlerPanel extends JInternalFrame{

	private static final long serialVersionUID = 1L;
	
	JButton colorRemover, imageMixture;
	JPanel mainPanel;

	public ImageFeaturesHandlerPanel() {
		
		colorRemover = new JButton("Remove color");
		imageMixture = new JButton("Mix images");
		mainPanel = new JPanel();
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
		
		setLayout(new BoxLayout(getContentPane(), BoxLayout.X_AXIS));
		setResizable(true);
		setClosable(false);
		setSize(200, 300);
		setVisible(true);
		setBackground(Color.WHITE);
		setLocation(30, 40);
		
		add(colorRemover);
		
		Container c = getContentPane();
		c.add(mainPanel);
		mainPanel.add(colorRemover);
		mainPanel.add(imageMixture);
		
	}
}
