package org.lemon.gui;

import java.util.List;

import org.lemon.gui.node.NodePt;
import org.lemon.lang.LemonObject;

/**
 * 
 * ControllableNode is sub-class of {@code Node} which is 
 * basically a class which contains {@code NodePt}s within it
 * which are then used to controlled by {@code ControllerNode}s.
 * <p>
 * Components which implements {@code ControllableNode} are usually
 * the view holders which holds images to be modified or controlled
 * by {@code ControllerNode}.
 * 
 * @author Ramesh Poudel
 *
 **/
@LemonObject( type = LemonObject.HELPER_CLASS )
public interface ControllableNode extends Node {
	
	public void addController( ControllerNode controller );
	
	public List<ControllerNode> getControllers();
	
	public void updateNodePt();
	
	public NodePt getNodePt();
	
}
