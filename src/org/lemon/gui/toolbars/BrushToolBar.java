package org.lemon.gui.toolbars;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JToolBar;

import org.lemon.AppGlobalProperties;
import org.lemon.tools.BrushTool;


/**
 * Main toolbar sits on the top of application and below menu.
 * Main toolbar for fast access of tools which are frequently used in application.
 * MainToolBar's container is {@code MainApplicationFrame}.
 * */

public class BrushToolBar extends JToolBar implements ActionListener {
	private static final long serialVersionUID = 1L;
	
	private JButton colorPicker;
	
	private Color choosenColor = Color.red;
	
	private AppGlobalProperties agp;

	private BrushTool brush;
	
	public BrushToolBar(AppGlobalProperties agp, BrushTool brush) {
		setRollover(true);
		setFloatable(false);
		
		this.agp = agp;
		this.brush = brush;
		
		this.init();
		this.actionEvents();
		this.addAll();
		
	}
	
	
	/*init every widget in toolbar*/
	private void init() {
		this.colorPicker = new JButton("COLOR");
		this.colorPicker.setBackground(this.choosenColor);
		this.colorPicker.setToolTipText("Choose color");
		this.colorPicker.setForeground(this.choosenColor);
		
	}
	
	
	
	/*add all the widgets to the toolbar*/
	private void addAll() {
		add(this.colorPicker);
	}
	
	
	/*init every widget action event in this method*/
	private void actionEvents() {
		colorPicker.addActionListener(this);
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
			
			this.choosenColor = JColorChooser.showDialog(getParent(), "Color Picker", Color.white);
			this.colorPicker.setBackground(choosenColor);
			this.colorPicker.setForeground(choosenColor);
			this.agp.setGlobalColor(choosenColor);
			this.brush.setStrokeColor(choosenColor);
		}
		
	}

}
