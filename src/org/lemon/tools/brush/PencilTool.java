package org.lemon.tools.brush;

import java.awt.BasicStroke;
import java.awt.Graphics2D;

import org.lemon.tools.BrushTool;

public class PencilTool extends BrushTool {
	public static final int defaultPencilSize = 1;
	
	public PencilTool(Graphics2D context) {
		super(context, new BasicStroke(PencilTool.defaultPencilSize));
	}
}