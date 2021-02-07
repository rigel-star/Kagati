package org.lemon.filter;

/**
 * 
 * This filter gives image a posterized look.
 * 
 * */
public class PosterizeImageFilter extends SinglePixelFilter {

	private int numLevels;
	private int[] levels;
	private boolean initialized = false;
	
	/**
	 * Constructs the Posterize Image Filter.
	 * */
	public PosterizeImageFilter() {
		setNumLevels( 6 );
	}
	
	public void setNumLevels( int numLvls ) {
		this.numLevels = numLvls;
	}

	/**
	 * Get the number of levels in the output image.
	 * 
	 * @return the number of levels
	 * @see #setNumLevels
	 */
	public int getNumLevels() {
		return numLevels;
	}

	/**
	 * Initialize the filter.
	 */
	protected void initialize() {
		levels = new int[256];
		if (numLevels != 1)
			for (int i = 0; i < 256; i++)
				levels[i] = 255 * ( numLevels * i / 256 ) / ( numLevels - 1 );
	}
	
	@Override
	public int processRGB( int x, int y, int rgb ) {
		
		if ( !initialized ) {
			initialized = true;
			initialize();
		}
		
		int a = rgb & 0xff000000;
		int r = ( rgb >> 16 ) & 0xff;
		int g = ( rgb >> 8 ) & 0xff;
		int b = rgb & 0xff;
		r = levels[r];
		g = levels[g];
		b = levels[b];
		return a | ( r << 16 ) | ( g << 8 ) | b;
	}
}
