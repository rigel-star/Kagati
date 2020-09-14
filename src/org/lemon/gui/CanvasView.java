package org.lemon.gui;

import java.awt.Color;
import java.awt.image.BufferedImage;

import org.lemon.gui.image.PanelMode;
import org.lemon.image.LImage;

/**
 * 
 * Sub class of {@code ImageView} which holds blank image for painting.
 * 
 * */
public class CanvasView extends ImageView {

	/**
	 * Serial UID
	 */
	private static final long serialVersionUID = 1L;
	
	
	/**
	 * 
	 * Construct a {@code Canvas} to draw on with specified bounds and properties.
	 * 
	 * @param width 	width for the canvas
	 * @param height	height for the canvas
	 * @param bg		background color for canvas 
	 * @param title		title of the canvas
	 * @param closeable	can be closed or not
	 * @param panelMode	panel mode for canvas
	 * 
	 * */
	public CanvasView( int width, int height, Color bg, String title, boolean closeable, PanelMode panelMode ) {
		super( LImage.createImage( width, height, bg, BufferedImage.TYPE_INT_ARGB ).getAsBufferedImage(), 
				title, closeable, panelMode);
	}

}
