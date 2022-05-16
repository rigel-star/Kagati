package org.lemon.gui.node;

public class NodePair 
{
	private Node view1;
	private Node view2;
	
	public NodePair(Node view1, Node view2)
	{
		this.view1 = view1;
		this.view2 = view2;
	}
	
	public Node getNode1()
	{
		return view1;
	}
	
	public Node getNode2()
	{
		return view2;
	}
}
