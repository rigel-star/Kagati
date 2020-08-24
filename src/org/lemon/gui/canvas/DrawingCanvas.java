package org.lemon.gui.canvas;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.List;

public class DrawingCanvas extends Canvas implements MouseMotionListener {

	private static final long serialVersionUID = 1L;
	
	private Point startPt, endPt;

	// shapes list
	private List<Shape> shapes = new ArrayList<Shape>();

	public DrawingCanvas(Color choosenCol, int shape) {

		setBackground(Color.WHITE);
		addMouseMotionListener(this);

		this.addMouseListener(new MouseAdapter() {

			public void mousePressed(MouseEvent e) {
				startPt = new Point(e.getX(), e.getY());
				endPt = startPt;
				repaint();
			}

			public void mouseReleased(MouseEvent e) {
				Shape r = makeRectangle(startPt.x, startPt.y, e.getX(), e.getY());
				shapes.add(r);
				startPt = null;
				endPt = null;
				repaint();
			}
		});
	}

	private Rectangle2D.Float makeRectangle(int x1, int y1, int x2, int y2) {
		return new Rectangle2D.Float(Math.min(x1, x2), Math.min(y1, y2), Math.abs(x1 - x2), Math.abs(y1 - y2));
	}

	@Override
	public void paint(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		g2.setStroke(new BasicStroke(2));
		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.50f));

		for (Shape s : shapes) {
			g2.setPaint(Color.BLACK);
			g2.draw(s);
		}

		if (startPt != null && endPt != null) {
			g2.setPaint(Color.LIGHT_GRAY);
			Shape r = makeRectangle(startPt.x, startPt.y, endPt.x, endPt.y);
			g2.draw(r);
		}
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		endPt = new Point(e.getX(), e.getY());
        repaint();
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// when mouse is moved, this class helps to change the mouse cursor
		Cursor cursor = new Cursor(Cursor.CROSSHAIR_CURSOR);
		setCursor(cursor); // setting cursor to canvas
	}

	public void setShape(int sh) {
		
	}

	public void setColor(Color c) {
		
	}

}
