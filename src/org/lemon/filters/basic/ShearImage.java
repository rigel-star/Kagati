package org.lemon.filters.basic;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSlider;

public class ShearImage extends JPanel {
	private static final long serialVersionUID = 1L;

	
	private BufferedImage src;
	private AffineTransform af = new AffineTransform();
	private float per;

	public ShearImage(BufferedImage src, float per) {
		
		this.src = src;
		this.per = per;
		
	}
	
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		af.shear(per, per);
		
		Graphics2D g2 = (Graphics2D) g;
		g2.setTransform(af);
		g2.drawImage(src, af, this);
	}
	
	
	public void changePer(float per) {
		af.shear(per, per);
	}
	
	
	
	public static void main(String[] args) throws IOException {
		
		var shearer = new ShearImage(ImageIO.read(new File("C:\\Users\\Ramesh\\Desktop\\opencv\\mack.jpg")), .1f);
		
		var f = new JFrame();
		f.add(shearer, BorderLayout.CENTER);
		
		var slider = new JSlider(JSlider.HORIZONTAL, 1, 10, 1);
		slider.setMajorTickSpacing(1);
		slider.setPaintTicks(true);
		
		f.add(slider, BorderLayout.SOUTH);
		
		f.setSize(new Dimension(600, 600));
		f.setResizable(false);
		f.setVisible(true);
		
		slider.addChangeListener(e -> {
			var p = slider.getValue() / 10;
			shearer.changePer(0);
			shearer.changePer(p);
			shearer.repaint();
		});
		
	}
	
}
