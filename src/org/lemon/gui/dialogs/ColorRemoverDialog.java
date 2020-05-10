package org.lemon.gui.dialogs;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JWindow;

import org.lemon.colors.ColorRemover;
import org.lemon.gui.image.ImagePanel;
import org.lemon.gui.image.ImagePanel.PanelMode;
import org.lemon.gui.image.MiniImageView;

public class ColorRemoverDialog extends JWindow implements ActionListener {

	private static final long serialVersionUID = 1L;
	
	//input fields
	private JTextField preferredColor;
	private JPanel imgPanel, btnPanel;
	
	//ok button
	private JButton okBttn, closeBttn;
	
	//src
	private BufferedImage img;
	
	public ColorRemoverDialog(BufferedImage img) {
		this.img = img;
		this.init();
		
		setSize(img.getWidth() + 100, img.getHeight() + 100);
		setVisible(true);
		setLocation(new Point(200, 100));
		
		Container c = this.getContentPane();
		c.add(this.preferredColor, BorderLayout.NORTH);
		c.add(this.imgPanel);
		c.add(this.btnPanel, BorderLayout.SOUTH);
		
		//adding bttn
		this.btnPanel.add(this.okBttn);
		this.btnPanel.add(this.closeBttn);
		
		try {
			var pan = new ImagePanel(img, PanelMode.defaultMode);
			
			var handler = new MouseEventHandler();
			pan.addMouseListener(handler);
			new MiniImageView(pan);
			
			this.imgPanel.add(pan);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	//init widgets
	private void init() {
		this.preferredColor = new JTextField("255, 255, 255");
		this.imgPanel = new JPanel();
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
			
			var r = Integer.parseInt(col[0].trim());
			var g = Integer.parseInt(col[1].trim());
			var b = Integer.parseInt(col[2].trim());
			
			new ColorRemover(img, new Color(r, g, b));
			
			imgPanel.repaint();
		}
	}
	
	
	private class MouseEventHandler extends MouseAdapter {
		
		@Override
		public void mouseClicked(MouseEvent e) {
			super.mouseClicked(e);
			
			var x = e.getX();
			var y = e.getY();
			
			var c = new Color(img.getRGB(x, y));
			var cStr = String.valueOf(c.getRed() + ", " + c.getGreen() + ", " + c.getBlue());
			preferredColor.setText(cStr);
		}
	}
	
	
	
	
	
	
}
