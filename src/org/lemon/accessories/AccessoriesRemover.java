package org.lemon.accessories;

import java.awt.Component;

import javax.swing.JPanel;

import org.lemon.image.ImageView;

public class AccessoriesRemover {

	public AccessoriesRemover(JPanel panel) {
		Component[] comps = panel.getComponents();
		
		//deleting all components attached to editing panel
		for(Component c: comps) {
			panel.remove(c);
		}
	}
	
	public AccessoriesRemover(ImageView panel) {
		Component[] comps = panel.getComponents();
		
		//deleting all components attached to editing panel
		for(Component c: comps) {
			panel.remove(c);
		}
	}
	
}
