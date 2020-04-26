package org.lemon.frames.alert_dialogs;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.lemon.image.ImageView;
import org.rampcv.rampcv.RampCV;
import org.rampcv.utils.Tools;

public class PixelateImgDg extends JFrame implements ChangeListener {

	private static final long serialVersionUID = 1L;

	//panels
	private JPanel imgPanel, editPanel;
	private BufferedImage original, copy;
	private JSlider slider;
	private ImageView copyImagePan;
	
	public PixelateImgDg(BufferedImage img) {
		this.original = img;
		this.init();
		setSize((img.getWidth()) + 150, img.getHeight() + 150);
		setResizable(false);
		setDefaultCloseOperation(PixelateImgDg.DISPOSE_ON_CLOSE);
		setTitle("Pixelate Image");
		setVisible(true);
		
		Container c = this.getContentPane();
		
		try {
			//for preview
			this.copyImagePan = new ImageView(this.original, "Preview");
			this.imgPanel.add(this.copyImagePan);
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.editPanel.add(this.slider);
		c.add(this.imgPanel, BorderLayout.CENTER);
		c.add(this.editPanel, BorderLayout.SOUTH);
	}
	
	private void init() {
		this.imgPanel = new JPanel();
		this.editPanel = new JPanel();
		
		this.imgPanel.setLayout(new FlowLayout());
		this.editPanel.setLayout(new FlowLayout());
		
		//slider properties
		this.slider = new JSlider(JSlider.HORIZONTAL, 0, 5, 0);
		this.slider.setPaintTicks(true);
		this.slider.setMajorTickSpacing(1);
		this.slider.setPaintLabels(true);
		this.slider.setSize(150, 30);
		this.slider.addChangeListener(this);
	}

	//slider bar value
	private int value;
	
	@Override
	public void stateChanged(ChangeEvent e) {
		if(e.getSource() == this.slider) {
			copy = Tools.copyImage(original);
			this.value = this.slider.getValue();
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
			new Thread(new Runnable() {
				
				@Override
				public void run() {	
					try {
						RampCV.pixelate(copy, value);
						imgPanel.remove(copyImagePan);
						copyImagePan = new ImageView(copy, "Preview");
						imgPanel.add(copyImagePan);
						imgPanel.revalidate();
					} catch (IOException e) {
						e.getMessage();
					}
				}
			}).start();;
		}
	}
	
}
