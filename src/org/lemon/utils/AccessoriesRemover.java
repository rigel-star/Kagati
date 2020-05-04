package org.lemon.utils;

import java.awt.Component;

import javax.swing.JPanel;

import org.lemon.gui.image.LemonImageView;

public class AccessoriesRemover {

	public AccessoriesRemover(JPanel panel) {
		Component[] comps = panel.getComponents();
		
		//deleting all components attached to editing panel
		for(Component c: comps) {
			panel.remove(c);
		}
	}
	
	public AccessoriesRemover(LemonImageView panel) {
		Component[] comps = panel.getComponents();
		
		//deleting all components attached to editing panel
		for(Component c: comps) {
			panel.remove(c);
		}
	}
	
}
