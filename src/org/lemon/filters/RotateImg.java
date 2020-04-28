package org.lemon.filters;

import java.awt.image.BufferedImage;

public class RotateImg {

	private BufferedImage img, out;
	private float degree;
	
	public RotateImg() {}
	
	public RotateImg(float degree, BufferedImage img) {
		this.img = img;
		this.degree = degree;
		processImg();
	}
	
	private void processImg() {
		
		out = new BufferedImage(img.getWidth(), img.getHeight(), img.getType());
		
		if(degree == 180) {
			rotate180();
		}
		else if(degree == 90) {
			rotate90();
		}
		
	}
	
	private void rotate180() {
		
		System.out.println("Wait, image is rotating!");
		int w = img.getWidth();
		int h = img.getHeight();
	
		int posX = 0;
		int posY = 0;
		
		for(int y=h-1;y>=0;y--) {
			for(int x=w-1;x>=0;x--) {
				out.setRGB(posX, posY, img.getRGB(x, y));
				
				posX++;
				
			}
			//make posX 0 again cause pixel should be repeated from 0 everytime
			posX = 0;
			posY++;
		}
	}
	
	private void rotate90() {
		
		int w = img.getWidth();
		int h = img.getHeight();
		
		for(int y=0; y<h;y++) 
        { 
            for(int lx=0, rx=w-1; lx<w; lx++, rx--) {   
                int p = img.getRGB(lx, y); 
  
                out.setRGB(rx, y, p); 
            } 
        } 
	}
	
	public BufferedImage getRotatedImg() {	
		return out;
	}
	
}
