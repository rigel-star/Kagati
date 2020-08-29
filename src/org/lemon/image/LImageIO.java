package org.lemon.image;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public final class LImageIO {

	
	public static LImage read(String path) throws IOException {
		
		var img = ImageIO.read(new File(path));
		var raster = img.getRaster();
		
		var limg = new LImage(raster, raster.getWidth(), raster.getHeight(), img.getAlphaRaster() != null, LImage.DEFAULT);
		
		return limg;
	}
	
	
	public static boolean write(LImage img, String type, String path) throws IOException {
		return ImageIO.write(img.getAsBufferedImage(), type, new File(path));
	}
	
}
