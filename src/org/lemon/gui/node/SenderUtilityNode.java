package org.lemon.gui.node;

import org.lemon.gui.Node;
import org.lemon.gui.NodePt;

public interface SenderUtilityNode extends Node {
	
	public void addReceiver( SenderNode snode );
	
	public NodePt[] getNodePts();
	
	public void updateNodePts();
	
}
