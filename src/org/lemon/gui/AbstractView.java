package org.lemon.gui;

import javax.swing.JInternalFrame;

public abstract class AbstractView extends JInternalFrame
{
	private static final long serialVersionUID = 1L;
	
	private Layer layer;

	public void attachLayer(Layer layer)
	{
		this.layer = layer;
	}
	
	public Layer getAttachedLayer()
	{
		return layer;
	}
}
