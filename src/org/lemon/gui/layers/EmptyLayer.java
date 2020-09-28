package org.lemon.gui.layers;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

public class EmptyLayer extends ViewLayer {
	
	public EmptyLayer() {
		super(new EmptyCanvas(), "New Layer", NORMAL_LAYER);
	}
	
	
	/*Empty canvas for empty layer*/
	public static class EmptyCanvas extends JPanel {
		private static final long serialVersionUID = 1L;
		
		private Canvas canvas = null;
		
		public EmptyCanvas() {
			canvas = new Canvas();
			canvas.setBackground(Color.white);
			
			setLayout(new BorderLayout());
			add(canvas, BorderLayout.CENTER);
		}
		
		public BufferedImage getEmptyImage() {
			return new BufferedImage(700, 700, BufferedImage.TYPE_INT_ARGB);
		}
	}
}
