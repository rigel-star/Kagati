package org.lemon.frames.alert_dialogs;

import java.awt.Container;
import java.awt.Cursor;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.lemon.image.ImageView;
import org.lemon.utils.AccessoriesRemover;

public class ImageCropDg extends JFrame implements MouseMotionListener, MouseListener, ActionListener{

	private static final long serialVersionUID = 1L;
	
	private BufferedImage img;
	private BufferedImage cropped;
	private JLabel imgLabel, previewLabel;
	private JPanel mainPanel;
	private JButton okBttn;
	
	//crop box
	private int startX = 50, startY = 50;
	private int height = 0, width = 0;
	
	public ImageCropDg(BufferedImage img, JPanel mainPanel) {
		this.img = img;
		this.cropped = img;
		this.mainPanel = mainPanel;
		this.imgLabel = new JLabel(new ImageIcon(this.img));
		this.previewLabel = new JLabel(new ImageIcon(this.img));
		this.okBttn = new JButton("OK");
		this.okBttn.addActionListener(this);
		
		int h = img.getHeight();
		int w = img.getWidth() * 2;
		
		setSize(w + 100, h);
		setTitle("Crop Image");
		setDefaultCloseOperation(ImageCropDg.DISPOSE_ON_CLOSE);
		setResizable(false);
		setVisible(true);
		this.imgLabel.addMouseMotionListener(this);
		this.imgLabel.addMouseListener(this);

		Container c = getContentPane();
		c.setLayout(new FlowLayout());
		c.add(this.imgLabel);
		c.add(this.previewLabel);
		c.add(this.okBttn);
	}

	public BufferedImage getCroppedImg() {
		return this.cropped;
	}
	
	@Override
	public void mouseDragged(MouseEvent e) {
		try {
			int finalX = e.getX(), finalY = e.getY();
			this.width = Math.abs(this.startX - finalX);
			this.height = Math.abs(this.startY - finalY);
			
			cropped = this.img.getSubimage(this.startX, this.startY, this.width, this.height);
			
			this.previewLabel.setIcon(new ImageIcon(cropped));
			this.repaint();
		} catch(Exception ex) {
			
		}
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		Cursor cur = new Cursor(Cursor.CROSSHAIR_CURSOR);
		this.imgLabel.setCursor(cur);
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		
		
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == this.okBttn) {
			new AccessoriesRemover(this.mainPanel);
			try {
				this.mainPanel.add(new ImageView(getCroppedImg()));
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			this.img = this.getCroppedImg();
			this.mainPanel.repaint();
			this.dispose();
		}
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		this.startX = e.getX();
		this.startY = e.getY();
 	}

	@Override
	public void mousePressed(MouseEvent e) {
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		
	}
}
