package org.lemon.tools.brush;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.geom.Ellipse2D;

public class BrushToolAdapter implements BrushTool {

	
	public Stroke stroke = defaultStroke;
	public Color strokeColor = defaultStrokeColor;
	public int strokeSize = defaultStrokeSize;
	
	private Graphics2D context;
	
	public BrushToolAdapter(Graphics2D context) {
		this.context = context;
		context.setStroke(getStroke());
		context.setColor(getStrokeColor());
	}
	
	
	@Override
	public void draw(int x, int y) {
		context.fill(new Ellipse2D.Double(x, y, getStrokeSize(), getStrokeSize()));
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
	public void setStroke(Stroke stroke) {
		this.stroke = stroke;
	}

	@Override
	public Stroke getStroke() {
		return stroke;
	}

}
