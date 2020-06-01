package org.lemon.tools.brush;

import java.awt.BasicStroke;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

public class SoftBrushTool extends BrushToolAdapter {
	
	
//	private int strokeSize = BrushTool.defaultStrokeSize;
//	private Color strokeColor = BrushTool.defaultStrokeColor;
//	private Stroke stroke;
//	
//	private Graphics2D context;
	
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
		
		if(strokeSize == 0)
			strokeSize = 1;
		
		stroke = new BasicStroke(getStrokeSize(), BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND);
		
		context.setStroke(stroke);
	}
	
	
//	@Override
//	public void draw(int newX, int newY, int oldX, int oldY) {
//		context.draw(new Line2D.Double(newX, newY, oldX, oldY));
//	}
//	
//	
//	@Override
//	public Graphics2D getContext() {
//		return context;
//	}
//	
//	
//	@Override
//	public void setStrokeSize(int size) {
//		this.strokeSize = size;
//	}
//
//	@Override
//	public int getStrokeSize() {
//		return strokeSize;
//	}
//
//	@Override
//	public void setStrokeColor(Color color) {
//		this.context.setColor(color);
//	}
//
//	@Override
//	public Color getStrokeColor() {
//		return strokeColor;
//	}
//
//	@Override
//	public Stroke getStroke() {
//		return stroke;
//	}


}
