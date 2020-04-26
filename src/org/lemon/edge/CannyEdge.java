package org.lemon.edge;

import java.awt.image.BufferedImage;

public class CannyEdge {

	int n0, n1, n2, n3, n4, n5, n6, n7, n8;
	BufferedImage src, out;
	
	public CannyEdge(BufferedImage src){
		this.src = src;
		out = new BufferedImage(src.getWidth(), src.getHeight(), src.getType());
	}
	
	public void highlight() {
		for(int y=1;y<src.getHeight()-1;y++) {
			for(int x=1;x<src.getWidth()-1;x++) {
			
				//parent
				n0 = src.getRGB(x, y) * 4;
				n1 = src.getRGB(x, y+1) * (-1);
				n2 = src.getRGB(x-1, y+1) * 0;//0
				n3 = src.getRGB(x-1, y) * (-1);
				n4 = src.getRGB(x-1, y-1) * 0;//0
				n5 = src.getRGB(x, y-1) * (-1);
				n6 = src.getRGB(x+1, y-1) * 0;//0
				n7 = src.getRGB(x+1, y) * (-1);
				n8 = src.getRGB(x+1, y+1) * 0;//0
				
				out.setRGB(x, y, calcAvrg());
			}
		}
	}
	
	public void silverEffect() {
		for(int y=1;y<src.getHeight()-1;y++) {
			for(int x=1;x<src.getWidth()-1;x++) {
			
				//parent
				n0 = src.getRGB(x, y);
				n1 = src.getRGB(x, y+1);
				n2 = src.getRGB(x-1, y+1);
				n3 = src.getRGB(x-1, y);
				n4 = src.getRGB(x-1, y-1);
				n5 = src.getRGB(x, y-1);
				n6 = src.getRGB(x+1, y-1);
				n7 = src.getRGB(x+1, y);
				n8 = src.getRGB(x+1, y+1);
				
				out.setRGB(x, y, calcAvrg());
			}
		}
	}
	
	int calcAvrg() {
		return (n0+n1+n2+n3+n4+n5+n6+n7+n8);
	}
	
	public BufferedImage getHighlightedImg() {
		return out;
	}
}
