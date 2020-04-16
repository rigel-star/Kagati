package org.lemon.frames.alert_dialogs;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.lemon.filters.BlurImg;
import org.lemon.image.ImageView;
import org.rampcv.utils.Tools;

public class BlurImgDg extends JFrame implements ChangeListener {

	private static final long serialVersionUID = 1L;
	
	private JPanel imgPanel;
	private JPanel editPanel;
	private JSlider slider;
	private BufferedImage img, original;
	private ImageView imgView;
	
	//constructor
	public BlurImgDg(BufferedImage img) {
		this.init(img);
		this.img = img;
		this.original = img;
		
		//frame properties
		setSize(img.getWidth() + 50, img.getHeight() + 100);
		setDefaultCloseOperation(BlurImgDg.DISPOSE_ON_CLOSE);
		setResizable(false);
		setTitle("Blur");
		setVisible(true);
		
		Container c = this.getContentPane();
		c.add(this.imgPanel, BorderLayout.CENTER);
		c.add(this.editPanel, BorderLayout.SOUTH);
		
	}
	
	public static void main(String[] args) throws IOException {
		new BlurImgDg(ImageIO.read(new File("C:\\Users\\Ramesh\\Desktop\\opencv\\flow.jpg")));
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
			this.imgView = new ImageView(img, false);
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.imgPanel.add(this.imgView);
	}

	@Override
	public void stateChanged(ChangeEvent e) {
		if(e.getSource() == this.slider)
			new Thread(new BlurringThread(this.slider.getValue())).start();
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
			img = Tools.copyImage(original);
			imgPanel.remove(imgView);
			BlurImg bimg = new BlurImg(img, i);
			img = bimg.getBlurredImg();
			try {
				imgView = new ImageView(img, false);
			} catch (IOException e) {
				e.printStackTrace();
			}
			imgPanel.add(imgView);
			revalidate();
		}
		
	}
	
}

