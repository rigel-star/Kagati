package org.lemon.utils;

import javax.swing.JComponent;

import org.lemon.math.Vec2d;

public class Pair {

	private JComponent firstComp, secondComp;
	private Vec2d firstPt, secondPt;
	
	public Pair(JComponent comp1, JComponent comp2) {
		this.firstComp = comp1;
		this.secondComp = comp2;
		
		this.firstPt = new Vec2d(comp1.getLocation().x, comp1.getLocation().y);
		this.secondPt = new Vec2d(comp2.getLocation().x, comp2.getLocation().y);
	}
	
	
	public JComponent getFirstComponent() {
		return this.firstComp;
	}
	
	public JComponent getSecondComponent() {
		return this.secondComp;
	}
	
	public Vec2d getFirstPoint() {
		return this.firstPt;
	}
	
	public Vec2d getSecondPoint() {
		return this.secondPt;
	}
	
}
