package org.lemon.tools.brush;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.geom.Line2D;

import org.lemon.tools.BrushTool;

public class BrushToolAdapter implements BrushTool {

	
	public Color strokeColor = Color.red;
	public int strokeSize = defaultStrokeSize;
	public Stroke stroke = new BasicStroke(getStrokeSize());
	
	
	public Graphics2D context;
	
	
	public BrushToolAdapter() {}
	
	public BrushToolAdapter(Graphics2D context, Color c) {
		this.context = context;
		this.strokeColor = c;
		
		if(strokeSize < 1)
			strokeSize = 1;
		
		context.setStroke(getStroke());
		context.setPaint(getStrokeColor());
	}
	
	
	@Override
	public void draw(int newX, int newY, int oldX, int oldY) {
		context.draw(new Line2D.Double(newX, newY, oldX, oldY));
	}
	
	
	@Override
	public Graphics2D getContext() {
		return context;
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
