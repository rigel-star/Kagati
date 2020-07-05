package org.lemon.gui.layers;

import javax.swing.JComponent;


public class Layer {
	
	public static final int BACKGROUND_LAYER = 0;
	public static final int NORMAL_LAYER = 1;
	
	private JComponent comp = null;
	private String title = null;
	private int property = NORMAL_LAYER;
	public int id;
	
	
	/*default constructor*/
	public Layer() {
		
	}
	
	
	/**
	 * @param comp i.e. {@code ImageView} or {@code DrawingPanel}
	 * */
	public Layer(JComponent comp) {
		this(comp, "Layer", NORMAL_LAYER);
	}
	
	
	/**
	 * @param comp i.e. {@code ImageView} or {@code DrawingPanel}
	 * @param title Title for the {@code Layer}
	 * */
	public Layer(JComponent comp, String title) {
		this(comp, title, NORMAL_LAYER);
	}
	
	
	/**
	 * @param comp i.e. {@code ImageView} or {@code DrawingPanel}
	 * @param property Property of the {@code Layer}
	 * */
	public Layer(JComponent comp, int property) {
		this(comp, "Layer", NORMAL_LAYER);
	}
	
	
	/**
	 * @param comp i.e. {@code ImageView} or {@code DrawingPanel}
	 * @param title Title for the {@code Layer}
	 * @param property Property of the {@code Layer}
	 * */
	public Layer(JComponent comp, String title, int property) {
		this.comp = comp;
		this.title = title;
		this.property = property;
	}

	
	public void setLayerComponent(JComponent comp) {
		this.comp = comp;
	}

	
	public void setTitle(String  title) {
		this.title = title;
	}
	
	
	public void setProperty(int property) {
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
