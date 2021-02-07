package org.lemon.gui;

import java.awt.Color;
import java.awt.image.BufferedImage;

import org.lemon.gui.image.ImagePanel;
import org.lemon.image.LImage;

/**
 * Sub class of {@code ImageView} which holds blank image for painting.
 * 
 * */
public class CanvasView extends ImageView {

	/**
	 * Serial UID
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Construct a blank {@code Canvas} with specified bounds and properties.
	 * 
	 * @param width 	Width for the canvas.
	 * @param height	Height for the canvas.
	 * @param bg		Background color for canvas. 
	 * @param title		Title of the canvas.
	 * @param closeable	Can be closed or not.
	 * @param panelMode	Panel mode for canvas.
	 * */
	public CanvasView( int width, int height, Color bg, String title, 
			boolean closeable, ImagePanel.PanelMode panelMode, LayerContainer lycont ) {
		super( LImage.createImage( width, height, bg, BufferedImage.TYPE_INT_ARGB ).getAsBufferedImage(), 
				title, closeable, panelMode, lycont );
	}
}
