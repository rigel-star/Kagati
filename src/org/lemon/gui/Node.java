package org.lemon.gui;

import javax.swing.JComponent;

import org.lemon.LemonObject;
import org.lemon.math.Vec2d;

@LemonObject(type = LemonObject.GUI_CLASS)
public class Node {
	
	public Vec2d start = null, end = null, mid = null;
	private JComponent con;
	
	
	public Node(Vec2d start, Vec2d end) {
		this(start, end, null);
	}
	
	
	public Node(Vec2d start, Vec2d end, JComponent con) {
		this.start = start;
		this.end = end;
		this.mid = start.midpoint(end);
	}
	
	
	public JComponent getComponent() {
		return con;
	}
	
}
