package org.lemon.filter;

public class GrayImageFilter extends SinglePixelFilter {
	@Override
	public int processRGB(int x, int y, int rgb) {
		int a = (rgb >> 24) & 0xFF;
		int r = (rgb >> 16) & 0xFF;
		int g = (rgb >> 8 ) & 0xFF;
        int b = rgb & 0xFF;
        final int gray = (int) (b * 0.0722 + g * 0.7152 + r * 0.2126);
        return (a << 24) | (gray << 16) | (gray << 8) | gray;
	}
}