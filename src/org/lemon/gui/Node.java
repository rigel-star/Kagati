package org.lemon.gui;

import javax.swing.ImageIcon;

import org.lemon.gui.node.ReceiverNode;
import org.lemon.gui.node.SenderNode;

/**
 * 
 * This have two sub-classes (maybe for now), {@link ReceiverNode} 
 * and {@link SenderNode}.
 * 
 * @author Ramesh Poudel
 * 
 * */
public interface Node {
	
	/**
	 * Specify the type of node.
	 * */
	public enum NodeType {
		
		/**
		 * Can only receive data from {@link SenderNode}.
		 * */
		RECEIVER,
		
		/**
		 * Can only send data to {@link ReceiverNode}.
		 * */
		SENDER,
		
		/**
		 * Can do both, receive data from {@link SenderNode} 
		 * and send to {@link ReceiverNode}.
		 * */
		DUPLEX,
		
		/**
		 * Can send data to only {@link SenderNode}.
		 * */
		SENDER_UTILITY,
		
		/**
		 * Can send data to {@link CompositeNodeComponent}
		 * */
		UTILITY
		
	}
	
	/**
	 * @return Type of node.
	 * */
	NodeType getNodeType();
	
	/**
	 * Get icon of this {@link Node}.
	 * @return Icon of node.
	 * 
	 * */
	ImageIcon getNodeIcon();
}
