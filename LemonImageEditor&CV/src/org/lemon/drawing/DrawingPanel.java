package org.lemon.drawing;

import java.awt.Color;

import javax.swing.JInternalFrame;

public class DrawingPanel extends JInternalFrame {

	private static final long serialVersionUID = 1L;
	

	private DrawingCanvas drawPanel;
	private Color col;
	
	public DrawingPanel(Color col, int w, int h) {
		
		this.col = col;
		
		setSize(w, h);
		setBackground(Color.WHITE);
		setVisible(true);
		setClosable(true);
		
		drawPanel = new DrawingCanvas(this.col);;
		getContentPane().add(drawPanel.getCanvas());
	}

}
