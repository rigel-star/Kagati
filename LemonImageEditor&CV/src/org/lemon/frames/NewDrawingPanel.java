package org.lemon.frames;

import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.border.Border;


public class NewDrawingPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	

	public NewDrawingPanel() {
		//layout for panel
		BoxLayout layout = new BoxLayout(this, BoxLayout.Y_AXIS);
        
		//border for panel
		Border layoutBorder = BorderFactory.createLineBorder(Color.BLACK, 2);
		
		setLayout(layout);
		setBorder(layoutBorder);
		setSize(200, 300);
		
		add(new DrawingPanel());
	}
}
