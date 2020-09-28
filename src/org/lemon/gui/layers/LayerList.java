package org.lemon.gui.layers;

import java.util.Enumeration;

import javax.swing.DefaultListModel;
import javax.swing.JList;

import org.lemon.gui.Layer;

public class LayerList extends JList<Layer> {
	
	/**
	 * Serial UID
	 * */
	private static final long serialVersionUID = 1L;	
	
	private static DefaultListModel<Layer> model = new DefaultListModel<>();
	private static LayerItemRenderer renderer = new LayerItemRenderer();
	
	public LayerList() {
		super( model );
		setCellRenderer( renderer );
	}
	
	
	public void refresh() {
		for( Enumeration<Layer> enu = model.elements(); enu.hasMoreElements(); ) {
			Layer layer  = enu.nextElement();
			layer.getLayerComponent().repaint();
		}
	}
	
	
	public void add( Layer l ) {
		model.addElement( l );
	}
	
	
	public int getLayerCount() {
		return model.size();
	}
	
	
	public void remove( Layer l ) {
		model.removeElement( l );
	}
}