package org.lemon.image;

public class ImageMath {
	
	/**
     * The value of half pi as a float.
     */
	public final static float HALF_PI = (float) Math.PI / 2.0f;

    /**
     * The value of quarter pi as a float.
     */
	public final static float QUARTER_PI = (float) Math.PI / 4.0f;

    /**
     * The value of two pi as a float.
     */
	public final static float TWO_PI = (float) Math.PI * 2.0f;
	
	/**
	 * Premultiply alpha with image data.
	 * 
	 * @param data image data to premultiply alpha with
	 * */
	public static void premultiplyAlpha(int[] data) {
		for(int ii = 0; ii < data.length; ii++) {
			int a = (data[ii] >> 24) & 0xFF;
			int r = (data[ii] >> 16) & 0xFF;
			int g = (data[ii] >> 8) & 0xFF;
			int b = (data[ii]) & 0xFF;
			
			float f = a * (1 / 255);
			if(f != 0) {
				r *= f;
				g *= f;
				b *= f;
				data[ii] = (a << 24) | (r << 16) | (g << 8) | b;
			}
		}	
	}
	
	/**
	 * Unpremultiply alpha from given image data.
	 * 
	 * @param data image data to unpremultiply alpha from
	 * */
	public static void unpremultiplyAlpha( int[] data ) {
		for ( int i = 0; i < data.length; i ++ ) {
            int a = ( data[i] >> 24 ) & 0xff;
            int r = ( data[i] >> 16 ) & 0xff;
            int g = ( data[i] >> 8 ) & 0xff;
            int b = ( data[i] ) & 0xff;
            
            if ( a != 0 && a != 255 ) {
                float f = 255.0f / a;
                r *= f;
                g *= f;
                b *= f;
                if ( r > 255 )
                    r = 255;
                if ( g > 255 )
                    g = 255;
                if ( b > 255 )
                    b = 255;
                data[i] = (a << 24) | (r << 16) | (g << 8) | b;
            }
        }
    }
	
	/**
	 * Clamp a value to an interval.
	 * @param a the lower clamp threshold
	 * @param b the upper clamp threshold
	 * @param x the input parameter
	 * @return the clamped value
	 */
	public static float clamp(float x, float a, float b) {
		return (x < a) ? a : (x > b) ? b : x;
	}

	/**
	 * Clamp a value to an interval.
	 * @param a the lower clamp threshold
	 * @param b the upper clamp threshold
	 * @param x the input parameter
	 * @return the clamped value
	 */
	public static int clamp(int x, int a, int b) {
		return (x < a) ? a : (x > b) ? b : x;
	}

	/**
	 * Return a mod b. This differs from the % operator with respect to negative numbers.
	 * @param a the dividend
	 * @param b the divisor
	 * @return a mod b
	 */
	public static double mod(double a, double b) {
		int n = (int)(a/b);
		a -= n*b;
		if (a < 0)
			return a + b;
		return a;
	}

	/**
	 * Return a mod b. This differs from the % operator with respect to negative numbers.
	 * @param a the dividend
	 * @param b the divisor
	 * @return a mod b
	 */
	public static float mod(float a, float b) {
		int n = (int)(a/b);
		a -= n*b;
		if (a < 0)
			return a + b;
		return a;
	}

	/**
	 * Return a mod b. This differs from the % operator with respect to negative numbers.
	 * @param a the dividend
	 * @param b the divisor
	 * @return a mod b
	 */
	public static int mod(int a, int b) {
		int n = a/b;
		a -= n*b;
		if (a < 0)
			return a + b;
		return a;
	}
	
	/**
	 * Bilinear interpolation of ARGB values.
	 * @param x the X interpolation parameter 0..1
	 * @param y the y interpolation parameter 0..1
	 * @param rgb array of four ARGB values in the order NW, NE, SW, SE
	 * @return the interpolated value
	 */
	public static int bilinearInterpolate(float x, float y, int nw, int ne, int sw, int se) {
		float m0, m1;
		int a0 = (nw >> 24) & 0xff;
		int r0 = (nw >> 16) & 0xff;
		int g0 = (nw >> 8) & 0xff;
		int b0 = nw & 0xff;
		int a1 = (ne >> 24) & 0xff;
		int r1 = (ne >> 16) & 0xff;
		int g1 = (ne >> 8) & 0xff;
		int b1 = ne & 0xff;
		int a2 = (sw >> 24) & 0xff;
		int r2 = (sw >> 16) & 0xff;
		int g2 = (sw >> 8) & 0xff;
		int b2 = sw & 0xff;
		int a3 = (se >> 24) & 0xff;
		int r3 = (se >> 16) & 0xff;
		int g3 = (se >> 8) & 0xff;
		int b3 = se & 0xff;

		float cx = 1.0f-x;
		float cy = 1.0f-y;

		m0 = cx * a0 + x * a1;
		m1 = cx * a2 + x * a3;
		int a = (int)(cy * m0 + y * m1);

		m0 = cx * r0 + x * r1;
		m1 = cx * r2 + x * r3;
		int r = (int)(cy * m0 + y * m1);

		m0 = cx * g0 + x * g1;
		m1 = cx * g2 + x * g3;
		int g = (int)(cy * m0 + y * m1);

		m0 = cx * b0 + x * b1;
		m1 = cx * b2 + x * b3;
		int b = (int)(cy * m0 + y * m1);
		return (a << 24) | (r << 16) | (g << 8) | b;
	}
}