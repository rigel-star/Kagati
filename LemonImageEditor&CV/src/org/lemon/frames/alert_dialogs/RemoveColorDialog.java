package org.lemon.frames.alert_dialogs;

import java.awt.Color;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class RemoveColorDialog {

	private Color color;
	
	public RemoveColorDialog(JPanel parent) {
		
		String color = JOptionPane.showInputDialog(parent, "Enter color to remove");
		
		String[] colArray = color.split(",");
		
		int R = Integer.parseInt(colArray[0].trim());
		int G = Integer.parseInt(colArray[1].trim());
		int B = Integer.parseInt(colArray[2].trim());
		
		this.color = new Color(R, G, B);
		
		System.out.println(R + " " + G + " " + B);
	}
	
	public Color getPreferredColor() {
		return this.color;
	}
}
