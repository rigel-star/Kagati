package org.lemon.image;

public class ImageMath {

	
	/**
	 * 
	 * Premultiply alpha with image data.
	 * 
	 * @param data image data to premultiply alpha with
	 * 
	 * */
	public static void premultiplyAlpha( int[] data ) {
		
		for( int ii = 0; ii < data.length; ii++ ) {
			
			int a = ( data[ii] >> 24 ) & 0xFF;
			int r = ( data[ii] >> 16 ) & 0xFF;
			int g = ( data[ii] >> 8 ) & 0xFF;
			int b = ( data[ii] ) & 0xFF;
			
			float f = a * ( 1 / 255 );
			
			if( f != 0 ) {
				r *= f;
				g *= f;
				b *= f;
				data[ii] = (a << 24) | (r << 16) | (g << 8) | b;
			}
		}
		
	}
	
	
	/**
	 * 
	 * Unpremultiply alpha from given image data.
	 * 
	 * @param data image data to unpremultiply alpha from
	 * 
	 * */
	public static void unpremultiplyAlpha( int[] data ) {
		
		for ( int i = 0; i < data.length; i ++ ) {
        
            int a = ( data[i] >> 24 ) & 0xff;
            int r = ( data[i] >> 16 ) & 0xff;
            int g = ( data[i] >> 8 ) & 0xff;
            int b = ( data[i] ) & 0xff;
            
            if ( a != 0 && a != 255 ) {
                float f = 255.0f / a;
                
                r *= f;
                g *= f;
                b *= f;
                if ( r > 255 )
                    r = 255;
                if ( g > 255 )
                    g = 255;
                if ( b > 255 )
                    b = 255;
                
                data[i] = (a << 24) | (r << 16) | (g << 8) | b;
            }
        }
    }
	
}
