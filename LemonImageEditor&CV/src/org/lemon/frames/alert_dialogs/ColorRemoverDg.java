package org.lemon.frames.alert_dialogs;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.lemon.accessories.AccessoriesRemover;
import org.lemon.colors.ColorRemover;
import org.lemon.frames.NewImagePanel;

public class ColorRemoverDg extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1L;
	
	//input fields
	private JTextField preferredColor;
	private JPanel imgPanel, btnPanel;
	
	//ok button
	private JButton okBttn, closeBttn;
	
	//src
	BufferedImage img;
	
	public ColorRemoverDg(BufferedImage img) {
		this.img = img;
		this.init();
		setSize(img.getWidth() + 100, img.getHeight() + 100);
		setTitle("Smart Color Remover");
		setDefaultCloseOperation(ColorRemoverDg.DISPOSE_ON_CLOSE);
		setResizable(false);
		setVisible(true);
		
		this.getContentPane().add(this.preferredColor, BorderLayout.NORTH);
		this.getContentPane().add(this.imgPanel);
		this.getContentPane().add(this.btnPanel, BorderLayout.SOUTH);
		
		//adding bttn
		this.btnPanel.add(this.okBttn);
		this.btnPanel.add(this.closeBttn);
		
		try {
			this.imgPanel.add(new NewImagePanel(img));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	//init widgets
	private void init() {
		this.preferredColor = new JTextField("eg. 255, 255, 255");
		this.imgPanel = new JPanel(new FlowLayout());
		this.btnPanel = new JPanel(new FlowLayout());
		this.okBttn = new JButton("Remove");
		this.closeBttn = new JButton("Close");
		this.okBttn.addActionListener(this);
		this.closeBttn.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == this.okBttn) {
			
			//start remove thread
			new Thread(new RemoverThread()).start();
			
		}
		else if(e.getSource() == this.closeBttn) {
			this.dispose();
		}
	}

	//helper class to implement thread based color remover
	private class RemoverThread implements Runnable {
		
		@Override
		public void run() {
			String[] col = preferredColor.getText().toString().split(",");
			
			new AccessoriesRemover(imgPanel);
			new ColorRemover(img, new Color(Integer.parseInt(col[0].trim()),
					Integer.parseInt(col[1].trim()), Integer.parseInt(col[1].trim())));
			try {
				imgPanel.add(new NewImagePanel(img));
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}
	
	
}
