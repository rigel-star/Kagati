package org.lemon.filter;

import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;

import org.lemon.image.ImageMath;
import org.lemon.image.LImage;
import org.lemon.math.Vec2;

public class WaterRippleImageFilter extends TransformFilter {

	private static float wavelength = 16;
	private static float amplitude = 10;
	private static float phase = 0;
	private static float centreX = 0.5f;
	private static float centreY = 0.5f;
	private static float radius = 50;

	private float radius2 = 0;
	private float icentreX;
	private float icentreY;

	public WaterRippleImageFilter() {
		this( CLAMP, new Vec2( centreX, centreY ), radius, wavelength, amplitude, phase );
	}
	
	public WaterRippleImageFilter( float wv, float amp, float radi ) {
		setWavelength( wv );
		setAmplitude( amp );
		setRadius( radi );
	}
	
	public WaterRippleImageFilter( int ea, Vec2 centre, float radi, float wl, float amp, float ph ) {
		setEdgeAction( ea );
		setWavelength( wl );
		setCentre( centre );
		setRadius( radi );
		setAmplitude( amp );
		setPhase( ph );
	}

	/**
	 * Set the wavelength of the ripples.
	 * @param wl the wavelength
     * @see #getWavelength
	 */
	public void setWavelength( float wl ) {
		wavelength = wl;
	}

	/**
	 * Get the wavelength of the ripples.
	 * @return the wavelength
     * @see #setWavelength
	 */
	public float getWavelength() {
		return wavelength;
	}

	/**
	 * Set the amplitude of the ripples.
	 * @param amp the amplitude
     * @see #getAmplitude
	 */
	public void setAmplitude(float amp) {
		amplitude = amp;
	}

	/**
	 * Get the amplitude of the ripples.
	 * @return the amplitude
     * @see #setAmplitude
	 */
	public float getAmplitude() {
		return amplitude;
	}

	/**
	 * Set the phase of the ripples.
	 * @param ph the phase
     * @see #getPhase
	 */
	public void setPhase( float ph ) {
		phase = ph;
	}

	/**
	 * Get the phase of the ripples.
	 * @return the phase
     * @see #setPhase
	 */
	public float getPhase() {
		return phase;
	}

	/**
	 * Set the centre of the effect in the X direction as a proportion of the image size.
	 * @param cx the center
     * @see #getCentreX
	 */
	public void setCentreX( float cx ) {
		centreX = cx;
	}

	/**
	 * Get the centre of the effect in the X direction as a proportion of the image size.
	 * @return the center
     * @see #setCentreX
	 */
	public float getCentreX() {
		return centreX;
	}
	
	/**
	 * Set the centre of the effect in the Y direction as a proportion of the image size.
	 * @param cy the center
     * @see #getCentreY
	 */
	public void setCentreY( float cy ) {
		centreY = cy;
	}

	/**
	 * Get the centre of the effect in the Y direction as a proportion of the image size.
	 * @return the center
     * @see #setCentreY
	 */
	public float getCentreY() {
		return centreY;
	}
	
	/**
	 * Set the centre of the effect as a proportion of the image size.
	 * @param centre the center
     * @see #getCentre
	 */
	public void setCentre( Vec2 centre ) {
		centreX = (float)centre.getX();
		centreY = (float)centre.getY();
	}

	/**
	 * Get the centre of the effect as a proportion of the image size.
	 * @return the center
     * @see #setCentre
	 */
	public Point2D getCentre() {
		return new Point2D.Float( centreX, centreY );
	}
	
	/**
	 * Set the radius of the effect.
	 * @param radi the radius
     * @min-value 0
     * @see #getRadius
	 */
	public void setRadius(float radi) {
		radius = radi;
	}

	/**
	 * Get the radius of the effect.
	 * @return the radius
     * @see #setRadius
	 */
	public float getRadius() {
		return radius;
	}
	
	@Override
    public LImage filter( LImage limage ) {
		BufferedImage src = limage.getAsBufferedImage();
		icentreX = src.getWidth() * centreX;
		icentreY = src.getHeight() * centreY;
		if ( radius == 0 )
			radius = Math.min( icentreX, icentreY );
		radius2 = radius * radius;
		return super.filter( new LImage( src ));
	}
	
	protected void transformInverse( int x, int y, float[] out ) {
		float dx = x - icentreX;
		float dy = y - icentreY;
		float distance2 = dx * dx + dy * dy;
		if ( distance2 > radius2 ) {
			out[0] = x;
			out[1] = y;
		} else {
			float distance = (float)Math.sqrt(distance2);
			float amount = amplitude * (float)Math.sin(distance / wavelength * ImageMath.TWO_PI - phase);
			amount *= (radius-distance)/radius;
			if ( distance != 0 )
				amount *= wavelength/distance;
			out[0] = x + dx*amount;
			out[1] = y + dy*amount;
		}
	}
	
	@Override
	public String toString() {
		return "Distort/Water Ripples...";
	}	
}