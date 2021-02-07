package org.lemon.filter;

public class RotateImageFilter extends TransformFilter {
	
	float angle;
	
	public RotateImageFilter( float angle ) {
		
	}
	
	public float getAngle() {
		return angle;
	}

	@Override
	protected void transformInverse(int x, int y, float[] out) {
		
	}
}
