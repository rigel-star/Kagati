package org.lemon.filters;

public class GrayImageFilter extends AbstractImageFilter {
	
	
	@Override
	public int process( int rgb ) {
        
		int a = (int) ( ( rgb >> 24 ) & 0xff);
        int b = (int) ( rgb & 0xff);
        int g = (int) ( ( rgb >> 8 ) & 0xff);
        int r = (int) ( ( rgb >> 16 ) & 0xff);
        
        final int lum = (int) ( b * 0.0722 + g * 0.2126 + r * 0.2126 );
		
        a = (rgb << 24 );
        b = ( lum );
        g = ( lum << 8 );
        r = ( lum << 16 );
        
		return ( r + g + b + a );
	}

}
