package org.lemon.tools.brush;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.geom.Line2D;

public class WobbleBrush implements BrushTool {

	
	
	private int strokeSize = BrushTool.defaultStrokeSize;
	private Color strokeColor = BrushTool.defaultStrokeColor;
	private Stroke stroke;
	
	private Graphics2D context;
	
	
	public WobbleBrush(Graphics2D context) {
		
		this.context = context;
		
		if(strokeSize == 0)
			strokeSize = 1;
		
		stroke = new WobbleStroke(getStrokeSize(), getStrokeSize(), getStrokeSize());
		
		this.context.setStroke(stroke);
		
	}
	
	
	@Override
	public void draw(int newX, int newY, int oldX, int oldY) {
		context.draw(new Line2D.Double(newX, newY, oldX, oldY));
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
	public void setStrokeColor(Color color) {
		this.strokeColor = color;
	}

	
	@Override
	public Color getStrokeColor() {
		return strokeColor;
	}

	
	@Override
	public Stroke getStroke() {
		return stroke;
	}

}
