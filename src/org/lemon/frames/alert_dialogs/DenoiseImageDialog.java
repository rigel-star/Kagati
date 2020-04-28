package org.lemon.frames.alert_dialogs;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JWindow;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.lemon.image.ImagePanel;
import org.lemon.image.ImagePanel.PanelMode;
import org.rampcv.rampcv.RampCV;
import org.rampcv.utils.Tools;

public class DenoiseImageDialog extends JWindow implements ChangeListener, ActionListener {

	private static final long serialVersionUID = 1L;

	private ImagePanel 			impanel;
	
	private JPanel 				editPanel, imgPanel, bttnPanel;
	
	private JSlider 			slider;
	
	private BufferedImage 		img, copy;
	
	private JButton				okBttn, cnclBttn;
	
	private JComponent			container;
	
	
	
	public DenoiseImageDialog(JComponent container, BufferedImage img) {
		this.init(img);
		this.img = img;
		this.container = container;
		
		//frame properties
		setSize(img.getWidth() + 100, img.getHeight() + 50);
		setVisible(true);
		setLocation(200, 100);
				
		Container c = this.getContentPane();
		c.add(this.imgPanel, BorderLayout.CENTER);
		c.add(this.editPanel, BorderLayout.SOUTH);
	}
	
	
	private void init(BufferedImage img) {
		
		this.impanel = new ImagePanel(img, PanelMode.defaultMode);
		this.editPanel = new JPanel();
		this.imgPanel = new JPanel();
		this.bttnPanel = new JPanel();
		
		BoxLayout btnPanelLayout = new BoxLayout(bttnPanel, BoxLayout.Y_AXIS);
		
		okBttn = new JButton("OK");
		okBttn.addActionListener(this);
		
		cnclBttn = new JButton("Cancel");
		cnclBttn.addActionListener(this);
		
		bttnPanel.setLayout(btnPanelLayout);
		
		//slider properties
		this.slider = new JSlider(JSlider.HORIZONTAL, 0, 5, 0);
		this.slider.setPaintTicks(true);
		this.slider.setMajorTickSpacing(1);
		this.slider.setPaintLabels(true);
		this.slider.addChangeListener(this);
		
		imgPanel.add(impanel);
		imgPanel.add(bttnPanel);
		editPanel.add(slider);
		bttnPanel.add(okBttn);
		bttnPanel.add(cnclBttn);
	}

	@Override
	public void stateChanged(ChangeEvent e) {
		if(e.getSource() == this.slider) {
			
			int curr = this.slider.getValue();
			copy = Tools.copyImage(img);
			
			//starting another thread
			//to denoise the image
			new Thread(new Runnable() {
				
				@Override
				public void run() {
					//new AccessoriesRemover(imgPanel);
					RampCV.denoise(copy, curr);
					
					//set new image and revalidate
					impanel.setIcon(new ImageIcon(copy));
					imgPanel.revalidate();
				}
			}).start();
			
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		if(e.getSource() == this.okBttn) {
			if(this.copy != null) {
				this.img = copy;
				this.container.revalidate();
				this.dispose();
			}
		}
		
		else if(e.getSource() == this.cnclBttn) {
			this.dispose();
		}
	}

}
