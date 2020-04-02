package org.lemon.drawing;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

public class DrawingCanvas extends MouseAdapter implements MouseMotionListener {

	private Canvas canvas = new Canvas();
	private Color choosenCol;
	
	private int startX = 0, startY = 0;
	private int endX = 0, endY = 0;
	
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
		
		//g.fillOval(x, y, 5, 5);
		g.draw3DRect(x, y, 100, 100, false);
		
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		
		int x, y;
		x = e.getX();
		y = e.getY();
		
		Graphics g = canvas.getGraphics();
		g.setColor(this.choosenCol);
		
		g.fillOval(x, y, 5, 5);
		
		if(this.startX == 0 && this.startY == 0) {
			this.startX = x;
			this.startY = y;
		}
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		
		//when mouse is moved, this class helps to change the mouse cursor
		Cursor cursor = new Cursor(Cursor.CROSSHAIR_CURSOR);
		//setting cursor to canvas
		canvas.setCursor(cursor);
		
	}
	
	@Override
	public void mouseReleased(MouseEvent e) {
		
//		this.endX = e.getX();
//		this.endY = e.getY();

	}

}
