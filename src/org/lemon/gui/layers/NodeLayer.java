package org.lemon.gui.layers;

import javax.swing.ImageIcon;
import javax.swing.JComponent;

import org.lemon.gui.Layer;

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
public class NodeLayer implements Layer {
	
	private JComponent comp = null;
	private String title = null;
	private ImageIcon ic = null;
	
	public NodeLayer( JComponent comp, ImageIcon imgIcon, String title ) {
		this.comp = comp;
		this.title = title;
		this.ic = imgIcon;
	}
	
	
	public ImageIcon getIcon() {
		return ic;
	}
	
	
	@Override
	public void setTitle(String title) {
		this.title = title;
	}
	
	
	@Override
	public String getTitle() {
		return title;
	}
	
	
	@Override
	public JComponent getLayerComponent() {
		return comp;
	}
}