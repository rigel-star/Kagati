package org.lemon.gui.node;

import java.awt.Color;

import org.lemon.filter.AbstractImageFilter;

public class FilterOutputNode extends OutputNode {
	private AbstractImageFilter filter;
	
	public FilterOutputNode(AbstractImageFilter filter) {
		super(Color.PINK);
		this.filter = filter;
	}
	
	public AbstractImageFilter getFilter() {
		return filter;
	}

	@Override
	public String getName() {
		return "Filter";
	}
}
