package org.lemon.gui;

import javax.swing.JInternalFrame;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;

public abstract class AbstractView extends JInternalFrame
{
	private static final long serialVersionUID = 1L;
	
	private LayerContainer layerContainer;
	private Layer layer;
	
	public AbstractView(LayerContainer layerContainer)
	{
		this.layerContainer = layerContainer;
		
		addInternalFrameListener(new InternalFrameAdapter() {
			@Override
			public void internalFrameClosing(InternalFrameEvent e) 
			{
				super.internalFrameClosing(e);
				AbstractView.this.layerContainer.removeLayer(layer);
			}
		});
	}

	public void attachLayer(Layer layer)
	{
		this.layer = layer;
	}
	
	public Layer getAttachedLayer()
	{
		return layer;
	}
}
