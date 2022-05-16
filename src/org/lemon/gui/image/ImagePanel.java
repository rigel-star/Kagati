package org.lemon.gui.image;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import javax.swing.BorderFactory;
import javax.swing.JPanel;

import org.lemon.utils.Utils;

public class ImagePanel extends JPanel {
	private static final long serialVersionUID = 1L;
	
	private BufferedImage originalImage, copyImage;
	
	/**
	 * Constructs {@code ImagePanel} with image and {@code PanelMode}.
	 * 
	 * @param img 		Image for {@code this ImagePanel}.
	 * @param mode		{@code PanelMode} for {@code this ImagePanel}.
	 * */
	public ImagePanel(BufferedImage img) {
		this.originalImage = img;
		this.copyImage = Utils.getImageCopy(img);
		this.originalImage.getGraphics().dispose();
		
		setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));	
		setBackground(Color.WHITE);
	}
	
	@Override
	protected void paintComponent( Graphics g ) {
		super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.drawImage(this.copyImage, 0, 0, this);
	}
	
	/**
	 * Get non-edited or non-transformed original image.
	 * 
	 * @return img original image
	 * 
	 * */
	public BufferedImage getOriginalImage() {
		return this.originalImage;
	}
	
	/**
	 * Get edited or transformed image.
	 * 
	 * @return img copied image from original
	 * */
	public BufferedImage getCurrentImage() {
		return this.copyImage;
	}
	
	@Override
	public Dimension getPreferredSize() {
		return new Dimension(originalImage.getWidth(), originalImage.getHeight());
	}
	
	/** 
	 * Sets the new image to {@code this ImagePanel}.
	 * 
	 * @param img	{@code BufferedImage} object.
	 * */
	public void setImage(BufferedImage imgg) {
		this.originalImage = imgg;
		this.copyImage = Utils.getImageCopy(imgg);
	}
}