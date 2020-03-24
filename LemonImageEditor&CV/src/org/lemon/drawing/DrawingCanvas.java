package org.lemon.drawing;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

public class DrawingCanvas extends MouseAdapter implements MouseMotionListener {

	private Canvas canvas = new Canvas();
	private Color choosenCol;
	
	public DrawingCanvas(Color choosenCol) {
		
		this.choosenCol = choosenCol;
		
		canvas.setBackground(Color.WHITE);
		canvas.addMouseMotionListener(this);
		canvas.addMouseListener(this);
	}
	
	public Canvas getCanvas() {
		return this.canvas;
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		
		int x, y;
		x = e.getX();
		y = e.getY();
		
		Graphics g = canvas.getGraphics();
		g.setColor(this.choosenCol);
		
		g.fillOval(x, y, 5, 5);
		
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		
		int x, y;
		x = e.getX();
		y = e.getY();
		
		Graphics g = canvas.getGraphics();
		g.setColor(this.choosenCol);
		
		g.fillOval(x, y, 5, 5);
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		
	}

}
