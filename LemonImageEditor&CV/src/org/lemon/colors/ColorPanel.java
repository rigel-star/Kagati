package org.lemon.colors;

import java.awt.Color;

import javax.swing.JColorChooser;

public class ColorPanel extends JColorChooser {
	
	private static final long serialVersionUID = 1L;

	public ColorPanel() {
		setSize(200, 200);
		setPreviewPanel(null);
	}
	
	public Color getChoosenColor() {
		return this.getColor();
	}
}
