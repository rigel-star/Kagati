package org.lemon.drawing;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class DrawingCanvasOnImage extends Canvas implements MouseListener, MouseMotionListener{

	private static final long serialVersionUID = 1L;
	
	public DrawingCanvasOnImage() {
		addMouseListener(this);
		addMouseMotionListener(this);
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		
		int x = getX();
		int y = getY();
		
		Graphics g = getGraphics();
		g.setColor(Color.RED);
		
		g.fillOval(x, y, 5, 5);
		
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		int x = getX();
		int y = getY();
		
		Graphics g = getGraphics();
		g.setColor(Color.RED);
		
		g.fillOval(x, y, 5, 5);
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
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
