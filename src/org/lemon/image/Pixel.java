package org.lemon.image;

import java.awt.image.Raster;

public class Pixel {
	
	
	/**
	 * 
	 * Get the neighbors of the image of particularly specified pixel coordinate.
	 * @param img the source image.
	 * @param x x-coordinate
	 * @param y y-coordinate
	 * 
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
	 * 
	 * Average the pixels.
	 * @param pixels the array of pixels to average.
	 * */
	public static int average( int[] pixels ) {
		
		int len = pixels.length;
		int a = 0;
		int r = 0;
		int g = 0;
		int b = 0;
		
		for( int argb: pixels ) {
			a += ( argb >> 24 ) & 0xFF;
			
			int[] rgb = getRGB( argb );
			
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
	 * 
	 * Get RGB values extracted from single rgb integer.
	 * @param rgb the rgb value to extract.
	 * */
	public static int[] getRGB( int rgb ) {
		return new int[] {
				( (rgb >> 16) & 0xFF ),
				( (rgb >> 8) & 0xFF ),
				( (rgb >> 0) & 0xFF ),
		};
	}
	
}
