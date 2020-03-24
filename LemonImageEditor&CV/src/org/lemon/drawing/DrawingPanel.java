package org.lemon.drawing;

import java.awt.Color;

import javax.swing.JInternalFrame;

public class DrawingPanel extends JInternalFrame {

	private static final long serialVersionUID = 1L;
	

	private DrawingCanvas drawPanel;
	private Color col;
	
	public DrawingPanel(Color col) {
		
		this.col = col;
		
		setLocation(100, 50);
		setBackground(Color.WHITE);
		setVisible(true);
		setClosable(true);
		
		drawPanel = new DrawingCanvas(this.col);;
		getContentPane().add(drawPanel.getCanvas());
	}

}
