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
import org.lemon.filters.BlurImg;
import org.lemon.frames.ImageView;

public class BlurImgDg extends JFrame implements ChangeListener {

	private static final long serialVersionUID = 1L;
	
	private JPanel imgPanel;
	private JPanel editPanel;
	private JSlider slider;
	private BufferedImage img;
	
	//constructor
	public BlurImgDg(BufferedImage img) {
		this.init(img);
		this.img = img;
		//frame properties
		setSize(600, 600);
		setDefaultCloseOperation(BlurImgDg.DISPOSE_ON_CLOSE);
		setResizable(false);
		setTitle("Blur");
		setVisible(true);
		
		Container c = this.getContentPane();
		c.add(this.imgPanel, BorderLayout.CENTER);
		c.add(this.editPanel, BorderLayout.SOUTH);
	}
	
	//init components
	private void init(BufferedImage img) {
		this.imgPanel = new JPanel();
		this.editPanel = new JPanel();
		//slider properties
		this.slider = new JSlider(JSlider.HORIZONTAL, 0, 3, 0);
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
		new Thread(new BlurringThread(this.slider.getValue())).start();;
	}

	//thread helper class
	private class BlurringThread implements Runnable {

		private int i;
		public BlurringThread(int i) {
			this.i = i;
		}
		@Override
		public void run() {
			//removes existing image from img panel and add new blurred img
			new AccessoriesRemover(imgPanel);
			BlurImg bimg = new BlurImg(img, i);
			img = bimg.getBlurredImg();
			
			try {
				imgPanel.add(new ImageView(img));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}
	
}

