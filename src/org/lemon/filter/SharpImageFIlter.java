package org.lemon.filter;

public class SharpImageFIlter extends ConvolutionFilter {

	/**
     * A 3x3 convolution kernel for a simple sharpeing.
     */
    protected static float[] sharpMatrix = {
		0f, -1f, 0f,
		-1f, 5f, -1f,
		0f, -1f, 0f
	};
    
	
	public SharpImageFIlter() {
		super( sharpMatrix );
	}
	
}
