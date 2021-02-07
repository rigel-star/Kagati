package org.lemon.filter;

import static java.lang.Math.min;
import static java.lang.Math.max;
import static java.lang.Math.pow;
import static java.lang.Math.sqrt;

import org.lemon.math.Vec2;

public class LightImageFilter extends SinglePixelFilter {
	
	protected static int W;
	protected static int H;
	protected static int strength;
	protected static int radius;
	protected static Vec2 pos;
	
	/**
	 * Constructs {@code LightImageFilter} with specified 
	 * width & height of an image, arbitary light strength, 
	 * radius and position of light.
	 * 
	 * @param w 			Width of an image.
	 * @param h 			Height of an image.
	 * @param strnth 		Strength of light.
	 * @param radi 			Radius of light.
	 * @param posi 			Position of light.
	 * */
	public LightImageFilter( int w, int h, int strnth, int radi, Vec2 posi ) {
		W = w;
		H = h;
		strength = strnth;
		radius = radi;
		pos = posi;
	}
	
	/**
	 * Change strength of light.
	 * 
	 * @param strnth 		New strength of light.
	 * */
	public void setStrength( int strnth ) {
		strength = strnth;
	}
	
	/**
	 * @return 		Strength of light.
	 * */
	public int getStrength() {
		return strength;
	}
	
	/**
	 * Change radius of light.
	 * 
	 * @param radi 		New radius of light.
	 * */
	public void setRadius( int radi ) {
		radius = radi;
	}
	
	/**
	 * @return 		Radius of light.
	 * */
	public int getRadius() {
		return radius;
	}
	
	/**
	 * Change position of light.
	 * 
	 * @param p 	New position of light.
	 * */
	public void setPosition( Vec2 p ) {
		pos = p;
	}
	
	/**
	 * @return Position of light.
	 * */
	public Vec2 getPosition() {
		return pos;
	}
	
	@Override
	public int processRGB( int x, int y, int rgb ) {
		
		int centerX = (int) pos.x, centerY = (int) pos.y;
		int newR = 0, newG = 0, newB = 0;
		
		final int R = ( rgb >> 0b10000 ) & 0xFF;
		final int G = ( rgb >> 0b1000 ) & 0xFF;
		final int B = ( rgb >> 0b0 ) & 0xFF;
		
		newR = R;
		newG = G;
		newB = B;

		int dist = (int) ( pow( centerY - y, 2 ) + pow( centerX - x, 2 ));

		if ( dist < radius * radius ) {
			int res = (int) ( strength * ( 1 - sqrt( dist ) / radius ));
			newR = R + res;
			newG = G + res;
			newB = B + res;
		}
		
		newR = (int) min( 255, max( 0, newR ));
		newG = (int) min( 255, max( 0, newG ));
		newB = (int) min( 255, max( 0, newB ));

		int newPix = ( newR << 16 ) | ( newG << 8 ) | ( newB );
		return newPix;
	}
}
