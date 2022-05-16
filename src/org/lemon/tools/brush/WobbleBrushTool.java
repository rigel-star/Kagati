package org.lemon.tools.brush;

import java.awt.Graphics2D;

import org.lemon.tools.BrushTool;

public class WobbleBrushTool extends BrushTool {
	
	public WobbleBrushTool(Graphics2D context) {
		super(context);
		super.getContext().setStroke(new WobbleStroke(getStrokeSize(), getStrokeSize(), getStrokeSize()));
	}
}