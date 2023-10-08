package org.lemon.gui.node;

import java.awt.Color;
import java.util.EventObject;

public abstract class Node {
	private Color color;
	
	public Node(Color color) {
		this.color = color;
	}
	
	public Color getColor() {
		return color;
	}

	public String getName() {
		return "Node";
	}

	public static interface NodeClickListener {
		public void nodeClick(NodeClickEvent event);
	}

	public static class NodeClickEvent extends EventObject {
		private int x;
		private int y;

		public NodeClickEvent(Object source, int x, int y) {
			super(source);
			this.x = x;
			this.y = y;
		}

		public Node getNode() {
			if(getSource() instanceof Node node) return node;
			return null;
		}

		public int getX() {
			return x;
		}

		public int getY() {
			return y;
		}
	}
}