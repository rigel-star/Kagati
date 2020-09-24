package org.lemon.gui;

import org.lemon.gui.node.NodePt;
import org.lemon.lang.LemonObject;

/**
 * 
 * ControllerNode is sub-class of {@code Node} which is 
 * basically a controller which have {@code NodePt}s within it, 
 * which is used to control any {@code ControllableNode}.
 * <p>
 * Components which implements {@code ControllerNode} are basically
 * just the image filter parameter handlers. For e.g {@code BlurFilterNode}.
 * 
 * @author Ramesh Poudel
 * 
 * */
@LemonObject( type = LemonObject.HELPER_CLASS )
public interface ControllerNode extends Node {
	
	public NodePt[] getNodePts();
	
	public void updateNodePts();
	
}
