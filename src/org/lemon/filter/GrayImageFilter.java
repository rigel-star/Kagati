package org.lemon.filter;

public class GrayImageFilter extends SinglePixelFilter {
	
	@Override
	public int processRGB(int x, int y, int rgb) {
		int a = (rgb >> 24) & 0xFF;
		int r = (rgb >> 16) & 0xFF;
		int g = (rgb >> 8 ) & 0xFF;
        int b = rgb & 0xFF;
        
        final int gray = (int) (b * 0.0722 + g * 0.7152 + r * 0.2126);
		
        b = (gray);
        g = (gray << 8);
        r = (gray << 16);
        a = (rgb << 24);
        return (a << 24) | (r << 16) | (g << 8) | b;
	}
}