package org.lemon.gui.panels;

import java.awt.Color;
import java.awt.Component;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.border.Border;

import application.MainApplicationScene;


public class LemonToolPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	
	/*Targeted component to edit*/
	private Component target;
	
	/*This ToolPanel's main child ToolsMenu which is responsible for all tools management*/
	private LemonToolsMenu toolMenu;
	
	public LemonToolPanel() {
		this(null, null, null);
	}
	
	public LemonToolPanel(MainApplicationScene parent) {
		this(parent, null, null);
	}
	
	public LemonToolPanel (MainApplicationScene parent, Component tar, Color col) {
		
		if(tar != null)
			target = tar;
		
		//layout for panel
		BoxLayout layout = new BoxLayout(this, BoxLayout.Y_AXIS);
        
		//border for panel
		Border layoutBorder = BorderFactory.createLineBorder(Color.BLACK, 2);
		
		setLayout(layout);
		setBorder(layoutBorder);
		setSize(200, 300);
		
		this.toolMenu = new LemonToolsMenu(parent, null);
		
		add(toolMenu);
				
	}
	
	
	
	/**
	 * Set target for {@code this ToolPanel} and all its childs.
	 * Target is component which is currently accessible to {@code this ToolPanel}.
	 * Setting target means giving {@code this ToolPanel} some component to act on.
	 * @param {@code Component} target
	 * */
	public void setTarget(Component tar) {
		this.target = tar;
	}
	
	
	/**
	 * Get target of this {@code ToolPanel}.
	 * @return {@code Component} current target.
	 * */
	public Component getTarget() {
		return this.target;
	}
	
}
