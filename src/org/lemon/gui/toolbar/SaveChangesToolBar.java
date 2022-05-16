package org.lemon.gui.toolbar;

import java.awt.FlowLayout;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JToolBar;

public class SaveChangesToolBar extends JToolBar {
	private static final long serialVersionUID = 1L;
	
	
	private JButton cancel, done;
	
	public SaveChangesToolBar() {
		
		init();
		
		setLayout(new FlowLayout(FlowLayout.RIGHT));
		
		add(cancel);
		add(done);
	}
	
	
	private void init() {
		
		done = new JButton();
		done.setIcon(new ImageIcon("icons/tick.png"));
		
		cancel = new JButton();
		cancel.setIcon(new ImageIcon("icons/cross.png"));
	}

}
