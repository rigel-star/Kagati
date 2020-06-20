package org.lemon.utils;

import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;

public class Utils {

	
	public static BufferedImage getImageCopy(BufferedImage sr) {
		ColorModel cm = sr.getColorModel();
		boolean iap = sr.isAlphaPremultiplied();
		WritableRaster raster = sr.copyData(sr.getRaster().createCompatibleWritableRaster());
		return new BufferedImage(cm, raster, iap, null);
	}
	
}
