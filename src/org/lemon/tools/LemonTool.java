package org.lemon.tools;

import java.awt.Color;

public class LemonTool {

	public static int penSize = 5;
	Color color = Color.black;
	
	public void setPenSize(int ps) {
		penSize = ps;
	}
	
	public int getPenSize() {
		return penSize;
	}
}
