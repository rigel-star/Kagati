package org.lemon.filters;

import java.awt.image.Kernel;

import org.lemon.image.ImageMath;
import org.lemon.image.LImage;

public class ConvolutionFilter extends AbstractImageFilter {

	/**
     * Treat pixels off the edge as zero.
     */
	public static int ZERO_EDGES = 0;

    /**
     * Clamp pixels off the edge to the nearest edge.
     */
	public static int CLAMP_EDGES = 1;

    /**
     * Wrap pixels off the edge to the opposite edge.
     */
	public static int WRAP_EDGES = 2;
	
	private Kernel kernel = null;
	
	/**
     * Whether to convolve alpha.
     */
	protected boolean alpha = true;

    /**
     * Whether to premultiply the alpha before convolving.
     */
	protected boolean premultiplyAlpha = true;

    /**
     * What do do at the image edges.
     */
	private int edgeAction = CLAMP_EDGES;
	
	
	public ConvolutionFilter() {
		this( new float[9] );
	}
	
	
	public ConvolutionFilter( float[] matrix ) {
		this( new Kernel( 3, 3, matrix ) );
	}
	
	
	public ConvolutionFilter( int rows, int cols, float[] matrix ) {
		this( new Kernel( cols, rows, matrix ) );
	}
	
	
	public ConvolutionFilter( Kernel kernel ) {
		this.kernel = kernel;
	}
	
	
	@Override
	public void filter( LImage limage ) {
		
		int width = limage.width;
		int height = limage.height;
		
		int data[]  = new int[ width * height ];
		limage.getPixels( 0, 0, width, height, data );
		
		int out[]  = new int[ width * height ];
		
		if( premultiplyAlpha )
			ImageMath.premultiplyAlpha( data );
		
		convolve( kernel, data, out, width, height, edgeAction );
		
		if( premultiplyAlpha )
			ImageMath.unpremultiplyAlpha( data );
		
		limage.setPixels( 0, 0, width, height, out );
	}
	
	
	public void convolve( Kernel kernel, int[] data, int[] out, int width, int height, int edgeAction ) {
		convolve(kernel, data, out, width, height, true, edgeAction);
	}
	
	
	public void convolve( Kernel kernel, int[] data, int[] out, int width, int height, boolean alpha, int edgeAction ) {
		if (kernel.getHeight() == 1)
			convolveH(kernel, data, out, width, height, alpha, edgeAction);
		else if (kernel.getWidth() == 1)
			convolveV(kernel, data, out, width, height, alpha, edgeAction);
		else
			convolveHV(kernel, data, out, width, height, alpha, edgeAction);
	}
	
	
	public static void convolveHV(Kernel kernel, int[] inPixels, int[] outPixels, int width, int height, boolean alpha, int edgeAction) {
		int index = 0;
		float[] matrix = kernel.getKernelData( null );
		int rows = kernel.getHeight();
		int cols = kernel.getWidth();
		int rows2 = rows/2;
		int cols2 = cols/2;

		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				float r = 0, g = 0, b = 0, a = 0;

				for (int row = -rows2; row <= rows2; row++) {
					int iy = y+row;
					int ioffset;
					if (0 <= iy && iy < height)
						ioffset = iy*width;
					else if ( edgeAction == CLAMP_EDGES )
						ioffset = y*width;
					else if ( edgeAction == WRAP_EDGES )
						ioffset = ((iy+height) % height) * width;
					else
						continue;
					int moffset = cols*(row+rows2)+cols2;
					for (int col = -cols2; col <= cols2; col++) {
						float f = matrix[moffset+col];

						if (f != 0) {
							int ix = x+col;
							if (!(0 <= ix && ix < width)) {
								if ( edgeAction == CLAMP_EDGES )
									ix = x;
								else if ( edgeAction == WRAP_EDGES )
									ix = (x+width) % width;
								else
									continue;
							}
							int rgb = inPixels[ioffset+ix];
							a += f * ((rgb >> 24) & 0xff);
							r += f * ((rgb >> 16) & 0xff);
							g += f * ((rgb >> 8) & 0xff);
							b += f * (rgb & 0xff);
						}
					}
				}
				
				int ia = alpha ? constrain( (int)(a + 0.5), 0, 255 ) : 0xff;
				int ir = constrain( (int)(r + 0.5), 0, 255 );
				int ig = constrain( (int)(g + 0.5), 0, 255 );
				int ib = constrain( (int)(b + 0.5), 0, 255 );
				
				outPixels[index++] = (ia << 24) | (ir << 16) | (ig << 8) | ib;
			}
		}
	}
	
	
	/**
	 * 
	 * Convolve with a kernel consisting of one row.
     * @param kernel the kernel
     * @param inPixels the input pixels
     * @param outPixels the output pixels
     * @param width the width
     * @param height the height
     * @param alpha include alpha channel
     * @param edgeAction what to do at the edges
     * 
	 */
	public static void convolveH(Kernel kernel, int[] inPixels, int[] outPixels, int width, int height, boolean alpha, int edgeAction) {
		int index = 0;
		float[] matrix = kernel.getKernelData( null );
		int cols = kernel.getWidth();
		int cols2 = cols/2;

		for (int y = 0; y < height; y++) {
			int ioffset = y*width;
			for (int x = 0; x < width; x++) {
				float r = 0, g = 0, b = 0, a = 0;
				int moffset = cols2;
				for (int col = -cols2; col <= cols2; col++) {
					float f = matrix[moffset+col];

					if (f != 0) {
						int ix = x+col;
						if ( ix < 0 ) {
							if ( edgeAction == CLAMP_EDGES )
								ix = 0;
							else if ( edgeAction == WRAP_EDGES )
								ix = (x+width) % width;
						} else if ( ix >= width) {
							if ( edgeAction == CLAMP_EDGES )
								ix = width-1;
							else if ( edgeAction == WRAP_EDGES )
								ix = (x+width) % width;
						}
						int rgb = inPixels[ioffset+ix];
						a += f * ((rgb >> 24) & 0xff);
						r += f * ((rgb >> 16) & 0xff);
						g += f * ((rgb >> 8) & 0xff);
						b += f * (rgb & 0xff);
					}
				}
				
				int ia = alpha ? constrain( (int)(a + 0.5), 0, 255 ) : 0xff;
				int ir = constrain( (int)(r + 0.5), 0, 255 );
				int ig = constrain( (int)(g + 0.5), 0, 255 );
				int ib = constrain( (int)(b + 0.5), 0, 255 );
				
				outPixels[index++] = (ia << 24) | (ir << 16) | (ig << 8) | ib;
			}
		}
	}
	
	
	/**
	 * 
	 * Convolve with a kernel consisting of one column.
     * @param kernel the kernel
     * @param inPixels the input pixels
     * @param outPixels the output pixels
     * @param width the width
     * @param height the height
     * @param alpha include alpha channel
     * @param edgeAction what to do at the edges
     * 
	 */
	public static void convolveV(Kernel kernel, int[] inPixels, int[] outPixels, int width, int height, boolean alpha, int edgeAction) {
		int index = 0;
		float[] matrix = kernel.getKernelData( null );
		int rows = kernel.getHeight();
		int rows2 = rows/2;

		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				float r = 0, g = 0, b = 0, a = 0;

				for (int row = -rows2; row <= rows2; row++) {
					int iy = y+row;
					int ioffset;
					if ( iy < 0 ) {
						if ( edgeAction == CLAMP_EDGES )
							ioffset = 0;
						else if ( edgeAction == WRAP_EDGES )
							ioffset = ((y+height) % height)*width;
						else
							ioffset = iy*width;
					} else if ( iy >= height) {
						if ( edgeAction == CLAMP_EDGES )
							ioffset = (height-1)*width;
						else if ( edgeAction == WRAP_EDGES )
							ioffset = ((y+height) % height)*width;
						else
							ioffset = iy*width;
					} else
						ioffset = iy*width;

					float f = matrix[row+rows2];

					if ( f != 0 ) {
						int rgb = inPixels[ioffset+x];
						a += f * ((rgb >> 24) & 0xff);
						r += f * ((rgb >> 16) & 0xff);
						g += f * ((rgb >> 8) & 0xff);
						b += f * (rgb & 0xff);
					}
				}
				
				int ia = alpha ? constrain( (int)(a + 0.5), 0, 255 ) : 0xff;
				int ir = constrain( (int)(r + 0.5), 0, 255 );
				int ig = constrain( (int)(g + 0.5), 0, 255 );
				int ib = constrain( (int)(b + 0.5), 0, 255 );
				
				outPixels[index++] = (ia << 24) | (ir << 16) | (ig << 8) | ib;
			}
		}
	}
	
	
	public void setEdgeAction( int edgeAction ) {
		this.edgeAction = edgeAction;
	}
	
	
	public int getEdgeAction() {
		return edgeAction;
	}
	
	
	public void setUseAlpha( boolean useAlpha ) {
		this.alpha = useAlpha;
	}


	public boolean getUseAlpha() {
		return alpha;
	}


	public void setPremultiplyAlpha( boolean premultiplyAlpha ) {
		this.premultiplyAlpha = premultiplyAlpha;
	}

 
	public boolean getPremultiplyAlpha() {
		return premultiplyAlpha;
	}
	
	
	public void setKernel( Kernel kernel ) {
		this.kernel = kernel;
	}
	
	
	public Kernel getKernel() {
		return kernel;
	}

}
