package org.lemon.gui;

import java.util.List;

import org.lemon.lang.LemonObject;

/**
 * The component which can be controlled by {@code FilterController}
 * */

@LemonObject(type = LemonObject.HELPER_CLASS)
public interface FilterControllable {
	
	public void addController(FilterController controller);
	
	public List<FilterController> getControllers();
	
	public void updateNode();
	
	public Node getNode();
	
	
}
