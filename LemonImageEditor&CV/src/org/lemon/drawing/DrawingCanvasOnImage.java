package org.lemon.drawing;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

import org.lemon.image.ImagePanel;

/**
 * Class description: This class takes an image as param and applies canvas in it.
 * This class is mainly used for drawing shapes in image using BufferedImage.
 * Canvas is java class which is mainly used for drawing shapes.
 * */
public class DrawingCanvasOnImage implements MouseMotionListener{
	
	//global
	private Graphics g2d;
	private ImagePanel ip;
	
	public DrawingCanvasOnImage(ImagePanel panel) {
		//assigning globals
		this.ip = panel;
		this.g2d = ip.getCurrentImg().getGraphics();
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		
		int x = e.getX();
		int y = e.getY();
		
		Color c = new Color(255, 0, 0);
		this.g2d.setColor(c);
		
		//filling oval of size 3X3 wherever the mouse goes
		this.g2d.fillOval(x, y, 10, 10);
		//this.g2d.dispose();
		this.ip.repaint();
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		Cursor cursor = new Cursor(Cursor.CROSSHAIR_CURSOR);
		this.ip.setCursor(cursor);
	}
	
}
