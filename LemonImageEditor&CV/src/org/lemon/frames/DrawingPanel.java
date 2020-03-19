package org.lemon.frames;

import java.awt.Color;

import javax.swing.JInternalFrame;

import org.lemon.drawing.DrawingCanvas;

public class DrawingPanel extends JInternalFrame {

	private static final long serialVersionUID = 1L;
	

	DrawingCanvas drawPanel = new DrawingCanvas();
	
	public DrawingPanel() {
		
		setLocation(100, 50);
		setBackground(Color.WHITE);
		setVisible(true);
		setClosable(true);
		
		getContentPane().add(drawPanel.getCanvas());
	}

}
