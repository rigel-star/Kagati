package org.lemon.image;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public final class LImageIO {

	
	public static LImage read( String path ) throws IOException {
		
		var img = ImageIO.read( new File( path ) );
		var limg = new LImage( img );
		
		return limg;
	}
	
	
	public static boolean write( LImage img, String type, String path ) throws IOException {
		return ImageIO.write( img.getAsBufferedImage(), type, new File( path ) );
	}
	
}
