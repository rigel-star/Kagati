package org.lemon.gui;

import org.lemon.LemonObject;

/**
 * {@code FilterController}'s is used in filter controller GUI(like the {@code HSBController}) component to 
 * show it's connection with the other components in application.
 * */

@LemonObject(type = LemonObject.HELPER_CLASS)
public interface FilterController {
	
	public void addNode(Node node, int index);
	public Node[] getNodes();
	
}
