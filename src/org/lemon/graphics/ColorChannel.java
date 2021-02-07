package org.lemon.graphics;

import org.lemon.image.LImage;
import org.lemon.lang.NonNull;

public class ColorChannel {
	
	private static LImage img = null;
	
	private static Channel redC = null;
	private static LImage red = null;
	
	private static Channel greenC = null;
	private static LImage green = null;
	
	private static Channel blueC = null;
	private static LImage blue = null;
	
	/**
	 * Create different image for each channel, i.e. RGB.
	 * @param li 		{@link LImage} to extract channel from.
	 * */
	public ColorChannel( @NonNull LImage li ) {
		img = li;
		
		LImage[] imgs = extractChannels( img );
		red = imgs[0];
		green = imgs[1];
		blue = imgs[2];
	}
	
	/**
	 * Extract channels of image.
	 * 
	 * @return Array of image with RGB channels respectively.
	 * */
	private LImage[] extractChannels( LImage img ) {
		return extractChannels( img, new LImage[3]);
	}
	
	/**
	 * Extract channels of image and stores in specified array 
	 * of {@link LImage}.
	 * 
	 * @return Array of image with RGB channels respectively.
	 * */
	private LImage[] extractChannels( LImage img, LImage[] arr ) {
		
		final int W = img.width;
		final int H = img.height;
		
		LImage rimg = new LImage( W, H, LImage.DEFAULT );
		LImage gimg = new LImage( W, H, LImage.DEFAULT );
		LImage bimg = new LImage( W, H, LImage.DEFAULT );
		
		for( int x = 0; x < img.width; x++ ) {
			for ( int y = 0; y < img.height; y++ ) {
				int pix = img.getPixel( x, y );
				int rgb[] = Pixel.extractRGB( pix );
				
				int rPixData = rgb[0] << 16 | 0 << 8 | 0;
				rimg.setPixel( x, y, rPixData );
				
				int gPixData = rgb[1] << 16 | 0 << 8 | 0;
				gimg.setPixel( x, y, gPixData );
				
				int bPixData = rgb[2] << 16 | 0 << 8 | 0;
				bimg.setPixel( x, y, bPixData );
			}
		}
		
		arr[0] = rimg;
		arr[1] = gimg;
		arr[2] = bimg;
		
		return arr;
	}
	
	/**
	 * @return Red channel of image.
	 * */
	public RedChannel getRedChannel() {
		return (RedChannel) redC; 
	}
	
	/**
	 * @return Green channel of image.
	 * */
	public GreenChannel getGreenChannel() {
		return (GreenChannel) greenC; 
	}
	
	/**
	 * @return Blue channel of image.
	 * */
	public BlueChannel getBlueChannel() {
		return (BlueChannel) blueC; 
	}
	
	public interface Channel {
		
		/**
		 * Get image of this specific channel.
		 * 
		 * @return Channel image.
		 * */
		LImage getImage();
	}
	
	public class RedChannel implements Channel {
		
		@Override
		public LImage getImage() {
			return red;
		}
	}
	
	public class GreenChannel implements Channel {
		
		@Override
		public LImage getImage() {
			return green;
		}
	}
	
	public class BlueChannel implements Channel {
		
		@Override
		public LImage getImage() {
			return blue;
		}
	}
}
