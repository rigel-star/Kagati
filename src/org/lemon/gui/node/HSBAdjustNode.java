package org.lemon.gui.node;

import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.image.BufferedImage;

import javax.swing.JComponent;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;

import org.lemon.gui.ControllerNode;
import org.lemon.gui.ImageView;
import org.lemon.lang.LemonObject;
import org.lemon.math.Vec2d;
import org.lemon.utils.Utils;

import org.rampcv.filters.Brightness;
import org.rampcv.filters.Saturation;


@LemonObject( type = LemonObject.GUI_CLASS )
public class HSBAdjustNode extends JInternalFrame implements 
																ChangeListener,
																Runnable,
																ControllerNode {

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
	
	private JComponent comp = null;
	private BufferedImage src = null, copy = null;
	
	
	private final static int HUE = 1, BRGT = 2, SATR = 3;
	private int operation = 0;
	
	
	public HSBAdjustNode() {
		
	}
	
	
	/**
	 * 
	 * GUI to increase or decrease the HSB values of an image.
	 * 
	 * @param comp the component which contains the image (must be {@code ImageView} or {@code DrawingCanvas})
	 * @param src the image to modify
	 * 
	 * */
	public HSBAdjustNode(JComponent comp, BufferedImage src) {
		this.init();
		this.comp = comp;
		this.src = src;
		this.copy = Utils.getImageCopy(src);
		
		
		/* 
		 * the connector node
		 * */
		var start = new Point(getLocation().x + this.getWidth(), getLocation().y + (this.getHeight() - 15));
		imgNode = new NodePt(new Vec2d(start), null, this);
		nodes[0] = imgNode;
		
		
		/* **TEST**
		 * setting image copy on ImageView before modifying
		 * */
		if(comp instanceof ImageView) {
			var view = (ImageView) comp;
			view.getImagePanel().setImage(copy);
			view.getImagePanel().revalidate();
		}
		
		
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
			operation = HSBAdjustNode.BRGT;
		}
		else if(e.getSource() == satrjs) {
			operation = HSBAdjustNode.SATR;
		}
		
		new Thread(this).start();
	}


	@Override
	public void run() {
		
		copy = Utils.getImageCopy(src);
		
		switch(operation) {
		
		case HSBAdjustNode.HUE: {
			break;
		}
		
		case HSBAdjustNode.SATR: {
			
			if(satrjs.getValue() == 0)
				return;
			
			new Saturation(copy, satrjs.getValue() / 10);
			break;
		}
		
		case HSBAdjustNode.BRGT: {
			
			if(brgtjs.getValue() == 0)
				return;
			
			new Brightness(copy, brgtjs.getValue() * 10);
			break;
		}
		
		default:
			break;
		
		}
		
		if(comp instanceof ImageView) {
			var v = (ImageView) comp;
			v.getImagePanel().setImage(copy);
			v.getImagePanel().revalidate();
		}
	}
	
	
	@Override
	public void updateNodePts() {
		imgNode.start.x = this.getLocation().x + this.getWidth();
		imgNode.start.y = this.getLocation().y + this.getHeight() - 30;
	}
	
	
	@Override
	public NodePt[] getNodePts() {
		return nodes;
	}
	
	
	/*
	 * Window operation handler like, window closing, opening etc...
	 * */
	private class WindowHandler extends InternalFrameAdapter {
		
		@Override
		public void internalFrameClosing(InternalFrameEvent e) {
			super.internalFrameClosing(e);
			
			if(comp instanceof ImageView) {
				var view = (ImageView) comp;
				
				/*
				 * if done adjusting image then finally override original image with edited one
				 * */
				src = copy;
				view.getImagePanel().setImage(src);
				view.getImagePanel().revalidate();
			}
		}
	}	
}
