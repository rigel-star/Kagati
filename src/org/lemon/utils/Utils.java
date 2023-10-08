package org.lemon.utils;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;

import javax.swing.ImageIcon;

import org.lemon.image.LImage;

public class Utils {
	public final static int STD_KAGATI_TOOL_ICON_WIDTH = 24;
	public final static int STD_KAGATI_TOOL_ICON_HEIGHT = 24;
	
	/**
	 * Get copy of an image with same bounds and data.
	 * 
	 * @param sr		image to copy
	 * @return Copied image.
	 * */
	public static BufferedImage getImageCopy(BufferedImage sr) {
		ColorModel cm = sr.getColorModel();
		boolean iap = sr.isAlphaPremultiplied();
		WritableRaster raster = sr.copyData( sr.getRaster().createCompatibleWritableRaster() );
		return new BufferedImage(cm, raster, iap, null);
	}
	
	/**
	 * Get copy of an image with same bounds but not same data.
	 * 
	 * @param sr		image to copy
	 * @return Blank image.
	 * */
	public static LImage getBlankCopy(LImage sr) {
		return new LImage(new BufferedImage(sr.width, sr.height, sr.getAsBufferedImage().getType()));
	}
	
	public static ImageIcon resizeIconToStdKagatiIconSize(ImageIcon icon) {
		Image image = icon.getImage();
		return new ImageIcon(image.getScaledInstance(STD_KAGATI_TOOL_ICON_WIDTH, STD_KAGATI_TOOL_ICON_HEIGHT, Image.SCALE_SMOOTH));
	}
}