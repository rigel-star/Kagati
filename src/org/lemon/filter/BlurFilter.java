package org.lemon.filter;

import org.lemon.image.LImage;

public interface BlurFilter {
	
	/**
	 * Blur an image.
	 * 
	 * @param src Image to blur.
	 * @return Blurred image.
	 * */
	public LImage blur( LImage src );
	
}
