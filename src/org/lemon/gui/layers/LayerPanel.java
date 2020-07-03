package org.lemon.gui.layers;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

import org.lemon.filters.ResizeImage;
import org.lemon.gui.image.ImageView;

public class LayerPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	
	
	private Layer layer = null;
	private BufferedImage indiImg = null;
	
	
	public LayerPanel(Layer layer) {
		this.layer = layer;
		
		if(layer.getLayerComponent() instanceof ImageView) {
			indiImg = ((ImageView) layer.getLayerComponent()).getImage();
		}
		
		if(indiImg != null)
			indiImg = new ResizeImage(indiImg).getImageSizeOf(50, 50);
	}
	
	
	public Layer getLayer() {
		return layer;
	}
	
	
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(indiImg, 0, 0, null);
	}
	
	
	@Override
	public Dimension getPreferredSize() {
		return new Dimension(50, 50);
	}
	
	
}
