package org.lemon.filters.transform;

import java.awt.Point;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

/**
 * Perspective Plane contains 4 points. This class contains the information of user drawn perspective on 
 * {@code VanishingPointFilter}.
 * */
public class PerspectivePlane {
	
	
	public Point2D p0, p1, p2, p3;
	private List<Point2D> coords = new ArrayList<>();
	
	
	public PerspectivePlane() {
		
	}
	
	
	public PerspectivePlane(Rectangle bounds) {
		p0 = new Point(bounds.x, bounds.y);
		p1 = new Point(bounds.x, bounds.y + bounds.height);
		p2 = new Point(bounds.x + bounds.width, bounds.y + bounds.height);
		p3 = new Point(bounds.x + bounds.width, bounds.y);
		
		coords.add(p0);
		coords.add(p1);
		coords.add(p2);
		coords.add(p3);
	}
	
	
	public PerspectivePlane(List<Point2D> pts){
		this.coords = pts;
		p0 = pts.get(0);
		p1 = pts.get(1);
		p2 = pts.get(2);
		p3 = pts.get(3);
	}
	
	
	public PerspectivePlane(Point2D p0, Point2D p1, Point2D p2, Point2D p3) {
		this.p0 = p0;
		this.p1 = p1;
		this.p2 = p2;
		this.p3 = p3;
		
		coords.add(p0);
		coords.add(p1);
		coords.add(p2);
		coords.add(p3);
	}
	
	
	public Rectangle getArea() {
		var poly = new Polygon();
		
		for(Point2D pt: coords) {
			poly.addPoint((int) pt.getX(), (int) pt.getY());
		}
		var bound = poly.getBounds();
		bound.setLocation((int) p0.getX(), (int) p0.getY());
		
		return bound;
	}
	
	
	public List<Point2D> getCoords(){
		return coords;
	}
	
	
}
