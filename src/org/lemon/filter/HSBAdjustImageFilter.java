package org.lemon.filter;

import java.awt.Color;

import org.lemon.color.IllegalRGBValueException;
import org.lemon.image.Pixel;

public class HSBAdjustImageFilter extends SinglePixelFilter {
	
	/**
	 * The Hue flag is used to denote either increasing 
	 * or decreasing the hue of an image.
	 * */
	public static final int HUE = 0xA;
	
	/**
	 * The Saturation flag is used to denote either increasing 
	 * or decreasing the saturation of an image.
	 * */
	public static final int SATURATION = 0xB;
	
	/**
	 * The Brightness flag is used to denote either increasing
	 * or decreasing the birghtness of an image.
	 * */
	public static final int BRIGHTNESS = 0xC;
	
	/**
	 * The All flag is used to denote either increasing 
	 * or decreasing the all HSB components of an image.
	 * */
	public static final int ALL = 0xD;
	
	/*mode: hue, saturation or brightness*/
	private int mode = 0;
	
	/*val: the value to increase or decrease by*/
	private int val = 0;
	
	
	/**
	 * 
	 * Increase or decrease HSB ( Hue, Saturation and Brightness ) of an image.
	 * 
	 * @param 	mode	 hue, saturation or brightness to increase
	 * @param 	val		the value to increase or decrease by
	 * 
	 * */
	public HSBAdjustImageFilter( int mode, int val ) {
		this.mode = mode;
		this.val = val;
	}
	
	
	@Override
	public int processRGB( int rgb ) {
		
		float[] hsb = new float[3];
		int[] rgbsep = Pixel.extractRGB( rgb );
		int frgb = 0;
		
		hsb = Color.RGBtoHSB( rgbsep[0], rgbsep[1], rgbsep[2], hsb );
		
		switch( mode ) {
		
		case HUE: {
			
		}
		break;
		
		case SATURATION: {
			
		}
		break;
		
		case BRIGHTNESS: {
			rgbsep[0] = incBrightness( (int) rgbsep[0], val );
			rgbsep[1] = incBrightness( (int) rgbsep[1], val );
			rgbsep[2] = incBrightness( (int) rgbsep[2], val );
			
			frgb = ( rgbsep[0] << 16 ) | ( rgbsep[1] << 8 ) | ( rgbsep[2] );
		}
		break;
		
		case ALL: {
			
		}
		break;
		
		default: {
			System.out.println("Mode " + mode + " is not available!");
		}
		break;
		
		}
		
		return frgb;
	}
	
	
	/**
	 * 
	 * Adds the new specified brightness value with current brightness value.
	 * @param brgt the current brightness value of the pixel
	 * @param add the brightness value to add with current
	 * 
	 * */
	private int incBrightness( int hsb2, int add ) {
		
		if( add < 0 || add > 255 )
			throw new IllegalRGBValueException( "add value must be between 0 to 255. Your value: " + add );
		return constrain( hsb2 + add, 0, 255 );
	}
	
	
	/**
	 * 
	 * Change mode between HSB.
	 * @param mode 	new mode
	 * 
	 * */
	public void setMode( int mode ) {
		this.mode = mode;
	}
	
}
