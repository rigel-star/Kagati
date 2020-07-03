package org.lemon.gui.layers;


import javax.swing.DefaultListModel;
import javax.swing.JList;

public class LayerList extends JList<Layer> {
	private static final long serialVersionUID = 1L;
	
	
	static DefaultListModel<Layer> model = new DefaultListModel<>();
	static LayerRenderer renderer = new LayerRenderer();
	
	public LayerList() {
		super(model);
		setCellRenderer(renderer);
	}
	
	
	public void add(Layer l) {
		model.addElement(l);
		
	}

}
