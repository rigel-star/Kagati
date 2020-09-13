package org.lemon.image;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

public abstract class ImageGraphics {
	
	public abstract void draw( Shape shape );
	
	public abstract void dispose();

	
	/**
	 * 
	 * Create blank image with the specified bounds and properties.
	 * 
	 * @param width 	width for the image
	 * @param height 	height for the image
	 * @param type 		type for the image
	 * @return 			{@code LImage} with specified bounds and properties
	 * 
	 * */
	public static LImage createImage( int width, int height, int type ) {
		return new LImage( width, height, type );
	}
	
	
	/**
	 * 
	 * Create blank image with the specified bounds and properties.
	 * 
	 * @param width 	width for the image
	 * @param height 	height for the image
	 * @param type 		type for the image
	 * @param bg 		background {@code Color} for the image
	 * @return			{@code LImage} with specified bounds and properties
	 * 
	 * */
	public static LImage createImage( int width, int height, Color bg, int type ) {
		BufferedImage img = createImage( width, height, type ).getAsBufferedImage();
		Graphics2D g2 = img.createGraphics();
		
		bg = bg == null ? Color.white : bg;
		g2.setPaint( bg );
		
		Rectangle2D.Double rect = new Rectangle2D.Double( 0, 0, width, height );
		g2.fill( rect );
		
		return new LImage( img );
	}
}
