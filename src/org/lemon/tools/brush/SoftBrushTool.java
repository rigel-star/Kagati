package org.lemon.tools.brush;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Stroke;
import java.awt.geom.Ellipse2D;

public class SoftBrushTool implements BrushTool {
	
	
	private int strokeSize = BrushTool.defaultStrokeSize;
	private Color strokeColor = BrushTool.defaultStrokeColor;
	private Stroke stroke = BrushTool.defaultStroke;
	
	private Graphics2D context;
	
	/**
	 * Soft Brush is brush with feature of anti aliasing. After brushing, the brushed part will be
	 * anti aliased with surrounding.
	 * @param context canvas to draw on
	 * @param strokeColor color to draw with
	 * @param strokeSize stroke size
	 * */
	public SoftBrushTool(Graphics2D context) {
		this.context = context;
		RenderingHints rhs = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		context.setRenderingHints(rhs);
		context.setColor(getStrokeColor());
		context.setStroke(getStroke());
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
		this.context.setColor(color);
	}

	@Override
	public Color getStrokeColor() {
		return strokeColor;
	}




	@Override
	public void setStroke(Stroke stroke) {
		this.context.setStroke(stroke);
	}




	@Override
	public Stroke getStroke() {
		return stroke;
	}


}
