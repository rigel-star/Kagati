package org.lemon.gui;

import javax.swing.JComponent;

public abstract class Layer {
	private JComponent component;
	private String title;
	
	public Layer(JComponent comp, String title)
	{
		this.component = comp;
		this.title = title;
	}
	
	public JComponent getLayerComponent()
	{
		return component;
	}
	
	public String getTitle()
	{
		return title;
	}
	
	public void setTitle( String title )
	{
		this.title = title;
	}
}