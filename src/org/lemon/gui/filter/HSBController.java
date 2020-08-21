package org.lemon.gui.filter;

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

import org.lemon.LemonObject;
import org.lemon.filters.basic.ImageHSB;
import org.lemon.gui.FilterController;
import org.lemon.gui.ImageView;
import org.lemon.gui.Node;
import org.lemon.math.Vec2d;
import org.lemon.utils.Utils;
import org.rampcv.filters.Brightness;
import org.rampcv.filters.Saturation;


@LemonObject(type = LemonObject.GUI_CLASS)
public class HSBController extends JInternalFrame implements 
																ChangeListener,
																Runnable,
																FilterController {

	private static final long serialVersionUID = 1L;
	
	/*node control points*/
	private final int nodeCount = 1;
	private Node imgNode = null;
	private Node[] nodes = new Node[nodeCount];
	
	//image node text
	private JLabel imgNodeTxt = new JLabel("Image");
	
	//global vars
	//jslider: hue, saturation and brightness slider
	private JSlider huejs, satrjs, brgtjs;
	
	//text fields
	private JLabel huetf, satrtf, brgttf;
	
	
	//slider and buttons panel
	private JPanel editPanel = null;
	
	private JComponent comp = null;
	private BufferedImage src = null, copy = null;
	
	
	private final static int HUE = 1, BRGT = 2, SATR = 3;
	private int operation = 0;
	
	
	public HSBController() {
		
	}
	
	
	/**
	 * @param comp the component which contains the image (must be {@code ImageView} or {@code DrawingCanvas})
	 * @param src the image which will be edited
	 * */
	public HSBController(JComponent comp, BufferedImage src) {
		this.init();
		this.comp = comp;
		this.src = src;
		this.copy = Utils.getImageCopy(src);
		
		
		/*the node which connects this HSB control panel to image panels*/
		var start = new Point(getLocation().x + this.getWidth(), getLocation().y + (this.getHeight() - 15));
		imgNode = new Node(new Vec2d(start), null, this);
		nodes[0] = imgNode;
		
		
		/*setting image copy on ImageView before modifying*/
		if(comp instanceof ImageView) {
			var view = (ImageView) comp;
			view.getImagePanel().setImage(copy);
			view.getImagePanel().revalidate();
		}
		
		
		setSize(225, 220);
		setResizable(false);
		setTitle("HSB Controller");
		setVisible(true);
		setClosable(true);
		setLocation(20, 50);
		
		
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
	
	
	//initializing components
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
	
	
	//all three sliders will have same functions so one method is
	//appealling to handle all 3
	private void addSliderFeature(JSlider js) {
		//js properties
		js.setPaintTicks(true);
		js.setMajorTickSpacing(1);
		js.setSize(110, 45);
	}


	@Override
	public void stateChanged(ChangeEvent e) {
		
		if(e.getSource() == brgtjs) {
			operation = HSBController.BRGT;
		}
		else if(e.getSource() == satrjs) {
			operation = HSBController.SATR;
		}
		
		new Thread(this).start();
	}


	@Override
	public void run() {
		
		copy = Utils.getImageCopy(src);
		
		switch(operation) {
		
		case HSBController.HUE: {
			break;
		}
		
		case HSBController.SATR: {
			
			if(satrjs.getValue() == 0)
				return;
			
			new Saturation(copy, satrjs.getValue() / 10);
			break;
		}
		
		case HSBController.BRGT: {
			
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
	
	
	
	/**
	 * increase the saturation of image
	 * */
	public void increaseSatr(BufferedImage src, float intensity) {
		
		for(int x=0; x<src.getHeight(); x++) {
			for(int y=0; y<src.getWidth(); y++) {
				var pix = ImageHSB.incSaturation(src.getRGB(x, y), intensity);
				src.setRGB(x, y, pix);
			}
		}
	}
	
	
	@Override
	public void updateNodes() {
		imgNode.start.x = this.getLocation().x + this.getWidth();
		imgNode.start.y = this.getLocation().y + this.getHeight() - 30;
	}
	
	
	@Override
	public Node[] getNodes() {
		return nodes;
	}
	
	
	/*Window operation handler like, window closing, opening etc...*/
	private class WindowHandler extends InternalFrameAdapter {
		
		@Override
		public void internalFrameClosing(InternalFrameEvent e) {
			super.internalFrameClosing(e);
			
			if(comp instanceof ImageView) {
				var view = (ImageView) comp;
				
				/*if done adjusting image then finally override original image with edited one*/
				src = copy;
				view.getImagePanel().setImage(src);
				view.getImagePanel().revalidate();
			}
			
		}
		
	}
	
	
	
	
}
