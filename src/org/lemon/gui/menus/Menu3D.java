package org.lemon.gui.menus;

import javax.swing.JMenu;
import javax.swing.JMenuItem;

public class Menu3D extends JMenu {
	private static final long serialVersionUID = 1L;
	
	
	private JMenu light3d;
	
	/*types of lights*/
	private JMenuItem spotLight;
	private JMenuItem infiniteLight;
	
	
	public Menu3D(Object container) {
		
		setText("3D");
		light3d = createLight3dMenu();
		
		add(light3d);
	}
	
	
	private JMenu createLight3dMenu() {
		var l3d = new JMenu("Light");
		
		spotLight = new JMenuItem("Spot Light");
		infiniteLight = new JMenuItem("Infinite Light");
		
		l3d.add(spotLight);
		l3d.add(infiniteLight);
		
		return l3d;
	}

}
