package org.lemon.filters.basic;

import java.awt.Color;
import java.awt.image.BufferedImage;

import org.rampcv.color.Range;
import org.rampcv.utils.Tools;

public class ImageHSB {

	private static float[] hsb;
	private static Color color;
	
	
	
	public static void incHue(BufferedImage src, int x, int y, float intensity) {
		
	}
	
	
	/**
	 * @param pixel specific pixel value
	 * @param intensity how much to increase or decrease saturation
	 * */
	public static int incSaturation(int pixel, float intensity) {
		
		if(intensity <= 0 || intensity > 1.0)
			throw new IllegalArgumentException("Intensity must be between 0.0 to 1.0");
		
		color = new Color(pixel);
		int r = color.getRed();
		int g = color.getGreen();
		int b = color.getBlue();
		
		hsb = Tools.RGBtoHSB(r, g, b);
		
		hsb[1] = Range.constrain(hsb[1] * intensity, 0.0f, 1.0f);

		Color res = new Color(hsb[0], hsb[1], hsb[2]);
		return res.getRGB();
	}
	
	
	public static void incBrightness(BufferedImage src, int x, int y, float intensity) {
		
		if(intensity <= 0 || intensity > 255)
			throw new IllegalArgumentException("Intensity must be between 0 to 255");
		
		color = new Color(src.getRGB(x, y));
		
		float[] rgb = new float[3];
		
		rgb[0] = color.getRed() + intensity;
		rgb[1] = color.getGreen() + intensity;
		rgb[2] = color.getBlue() + intensity;
		
		rgb[0] = Range.constrain(rgb[0], 0, 255);
		rgb[1] = Range.constrain(rgb[1], 0, 255);
		rgb[2] = Range.constrain(rgb[2], 0, 255);
		
		Tools.setColor(src, rgb, x, y);
	}
	
	
	
}
