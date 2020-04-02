package org.lemon.drawing;

import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.border.Border;


public class NewDrawingPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	
	//private Color col;

	public NewDrawingPanel(Color col) {
		
		//button will show the selected color and properties of button
		//this.col = col;
		
		//layout for panel
		BoxLayout layout = new BoxLayout(this, BoxLayout.Y_AXIS);
        
		//border for panel
		Border layoutBorder = BorderFactory.createLineBorder(Color.BLACK, 2);
		
		setLayout(layout);
		setBorder(layoutBorder);
		setSize(200, 300);
		
		//add(new DrawingPanel(this.col));
	}
}
