package org.lemon.gui;

import java.awt.Shape;
import java.awt.geom.Ellipse2D;
import java.util.Stack;

import javax.swing.JComponent;

import org.lemon.LemonObject;
import org.lemon.math.Vec2d;

@LemonObject(type = LemonObject.HELPER_CLASS)
public class Node {
	
	public Vec2d start = null, end = null, mid = null;
	private JComponent  parent;
	
	private Stack<FilterControllable> cons = new Stack<>();
	
	public Node(Vec2d start, Vec2d end) {
		this(start, end, null);
	}
	
	
	public Node(Vec2d start, Vec2d end, JComponent parent) {
		this.parent = parent;
		this.start = start;
		this.end = end;
		
		if(this.end != null)
			this.mid = start.midpoint(end);
	}
	
	
	public void setStart(Vec2d start) {
		this.start = start;
	}
	
	
	public void setEnd(Vec2d end) {
		this.end = end;
		if(this.end != null)
			this.mid = start.midpoint(end);
	}
	
	
	public void addConnection(FilterControllable controllable) {
		cons.push(controllable);
		System.out.println("Added!");
		System.out.println("Count: " + cons.size());
	}
	
	
	public JComponent getParent() {
		return parent;
	}
	
	
	public Stack<FilterControllable> getConnections() {
		return cons;
	}
	
	
	public Shape getDrawable() {
		return new Ellipse2D.Double(start.x - 5, start.y - 5, 10, 10);
	}
	
	
}
