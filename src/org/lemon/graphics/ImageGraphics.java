package org.lemon.graphics;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;

import org.lemon.image.LImage;

public abstract class ImageGraphics {
	
	/**
	 * Draw temporarily on image with {@code this draw} method.
	 * On {@code clear} method call, all the previously drawn 
	 * shapes and images will be cleared. That is why temporarily 
	 * is mentioned.
	 * @param shape		Shape to draw
	 * */
	public abstract void draw( Shape shape );
	
	
	/**
	 * Disposes of this images drawing capability.
	 * {@code draw} method can't be used after {@code dispose}
	 * has been called.
	 * */
	public abstract void dispose();
	
	
	/**
	 * Clears all the shapes and other data added or drawn
	 * later on image. 
	 * */
	public abstract void clear();
	
	
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
	
	
	/**
	 * Create new image like the given image.
	 * 
	 * @param lsrc 		Image to copy properties of.
	 * @param cm 		Color model for the image.
	 * @return 			LImage with the same properties as source image.
	 * 
	 * */
	public static LImage createCompitableDestImage( LImage lsrc, ColorModel cm ) {
		BufferedImage src = lsrc.getAsBufferedImage();
		if( cm == null )
            cm = src.getColorModel();
        return new LImage( new BufferedImage(cm, cm.createCompatibleWritableRaster(src.getWidth(), src.getHeight()),
        							cm.isAlphaPremultiplied(), null));
	}
}
