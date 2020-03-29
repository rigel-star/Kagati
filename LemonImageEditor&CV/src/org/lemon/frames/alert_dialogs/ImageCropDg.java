package org.lemon.frames.alert_dialogs;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class ImageCropDg extends JFrame implements MouseMotionListener, MouseListener, ActionListener{

	private static final long serialVersionUID = 1L;
	
	private BufferedImage img;
	private JLabel imgLabel;
	private JButton okBttn;
	
	private int startX = 0, startY = 0, endX = 0, endY = 0;
	
	public ImageCropDg(BufferedImage img) {
		this.img = img;
		this.imgLabel = new JLabel(new ImageIcon(this.img));
		this.okBttn = new JButton("OK");
		this.okBttn.addActionListener(this);
		
		int h = img.getHeight();
		int w = img.getWidth();
		
		setSize(w + 100, h);
		setTitle("Crop Image");
		setDefaultCloseOperation(ImageCropDg.EXIT_ON_CLOSE);
		setResizable(false);
		setVisible(true);
		setLayout(new FlowLayout());
		addMouseMotionListener(this);
		add(this.imgLabel);
		add(this.okBttn);
	}
	
	private BufferedImage crop() {
		int h = Math.abs(this.startY - this.endY);
		int w = Math.abs(this.startX - this.endX);
		
		BufferedImage cropped = new BufferedImage(w, h, this.img.getType());
		Color col;
		
		for(int y=this.startY; y<this.endY; y++) {
			for(int x=this.startX; x<this.endX; x++) {
				col = new Color(this.img.getRGB(x, y));
				cropped.setRGB(x, y, col.getRGB());
			}
		}
		return cropped;
	}

	public BufferedImage getCroppedImg() {
		return this.img;
	}
	@Override
	public void mouseDragged(MouseEvent e) {
		this.startX = e.getX();
		this.startY = e.getY();
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		Cursor cur = new Cursor(Cursor.CROSSHAIR_CURSOR);
		this.imgLabel.setCursor(cur);
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		this.endX = e.getX();
		this.endY = e.getY();
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == this.okBttn) {
			this.img = this.crop();
			this.dispose();
		}
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
}
