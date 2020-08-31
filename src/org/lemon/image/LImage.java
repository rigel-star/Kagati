package org.lemon.image;

import java.awt.Shape;
import java.awt.image.BufferedImage;
import java.awt.image.DataBuffer;
import java.awt.image.DataBufferByte;
import java.awt.image.Raster;

import org.lemon.filters.GrayImageFilter;

public class LImage extends ImageGraphics {

	private BufferedImage asBufferedImg = null;
	private Raster raster = null;
	private DataBuffer data = null;
	private byte[] actData;
	
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
		actData = new byte[w * h];
		this.raster = raster;
		data = raster.getDataBuffer();
		
		if( data instanceof DataBufferByte ) {
			actData = (( DataBufferByte ) data ).getData();
		}
		
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
		
		if(!disposed) {
			
		}
	}
	
	
	@Override
	public void dispose() {
		disposed = true;
	}
	
	
	public void setPixels( byte[] pixels ) {
		this.actData = pixels;
	}
	
	
	public byte[] getPixels() {
		return actData;
	}
	
	
	public DataBuffer getDataBuffer() {
		return data;
	}
	
	
	public Raster getRaster() {
		return raster;
	}
	
	
	public BufferedImage getAsBufferedImage() {
		return asBufferedImg;
	}

}
