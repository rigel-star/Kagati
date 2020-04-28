package org.lemon.frames.alert_dialogs;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JPanel;

import org.lemon.image.ImageView;
import org.lemon.utils.AccessoriesRemover;
import org.rampcv.rampcv.RampCV;

public class NegativeImageDialog extends JFrame {

	private static final long serialVersionUID = 1L;

	private JPanel main;
	private JPanel editPanel;
	private JPanel imagePanel;
	private JPanel bttnPanel;
	private BufferedImage src, original;
	
	//red, green and blue color inversion
	private JCheckBox redcb, greencb, bluecb;
	private JButton okBttn, cancelBttn;
	
	public NegativeImageDialog(BufferedImage src, JPanel panel) {
		this.init(src);
		this.original = src;
		this.main = panel;
		//frame properties
		setSize(src.getWidth() + 100, src.getHeight() + 100);
		setResizable(false);
		setDefaultCloseOperation(NegativeImageDialog.DISPOSE_ON_CLOSE);
		setTitle("Invert image");
		setBackground(Color.white);
		setVisible(true);
		
		Container c = this.getContentPane();
		c.add(this.editPanel, BorderLayout.NORTH);
		c.add(this.imagePanel, BorderLayout.CENTER);
		c.add(this.bttnPanel, BorderLayout.SOUTH);
	}
	
	private void init(BufferedImage src) {
		this.src = (src);
		this.editPanel = new JPanel(new FlowLayout());
		this.imagePanel = new JPanel();
		this.bttnPanel = new JPanel(new FlowLayout());
		this.redcb = new JCheckBox("Red");
		this.greencb = new JCheckBox("Green");
		this.bluecb = new JCheckBox("Blue");
		this.okBttn = new JButton("OK");
		this.cancelBttn = new JButton("Cancel");
		
		this.editPanel.add(redcb);
		this.editPanel.add(greencb);
		this.editPanel.add(bluecb);
		this.bttnPanel.add(okBttn);
		this.bttnPanel.add(cancelBttn);
		
		//init actions for components
		this.initActions();
		//if filters have applied
		this.filterObserver(src);
		
		try {
			this.imagePanel.add(new ImageView(this.src));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	private void filterObserver(BufferedImage src) {
		if(this.redcb.isEnabled()) {
			RampCV.invertColors(src, true, false, false);
		}
		if(this.greencb.isEnabled()) {
			RampCV.invertColors(src, false, true, false);
		}
		if(this.bluecb.isEnabled()) {
			RampCV.invertColors(src, false, false, true);
		}
	}
	
	//**********defining actions of components*************
	private void initActions() {
		ComponentsActionListener cal = new ComponentsActionListener();
		this.redcb.addActionListener(cal);
		this.greencb.addActionListener(cal);
		this.bluecb.addActionListener(cal);
		this.okBttn.addActionListener(cal);
		this.cancelBttn.addActionListener(cal);
	}
	
	//updating the imagePanel
	private void updateState(BufferedImage src, JPanel imagePanel) {
		new AccessoriesRemover(imagePanel);
		try {
			this.imagePanel.add(new ImageView(src));
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.revalidate();
	}
	
	/*HELPER CLASS::components action listener for real time preview*/
	private class ComponentsActionListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			if(e.getSource() == redcb) {
				this.inverision(true, false, false);
			}
			else if(e.getSource() == greencb) {
				this.inverision(false, true, false);
			}
			else if(e.getSource() == bluecb) {
				this.inverision(false, false, true);
			}
			else if(e.getSource() == okBttn) {
				dispose();
			}
			else if(e.getSource() == cancelBttn) {
				src = original;
				updateState(src, main);
				dispose();
			}
		}
		
		//inversing color
		private void inverision(boolean r, boolean g, boolean b) {
			new Thread(new Runnable() {		
				@Override
				public void run() {
					RampCV.invertColors(src, r, g, b);
					updateState(src, imagePanel);
				}
			}).start();
			
		}
		
	}
	
}
