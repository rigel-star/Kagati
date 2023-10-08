package org.lemon.gui.node;

import java.awt.Color;

public class FilterInputNode extends InputNode {	
	public FilterInputNode() {
		super(Color.PINK);
	}

	@Override
	public String getName() {
		return "Filter";
	}
}