package org.lemon.filter;

import org.lemon.image.LImage;

public class GaussianBlurImageFilter extends ConvolutionFilter implements BlurFilter {
	
	/**
     * A 3x3 convolution kernel for a simple blur.
     */
    protected static float[] blurMatrix = {
		1/14f, 2/14f, 1/14f,
		2/14f, 2/14f, 2/14f,
		1/14f, 2/14f, 1/14f
	};
	
    
	public GaussianBlurImageFilter() {
		super(blurMatrix);
	}


	@Override
	public LImage blur(LImage src) {
		return filter(src);
	}
	
}