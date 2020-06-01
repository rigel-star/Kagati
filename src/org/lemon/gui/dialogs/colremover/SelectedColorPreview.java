package org.lemon.gui.dialogs.colremover;


import java.awt.Color;
import java.awt.image.BufferedImage;

import org.lemon.utils.UndoRedo;
import org.rampcv.color.Range;
import org.rampcv.utils.Tools;

public class SelectedColorPreview  {
	
	private BufferedImage preview;
	private final BufferedImage source;
	
	
	private UndoRedo undoRedoManager = new UndoRedo();
	
	
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
		undoRedoManager.addToUndo(c);
	}
	
	
	
	/**
	 * Update the preview with new colors.
	 * */
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
				
				
				/*undo stack stores all the previous colors selected in it so just calling
				 * the getUndoStack gives all the previous selected colors and we can process it.*/
				undoRedoManager.getUndoStack().forEach(obj -> {
					
					var color = (Color) obj;
					
					int r2 = color.getRed();
					int g2 = color.getGreen();
					int b2 = color.getBlue();
					
					double dist = org.rampcv.math.BasicMath.dist(r1, g1, b1, r2, g2, b2);
					
					if(range.is(dist).inRange(0, 30)) {
						preview.setRGB(dx, dy, new Color(255, 255, 255).getRGB());
					}
					
				});
				
			}
		}
		
		return true;
	}
		
	
	
	/**
	 * Make new preview erasing all previous data.
	 * */
	public void remakePreview() {
		undoRedoManager.getRedoStack().clear();
		undoRedoManager.getUndoStack().clear();
		preview = Tools.createBlankImageLike(source, BufferedImage.TYPE_3BYTE_BGR);
	}
	
	
	/**
	 * Get final rendered preview image.
	 * */
	public BufferedImage getRenderedPreview() {
		return preview;
	}
	
	
	public UndoRedo getUndoRedoManager() {
		return undoRedoManager;
	}
	
	
	public void setImage(BufferedImage newImg) {
		this.preview = newImg;
	}
	
	
	public BufferedImage getImage() {
		return source;
	}
	
	
}
