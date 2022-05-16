package org.lemon.gui.layer;

import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;

import org.lemon.filter.ResizeImageFilter;
import org.lemon.gui.ImageView;
import org.lemon.image.LImage;

public class ViewLayerPanel extends LayerPanel 
{
	private static final long serialVersionUID = 1L;
	
	private BufferedImage indiImg = null;
	private String title = null;
	
	public ViewLayerPanel(ViewLayer layer) 
	{
		super(layer);
		
		if(layer.getLayerComponent() instanceof ImageView) 
		{	
			ImageView view = ((ImageView) layer.getLayerComponent());
			title = layer.getTitle();
			indiImg = view.getCurrentImage();
		}
		
		if(indiImg != null) 
			indiImg = new ResizeImageFilter(50, 50).filter(new LImage(indiImg)).getAsBufferedImage();
		
		super.setTitle(title);
		super.setIcon(new ImageIcon(indiImg));
		super.repaint();
		super.revalidate();
	}		
}
