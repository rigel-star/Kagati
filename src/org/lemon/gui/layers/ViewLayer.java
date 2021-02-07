package org.lemon.gui.layers;

import javax.swing.JComponent;

import org.lemon.gui.Layer;
import org.lemon.gui.node.NodeComponent;

public class ViewLayer implements Layer {

	public static final int BACKGROUND_LAYER = 0;
	public static final int NORMAL_LAYER = 1;
	
	private JComponent comp = null;
	private String title = null;
	
	private int property = NORMAL_LAYER;
	
	/**
	 * 
	 * @param comp 		the image container
	 * 
	 * */
	public ViewLayer( JComponent comp ) {
		this( comp, "Layer", NORMAL_LAYER );
	}
	
	/**
	 * @param comp 		the image container
	 * @param title 	title for the {@code Layer}
	 * */
	public ViewLayer( JComponent comp, String title ) {
		this( comp, title, NORMAL_LAYER );
	}
	
	/**
	 * @param comp 		the image container
	 * @param property 	property of the {@code Layer}
	 * 
	 * */
	public ViewLayer( JComponent comp, int property ) {
		this( comp, "Layer", NORMAL_LAYER );
	}
	
	/**
	 * @param comp 		the image container
	 * @param title 	title for the {@code Layer}
	 * @param property 	Property of the {@code Layer}
	 * 
	 * */
	public ViewLayer( JComponent comp, String title, int property ) {
		this.comp = comp;
		this.title = title;
		this.property = property;
		
		if ( comp instanceof NodeComponent ) {
			((NodeComponent) comp).attachLayer( this );
		}
	}
	
	public void setProperty( int property ) {
		this.property = property;
	}
	
	public int getProperty() {
		return property;
	}
	
	@Override
	public void setTitle( String  title ) {
		this.title = title;
	}
	
	@Override
	public String getTitle() {
		return title;
	}
	
	@Override
	public JComponent getLayerComponent() {
		return comp;
	}
}