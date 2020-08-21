package org.lemon.gui.filter;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;

import org.lemon.gui.FilterController;
import org.lemon.gui.Node;
import org.lemon.math.Vec2d;

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
	
	private boolean showDegreeSlider = false;
	
	public BlurFilterController() {
		
		init();
		
		setSize(200, 140);
		setTitle("Blur Controller");
		setResizable(false);
		setVisible(true);
		setClosable(true);
		setLocation(20, 50);
		
		var size = getSize();
		var loc = getLocation();
		var pt = new Point(loc.x + (size.width / 2), loc.y + (size.height / 2));
		imgNode = new Node(new Vec2d(pt), null, this);
		
		nodes[0] = imgNode;
		
		blurChooserPanel.add(blurChooserTxt);
		blurChooserPanel.add(blurChooser);
		
		if(showDegreeSlider) {
			spinBlurPanel.add(degreeSliderTxt);
			spinBlurPanel.add(degreeSlider);
		}
		
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
				GAUSS,
				MOTION,
				BOX,
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
	
	
	private void initBlur(String bl) {
		
		if(bl.equals("Spin Blur"))
			choosenBlur = SPIN;
		else if(bl.equals("Gaussian Blur"))
			choosenBlur = GAUSS;
		else if(bl.equals("Box Blur"))
			choosenBlur = BOX;
		else if(bl.equals("Motion Blur"))
			choosenBlur = MOTION;
		
		switch (choosenBlur) {
		
		case SPIN: {
			showDegreeSlider = true;
			spinBlurPanel.revalidate();
			break;
		}

		default: {
			break;
		}
		
		}
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
		var size = getSize();
		var loc = getLocation();
		var pt = new Point(loc.x + size.width, loc.y + (size.height / 2));
		imgNode.start.x = pt.x;
		imgNode.start.y = pt.y;
	}


	@Override
	public void actionPerformed(ActionEvent e) {
		
		if(e.getSource() == blurChooser) {
			String bl = (String) blurChooser.getSelectedItem();
			initBlur(bl);
			System.out.println(choosenBlur);
		}
		
		spinBlurPanel.repaint();
		
	}

}
