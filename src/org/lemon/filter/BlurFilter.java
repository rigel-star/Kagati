package org.lemon.filter;

import org.lemon.image.LImage;

public interface BlurFilter {
	
	
	/**
	 * 
	 * Blur an image.
	 * @param src image to blur
	 * @return blurred image
	 * 
	 * */
	public LImage blur( LImage src );
	
}
