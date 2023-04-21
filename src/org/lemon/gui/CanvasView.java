package org.lemon.gui;

import java.awt.Color;
import java.awt.image.BufferedImage;

import org.lemon.image.LImage;

public class CanvasView extends ImageView {
	private static final long serialVersionUID = 1L;
	
	/**
	 * Construct a blank {@code Canvas} with specified bounds and properties.
	 * 
	 * @param width 	Width for the canvas.
	 * @param height	Height for the canvas.
	 * @param bg		Background color for canvas. 
	 * @param title		Title of the canvas.
	 * */
	public CanvasView(int width, int height, Color bg, String title)
	{
		super(LImage.createImage(width, height, bg, BufferedImage.TYPE_INT_ARGB).getAsBufferedImage(), title);
	}
}
