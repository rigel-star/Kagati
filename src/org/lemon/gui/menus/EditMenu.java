package org.lemon.gui.menus;

import javax.swing.JMenu;
import javax.swing.JMenuItem;

public class EditMenu extends JMenu {
	private static final long serialVersionUID = 1L;

	
	private JMenu rotate;
	private JMenuItem blur, crop, flipRight, flipHorizontal;
	
	
	public EditMenu(Object container) {
		
		setText("Edit");
		
		this.init();
		this.addAll();
	}
	
	
	private void init() {
		this.blur = new JMenuItem("Blur");
		this.crop = new JMenuItem("Crop");
		this.rotate = new JMenu("Rotate");
		
		this.flipHorizontal = new JMenuItem("Flip Horizontal");
		this.flipRight = new JMenuItem("Flip Right");
	}
	
	
	private void addAll() {
		
		add(rotate);
		add(crop);
		add(blur);
		
		this.rotate.add(flipHorizontal);
		this.rotate.add(flipRight);
	}
	
	
	
	
	
	
	
}
