package org.lemon.image;

import java.awt.Shape;
import java.awt.image.BufferedImage;
import java.awt.image.DataBuffer;
import java.awt.image.Raster;

import org.lemon.filter.GrayImageFilter;

public class LImage extends ImageGraphics {

	private BufferedImage asBufferedImg = null;
	private Raster raster = null;
	private DataBuffer data = null;
	
	private boolean disposed = false;
	
	public int width = 0;
	public int height = 0;
	public int type = BufferedImage.TYPE_INT_ARGB;
	
	public final static int GRAY = 00;
	public final static int DEFAULT = 11;
	
	
	public LImage( int w, int h, int type ) {
		this( null, w, h, type );
	}
	
	
	public LImage( int w, int h, boolean alpha, int type ) {
		this( null, w, h, type );
	}
	
	
	public LImage( BufferedImage img ) {
		asBufferedImg = img;
		width = img.getWidth();
		height = img.getHeight();
		type = DEFAULT;
		raster = img.getData();
		initImageType( type );
	}
	
	
	public LImage(Raster raster, int w, int h, int type) {
		
		width = w;
		height = h;
		this.type = type;
		
		asBufferedImg = new BufferedImage( w, h, BufferedImage.TYPE_INT_ARGB );
		asBufferedImg.setData( raster );
		this.raster = raster;
		data = raster.getDataBuffer();
		
		initImageType( type );
	}
	
	
	private void initImageType( int type ) {
		switch( type ) {
		
		case GRAY: {
			GrayImageFilter gray = new GrayImageFilter();
			gray.filter( this );
		}
		break;
		
		default: {
			
		}
		break;
		
		}
	}
	
	
	@Override
	public void draw(Shape shape) {
		
		if(disposed) 
			return;
		
		
	}
	
	
	@Override
	public void dispose() {
		disposed = true;
	}
	
	
	/**
	* 
	* Set the pixels to this image.
	* @param x       the left edge of the pixel block
    * @param y       the right edge of the pixel block
    * @param width   the width of the pixel arry
    * @param height  the height of the pixel arry
    * @param pixels  the array of pixels to set
	*
	*/
	public void setPixels( int x, int y, int width, int height, int[] pixels ) {
		BufferedImage image = getAsBufferedImage();
		int type = image.getType();
		
		if ( type == BufferedImage.TYPE_INT_ARGB || type == BufferedImage.TYPE_INT_RGB )
			image.getRaster().setDataElements( x, y, width, height, pixels );
		else
			image.setRGB( x, y, width, height, pixels, 0, width );	
	}
	
	
	/**
	 * 
	 * Get the all pixels of this image.
     * @param x       the left edge of the pixel block
     * @param y       the right edge of the pixel block
     * @param width   the width of the pixel arry
     * @param height  the height of the pixel arry
     * @param pixels  the array to hold the returned pixels. May be null.
     * @return the pixels
     * @see #setRGB
     * 
     */
	public int[] getPixels( int x, int y, int width, int height, int[] pixels ) {
		BufferedImage image = getAsBufferedImage();
		int type = image.getType();
		
		if ( type == BufferedImage.TYPE_INT_ARGB || type == BufferedImage.TYPE_INT_RGB )
			return (int [])image.getRaster().getDataElements( x, y, width, height, pixels );
		return image.getRGB( x, y, width, height, pixels, 0, width );
    }
	
	
	/**
	 * 
	 * Get {@code DataBuffer} attached with this image.
	 * @return data the {@code DataBuffer}
	 * 
	 * */
	public DataBuffer getDataBuffer() {
		return data;
	}
	
	
	/**
	 * 
	 * Get {@code Raster} attached with this image.
	 * @return data the {@code Raster}
	 * 
	 * */
	public Raster getRaster() {
		return raster;
	}
	
	
	/**
	 * 
	 * Get {@code this LImage} as {@code BufferedImage}.
	 * @return img the {@code BufferedImage} version of this {@code LImage}
	 * 
	 * */
	public BufferedImage getAsBufferedImage() {
		return asBufferedImg;
	}

}
