package org.lemon.gui.dialogs;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JWindow;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.lemon.gui.ImageView;
import org.lemon.gui.image.ImagePreviewPanel;
import org.rampcv.rampcv.RampCV;

public class DenoiseImageDialog extends JWindow implements ChangeListener, ActionListener {

	private static final long serialVersionUID = 1L;

	private ImagePreviewPanel 	previewPanel;
	
	private JPanel 				editPanel, imgPanel, bttnPanel;
	
	private JSlider 			slider;
	
	private BufferedImage 		img, copy;
	
	private JButton				okBttn, cnclBttn;
	
	private JComponent			container;
	
	
	
	/**
	 * Opens up image denosing dialog which removes noise from image using simple algorithm.
	 * @param container which contains image
	 * @param image which going to be denoised
	 * */
	public DenoiseImageDialog(JComponent container, BufferedImage img) {
		this.img = img;
		this.init(this.img);
		this.container = container;
		
		//frame properties
		setSize(400, 400);
		setVisible(true);
		setLocation(200, 100);
				
		Container c = this.getContentPane();
		c.add(this.imgPanel, BorderLayout.CENTER);
		c.add(this.editPanel, BorderLayout.SOUTH);
	}
	
	
	private void init(BufferedImage img) {
		
		this.previewPanel = new ImagePreviewPanel(img);
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
		
		imgPanel.add(previewPanel);
		imgPanel.add(bttnPanel);
		editPanel.add(slider);
		bttnPanel.add(okBttn);
		bttnPanel.add(cnclBttn);
	}

	@Override
	public void stateChanged(ChangeEvent e) {
		if(e.getSource() == this.slider) {
			
			int curr = this.slider.getValue();
			copy = previewPanel.getCopyImage();
			
			//starting another thread
			//to denoise the image
			new Thread(new Runnable() {
				
				@Override
				public void run() {
					//new AccessoriesRemover(imgPanel);
					RampCV.denoise(copy, curr);
					
					//set new image and revalidate
					//impanel.setIcon(new ImageIcon(copy));
					previewPanel.update(copy);
				}
			}).start();
			
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		if(e.getSource() == this.okBttn) {
			if(this.copy != null) {
				this.img = this.previewPanel.getFinalImage();
				((ImageView) this.container).getImagePanel().repaint();
				this.container.revalidate();
				this.dispose();
			}
		}
		
		else if(e.getSource() == this.cnclBttn) {
			this.dispose();
		}
	}

}
