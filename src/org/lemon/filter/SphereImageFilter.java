package org.lemon.filter;

import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;

import org.lemon.image.ImageMath;
import org.lemon.image.LImage;
import org.lemon.math.Vec2;

public class SphereImageFilter extends TransformFilter {
	
	private float a = 0;
	private float b = 0;
	private float a2 = 0;
	private float b2 = 0;
	private float centreX = 0.5f;
	private float centreY = 0.5f;
	private float refractionIndex = 1.5f;

	private float icentreX;
	private float icentreY;

	public SphereImageFilter() {
		setEdgeAction( CLAMP );
		setRadius( 100.0f );
	}
	
	/**
	 * Set the index of refaction.
	 * @param refractionIndex the index of refaction
     * @see #getRefractionIndex
	 */
	public void setRefractionIndex(float refractionIndex) {
		this.refractionIndex = refractionIndex;
	}

	/**
	 * Get the index of refaction.
	 * @return the index of refaction
     * @see #setRefractionIndex
	 */
	public float getRefractionIndex() {
		return refractionIndex;
	}

	/**
	 * Set the radius of the effect.
	 * @param r the radius
     * @min-value 0
     * @see #getRadius
	 */
	public void setRadius(float r) {
		this.a = r;
		this.b = r;
	}

	/**
	 * Get the radius of the effect.
	 * @return the radius
     * @see #setRadius
	 */
	public float getRadius() {
		return a;
	}

	/**
	 * Set the centre of the effect in the X direction as a proportion of the image size.
	 * @param centreX the center
     * @see #getCentreX
	 */
	public void setCentreX( float centreX ) {
		this.centreX = centreX;
	}

	public float getCentreX() {
		return centreX;
	}
	
	/**
	 * Set the centre of the effect in the Y direction as a proportion of the image size.
	 * @param centreY the center
     * @see #getCentreY
	 */
	public void setCentreY( float centreY ) {
		this.centreY = centreY;
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
	public void setCentre( Point2D centre ) {
		this.centreX = (float)centre.getX();
		this.centreY = (float)centre.getY();
	}

	/**
	 * Get the centre of the effect as a proportion of the image size.
	 * @return the center
     * @see #setCentre
	 */
	public Point2D getCentre() {
		return new Point2D.Float( centreX, centreY );
	}
	
	@Override
    public LImage filter( LImage limage ) {
		return null;
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
	
}