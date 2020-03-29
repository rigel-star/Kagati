package org.lemon.frames;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.border.Border;


public class ImagePanel extends JLabel implements MouseListener, MouseMotionListener{
	
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
		addMouseListener(this);
		
		setIcon(new ImageIcon(img));
	}
	
	//returns the current selected image
	public BufferedImage getCurrentImg() {
		return this.img;
	}
	
	//draws on image
	public void draw() {
		int x = getX();
		int y = getY();
		Graphics2D g2d = this.img.createGraphics();
		g2d.fill(new Ellipse2D.Double(x, y, 10, 10));
		g2d.dispose();
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		draw();
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		Cursor cursor = new Cursor(Cursor.CROSSHAIR_CURSOR);
		setCursor(cursor);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		draw();
	}

	@Override
	public void mousePressed(MouseEvent e) {
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		
	}
	
}
