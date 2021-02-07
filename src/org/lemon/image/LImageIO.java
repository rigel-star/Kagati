package org.lemon.image;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public final class LImageIO {
	
	/**
	 * Read image file from given path.
	 * */
	public static LImage read( String path ) throws IOException {
		
		var img = ImageIO.read( new File( path ) );
		var limg = new LImage( img );
		
		return limg;
	}
	
	/** 
	 * Write image file to the given destination.
	 * 
	 * */
	public static boolean write( LImage img, String type, String path ) throws IOException {
		return ImageIO.write( img.getAsBufferedImage(), type, new File( path ) );
	}
}
