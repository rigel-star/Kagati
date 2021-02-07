package org.lemon.gui.node;

import java.util.List;

import org.lemon.gui.Node;
import org.lemon.gui.NodePt;
import org.lemon.lang.LemonObject;

/**
 * 
 * ControllableNode is sub-class of {@link Node} which is 
 * basically a class which contains {@link NodePt}s within it
 * which are then used to receive from {@link SenderNode}s.
 * <p>
 * 
 * Components which implements {@link ReceiverNode} are usually
 * the view holders which holds images to be modified by 
 * {@link SenderNode}.
 * <p>
 * 
 * @author Ramesh Poudel
 *
 **/
@LemonObject( type = LemonObject.HELPER_CLASS )
public interface ReceiverNode extends Node {
	
	
	/**
	 * Add sender for this {@link ReceiverNode}.
	 * @param sndr 	node which can send commands to this node
	 * */
	public void addSender( SenderNode sndr );
	
	
	/**
	 * Get the list of {@link SenderNode}s which send commands 
	 * this {@link ReceiverNode}.
	 * @return List of senders.
	 * */
	public List<SenderNode> getSenders();
	
	
	/**
	 * Update the {@link NodePt} which this node contains.
	 * */
	public void updateReceiverNodePt();
	
	
	/**
	 * Get the {@link NodePt} which this node contains.
	 * @return {@link NodePt} attached within.
	 * */
	public NodePt getReceiverNodePt();
}
