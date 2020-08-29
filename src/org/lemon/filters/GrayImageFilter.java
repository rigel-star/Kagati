package org.lemon.filters;

import java.awt.image.DataBufferInt;
import java.awt.image.Raster;

import org.lemon.image.LImage;

public class GrayImageFilter extends AbstractImageFilter {

	
	@Override
	public void filter( LImage limage ) {
		
		byte[] data = limage.getPixels();
		
		int pixelLength = 3;
		
		if( limage.hasAlphaChannel() )
			pixelLength = 4;
		
		int[] fdata = new int[data.length / pixelLength];
		
		final int len = fdata.length;
		
		for( int ind = 0; ind < len - pixelLength; ind += pixelLength ) {
			
			int argb = 0;
			
			if( !limage.hasAlphaChannel() )
				argb += ( data[ind] + data[ind + 1] + data[ind + 2] );
			else
				argb = ( data[ind + 1] + data[ind + 2] + data[ind + 3]);
			
			fdata[ind] = process( argb );
		}
		
		DataBufferInt bint = new DataBufferInt( fdata, len );
		Raster ras = Raster.createRaster( null, bint, null );
		
		limage.getAsBufferedImage().setData( ras );
	}
	
	
	@Override
	public int process( int pixel ) {
		
		int alpha = ( pixel) & 0xff;
		int red = ( pixel) & 0xff;
		int green = ( pixel) & 0xff;
		int blue = ( pixel ) & 0xff;
		
		final int lum = ( int )( 0.2126 * red + 0.7152 * green + 0.0722 * blue );
		 
        alpha = ( alpha << 24 );
        red = ( lum << 16 );
        green = ( lum << 8 );
        blue = lum;
		
		return ( alpha + red + green + blue );
	}

}
