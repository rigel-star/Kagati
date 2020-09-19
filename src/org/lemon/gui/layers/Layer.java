package org.lemon.gui.layers;

import javax.swing.JComponent;


public class Layer {
	
	public static final int BACKGROUND_LAYER = 0;
	public static final int NORMAL_LAYER = 1;
	
	private JComponent comp = null;
	private String title = null;
	
	private int property = NORMAL_LAYER;
	public int id;
	
	
	/**
	 * 
	 * @param comp 		the image container which holds the image
	 * 
	 * */
	public Layer( JComponent comp ) {
		this( comp, "Layer", NORMAL_LAYER );
	}
	
	
	/**
	 * 
	 * @param comp 		the image container which holds the image
	 * @param title 	title for the {@code Layer}
	 * 
	 * */
	public Layer( JComponent comp, String title ) {
		this( comp, title, NORMAL_LAYER );
	}
	
	
	/**
	 * 
	 * @param comp 		the image container which holds the image
	 * @param property 	property of the {@code Layer}
	 * 
	 * */
	public Layer( JComponent comp, int property ) {
		this( comp, "Layer", NORMAL_LAYER );
	}
	
	
	/**
	 * 
	 * @param comp 		the image container which holds the image
	 * @param title 	title for the {@code Layer}
	 * @param property 	Property of the {@code Layer}
	 * 
	 * */
	public Layer( JComponent comp, String title, int property ) {
		this.comp = comp;
		this.title = title;
		this.property = property;
	}

	
	public void setLayerComponent( JComponent comp ) {
		this.comp = comp;
	}

	
	public void setTitle( String  title ) {
		this.title = title;
	}
	
	
	public void setProperty( int property ) {
		this.property = property;
	}
	
	
	public JComponent getLayerComponent() {
		return comp;
	}
	
	
	public String getTitle() {
		return title;
	}
	
	
	public int getProperty() {
		return property;
	}
}
