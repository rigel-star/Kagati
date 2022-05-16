package org.lemon.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.swing.BorderFactory;

import org.lemon.gui.image.ImagePanel;

public class ImageView extends View
{
	private static final long serialVersionUID = 1L;
	
	private ImagePanel imagePanel = null;
	private BufferedImage imageSource = null;
	
	public ImageView(BufferedImage img, final LayerContainer lycont) 
	{
		this(img, null, lycont);
	}
	
	public ImageView(BufferedImage img, final String title, final LayerContainer layerContainer )  
	{
		super(layerContainer);
		
		if(img == null)
			throw new NullPointerException( "Image can't be null." );
		
		this.imageSource = img;
		this.imagePanel = new ImagePanel(imageSource);
		
		setLayout(new BorderLayout());
		setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
		setTitle(title);
		setClosable(true);
		setSize(new Dimension(imageSource.getWidth(), imageSource.getHeight()));
		setLayout(new BorderLayout());
		setMaximizable(true);
		setIconifiable(true);
		add(imagePanel, BorderLayout.CENTER);
		setVisible(true);
	}
	
	public ImagePanel getImagePanel() {
		return imagePanel;
	}
	
	public BufferedImage getOriginalImage() {
		return imagePanel.getOriginalImage();
	}
	
	public BufferedImage getCurrentImage() {
		return imagePanel.getCurrentImage();
	}
	
	public Graphics2D getDrawable()
	{
		return this.getCurrentImage().createGraphics();
	}
}