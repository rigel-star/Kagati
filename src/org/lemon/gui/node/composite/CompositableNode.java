package org.lemon.gui.node.composite;

import org.lemon.gui.Node;
import org.lemon.gui.NodePt;

public interface CompositableNode extends Node {
	
	/**
	 * Get compositable node points.
	 * */
	NodePt[] getCompositableNodePts();
	
	
}
