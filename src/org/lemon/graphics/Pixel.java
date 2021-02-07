package org.lemon.graphics;

import java.awt.image.Raster;

/**
 * 
 * Represents single pixel. {@link Pixel} gives 
 * red, green and blue value of pixel seperately.
 * 
 * */
public class Pixel {
	
	private int value = 0;
	
	public Pixel() {}
	
	public Pixel( int argb ) {
		value = argb;
	}
	
	public int getRGB() {
		return value;
	}
	
	public int getAlpha() {
		return ( value >> 24 ) & 0xFF;
	}
	
	public int getRed() {
		return extractRGB( value )[0];
	}
	
	public int getGreen() {
		return extractRGB( value )[1];
	}
	
	public int getBlue() {
		return extractRGB( value )[2];
	}
	
	/**
	 * Get the 8 neighbor pixels of the particularly specified pixel coordinate.
	 * 
	 * @param img 	The source image.
	 * @param x 	x-coordinate
	 * @param y 	y-coordinate
	 * @return The neighbor pixels. The length of the array will be 9 
	 * 			including the source pixel itself.
	 * */
	public static int[] getNeighbors( Raster data, int x, int y, boolean hasAlpha ) {
		
		int[] neighs = new int[9];
		
		int index = 0;
		for( int xx = x - 1; xx <= x + 1; xx++ ) {
			for( int yy = y - 1; yy <= y + 1; yy++ ) {
				
				int[] rgb = new int[3];
				data.getPixel( xx, yy, rgb );
				
				int a = 0xFF;
				int r = rgb[0];
				int g = rgb[1];
				int b = rgb[2];
				
				if( hasAlpha )
					a = rgb[3];
				
				int frgb =  ( (a & 0xFF) << 24 ) |
							( (r & 0xFF) << 16 ) |
		                	( (g & 0xFF) << 8 )  |
		                	( (b & 0xFF) << 0 );
				
				neighs[index] = frgb;
				
				index += 1;
			}	
		}
		return neighs;
	}
	
	/**
	 * Average the block of pixels.
	 * 
	 * @param pixels the block of pixels to average.
	 * */
	public static int average( int[] pixels ) {
		
		int len = pixels.length;
		int a = 0;
		int r = 0;
		int g = 0;
		int b = 0;
		
		for( int argb: pixels ) {
			a += ( argb >> 24 ) & 0xFF;
			
			int[] rgb = extractRGB( argb );
			
			r += rgb[0];
			g += rgb[1];
			b += rgb[2];
		}
		
		a /= len;
		r /= len;
		g /= len;
		b /= len;
		
		int avrg = ( a << 24 ) | ( r << 16 ) | ( g << 8 ) | ( b );
		
		return avrg;
	}
	
	/**
	 * Get RGB values extracted seperately.
	 * 
	 * @param rgb the rgb value to extract.
	 * @return The {@code Integer} array of seperated RGB values
	 * */
	public static int[] extractRGB( int rgb ) {
		return new int[] {
				( (rgb >> 16) & 0xFF ), //red
				( (rgb >> 8) & 0xFF ),  //green
				( (rgb >> 0) & 0xFF ),  //blue
		};
	}
	
	/**
	 * Multiply two values in the range of 0 - 255.
	 * 
	 * @param val1 		First value.
	 * @param val2 		Second Value.
	 * @return 			Constrained value.
	 * */
	public static int multiply255( int val1, int val2 ) {
		int t = val1 * val2 + 0x80;
        return ((t >> 8) + t) >> 8;
	}
}
