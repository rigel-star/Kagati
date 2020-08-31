package org.lemon.filters;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;

import org.lemon.image.LImage;

public abstract class AbstractImageFilter {

	
	/**
	 * 
	 * Filter image.
	 * @param limage the image to filter.
	 * 
	 * */
	public void filter( LImage limage ) {

		BufferedImage img = limage.getAsBufferedImage();
		
		final byte[] pixels = (( DataBufferByte ) img.getRaster().getDataBuffer() ).getData();
		final int width = img.getWidth();
		final boolean hasAlphaChannel = img.getAlphaRaster() != null;
		
		int pixelLength = 3;
		
		if( hasAlphaChannel ) {
			
			pixelLength = 4;
			
		    for ( int pixel = 0, x = 0, y = 0; pixel < pixels.length; pixel += pixelLength ) {
		    	
		        int argb = 0;
		        argb += ( ((int) pixels[pixel] & 0xff) << 24 ); // alpha
		        argb += ( (int) pixels[pixel + 1] & 0xff ); // blue
		        argb += ( ((int) pixels[pixel + 2] & 0xff) << 8 ); // green
		        argb += ( ((int) pixels[pixel + 3] & 0xff) << 16 ); // red
		        
		        argb = process( argb );
		        img.setRGB( x, y, argb );
		        
		        x++;
		        
		        if ( x == width ) {
		           x = 0;
		           y++;
		        }
		     }
		     
		} else {
		     
		     for ( int pixel = 0, x = 0, y = 0; pixel < pixels.length; pixel += pixelLength ) {
		    	 
		        int argb = 0;
		        argb += -16777216; // 255 alpha
		        argb += ( (int) pixels[pixel] & 0xff ); // blue
		        argb += ( ((int) pixels[pixel + 1] & 0xff) << 8 ); // green
		        argb += ( ((int) pixels[pixel + 2] & 0xff) << 16 ); // red
		        
		        argb = process( argb );
		        img.setRGB( x, y, argb );
		        
		        x++;
		        
		        if ( x == width ) {
		           x = 0;
		           y++;
		        }
		     }
		  }
	}
	

	/**
	 * 
	 * Process the individual pixel.
	 * @param pixel the pixel to process
	 * 
	 * */
	public abstract int process( int rgb );

	
	/**
	 * 
	 * Constrain the given pixel value between given min and max value.
	 * 
	 * @param pix the value to constrain.
	 * @param min minimum constrain.
	 * @param max maximum constrain.
	 * 
	 * */
	public int constrain( int pix, int min, int max ) {

		if ( pix > max )
			pix = max;
		else if ( pix < min )
			pix = min;

		return pix;
	}
}
