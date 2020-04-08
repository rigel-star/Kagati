package org.lemon.frames.alert_dialogs;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.lemon.accessories.AccessoriesRemover;
import org.lemon.frames.ImageView;
import org.rampcv.rampcv.RampCV;

public class DenoiseImgDg extends JFrame implements ChangeListener {

	private static final long serialVersionUID = 1L;

	private JPanel imgPanel;
	private JPanel editPanel;
	private JSlider slider;
	private BufferedImage img, copy;
	
	public DenoiseImgDg(BufferedImage img) {
		this.init(img);
		this.img = img;
		//frame properties
		setSize(600, 600);
		setResizable(false);
		setDefaultCloseOperation(InvertColorDg.DISPOSE_ON_CLOSE);
		setTitle("Denoise");
		setVisible(true);
				
		Container c = this.getContentPane();
		c.add(this.imgPanel, BorderLayout.CENTER);
		c.add(this.editPanel, BorderLayout.SOUTH);
	}
	
	private void init(BufferedImage img) {
		this.imgPanel = new JPanel();
		this.editPanel = new JPanel();
		//slider properties
		this.slider = new JSlider(JSlider.HORIZONTAL, 0, 5, 0);
		this.slider.setPaintTicks(true);
		this.slider.setMajorTickSpacing(1);
		this.slider.setPaintLabels(true);
		this.slider.setSize(150, 30);
		this.slider.addChangeListener(this);
		
		this.editPanel.add(slider);
		try {
			this.imgPanel.add(new ImageView(img));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public void stateChanged(ChangeEvent e) {
		if(e.getSource() == this.slider) {
			
			int curr = this.slider.getValue();
			
			copy = img;
			
			//starting another thread
			//to denoise the image
			new Thread(new Runnable() {
				
				@Override
				public void run() {
					new AccessoriesRemover(imgPanel);
					RampCV.denoise(copy, curr);
				}
			}).start();
			
			try {
				this.imgPanel.add(new ImageView(copy));
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}

}
