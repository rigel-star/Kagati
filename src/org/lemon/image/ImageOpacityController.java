package org.lemon.image;

import java.awt.Color;
import java.awt.image.BufferedImage;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;


public class ImageOpacityController extends JPanel implements ChangeListener {
	private static final long serialVersionUID = 1L;
	
	private JTextField opacity;
	
	private BufferedImage img;
	
	public ImageOpacityController(BufferedImage img) {
		this.img = img;
		
		this.opacity = new JTextField("100%");
		
		setBackground(Color.white);
		
		Box box = new Box(BoxLayout.X_AXIS);
		box.add(new JLabel("Opacity"));
		box.add(this.opacity);
		
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

	@Override
	public void stateChanged(ChangeEvent e) {
		
	}
	
}
