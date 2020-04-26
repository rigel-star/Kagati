package org.lemon.frames;

import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.SpringLayout;
import javax.swing.border.Border;

public class ImageAnalyzeMenu extends JPanel {

	//version id
	private static final long serialVersionUID = 1L;
	
	public ImageAnalyzeMenu() {
		
		//layout for panel
		BoxLayout layout = new BoxLayout(this, BoxLayout.Y_AXIS);
        
		//border for panel
		Border layoutBorder = BorderFactory.createLineBorder(Color.BLACK, 2);
		
		setLayout(layout);
		setBorder(layoutBorder);
		
		SpringLayout sp = new SpringLayout();
		//setLayout(sp);
		

	}
}
