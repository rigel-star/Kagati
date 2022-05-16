package org.lemon.tools.brush;

import java.awt.BasicStroke;
import java.awt.Graphics2D;

import org.lemon.tools.BrushTool;

public class ZigzagBrushTool extends BrushTool {
	
	public ZigzagBrushTool(Graphics2D context) {
		super(context);
		super.getContext().setStroke(new ZigzagStroke(new BasicStroke(BrushTool.defaultStrokeSize), 2, 2));
	}
}
