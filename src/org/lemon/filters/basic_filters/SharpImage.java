package org.lemon.filters.basic_filters;

import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;
import java.io.IOException;


public class SharpImage {
	
	BufferedImage src;
	
	public SharpImage(BufferedImage src) throws IOException {
		
		this.src = src;
		//this.out = new BufferedImage(src.getWidth(), src.getHeight(), src.getType());
		
		Kernel kernel = 
				new Kernel(3, 3, 
				new float[] 
						{-1,-1,-1,
						-1,9,-1,
						-1,-1,-1});
		
		BufferedImageOp op = new ConvolveOp(kernel);
		this.src = op.filter(this.src, null);
		
	}
	
	public BufferedImage getSharpedImg() {
		return this.src;
	}
}
