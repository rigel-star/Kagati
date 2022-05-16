package org.lemon.gui.node;

import java.awt.Color;

import org.lemon.filter.AbstractImageFilter;

public class FilterSenderNode extends SenderNode 
{
	private AbstractImageFilter filter;
	
	public FilterSenderNode(AbstractImageFilter filter)
	{
		super(Color.PINK);
	}
	
	public AbstractImageFilter getFilter()
	{
		return filter;
	}
}
