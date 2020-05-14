package org.lemon.filters.basic_filters;

import java.awt.Color;
import java.awt.image.BufferedImage;

import org.rampcv.color.Range;
import org.rampcv.utils.Tools;

public class ImageHSB {

	private static float[] hsb;
	private static Color color;
	
	public static int incSaturation(BufferedImage src, int x, int y, float intensity) {
		
		if(intensity <= 0) {
			return src.getRGB(x, y);
		}
		
		color = new Color(src.getRGB(x, y));
		int r = color.getRed();
		int g = color.getGreen();
		int b = color.getBlue();
		
		hsb = Tools.RGBtoHSB(r, g, b);
		
		hsb[1] = Range.constrain(hsb[1] * intensity, 0.0f, 1.0f);
		
		return (int) hsb[1];
	}
	
}
