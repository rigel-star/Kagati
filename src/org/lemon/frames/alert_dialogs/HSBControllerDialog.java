package org.lemon.frames.alert_dialogs;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSlider;

@SuppressWarnings("unused")
public class HSBControllerDialog extends JFrame {

	private static final long serialVersionUID = 1L;
	
	//global vars
	//jslider: hue, saturation and brightness slider
	private JSlider huejs, satrjs, brgtjs;
	//images will be displayed in this panel
	private JPanel imgPanel;
	//slider and buttons panel
	private JPanel editPanel;
	
	public HSBControllerDialog() {
		this.init();
		
		//img properties
		//int h = img.getHeight();
		//int w = img.getWidth();
		
		setSize((500 + 100), (500 + 200));
		setResizable(false);
		setDefaultCloseOperation(HSBControllerDialog.EXIT_ON_CLOSE);
		setTitle("HSB Controller");
		setVisible(true);
		
		this.editPanel.add(this.huejs);
		this.editPanel.add(this.satrjs);
		this.editPanel.add(this.brgtjs);
		
		Container c = this.getContentPane();
		c.add(this.editPanel, BorderLayout.SOUTH);
	}
	
	public static void main(String[] args) {
		new HSBControllerDialog();
	}

	//initializing components
	private void init() {
		this.huejs = new JSlider(JSlider.HORIZONTAL, 0, 5, 0);
		this.addSliderFeature(this.huejs);
		this.satrjs = new JSlider(JSlider.HORIZONTAL, 0, 5, 0);
		this.addSliderFeature(this.satrjs);
		this.brgtjs = new JSlider(JSlider.HORIZONTAL, 0, 5, 0);
		this.addSliderFeature(this.brgtjs);
		
		this.imgPanel = new JPanel();
		this.editPanel = new JPanel(new GridLayout(3, 3));
	}
	//all three sliders will have same functions so one method is
	//appealling to handle all 3
	private void addSliderFeature(JSlider js) {
		//js properties
		js.setPaintTicks(true);
		js.setMajorTickSpacing(1);
		js.setPaintLabels(true);
		js.setSize(150, 30);
	}
}
