package org.lemon.tools.brush;

import java.awt.Color;
import java.awt.Graphics2D;

import javax.swing.JComponent;

public class SoftBrushTool implements LemonBrushTool {
	
	
	private int strokeSize = LemonBrushTool.defaultStrokeSize;
	private Color strokeColor = LemonBrushTool.defaultStrokeColor;
	
	
	Graphics2D g2d;
	
	/**
	 * Soft Brush is brush with feature of anti aliasing. After brushing, the brushed part will be
	 * anti aliased with surrounding.
	 * @param context canvas to draw on
	 * @param strokeColor color to draw with
	 * @param strokeSize stroke size
	 * */
	public SoftBrushTool(JComponent context, Color strokeColor, int strokeSize) {
		
		g2d = (Graphics2D) context.getGraphics();
	}

	
	
	@Override
	public void setStrokeSize(int size) {
		this.strokeSize = size;
	}

	@Override
	public int getStrokeSize() {
		return strokeSize;
	}

	@Override
	public void setColor(Color color) {
		this.strokeColor = color;
	}

	@Override
	public Color getColor() {
		return strokeColor;
	}

}
