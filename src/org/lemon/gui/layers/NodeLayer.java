package org.lemon.gui.layers;

import javax.swing.JComponent;

import org.lemon.gui.Layer;
import org.lemon.gui.node.NodeComponent;

/**
 * 
 * Like {@link ViewLayer} which holds {@link ReceiverNode}s 
 * (For e.g. {@link ImageView}), {@link NodeLayer} holds the 
 * {@link SenderNode}s (For e.g. {@link BlurFilterNodeComponent}). 
 * If any {@link SenderNode} added to the program then it 
 * will be attached to the new {@link NodeLayer} and shown in 
 * {@link LayerContainer}. 
 * <p>
 * {@link NodeLayer} won't hold {@link ReceiverNode}s 
 * because even if the {@link ReceiverNode}s are nodes, 
 * they belong to the view holder category because they hold 
 * images within themselves. So, {@link ReceiverNode}s are 
 * attached with {@link ViewLayer} instead of {@link NodeLayer}.
 * 
 * @author Ramesh Poudel
 * 
 * */
public class NodeLayer implements Layer {
	
	private JComponent comp = null;
	private String title = null;
	
	public NodeLayer( JComponent comp, String title ) {
		this.comp = comp;
		this.title = title;
		if ( comp instanceof NodeComponent ) {
			((NodeComponent) comp).attachLayer( this );
		}
	}
	
	@Override
	public void setTitle( String title ) {
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