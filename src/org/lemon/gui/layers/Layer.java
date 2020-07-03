package org.lemon.gui.layers;

import javax.swing.JComponent;

public class Layer {
	
	
	private JComponent comp;
	
	
	public Layer(JComponent comp) {
		this.comp = comp;
	}

	
	public JComponent getLayerComponent() {
		return comp;
	}
}
