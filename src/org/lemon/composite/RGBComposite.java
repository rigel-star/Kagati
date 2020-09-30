package org.lemon.composite;

import java.awt.Composite;
import java.awt.CompositeContext;
import java.awt.image.Raster;
import java.awt.image.WritableRaster;

public abstract class RGBComposite implements Composite {
	
	protected float extraAlpha;
	
	public RGBComposite() {
		this( 1.0f );
	}
	
	
	public RGBComposite( float alpha ) {
		if ( alpha < 0.0f || alpha > 1.0f )
			throw new IllegalArgumentException( "RGBComposite: alpha must be between 0 and 1" );
		this.extraAlpha = alpha;
	}
	
	
	/**
	 * 
	 * @return Alpha value.
	 * 
	 * */
	public float getAlpha() {
		return extraAlpha;
	}
	
	
	static abstract class RGBCompositeContext implements CompositeContext {
		
		private float alpha;

        public RGBCompositeContext( float alpha ) {
            this.alpha = alpha;
        }
        
		
		@Override
		public void dispose() {
			
		}
		
		
		@Override
		public void compose( Raster src, Raster dstIn, WritableRaster dstOut ) {
			float alpha = this.alpha;

            int[] srcPix = null;
            int[] dstPix = null;

            int x = dstOut.getMinX();
            int w = dstOut.getWidth();
            int y0 = dstOut.getMinY();
            int y1 = y0 + dstOut.getHeight();

            for ( int y = y0; y < y1; y++ ) {
                srcPix = src.getPixels( x, y, w, 1, srcPix );
                dstPix = dstIn.getPixels( x, y, w, 1, dstPix );
                composeRGB( srcPix, dstPix, alpha );
                dstOut.setPixels( x, y, w, 1, dstPix );
            }
		}
		
		
		/**
		 * 
		 * Compose RGB.
		 * @param src
		 * @param dst
		 * @param alpha
		 * 
		 * */
		public abstract void composeRGB( int[] src, int[] dst, float alpha );
	}
}
