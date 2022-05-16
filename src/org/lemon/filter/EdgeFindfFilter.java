package org.lemon.filter;

public class EdgeFindfFilter extends ConvolutionFilter {
	protected static float edgeFindMatrix[] = {
			1f, 0f, -2f,
			2f, 0f, -2f,
			1f, 0f, -1f
	};
	
	public EdgeFindfFilter() {
		super(edgeFindMatrix);
	}
}
