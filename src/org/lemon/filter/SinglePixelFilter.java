package org.lemon.filter;

import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;

import org.lemon.image.LImage;

public abstract class SinglePixelFilter extends AbstractImageFilter {
	
	@Override
	public LImage filter( LImage limage ) {
		
		final int width = limage.width;
        final int height = limage.height;
        
		BufferedImage asBuff = limage.getAsBufferedImage();
        WritableRaster srcRaster = limage.getAsBufferedImage().getRaster();
        
        BufferedImage outImg = new BufferedImage( width, height, asBuff.getType() );
        WritableRaster dstRaster = outImg.getRaster();
		
		int[] inPixels = new int[ width ];
		
		for( int y = 0; y < height; ++y ) {
			if( asBuff.getType() == BufferedImage.TYPE_INT_ARGB ) {
				srcRaster.getDataElements( 0, y, width, 1, inPixels );
				
				for( int x = 0; x < width; ++x ) 
					inPixels[x] = processRGB(x, y, inPixels[x] );
				
				dstRaster.setDataElements( 0, y, width, 1, inPixels );
			}
			else {
				asBuff.getRGB( 0, y, width, 1, inPixels, 0, width );
				for ( int x = 0; x < width; x++ )
					inPixels[x] = processRGB( x, y, inPixels[x] );
				outImg.setRGB( 0, y, width, 1, inPixels, 0, width );
			}
		}
		return new LImage( outImg );
	}
	
	/**
	 * Process RGB value obtained from specific X & Y 
	 * coordinate of image.
	 * 
	 * @param x 		X-coord of pixel.
	 * @param y 		Y-coord of pixel.
	 * @param rgb 		RGB value to process.
	 * @return 			Processed RGB value.
	 * */
	public abstract int processRGB(int x, int y, int rgb);
}
