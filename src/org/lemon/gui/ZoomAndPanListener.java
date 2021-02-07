package org.lemon.gui;


import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.geom.AffineTransform;
import java.awt.geom.NoninvertibleTransformException;
import java.awt.geom.Point2D;

import javax.swing.JFrame;

/**
 * Listener that can be attached to a Component to implement Zoom and Pan
 * functionality.
 *
 * @author Sorin Postelnicu
 * @since Jul 14, 2009
 */
public class ZoomAndPanListener extends MouseAdapter {
	
	
	public static final int DEFAULT_MIN_ZOOM_LEVEL = -20;
	public static final int DEFAULT_MAX_ZOOM_LEVEL = 10;
	public static final double DEFAULT_ZOOM_MULTIPLICATION_FACTOR = 1.2;

	private Component targetComponent;

	
	private int zoomLevel = 0;
	private int minZoomLevel = DEFAULT_MIN_ZOOM_LEVEL;
	private int maxZoomLevel = DEFAULT_MAX_ZOOM_LEVEL;
	private double zoomMultiplicationFactor = DEFAULT_ZOOM_MULTIPLICATION_FACTOR;

	
	private Point dragStartScreen;
	private Point dragEndScreen;
	private AffineTransform coordTransform = new AffineTransform();

	
	
	public ZoomAndPanListener(Component targetComponent) {
		this.targetComponent = targetComponent;
		
		JFrame frame = new JFrame("Zoom and Pan Canvas");
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		frame.add(targetComponent, BorderLayout.CENTER);
		frame.pack();
		frame.setVisible(true);
		frame.setResizable(false);
	}
	
	static class ZoomAndPanCanvas extends Canvas {
		private static final long serialVersionUID = 1L;
		
		@Override
		public void paint(Graphics g) {
			
			Dimension d = getSize();
			int xc = d.width / 2;
			int yc = d.height / 2;
			g.drawOval(xc - 25, yc - 25, 50, 50);
			
		};
		
		@Override
		public Dimension getPreferredSize() {
			return new Dimension(600, 500);
		}
		
	}
	
	public ZoomAndPanListener(Component targetComponent, int minZoomLevel, int maxZoomLevel,
																			double zoomMultiplicationFactor) {
		this.targetComponent = targetComponent;
		this.minZoomLevel = minZoomLevel;
		this.maxZoomLevel = maxZoomLevel;
		this.zoomMultiplicationFactor = zoomMultiplicationFactor;
	}

	@Override
	public void mousePressed(MouseEvent e) {
		System.out.println("Mouse drag started.");
		dragStartScreen = e.getPoint();
		dragEndScreen = null;
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		moveCamera(e);
		targetComponent.repaint();
	}
	
	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		zoomCamera(e);
	}
	
	private void moveCamera(MouseEvent e) {
		try {
			dragEndScreen = e.getPoint();
			Point2D.Float dragStart = transformPoint(dragStartScreen);
			Point2D.Float dragEnd = transformPoint(dragEndScreen);
			double dx = dragEnd.getX() - dragStart.getX();
			double dy = dragEnd.getY() - dragStart.getY();
			coordTransform.translate(dx, dy);
			dragStartScreen = dragEndScreen;
			dragEndScreen = null;
		} catch (NoninvertibleTransformException ex) {
			ex.printStackTrace();
		}
	}
	
	private void zoomCamera(MouseWheelEvent e) {
		try {
			int wheelRotation = e.getWheelRotation();
			Point p = e.getPoint();
			if (wheelRotation > 0) {
				if (zoomLevel < maxZoomLevel) {
					zoomLevel++;
					Point2D p1 = transformPoint(p);
					coordTransform.scale(1 / zoomMultiplicationFactor, 1 / zoomMultiplicationFactor);
					Point2D p2 = transformPoint(p);
					coordTransform.translate(p2.getX() - p1.getX(), p2.getY() - p1.getY());
					targetComponent.repaint();
				}
			} else {
				if (zoomLevel > minZoomLevel) {
					zoomLevel--;
					Point2D p1 = transformPoint(p);
					coordTransform.scale(zoomMultiplicationFactor, zoomMultiplicationFactor);
					Point2D p2 = transformPoint(p);
					coordTransform.translate(p2.getX() - p1.getX(), p2.getY() - p1.getY());
					targetComponent.repaint();
				}
			}
		} catch (NoninvertibleTransformException ex) {
			ex.printStackTrace();
		}
	}
	
	private Point2D.Float transformPoint(Point p1) throws NoninvertibleTransformException {
		AffineTransform inverse = coordTransform.createInverse();

		Point2D.Float p2 = new Point2D.Float();
		inverse.transform(p1, p2);
		return p2;
	}
	
	public int getZoomLevel() {
		return zoomLevel;
	}
	
	public void setZoomLevel(int zoomLevel) {
		this.zoomLevel = zoomLevel;
	}
	
	public AffineTransform getCoordTransform() {
		return coordTransform;
	}
	
	public void setCoordTransform(AffineTransform coordTransform) {
		this.coordTransform = coordTransform;
	}

}