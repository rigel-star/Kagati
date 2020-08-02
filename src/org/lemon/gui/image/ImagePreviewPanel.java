package org.lemon.gui.image;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.lemon.filters.ResizeImage;
import org.lemon.utils.Utils;


/**
 * Preview the image after applying filter.
 * */

public class ImagePreviewPanel extends JPanel {
	private static final long serialVersionUID = 1L;

	
	private JLabel imgContainer = new JLabel();
	
	/*have to keep original image untouched through the specific process*/
	private BufferedImage original = null;
	
	/*
	 * Effects will be applied to copy image until OK button clicked and when OK button clicked
	 * the original image will be changed to copy.
	 * */
	private BufferedImage copy = null;
	
	/*original size of image*/
	private int HEIGHT;
	private int WIDTH;
	
	
	public ImagePreviewPanel() {}
	
	
	public ImagePreviewPanel(final BufferedImage drawable){
		/*if width and height are not given, then resize image to size of 450 X 450*/
		this(drawable, 500, 500);
	}
	
	
	/**
	 * Image will be resized to given size and will be displayed.
	 * */
	public ImagePreviewPanel(BufferedImage drawable, int width, int height) {
		
		original = drawable;
		copy = Utils.getImageCopy(original);
		
		copy = resize(copy, width, height);
		
		this.imgContainer = new JLabel(new ImageIcon(copy));
		
		final int w = drawable.getWidth();
		final int h = drawable.getHeight();
		
		this.HEIGHT = h;
		this.WIDTH = w;
		
		setSize(w, h);
		setLayout(new BorderLayout());
		setBackground(Color.white);
		
		add(this.imgContainer, BorderLayout.CENTER);
	}
	
	
	/*
	 * Resize image to required size.
	 * */
	private BufferedImage resize(BufferedImage img, int w, int h) {
		
		if(img.getHeight() == h && img.getWidth() == w) {
			return img;
		}
		return new ResizeImage(img).getImageSizeOf(w, h);
	}
	
	
	
	/**
	 * Update the preview.
	 * */
	public void update() {
		this.imgContainer.revalidate();
	}
	
	
	
	/**
	 * Update with new image.
	 * */
	public void update(BufferedImage nimg) {
		this.original = nimg;
		this.imgContainer.setIcon(new ImageIcon(nimg));
		this.imgContainer.revalidate();
	}
	
	
	/**
	 * @return img untouched original image
	 * */
	public BufferedImage getOriginalImage() {
		return original;
	}
	
	
	/**
	 * @return img modified image(copy of original)
	 * */
	public BufferedImage getCopyImage() {
		return copy;
	}

	
	/**
	 * @return img final rendered image
	 * */
	public BufferedImage getFinalImage() {
		return resize(copy, WIDTH, HEIGHT);
	}
	
	
	
}
