package org.lemon.tools.brush;

import java.awt.Color;

public interface LemonBrushTool {
	
	public static final int defaultStrokeSize = 5;
	public static final Color defaultStrokeColor = Color.black;
	
	void setStrokeSize(int size);
	int getStrokeSize();
	void setColor(Color color);
	Color getColor();
}
