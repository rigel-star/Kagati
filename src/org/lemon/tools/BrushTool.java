package org.lemon.tools;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.geom.Line2D;

import org.lemon.tools.brush.PencilTool;
import org.lemon.tools.brush.SoftBrushTool;
import org.lemon.tools.brush.WobbleBrushTool;
import org.lemon.tools.brush.ZigzagBrushTool;

public class BrushTool implements LemonTool {
	public static final Color defaultStrokeColor = Color.black;
	public static final int defaultStrokeSize = 5;
	
	private Graphics2D context;
	private int strokeSize = BrushTool.defaultStrokeSize;
	private Color strokeColor = BrushTool.defaultStrokeColor;
	private Stroke stroke;
	
	public BrushTool(Graphics2D g)
	{
		this(g, new BasicStroke(BrushTool.defaultStrokeSize));
	}
	
	public BrushTool(Graphics2D g2d, Stroke stroke)
	{
		this.context = g2d;
		this.stroke = stroke;
		this.context.setStroke(stroke);
	}
	
	public Graphics2D getContext()
	{
		return context;
	}
	
	public void setStrokeSize(int size)
	{
		if(size <= 0)
			size = 1;
		
		this.strokeSize = size;
	}
	
	public int getStrokeSize()
	{
		return this.strokeSize;
	}
	
	public void setStrokeColor(Color color)
	{
		this.strokeColor = color;
	}
	
	public Color getStrokeColor()
	{
		return this.strokeColor;
	}
	
	public Stroke getStroke()
	{
		return this.stroke;
	}
	
	public void setStroke(Stroke stroke)
	{
		this.context.setStroke(stroke);
		this.stroke = stroke;
	}
	
	public void draw(int newX, int newY, int oldX, int oldY)
	{
		context.draw(new Line2D.Double(newX, newY, oldX, oldY));
	}
	
	/*
	 * Brush types
	 * */
	public static enum BrushType
	{
		NORMAL,
		PENCIL,
		ZIGZAG,
		WOBBLE,
		SOFT
	}
	
	/*
	 * Brush tool builder
	 * */
	public static class Builder
	{
		private BrushTool tool;
		
		public Builder(Graphics2D g2d, BrushType type)
		{
			switch(type)
			{
				case NORMAL:
					this.tool = new BrushTool(g2d);
					break;
					
				case PENCIL:
					this.tool = new PencilTool(g2d);
					break;
					
				case ZIGZAG:
					this.tool = new ZigzagBrushTool(g2d);
					break;
					
				case WOBBLE:
					this.tool = new WobbleBrushTool(g2d);
					break;
				
				case SOFT:
					this.tool = new SoftBrushTool(g2d);
					break;
					
				default:
					System.out.println("Brush invalid!");
			}
		}
		
		public BrushTool build()
		{
			return tool;
		}
	}
}