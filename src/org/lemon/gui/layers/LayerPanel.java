package org.lemon.gui.layers;

import javax.swing.JPanel;

public class LayerPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	
	
	private Layer layer;
	
	
	public LayerPanel(Layer layer) {
		this.layer = layer;
	}
	
	
	public Layer getLayer() {
		return layer;
	}
	
}
