package org.lemon.gui.node;

import org.lemon.gui.Node;
import org.lemon.gui.NodePt;
import org.lemon.lang.LemonObject;

/**
 * 
 * SenderNode is sub-class of {@link Node} which is 
 * basically a command sender which have {@link NodePt}s within it, 
 * which is used to send commands to any {@link ReceiverNode}.
 * <p>
 * 
 * Components which implements {@link SenderNode} are basically just 
 * the image filter parameter handlers. For e.g {@link BlurFilterNodeComponent}.
 * 
 * @author Ramesh Poudel
 * 
 * */
@LemonObject( type = LemonObject.HELPER_CLASS )
public interface SenderNode extends Node {
	
	public NodePt[] getSenderNodePts();
	
	public void updateSenderNodePts();
	
}
