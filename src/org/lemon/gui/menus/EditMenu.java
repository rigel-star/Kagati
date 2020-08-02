package org.lemon.gui.menus;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenu;
import javax.swing.JMenuItem;

import org.lemon.gui.HSBController;
import org.lemon.gui.ImageView;
import org.lemon.gui.Workspace;

public class EditMenu extends JMenu implements ActionListener {
	private static final long serialVersionUID = 1L;

	
	private JMenu rotate;
	private JMenuItem blur, crop, flipRight, flipHorizontal, hsbControl;
	
	private Workspace workspace;
	
	
	public EditMenu(Workspace workspace) {
		
		this.workspace = workspace;
		
		setText("Edit");
		
		this.init();
		this.addAll();
	}
	
	
	private void init() {
		this.blur = new JMenuItem("Blur");
		this.crop = new JMenuItem("Crop");
		this.rotate = new JMenu("Rotate");
		this.hsbControl = new JMenuItem("HSB");
		
		this.flipHorizontal = new JMenuItem("Flip Horizontal");
		this.flipRight = new JMenuItem("Flip Right");
	}
	
	
	private void addAll() {
		
		add(rotate);
		add(crop);
		
		add(hsbControl);
		hsbControl.addActionListener(this);
		
		add(blur);
		
		this.rotate.add(flipHorizontal);
		this.rotate.add(flipRight);
	}


	@Override
	public void actionPerformed(ActionEvent e) {
		
		if(e.getSource() == hsbControl) {
			
			HSBController hsb = null;
			
			if(workspace.getSelectedFrame() instanceof ImageView) {
				var v = (ImageView) workspace.getSelectedFrame();
				hsb = new HSBController(workspace.getSelectedFrame(), v.getImagePanel().getImage());
			}
			
			workspace.add(hsb);
		}
	}
	
	
	
}
