package org.lemon.gui;

import org.lemon.LemonObject;

/**
 * The component which can be controlled by {@code FilterController}
 * */

@LemonObject(type = LemonObject.HELPER_CLASS)
public interface FIlterControllable {
	
	public void setNode(Node node);
	public Node getNode();
	
}
