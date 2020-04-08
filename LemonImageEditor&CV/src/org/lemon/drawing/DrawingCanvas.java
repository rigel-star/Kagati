package org.lemon.drawing;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;

public class DrawingCanvas extends Canvas implements MouseMotionListener, MouseListener {

	private static final long serialVersionUID = 1L;
	
	private int w = 0, h = 0;
	private int startX = 20, startY = 20, endX = 0, endY = 0;
	private int shape = 2;
	private Color color;
	private Shape s;
	private Graphics2D g2d;
	
	public DrawingCanvas(Color choosenCol, int shape) {
		this.shape = shape;
		this.color = new Color(255, 0, 0);
		
		setBackground(Color.WHITE);
		addMouseMotionListener(this);
		addMouseListener(this);
	}
	
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		this.g2d = (Graphics2D) g;
		this.g2d.setColor(this.color);
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		this.drawShape(this.s, e.getX(), e.getY());
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		//when mouse is moved, this class helps to change the mouse cursor
		Cursor cursor = new Cursor(Cursor.CROSSHAIR_CURSOR);
		//setting cursor to canvas
		setCursor(cursor);
		
	}
	
	@Override
	public void mouseExited(MouseEvent e) {	}
	
	@Override
	public void mouseReleased(MouseEvent e) {}

	@Override
	public void mousePressed(MouseEvent e) {
		this.startX = e.getX();
		this.startY = e.getY();
	}

	@Override
	public void mouseEntered(MouseEvent e) {}
	
	private void drawShape(Shape shape, int x, int y) {
		switch(this.shape) {
		//case 1: rectangle
		case 1:
			this.endX = x;
			this.endY = y;
			this.w = Math.abs(this.startX - this.endX);
			this.h = Math.abs(this.startY - this.endY);
			System.out.println("start: " + startX + " " + startY);
			System.out.println("end: " + endX + " " + endY);
			System.out.println("size: " + h + " " + w);
			Rectangle2D r = new Rectangle2D.Double(startX, startY, w, h);
			this.g2d.draw(r);
			this.repaint();
			break;
		//case 2: line
		case 2:
			this.g2d.draw(new Line2D.Double(startX, startY, x, y));
			break;
		default:
			break;
		}
		
	}
	
	public void setShape(int sh) {
		this.shape = sh;
	}
	public void setColor(Color c) {
		this.color = c;
	}

}
