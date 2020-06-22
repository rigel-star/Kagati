package org.lemon.filters.transformations;

import java.awt.Point;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.util.List;

/**
 * Perspective Plane contains 4 points. This class contains the information of user drawn perspective on 
 * {@code VanishingPointFilter}.
 * */
public class PerspectivePlane {
	
	
	public Point p0, p1, p2, p3;
	private List<Point> coords;
	
	
	public PerspectivePlane() {
		
	}
	
	
	public PerspectivePlane(final Rectangle bounds) {
		p0 = new Point(bounds.x, bounds.y);
		p1 = new Point(bounds.x, bounds.y + bounds.height);
		p2 = new Point(bounds.x + bounds.width, bounds.y + bounds.height);
		p3 = new Point(bounds.x + bounds.width, bounds.y);
		
		coords.add(p0);
		coords.add(p1);
		coords.add(p2);
		coords.add(p3);
	}
	
	
	public PerspectivePlane(final List<Point> pts){
		this.coords = pts;
		p0 = pts.get(0);
		p1 = pts.get(1);
		p2 = pts.get(2);
		p3 = pts.get(3);
	}
	
	
	public PerspectivePlane(Point p0, Point p1, Point p2, Point p3) {
		this.p0 = p0;
		this.p1 = p1;
		this.p2 = p2;
		this.p3 = p3;
		
		coords.add(p0);
		coords.add(p1);
		coords.add(p2);
		coords.add(p3);
	}
	
	
	public Rectangle getBound() {
		Polygon poly = new Polygon();
		
		for(Point pt: coords) {
			poly.addPoint(pt.x, pt.y);
		}
		
		var bound = poly.getBounds();
		bound.setLocation(p0.x, p0.y);
		
		return bound;
	}
	
	
	public List<Point> getCoords(){
		return coords;
	}
	
	
}
