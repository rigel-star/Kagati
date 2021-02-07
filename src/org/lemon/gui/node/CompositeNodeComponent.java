package org.lemon.gui.node;

import org.lemon.math.Vec2;

import java.awt.Container;

import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JInternalFrame;

import org.lemon.filter.CompositeFilter;
import org.lemon.gui.NodePt;
import org.lemon.gui.NodePt.NodePtType;

public class CompositeNodeComponent extends JInternalFrame implements SenderNode {

	/**
	 * Serial UID
	 */
	private static final long serialVersionUID = 1L;
	
	private static NodePt compositePt = null;
	private static NodePt[] nodePts = new NodePt[1];
	
	private static JComboBox<CompositeFilter> composites = null;
	
	public CompositeNodeComponent() {
		
		this.init();
		setSize( 200, 300 );
		setTitle( "Composite" );
		setResizable( false );
		setVisible( true );
		setClosable( true );
		setLocation( 20, 50 );
		
		Container c = getContentPane();
		c.add( composites );
		
		var start = new Vec2( getLocation().x + getWidth(), getLocation().y + (getHeight() >> 1) );
		compositePt = new NodePt( start, null, this, NodePtType.SENDER );
		nodePts[0] = compositePt;
	}
	
	/**
	 * Initialize the widgets.
	 * */
	private void init() {
		composites = new JComboBox<>( new CompositeFilter[] {
				
		});
	}

	@Override
	public NodeType getNodeType() {
		return NodeType.SENDER;
	}

	@Override
	public NodePt[] getSenderNodePts() {
		return nodePts;
	}

	@Override
	public void updateSenderNodePts() {
		compositePt.start.x = getLocation().x + getWidth();
		compositePt.start.y = getLocation().y + ( getHeight() >> 1 );
	}

	@Override
	public ImageIcon getNodeIcon() {
		return null;
	}
}
