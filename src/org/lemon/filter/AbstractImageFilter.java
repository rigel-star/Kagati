package org.lemon.filter;

import org.lemon.image.LImage;

public abstract class AbstractImageFilter {

	
	/**
	 * 
	 * Filter image.
	 * @param limage the image to filter.
	 * 
	 * */
	public abstract LImage filter( LImage limage );
	
	
	/**
	 * 
	 * Constrain the given pixel value between given min and max value.
	 * @param pix the value to constrain.
	 * @param min minimum constrain.
	 * @param max maximum constrain.
	 * 
	 * */
	public static int constrain( int pix, int min, int max ) {
		return (pix < min) ? min : (pix > max) ? max : pix;
	}
}
