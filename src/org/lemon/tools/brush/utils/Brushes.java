package org.lemon.tools.brush.utils;

import java.awt.Color;
import java.awt.Graphics2D;

import org.lemon.tools.BrushTool;
import org.lemon.tools.brush.NormalBrushTool;
import org.lemon.tools.brush.SoftBrushTool;
import org.lemon.tools.brush.WobbleBrushTool;
import org.lemon.tools.brush.ZigzagBrushTool;

public class Brushes {
	
	public static BrushTool createNormalBrush(Graphics2D context) {
		return new NormalBrushTool(context, Color.black);
	}
	
	public static BrushTool createSoftBrush(Graphics2D context) {
		return new SoftBrushTool(context);
	}
	
	public static BrushTool createWobbleBrush(Graphics2D context) {
		return new WobbleBrushTool(context);
	}
	
	public static BrushTool createZigzagBrush(Graphics2D context) {
		return new ZigzagBrushTool(context);
	}
}
