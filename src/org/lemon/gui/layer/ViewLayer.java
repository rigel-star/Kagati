package org.lemon.gui.layers;

import javax.swing.JComponent;

import org.lemon.gui.Layer;
import org.lemon.gui.NodeView;

public class ViewLayer extends Layer 
{	
	public ViewLayer(JComponent comp, String title) 
	{
		super(comp, title);
		
		if(comp instanceof NodeView) 
		{
			((NodeView) comp).attachLayer(this);
		}
	}
}