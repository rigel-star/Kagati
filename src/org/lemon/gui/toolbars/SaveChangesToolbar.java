package org.lemon.gui.toolbars;

import java.awt.FlowLayout;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JToolBar;

public class SaveChangesToolbar extends JToolBar {
	
	/**
	 * Serial UID
	 * */
	private static final long serialVersionUID = 1L;
	
	private JButton cancel, done;
	
	public SaveChangesToolbar() {
		
		init();
		setLayout( new FlowLayout( FlowLayout.RIGHT ));
		
		add( cancel );
		add( done );
	}
	
	
	/**
	 * Init the widgets.
	 * */
	private void init() {
		
		done = new JButton();
		done.setIcon( new ImageIcon( "icons/tick.png" ));
		
		cancel = new JButton();
		cancel.setIcon( new ImageIcon( "icons/cross.png" ));
	}
}
