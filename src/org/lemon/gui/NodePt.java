package org.lemon.gui;

import java.awt.Shape;
import java.awt.geom.Ellipse2D;
import java.util.Stack;

import javax.swing.JComponent;

import org.lemon.lang.LemonObject;
import org.lemon.math.Vec2;

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
	
	public Vec2 start = null, end = null, mid = null;
	private JComponent  parent;
	
	private Stack<Node> cons = new Stack<>();
	
	public enum NodePtType {
		
		RECEIVER,
		SENDER
		
	}
	
	private NodePtType nodePtType = null;
	
	public NodePt( Vec2 start ) {
		this( start, null );
	}
	
	public NodePt( Vec2 start, Vec2 end ) {
		this( start, end, null, null );
	}
	
	public NodePt( Vec2 start, Vec2 end, JComponent comp, NodePtType type ) {
		this.parent = comp;
		this.start = start;
		this.end = end;
		this.nodePtType = type;
		
		if( end != null )
			this.mid = start.midpoint( end );
	}
	
	/**
	 * Set start point for this {@link NodePt}.
	 * @param start 	New start point.
	 * 
	 * */
	public void setStart( Vec2 start ) {
		this.start = start;
	}
	
	/**
	 * Set end point for this {@link NodePt}.
	 * @param end 	New end point.
	 * 
	 * */
	public void setEnd( Vec2 end ) {
		this.end = end;
		if( end != null )
			this.mid = start.midpoint( end );
	}
	
	public void addConnection( Node node ) {
		cons.push( node ); 
	}
	
	/** 
	 * @return Get {@code JComponent} attached with 
	 * 			this {@link NodePt}.
	 * */
	public JComponent getComponent() {
		return parent;
	}
	
	/**
	 * @return Connections of this {@link NodePt} which are other 
	 * 				{@link NodePt}'s.
	 * */
	public Stack<Node> getConnections() {
		return cons;
	}
	
	/**
	 * @return Get the {@code Shape} of this {@link NodePt}.
	 * */
	public Shape getDrawable() {
		return new Ellipse2D.Double( start.x - 5, start.y - 5, 10, 10 );
	}
	
	/**
	 * Set node point type.
	 * */
	public void setNodePtType( NodePtType type ) {
		this.nodePtType = type;
	}
	
	/**
	 * Get node point type.
	 * */
	public NodePtType getNodePtType() {
		return nodePtType;
	}
}