package org.lemon.gui.layers;

/**
 * 
 * Like {@code Layer} which holds image, {@code NodeLayer} 
 * holds the {@code ControllerNode}s. For e.g. if any 
 * {@code ControllerNode} added to the program then it 
 * will be attached to the new {@code NodeLayer} and shown 
 * in {@code LayerContainer}. 
 * <p>
 * {@code NodeLayer} won't not hold {@code ControllableNode}s 
 * because even if the {@code ControllableNode}s are nodes, 
 * they belong to the view holder category because they hold 
 * images within themselves. So, {@code ControllableNode}s are 
 * attached with {@code Layer} instead of {@code NodeLayer}.
 * 
 * @author Ramesh Poudel
 * 
 * */
public class NodeLayer {
	
	public NodeLayer() {
		
	}
}
