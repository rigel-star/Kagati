package org.lemon.filter;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import org.lemon.image.ImageGraphics;
import org.lemon.image.LImage;

/**
 * 
 * Resize image to the size of desired bounds.
 * 
 * */
public class ResizeImageFilter extends AbstractImageFilter {
	
	private int w = 0, h = 0;
	
	/**
	 * 
	 * Constructs {@code ResizeImageFilter} with 
	 * default width and height value of 0.
	 * 
	 * */
	public ResizeImageFilter() {
		
	}
	
	
	/**
	 * 
	 * Constructs {@code ResizeImageFilter} with new height
	 * and width.
	 * 
	 * @param newW 		new width
	 * @param newH 		new height
	 * 
	 * */
	public ResizeImageFilter( int newW, int newH ) {
		this.w = newW;
		this.h = newH;
	}
	
	
	@Override
	public LImage filter( LImage limage ) {
		
		if( w == 0)
			w = limage.width;
		if( h == 0)
			h = limage.height;
		
		BufferedImage src = limage.getAsBufferedImage();
		LImage out = ImageGraphics.createImage( w, h, LImage.DEFAULT );
		
		Graphics2D g2 = out.getAsBufferedImage().createGraphics();
		g2.drawImage( src, 0, 0, w, h, null );
		
		return out;
	}
	
	
	/**
	 * 
	 * Set new width to resize image to.
	 * @param w 	new width
	 * 
	 * */
	public void setNewWidth( int w ) {
		this.w = w;
	}
	
	
	/**
	 * 
	 * Set new height to resize image to.
	 * @param h 	new height
	 * 
	 * */
	public void setNewHeight( int h ) {
		this.h = h;
	}

}
