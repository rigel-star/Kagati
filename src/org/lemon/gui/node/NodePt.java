package org.lemon.gui.node;

import java.awt.Shape;
import java.awt.geom.Ellipse2D;
import java.util.Stack;

import javax.swing.JComponent;

import org.lemon.gui.ControllableNode;
import org.lemon.lang.LemonObject;
import org.lemon.math.Vec2d;

/**
 * 
 * {@code NodePt} is a Java's {@code Point} type class but
 * with end point. Like when constructing {@code Point} object, 
 * we usually give it the specific x, y coordinate. But when 
 * constructing the {@code NodePt} object, it asks for start & end 
 * point and {@code JComponent} object (optional because it is used 
 * to specify if this node is attached with any swing component).
 * <p> 
 * So, {@code NodePt} is created with the intent for holding the 
 * two points of different components. In simple terms, any class which 
 * implements either {@code ControllerNode} or {@code ControllableNode} 
 * have NodePt attacahed with themselves which is then accessed by 
 * {@code Workspace} and checked if it has connection with any other node. 
 * If connected, then curve or line will be drawn to show the connection.
 * 
 * @author Ramesh Poudel
 * 
 * */
@LemonObject( type = LemonObject.HELPER_CLASS )
public class NodePt {
	
	public Vec2d start = null, end = null, mid = null;
	private JComponent  parent;
	
	private Stack<ControllableNode> cons = new Stack<>();
	
	
	public NodePt( Vec2d start, Vec2d end ) {
		this( start, end, null );
	}
	
	
	public NodePt( Vec2d start, Vec2d end, JComponent parent ) {
		this.parent = parent;
		this.start = start;
		this.end = end;
		
		if( end != null )
			this.mid = start.midpoint( end );
	}
	
	
	public void setStart( Vec2d start ) {
		this.start = start;
	}
	
	
	public void setEnd( Vec2d end ) {
		this.end = end;
		if( end != null )
			this.mid = start.midpoint( end );
	}
	
	
	public void addConnection( ControllableNode controllable ) {
		cons.push( controllable ); 
	}
	
	
	public JComponent getParent() {
		return parent;
	}
	
	
	public Stack<ControllableNode> getConnections() {
		return cons;
	}
	
	
	public Shape getDrawable() {
		return new Ellipse2D.Double( start.x - 5, start.y - 5, 10, 10 );
	}	
}