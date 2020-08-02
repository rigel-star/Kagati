package org.lemon.gui.panels;

import java.awt.Color;
import java.awt.image.BufferedImage;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;


public class OpacityControlPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	
	private JTextField opacity;
	private JSlider opSlider;
	
	private BufferedImage img;
	
	public OpacityControlPanel(BufferedImage img) {
		this.img = img;
		
		this.opacity = new JTextField("100%");
		this.opSlider = new JSlider(JSlider.HORIZONTAL, 0, 100, 100);
		
		setBackground(Color.white);
		
		Box box = new Box(BoxLayout.X_AXIS);
		box.add(new JLabel("Opacity"));
		box.add(this.opSlider);
		
		add(box);
		
		//add(iframe);
		
	}
	
	/**
	 * Pass image on which to apply transparency effect.
	 * <p> @param {@code BufferedImage} object.
	 * */
	public void setImage(BufferedImage img) {
		this.img = img;
	}
	
	/**
	 * @return {@code BufferedImage}	currently selected {@code ImageView}'s image.
	 * */
	public BufferedImage getImage() {
		return this.img;
	}

	
}
