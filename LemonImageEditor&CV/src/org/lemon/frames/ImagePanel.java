package org.lemon.frames;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.border.Border;

public class ImagePanel extends JLabel implements MouseMotionListener{
	
	private static final long serialVersionUID = 1L;
	
	public BufferedImage img;
	
	public ImagePanel() {}

	public ImagePanel(BufferedImage img) throws IOException {
		
		this.img = img;
		//layout for panel
		//BoxLayout layout = new BoxLayout(getContentPane(), BoxLayout.Y_AXIS);
				
		Border border = BorderFactory.createLineBorder(Color.GRAY, 1);
		setBorder(border);
		setSize(400, 400);
		addMouseMotionListener(this);
		setIcon(new ImageIcon(img));
		setBackground(Color.WHITE);
	}
	
	//returns the current selected image
	public BufferedImage getCurrentImg() {
		return this.img;
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		Cursor cursor = new Cursor(Cursor.CROSSHAIR_CURSOR);
		setCursor(cursor);
	}
	
}
