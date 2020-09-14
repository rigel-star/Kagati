package org.lemon.filter;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import org.lemon.image.ImageGraphics;
import org.lemon.image.LImage;

public class ResizeImageFilter extends AbstractImageFilter {
	
	
	private int w, h;
	
	/**
	 * 
	 * Resize image to the size of desired bounds.
	 * 
	 * @param newW new width for the image
	 * @param newH new height for the image
	 * 
	 * */
	public ResizeImageFilter( int newW, int newH ) {
		this.w = newW;
		this.h = newH;
	}
	
	
	@Override
	public LImage filter( LImage limage ) {
		
		BufferedImage src = limage.getAsBufferedImage();
		LImage out = ImageGraphics.createImage( w, h, src.getType() );
		
		Graphics2D g2 = out.getAsBufferedImage().createGraphics();
		g2.drawImage( src, 0, 0, null );
		
		return out;
	}

}
