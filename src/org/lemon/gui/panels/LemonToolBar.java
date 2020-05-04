package org.lemon.gui.panels;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JToolBar;


/**
 * Main toolbar sits on the top of application and below menu.
 * Main toolbar for fast access of tools which are frequently used in application.
 * MainToolBar's container is {@code MainApplicationFrame}.
 * */

public class LemonToolBar extends JToolBar implements ActionListener {
	private static final long serialVersionUID = 1L;
	
	private JButton helloBttn;
	private JButton colorPicker;
	private JButton textures;
	
	Color choosenColor = Color.red;
	
	public LemonToolBar() {
		setRollover(true);
		setFloatable(false);
		
		this.init();
		this.actionEvents();
		this.addAll();
		
	}
	
	
	/*init every widget in toolbar*/
	private void init() {
		this.colorPicker = new JButton();
		this.colorPicker.setBackground(this.choosenColor);
		this.colorPicker.setToolTipText("Choose color");
		
		this.textures = new JButton();
		this.helloBttn = new JButton("Hello");
	}
	
	
	
	/*add all the widgets to the toolbar*/
	private void addAll() {
		add(this.helloBttn);
		add(this.colorPicker);
		add(this.textures);
	}
	
	
	/*init every widget action event in this method*/
	private void actionEvents() {
		colorPicker.addActionListener(this);
		textures.addActionListener(this);
		helloBttn.addActionListener(this);
	}
	
	
	/**
	 * Get slected color.
	 * @return choosenColor
	 * */
	public Color getChosenColor() {
		return choosenColor;
	}


	@Override
	public void actionPerformed(ActionEvent e) {
		
		if(e.getSource() == this.colorPicker) {
			this.choosenColor = JColorChooser.showDialog(getParent(), "Color Chooser", Color.white);
			this.colorPicker.repaint();
		}
		
		else if(e.getSource() == this.textures) {
			
		}
		
	}

}
