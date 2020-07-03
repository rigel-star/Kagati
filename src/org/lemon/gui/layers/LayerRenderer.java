package org.lemon.gui.layers;

import java.awt.Component;

import javax.swing.JList;
import javax.swing.ListCellRenderer;

public class LayerRenderer implements ListCellRenderer<Layer> {

	
	public LayerRenderer() {
	}
	
	
	@Override
	public Component getListCellRendererComponent(JList<? extends Layer> list, Layer value, int index,
			boolean isSelected, boolean cellHasFocus) {
		
		var lypan = new LayerPanel(value);
		return lypan;
	}

}
