package org.lemon.filters;

import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;

import org.lemon.image.LImage;
import org.lemon.image.Pixel;

public class GaussianBlurImageFilter extends AbstractImageFilter {

	/* max iteration */
	private int iter = 0;
	
	/**
	 * 
	 * Blur image with Gaussian image blurrig algorithm.
	 * 
	 * */
	public GaussianBlurImageFilter() {
		iter = 1;
	}
	
	
	/**
	 * 
	 * Blur image with Gaussian image blurrig algorithm.
	 * @param iteration how many times to blur the individual pixel
	 * 
	 * */
	public GaussianBlurImageFilter( final int iteration ) {
		this.iter = iteration;
	}
	
	
	@Override
	public void filter( LImage limage ) {
		
		BufferedImage img = limage.getAsBufferedImage();
		WritableRaster wdata = img.getRaster();
		
		final int width = img.getWidth();
		final int height = img.getHeight();
			
		for( int x = 1; x < width - 1; x++ ) {
			for( int y = 1; y < height - 1; y++ ) {
				
				for( int ii = 0; ii < iter; ii++ ) {
					
					int[] neighs = Pixel.getNeighbors( wdata, x, y, img.getAlphaRaster() != null );
			        
			        int argb = process( Pixel.average( neighs ) );
			        
			        int[] rgb = Pixel.getRGB( argb );
			        
			        wdata.setPixel(x, y, rgb);
			        
			        //img.setRGB( x, y, argb );
				}
			}
		}
		
		img.setData( wdata );
		
	}
	
	
	@Override
	public int process( int rgb ) {
		return rgb;
	}
	
}
