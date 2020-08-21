package org.lemon.gui.menus;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenu;
import javax.swing.JMenuItem;

import org.lemon.gui.ImageView;
import org.lemon.gui.Workspace;
import org.lemon.gui.filter.HSBController;
import org.lemon.gui.filter.TextureController;

public class EditMenu extends JMenu implements ActionListener {
	private static final long serialVersionUID = 1L;

	
	private JMenu rotate;
	private JMenuItem blur, crop, flipRight, flipHorizontal, hsbControl, texture;
	
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
		this.texture = new JMenuItem("Texture");
		
		this.flipHorizontal = new JMenuItem("Flip Horizontal");
		this.flipRight = new JMenuItem("Flip Right");
	}
	
	
	private void addAll() {
		
		rotate.add(flipHorizontal);
		rotate.add(flipRight);
		add(rotate);
		
		add(crop);
		
		add(hsbControl);
		hsbControl.addActionListener(this);
		
		add(blur);
		
		add(texture);
		texture.addActionListener(this);
		
	}


	@Override
	public void actionPerformed(ActionEvent e) {
		
		Component comp = null;
		
		if(e.getSource() == hsbControl) {
			
			if(workspace.getSelectedFrame() instanceof ImageView) {
				var v = (ImageView) workspace.getSelectedFrame();
				comp = new HSBController(v, v.getImagePanel().getImage());
			}
		}
		
		else if(e.getSource() == texture) {
			comp = new TextureController();
		}
		
		workspace.add(comp);
		workspace.fetchNodes();
		workspace.refresh();
		workspace.revalidate();
	}
	
	
	
}
