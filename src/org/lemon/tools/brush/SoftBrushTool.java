package org.lemon.tools.brush;

import java.awt.BasicStroke;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import org.lemon.tools.BrushTool;

public class SoftBrushTool extends BrushTool {
	
	/**
	 * Soft Brush is brush with feature of anti aliasing. After brushing, the brushed part will be
	 * anti aliased with surrounding.
	 * 
	 * @param context canvas to draw on
	 * */
	public SoftBrushTool(Graphics2D context) {
		super(context);
		
		RenderingHints rhs = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		context.setRenderingHints(rhs);
		context.setColor(getStrokeColor());
		context.setStroke(new BasicStroke(getStrokeSize(), BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
	}
}
