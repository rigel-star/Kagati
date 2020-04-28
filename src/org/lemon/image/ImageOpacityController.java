package org.lemon.image;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.image.BufferedImage;

import javax.swing.JInternalFrame;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.lemon.filters.Opacity;

public class ImageOpacityController extends JPanel implements ChangeListener {
	private static final long serialVersionUID = 1L;
	
	private JSlider opacity;
	
	private BufferedImage img;
	
	public ImageOpacityController(BufferedImage img) {
		this.img = img;
		
		this.opacity = new JSlider(JSlider.HORIZONTAL, 0, 10, 10);
		this.opacity.setPaintTicks(true);
		this.opacity.setMajorTickSpacing(1);
		this.opacity.setPaintLabels(true);
		this.opacity.setSize(150, 20);
		this.opacity.addChangeListener(this);
		
		setLayout(new BorderLayout());
		
		JInternalFrame iframe = new JInternalFrame("Opacity", true);
		iframe.setDefaultCloseOperation(JInternalFrame.DISPOSE_ON_CLOSE);
		iframe.setLayout(new BorderLayout());
		iframe.setVisible(true);
		iframe.setBackground(Color.white);
		iframe.add(this.opacity, BorderLayout.NORTH);
		
		add(iframe);
		
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

	@Override
	public void stateChanged(ChangeEvent e) {
		if(e.getSource() == this.opacity) {
			double op = (double) this.opacity.getValue() / 10.0;
			System.out.println("from opacity slider: " + op);
			new Opacity(img, (float) op);
		}
	}
	
}
