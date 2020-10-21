package org.lemon.filter;

public class GrayImageFilter extends SinglePixelFilter {
	
	@Override
	public int processRGB( int rgb ) {
        
		int a = (int) ( rgb & 0xFF000000);
        	int b = (int) ( rgb & 0xFF);
        	int g = (int) ( ( rgb >> 8 ) & 0xFF);
        	int r = (int) ( ( rgb >> 16 ) & 0xFF);
        
        	final int lum = (int) ( b * 0.0722 + g * 0.2126 + r * 0.2126 );
		
        	a = (rgb << 24 );
        	b = ( lum );
        	g = ( lum << 8 );
        	r = ( lum << 16 );
        
		return ( r + g + b + a );
	}
}
