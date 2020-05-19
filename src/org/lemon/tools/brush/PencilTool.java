package org.lemon.tools.brush;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;

public class PencilTool {

	
	private Color color = Color.black;
	private Graphics2D context;
	
	
	public PencilTool(Graphics2D context) {
		
		this.context = context;
		this.context.setPaint(color);
		this.context.setStroke(new BasicStroke(2));
		
	}
	
	
	public void draw(int newX, int newY, int oldX, int oldY) {
		context.draw(new Line2D.Double(newX, newY, oldX, oldY));
	}
	
	
	public void setStrokeColor(Color color) {
		this.color = color;
	}
	
}
