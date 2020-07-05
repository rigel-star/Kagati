package org.lemon.gui.layers;


import java.util.Enumeration;

import javax.swing.DefaultListModel;
import javax.swing.JList;

import org.lemon.utils.IDManager;

public class LayerList extends JList<Layer> {
	private static final long serialVersionUID = 1L;
	
	
	private static DefaultListModel<Layer> model = new DefaultListModel<>();
	private static LayerRenderer renderer = new LayerRenderer();
	private IDManager<Layer> idMngr = new IDManager<Layer>();
	
	
	public LayerList() {
		super(model);
		setCellRenderer(renderer);
	}
	
	
	public void refresh() {
		for(Enumeration<Layer> enu = model.elements(); enu.hasMoreElements();) {
			Layer layer  = enu.nextElement();
			layer.getLayerComponent().repaint();
		}
	}
	
	
	public int getLayerCount() {
		return model.size();
	}
	
	
	public void add(Layer l) {
		model.addElement(l);
		int id = idMngr.next();
		l.id = id;
		idMngr.add(id, l);
	}
	
	
	public void remove(Layer l) {
		model.removeElement(l);
	}
	

}
