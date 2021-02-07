package org.lemon.utils;

import javax.swing.JComponent;

import org.lemon.math.Vec2;

public class Pair {

	private JComponent firstComp, secondComp;
	private Vec2 firstPt, secondPt;
	
	public Pair(JComponent comp1, JComponent comp2) {
		this.firstComp = comp1;
		this.secondComp = comp2;
		
		this.firstPt = new Vec2(comp1.getLocation().x, comp1.getLocation().y);
		this.secondPt = new Vec2(comp2.getLocation().x, comp2.getLocation().y);
	}
	
	
	public JComponent getFirstComponent() {
		return this.firstComp;
	}
	
	public JComponent getSecondComponent() {
		return this.secondComp;
	}
	
	public Vec2 getFirstPoint() {
		return this.firstPt;
	}
	
	public Vec2 getSecondPoint() {
		return this.secondPt;
	}
	
}
