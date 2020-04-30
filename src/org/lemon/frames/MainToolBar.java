package org.lemon.frames;

import java.awt.Color;

import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JToolBar;

public class MainToolBar extends JToolBar {
	private static final long serialVersionUID = 1L;
	
	JButton b1 = new JButton("Hello");
	JButton colorChooser = new JButton();
	Color choosenColor = Color.red;
	
	public MainToolBar() {
		setRollover(true);
		setFloatable(false);
		
		add(b1);
		add(colorChooser);
		
		colorChooser.setBackground(Color.red);
		colorChooser.setToolTipText("Choose color");
		colorChooser.addActionListener(action -> {
			choosenColor = JColorChooser.showDialog(getParent(), "Color Chooser", Color.white);
			colorChooser.repaint();
		});
	}
	
	
	public Color getChosenColor() {
		return choosenColor;
	}

}
