package org.lemon.frames;


import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.border.Border;


public class FilterPanel extends JPanel {

	//version id
	private static final long serialVersionUID = 1L;
	
	public FilterPanel() {
	}
	
	public FilterPanel(BufferedImage img) throws IOException {
		
		//layout for panel
		BoxLayout layout = new BoxLayout(this, BoxLayout.Y_AXIS);
        
		//border for panel
		Border layoutBorder = BorderFactory.createLineBorder(Color.BLACK, 2);
		
		setLayout(layout);
		setBorder(layoutBorder);
	}
}
