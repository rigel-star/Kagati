package org.lemon.gui.node;

import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.Stack;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;

import org.lemon.gui.ImageView;
import org.lemon.gui.LayerContainer;
import org.lemon.gui.Node;
import org.lemon.gui.NodePt;
import org.lemon.gui.NodePt.NodePtType;
import org.lemon.lang.LemonObject;
import org.lemon.lang.NonNull;
import org.lemon.math.Vec2;
import org.lemon.utils.Utils;

import org.rampcv.filters.Brightness;
import org.rampcv.filters.Saturation;

@LemonObject( type = LemonObject.GUI_CLASS )
public class HSBAdjustNodeComponent extends NodeComponent implements ChangeListener, Runnable, SenderNode {

	private static final long serialVersionUID = 1L;
	
	/* node control points */
	private final int nodeCount = 1;
	private NodePt imgNode = null;
	private NodePt[] nodes = new NodePt[nodeCount];
	
	//image node text
	private JLabel imgNodeTxt = new JLabel( "Image" );
	
	/*
	 * jslider: hue, saturation and brightness
	 * */
	private JSlider huejs, satrjs, brgtjs;
	
	/* 
	 * text fields 
	 * */
	private JLabel huetf, satrtf, brgttf;
	
	
	/* 
	 * slider and buttons panel 
	 * */
	private JPanel editPanel = null;
	
	private BufferedImage copy = null;
	
	private final static int HUE = 1, BRGT = 2, SATR = 3;
	private int operation = 0;
	
	/**
	 * GUI to increase or decrease the HSB values of an image.
	 * 
	 * @param comp the component which contains the image (must be {@code ImageView} or {@code DrawingCanvas})
	 * @param src the image to modify
	 * 
	 * */
	public HSBAdjustNodeComponent( @NonNull LayerContainer lycont) {
		super( lycont );
		this.init();
		
		/* 
		 * the connector node
		 * */
		var start = new Point( getLocation().x + this.getWidth(), getLocation().y + ( this.getHeight() - 15) );
		imgNode = new NodePt( new Vec2(start), null, this, NodePtType.SENDER );
		nodes[0] = imgNode;
		
		setSize( 225, 220 );
		setResizable( false );
		setTitle( "HSB" );
		setVisible( true );
		setClosable( true );
		setLocation( 20, 50 );
		
		
		editPanel.setLayout(new FlowLayout(FlowLayout.TRAILING));
		editPanel.add(huetf);
		editPanel.add(huejs);
		
		editPanel.add(satrtf);
		editPanel.add(satrjs);
		
		editPanel.add(brgttf);
		editPanel.add(brgtjs);
		
		editPanel.add(imgNodeTxt);
		
		Container c = getContentPane();
		c.add(editPanel);
		
		addInternalFrameListener(new WindowHandler());
	}
	
	
	/* initializing components */
	private void init() {
		this.huejs = new JSlider(JSlider.HORIZONTAL, 0, 25, 0);
		this.huetf = new JLabel("Hue");
		this.addSliderFeature(this.huejs);
		
		this.satrjs = new JSlider(JSlider.HORIZONTAL, 0, 25, 0);
		this.satrtf = new JLabel("Saturation");
		this.addSliderFeature(this.satrjs);
		satrjs.addChangeListener(this);
		
		this.brgtjs = new JSlider(JSlider.HORIZONTAL, 0, 25, 0);
		this.brgttf = new JLabel("Brightness");
		this.addSliderFeature(this.brgtjs);
		brgtjs.addChangeListener(this);
		
		this.editPanel = new JPanel(new GridLayout(3, 3));
	}
	
	/*
	 * all three sliders will have same functions
	 * */
	private void addSliderFeature(JSlider js) {
		js.setPaintTicks(true);
		js.setMajorTickSpacing(1);
		js.setSize(110, 45);
	}

	@Override
	public void stateChanged(ChangeEvent e) {
		
		if(e.getSource() == brgtjs) {
			operation = HSBAdjustNodeComponent.BRGT;
		}
		else if(e.getSource() == satrjs) {
			operation = HSBAdjustNodeComponent.SATR;
		}
		
		new Thread(this).start();
	}

	@Override
	public void run() {
		
		for ( NodePt np: getSenderNodePts() ) {
			Stack<Node> nds = np.getConnections();
			
			for ( Node nd: nds ) {
				if ( nd instanceof ImageView ) {
					copy = Utils.getImageCopy( ((ImageView) nd).getCurrentImage() );
					
					switch(operation) {
					
					case HSBAdjustNodeComponent.HUE: {
						break;
					}
					
					case HSBAdjustNodeComponent.SATR: {
						
						if(satrjs.getValue() == 0)
							return;
						
						new Saturation(copy, satrjs.getValue() / 10);
						break;
					}
					
					case HSBAdjustNodeComponent.BRGT: {
						
						if(brgtjs.getValue() == 0)
							return;
						
						new Brightness(copy, brgtjs.getValue() * 10);
						break;
					}
					
					default:
						break;
					}
				}
			}
			((ImageView) np.getComponent()).getImagePanel().setImage( copy );
		}
	}
	
	@Override
	public void updateSenderNodePts() {
		imgNode.start.x = this.getLocation().x + this.getWidth();
		imgNode.start.y = this.getLocation().y + this.getHeight() - 30;
	}
	
	@Override
	public NodePt[] getSenderNodePts() {
		return nodes;
	}
	
	@Override
	public NodeType getNodeType() {
		return NodeType.SENDER;
	}
	
	@Override
	public ImageIcon getNodeIcon() {
		return new ImageIcon( "icons/layer/adjust.png" );
	}
	
	/*
	 * Window operation handler like, window closing, opening etc...
	 * */
	private class WindowHandler extends InternalFrameAdapter {
		
		@Override
		public void internalFrameClosing(InternalFrameEvent e) {
			super.internalFrameClosing(e);
		}
	}	
}
