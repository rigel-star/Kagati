package org.lemon.filters.transformations.gui;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JWindow;

public class VanishingPointFilterGUI extends JWindow {
	private static final long serialVersionUID = 1L;
	
	
	private Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
	
	public VanishingPointFilterGUI() {
		
		setSize(screen.width - 100, screen.height - 100);
		
	}
	
	
}
