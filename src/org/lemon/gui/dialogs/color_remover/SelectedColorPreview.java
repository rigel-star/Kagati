package org.lemon.gui.dialogs.color_remover;


import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import org.lemon.math.BasicMath;
import org.rampcv.color.Range;
import org.rampcv.utils.Tools;

public class SelectedColorPreview  {

	
	private List<Color> list = new ArrayList<Color>();
	private BufferedImage preview;
	private final BufferedImage source;
	
	
	public SelectedColorPreview(final BufferedImage src) {
		
		preview = Tools.createBlankImageLike(src, BufferedImage.TYPE_3BYTE_BGR);
		source = src;
		
		//setSize(src.getWidth(), src.getHeight());
		//setIcon(new ImageIcon(preview));
	}
	
	
	/**
	 * Add new color to change or remove from image. This method takes {@code Color} as parameter and
	 * tries to find that color in image in specific range.
	 * @param color to find in image
	 * */
	public void addNewColor(Color c) {
		list.add(c);
		System.out.println("From SelectedColorPreview.class: " + c.getRGB());
	}
	
	
	public boolean updatePreview() {
		
		final Range range = new Range();
		
			
		for(int x=0; x<preview.getWidth(); x++) {
			for(int y=0; y<preview.getHeight(); y++) {
				
				Color curr = new Color(source.getRGB(x, y));
				Color prev = new Color(preview.getRGB(x, y));
				
				/*if point is already highlighted, then skip it.*/
				if(prev.getRGB() != Color.black.getRGB()) {
					continue;
				}
				
				final int dx = x;
				final int dy = y;
				
				int r1 = curr.getRed();
				int g1 = curr.getGreen();
				int b1 = curr.getBlue();
				
				list.forEach(color -> {
					
					int r2 = color.getRed();
					int g2 = color.getGreen();
					int b2 = color.getBlue();
					
					double dist = BasicMath.dist(r1, g1, b1, r2, g2, b2);
					
					if(range.is(dist).inRange(0, 100)) {
						preview.setRGB(dx, dy, new Color(255, 255, 255).getRGB());
					}
					
				});
			}
		}
		
		return true;
	}
		
	
	
	public void remakePreview() {
		list.clear();
		preview = Tools.createBlankImageLike(source, BufferedImage.TYPE_3BYTE_BGR);
	}
	
	
	public BufferedImage getRenderedPreview() {
		return preview;
	}
	
	
	
	
	
}
