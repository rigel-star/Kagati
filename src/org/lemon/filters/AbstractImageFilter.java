package org.lemon.filters;

import org.lemon.image.LImage;

public abstract class AbstractImageFilter {
	
	public abstract void filter( LImage limage );
	
	public abstract int process( int pixel );
	
	
	public int constrain( int pix, int min, int max ) {
		
		if( pix > max )
			pix = max;
		else if( pix < min )
			pix = min;
		
		return pix;
	}
}
