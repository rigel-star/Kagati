package org.lemon.tools.brush.utils;

import java.awt.Graphics2D;

import org.lemon.tools.brush.BrushTool;
import org.lemon.tools.brush.NormalBrushTool;
import org.lemon.tools.brush.SoftBrushTool;
import org.lemon.tools.brush.WobbleBrush;

public class Brushes {
	
	public static BrushTool createNormalBrush(Graphics2D context) {
		return new NormalBrushTool(context);
	}
	
	public static BrushTool createSoftBrush(Graphics2D context) {
		return new SoftBrushTool(context);
	}
	
	public static BrushTool createWobbleBrush(Graphics2D context) {
		return new WobbleBrush(context);
	}
}
