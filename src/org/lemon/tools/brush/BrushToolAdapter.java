package org.lemon.tools.brush;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.geom.Line2D;

public class BrushToolAdapter implements BrushTool {

	
	public Color strokeColor = defaultStrokeColor;
	public int strokeSize = defaultStrokeSize;
	public Stroke stroke = new BasicStroke(getStrokeSize());
	
	
	private Graphics2D context;
	
	public BrushToolAdapter(Graphics2D context) {
		this.context = context;
		
		if(strokeSize == 0)
			strokeSize = 1;
		
		context.setStroke(getStroke());
		context.setColor(getStrokeColor());
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
