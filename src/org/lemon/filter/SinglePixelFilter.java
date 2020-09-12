package org.lemon.filters;

import org.lemon.image.LImage;

public abstract class SingleRGBFilter extends AbstractImageFilter {

	
	@Override
	public void filter( LImage limage ) {
		
		final int width = limage.width;
        final int height = limage.height;
		
		int[] data = new int[width * height];
		limage.getPixels( 0, 0, width, height, data );
		
		int[] out = new int[width * height];
		
		for( int ii = 0; ii < data.length; ii++ ) 
			out[ii] = processRGB( data[ii] );
		
		limage.setPixels( 0, 0, width, height, out );
		
	}
	
	
	/**
	 * 
	 * Process the single RGB value obtained from image.
	 * @param rgb the RGB value to process
	 * @return rgb the processed RGB
	 * 
	 * */
	public abstract int processRGB( int rgb );
	
}