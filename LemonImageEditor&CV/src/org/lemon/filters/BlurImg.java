package org.lemon.filters;

import java.awt.image.BufferedImage;

public class BlurImg {

	BufferedImage src, out;
	int n0, n1, n2, n3, n4, n5, n6, n7, n8;
	
	public BlurImg(BufferedImage src) {
		this.src = src;
		out = new BufferedImage(src.getWidth(), src.getHeight(), src.getType());
		blur();
	}
	
	private void blur() {
		for(int y=1;y<src.getHeight()-1;y++) {
			for(int x=1;x<src.getWidth()-1;x++) {
			
				//parent
				n0 = src.getRGB(x, y) * 4;
				n1 = src.getRGB(x, y+1) * (2);
				n2 = src.getRGB(x-1, y+1) * 1;//0
				n3 = src.getRGB(x-1, y) * (2);
				n4 = src.getRGB(x-1, y-1) * 1;//0
				n5 = src.getRGB(x, y-1) * (2);
				n6 = src.getRGB(x+1, y-1) * 1;//0
				n7 = src.getRGB(x+1, y) * (2);
				n8 = src.getRGB(x+1, y+1) * 1;//0
				
				out.setRGB(x, y, calcAvrg());
			}
		}
	}
	
	int calcAvrg() {
		return (n0+n1+n2+n3+n4+n5+n6+n7+n8);
	}
	
	public BufferedImage getBlurredImg() {
		return out;
	}
}
