package org.lemon.gui;

import javax.swing.JComponent;

/**
 * 
 * Layer Interface.
 * 
 * */
public interface Layer {
	
	/**
	 * @return 		{@code JComponent} attached with this {@link Layer}.
	 * */
	public JComponent getLayerComponent();
	
	
	/**
	 * @return Title of this {@link Layer}
	 * */
	public String getTitle();
	
	
	/**
	 * Set title of this {@link Layer}.
	 * @param title 	New title.
	 * 
	 * */
	public void setTitle( String title );
}