package org.lemon.utils;

import java.awt.BasicStroke;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Line2D;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JComponent;

import org.lemon.math.Vec2d;


public class ConnectionManager {

	private Graphics2D 				g;
	
	/*Stores every pair of connections*/
	private List<Pair> 				allCons = new ArrayList<ConnectionManager.Pair>();
	
	public ConnectionManager() {
		this(null);
	}
	
	
	/**
	 * Class Description: This class is basically written for drawing line and curves between two vectors.
	 * */
	public ConnectionManager(JComponent context) {
		
		if(context == null)
			return;
		
		g = (Graphics2D) context.getGraphics();
		
		if(g != null)
			g.setStroke(new BasicStroke(3));
	}
	
	
	
	/**
	 * Draw connection line with one single pair.
	 * @throws {@code NullPointerException} if pair is null.
	 * @param Pair of nodes
	 * */
	public void newConnection(Pair p) {
		if(drawConnection(p))
			this.allCons.add(p);		
	}
	
	
	/*The function which draws lines in pair*/
	private boolean drawConnection(Pair p) {
		
		if(p == null)
			throw new NullPointerException("null pair");
		
		Vec2d svec = p.getFirstPoint();
		Vec2d evec = p.getSecondPoint();
		JComponent start = p.getFirstComponent(), end = p.getSecondComponent();
		
		Point begin = new Point(svec.x + start.getWidth(),
				svec.y + (start.getHeight() / 2));
		Point stop = new Point(evec.x,
				evec.y + (end.getHeight() / 2));
		
		g.draw(new Line2D.Double(begin, stop));
		
		return true;
	}
	
	
	/**
	 * Make pair of two components or vectors.
	 * */
	public static class Pair {
		
		private Vec2d firstVec, secondVec;
		private JComponent fristComp, secondComp;
		
		public Pair(JComponent v1, JComponent v2) {
			this.firstVec = new Vec2d(v1.getLocation().x, v1.getLocation().y);
			this.secondVec = new Vec2d(v2.getLocation().x, v2.getLocation().y);
			this.fristComp = v1;
			this.secondComp = v2;
		}
		
		public Vec2d getFirstPoint() {
			return this.firstVec;
		}
		
		public Vec2d getSecondPoint() {
			return this.secondVec;
		}
		
		public JComponent getFirstComponent() {
			return this.fristComp;
		}
		
		public JComponent getSecondComponent() {
			return this.secondComp;
		}
		
	}
	
}

