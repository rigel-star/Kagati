package org.lemon.gui.filter;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

import javax.swing.JComboBox;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;

import org.lemon.LemonObject;
import org.lemon.gui.FilterControllable;
import org.lemon.gui.FilterController;
import org.lemon.gui.ImageView;
import org.lemon.gui.Node;
import org.lemon.math.Vec2d;

import org.rampcv.filters.blur.BoxBlur;


@LemonObject(type = LemonObject.GUI_CLASS)
public class BlurFilterController extends JInternalFrame implements FilterController, ActionListener {
	private static final long serialVersionUID = 1L;
	
	
	private Node[] nodes = new Node[1];
	private Node imgNode = null;
	
	//spin blur control widgets
	private JLabel degreeSliderTxt = new JLabel("Angle:");
	private JSlider degreeSlider = null;
	
	private JPanel controlPanel = null;
	
	private JLabel blurChooserTxt = new JLabel("Blurs:");
	private JComboBox<String> blurChooser = null;
	
	private JPanel spinBlurPanel = new JPanel();
	private JPanel blurChooserPanel = new JPanel();
	
	
	private final String GAUSS = "Gaussian Blur";
	private final String MOTION = "Motion Blur";
	private final String SPIN = "Spin Blur";
	private final String BOX = "Box Blur";
	
	private String choosenBlur = GAUSS;
	
	public BlurFilterController() {
		
		init();
		//initBlur();
		
		setSize(200, 140);
		setTitle("Blur Controller");
		setResizable(false);
		setVisible(true);
		setClosable(true);
		setLocation(20, 50);
		
		var start = new Point(getLocation().x + this.getWidth(), getLocation().y + (this.getHeight() / 2));
		imgNode = new Node(new Vec2d(start), null, this);
		nodes[0] = imgNode;
		
		blurChooserPanel.add(blurChooserTxt);
		blurChooserPanel.add(blurChooser);
		
		spinBlurPanel.add(degreeSliderTxt);
		spinBlurPanel.add(degreeSlider);
		
		controlPanel.add(blurChooserPanel);
		controlPanel.add(spinBlurPanel);
		
		Container c = getContentPane();
		c.add(controlPanel);
		
	}
	
	
	private void init() {
		degreeSlider = new JSlider(JSlider.HORIZONTAL, 0, 36, 0);
		degreeSlider.setMinorTickSpacing(2);
		degreeSlider.setPaintTicks(true);
		degreeSlider.setPreferredSize(new Dimension(130, 36));
		
		String[] blurs = {
				"Select",
				GAUSS,
				BOX,
				MOTION,
				SPIN
		};
		
		blurChooser = new JComboBox<String>(blurs);
		blurChooser.addActionListener(this);
		blurChooser.setPreferredSize(new Dimension(130, 40));
		
		spinBlurPanel = new JPanel();
		spinBlurPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		
		blurChooserPanel = new JPanel();
		blurChooserPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		
		controlPanel = new JPanel();
		controlPanel.setLayout(new FlowLayout(FlowLayout.TRAILING));
	}
	
	
	/*init the currently selected blur method*/
	private void initBlur() {
		
		switch (choosenBlur) {
		
		case SPIN: {
			/*adding extra options for spin blur*/
			degreeSlider.setEnabled(true);
			applyBlur(choosenBlur);
			break;
		}
		
		case MOTION:
		case BOX:
		case GAUSS: {
			degreeSlider.setEnabled(false);
			applyBlur(choosenBlur);
			break;
		}
		
		default: {
			degreeSlider.setEnabled(false);
			break;
		}
		
		}
		
		controlPanel.revalidate();
		revalidate();
	}
	
	
	private BufferedImage src = null;
	private Component view;
	private void applyBlur(String blur) {
		
		if(imgNode.getConnections().size() == 0) {
			System.out.println("No connections!");
			return;
		}
		
		for(FilterControllable fc: imgNode.getConnections()) {	
			
			if(fc instanceof ImageView) {
				view = (ImageView) fc;
				src = ((ImageView) view).getImagePanel().getImage();
			}
			
			new Thread(new Runnable() {
				
				@Override
				public void run() {
					
					switch(blur) {
					
					case BOX: {	
						var blur = new BoxBlur();
						blur.setRadius(5);
						src = blur.filter(src, null);
						((ImageView) view).getImagePanel().setImage(src);
					}
					
					}
				}
				
			}).start();
		}
		
		view.revalidate();
	}
	
	
	@Override
	public Dimension getPreferredSize() {
		return new Dimension(200, 140);
	}
	
	
	@Override
	public Node[] getNodes() {
		return nodes;
	}

	
	@Override
	public void updateNodes() {
		imgNode.start.x = this.getLocation().x + this.getWidth();
		imgNode.start.y = this.getLocation().y + (this.getHeight() / 2);
	}


	@Override
	public void actionPerformed(ActionEvent e) {
		
		if(e.getSource() == blurChooser) {
			choosenBlur = (String) blurChooser.getSelectedItem();
			initBlur();
		}
		
		spinBlurPanel.repaint();
		
	}

}
