package org.lemon.gui.node;

import java.awt.Color;

public abstract class Node 
{
	private Color color;
	
	public Node(Color color)
	{
		this.color = color;
	}
	
	public Color getColor()
	{
		return color;
	}
}
