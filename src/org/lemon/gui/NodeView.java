package org.lemon.gui;

import java.util.List;


import org.lemon.gui.node.Node;

public abstract class NodeView extends AbstractView
{
	private static final long serialVersionUID = 1L;
	
	private List<Node> senderNodes;
	private List<Node> receiverNodes;
	
	public NodeView(List<Node> receivers, List<Node> senders) 
	{
		this.senderNodes = senders;
		this.receiverNodes = receivers;
	}
	
	public List<Node> getSenderNodes()
	{
		return senderNodes;
	}
	
	public List<Node> getReceiverNodes()
	{
		return receiverNodes;
	}
}