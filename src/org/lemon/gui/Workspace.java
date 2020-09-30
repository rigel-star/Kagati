package org.lemon.gui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Line2D;
import java.awt.geom.QuadCurve2D;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JDesktopPane;
import javax.swing.JOptionPane;

import org.lemon.lang.LemonObject;
import org.lemon.utils.Pair;


@LemonObject( type = LemonObject.GUI_CLASS )
public class Workspace extends JDesktopPane implements ComponentListener {
	
	/**
	 * Serial UID
	 * */
	private static final long serialVersionUID = 1L;
	
	private List<NodePt> nodes = new ArrayList<NodePt>();
	private List<ControllableNode> fcontrollables = new ArrayList<>();
	private List<ControllerNode> fcontrollers = new ArrayList<>();
	
	private Graphics2D wpGraphics = null;
	private Line2D.Double connect = null;
	private Color nodeColor = new Color(55, 164, 237);
	
	/**
	 * 
	 * This boolean value will be checked when connecting the nodes.
	 * While connecting the nodes, the workspace have to be repainted 
	 * everytime to show the connecting line in real time.
	 * But while repainting the workspace, every node will be rechecked 
	 * and repainted on every repaint() method call and
	 * it is long process. So, to avoid the node reinitializing process, 
	 * this will check if the nodes are getting connected.
	 * If the nodes are getting connected this will avoid node reinitialization.
	 * See {@code paintComponent} method to understand what i'm talking about.
	 * 
	 * */
	private boolean connectingNode = false;
	
	
	//TODO:: remove null nodes
	public Workspace() {
		
		setSize( 5000, 5000 );
		setBackground( new Color( 160, 160, 160 ));
		setVisible( true );
		addComponentListener( this );
		fetchNodes();
		
		var mh = new MouseHandler();
		addMouseListener( mh );
		addMouseMotionListener( mh );
	}
	
	
	@Override
	public void paintComponent( Graphics g ) {
		super.paintComponent( g );
		wpGraphics = (Graphics2D) g;
		
		fcontrollables.stream().forEach( fc -> {
			var node = fc.getNodePt();
			drawNode( wpGraphics, node );
		});
		
		fcontrollers.stream().forEach( fc -> {
			for( NodePt node: fc.getNodePts() ) {
				drawNode( wpGraphics, node );
			}
		});
		
		if( connect != null ) {
			wpGraphics.setPaint( Color.white );
			wpGraphics.setStroke( new BasicStroke( 1 ) );
			wpGraphics.draw( connect );
		}
	}
	
	
	/**
	 * 
	 * Refresh's all the listeners.
	 * 
	 * */
	public void refresh() {
		
		for( Component c: getComponents() ) {
			if( c.getComponentListeners().length == 0 )
				c.addComponentListener( this );
		}
		
	}
	
	
	/**
	 * 
	 * Fetch all the nodes.
	 * 
	 * */
	public void fetchNodes() {
		for( Component c: getComponents() ) {
			if( c instanceof ControllerNode ) {
				var con = (ControllerNode) c;
				
				if( !fcontrollers.contains( con ) )
					fcontrollers.add( con );

				for( NodePt node: con.getNodePts() ) {
					if( !nodes.contains( node )) {
						nodes.add( node );
					}
					
				}
			}
			else if( c instanceof ControllableNode ) {
				var fc = (ControllableNode) c;
				
				if( !fcontrollables.contains( fc ) )
					fcontrollables.add( fc );
				
				if( !nodes.contains( fc.getNodePt() )) {
					nodes.add( fc.getNodePt() );
				}
			}
		}
	}

	
	private void drawNode(Graphics2D g2d, NodePt node) {
		g2d.setPaint(nodeColor);
		
		if(node != null && node.start != null) {
			g2d.fillOval((int) node.start.x - 5, (int) node.start.y - 5, 10, 10);
			g2d.setPaint(Color.white);
			g2d.drawOval((int) node.start.x - 5, (int) node.start.y - 5, 10, 10);
			
			if(connectingNode)
				return;
			
			if( node.getComponent() instanceof ControllableNode ) {
				
			}
			else if( node.getComponent() instanceof ControllerNode ) {
			
				node.getConnections().forEach(nd -> {
					
					ControllableNode c = (ControllableNode) nd;
					g2d.setPaint( Color.white );
					g2d.setStroke( new BasicStroke( 1 ) );
					
					/*
					var curve = new QuadCurve2D.Double((int) node.start.x, (int) node.start.y,
														(int) node.mid.x, (int) node.mid.y,
														(int) node.end.x, (int) node.end.y);								
					*/
					if( c.getNodePt().start != null ) {
						var line = new Line2D.Double( node.start.x, node.start.y, c.getNodePt().start.x, c.getNodePt().start.y);
						g2d.draw( line );
					}
				});
			}
			
			return;
		}
		
	}
	
	
	private void updateNodes() {
		
		fcontrollables.forEach(fc -> {
			if(fc.getNodePt().start != null)
				fc.updateNodePt();
		});
		
		fcontrollers.forEach(fc -> {
			for(NodePt node: fc.getNodePts()) {
				if(node.start != null) {
					fc.updateNodePts();
				}
			}
		});
		repaint();
	}
	
	
	/**
	 * Drawing curve between two connections.
	 * */
	public void draw( Graphics2D g2d, Pair p ) {
		g2d.setPaint( Color.white );
		g2d.setStroke( new BasicStroke( 3 ) );
		
		var v1 = p.getFirstPoint();
		var v2 = p.getSecondPoint();
		var start = p.getFirstComponent();
		var end = p.getSecondComponent();
		
		var begin = new Point((int) v1.x + start.getWidth(),
				(int) v1.y + (start.getHeight() / 2));
		
		var stop = new Point((int) v2.x,
				(int) v2.y + (end.getHeight() / 2));
		
		var ctrlPoint = v1.midpoint(v2);
		
		var curve = new QuadCurve2D.Double( begin.x, begin.y,
															ctrlPoint.x, ctrlPoint.y,
																stop.x, stop.y );
		g2d.fillOval( begin.x - 5, begin.y, 10, 10 );
		g2d.fillOval( stop.x - 5, stop.y, 10, 10 );
		g2d.draw( curve );
		
	}
	

	@Override
	public void componentResized(ComponentEvent e) {
		updateNodes();
	}

	@Override
	public void componentMoved(ComponentEvent e) {
		updateNodes();
	}

	@Override
	public void componentShown(ComponentEvent e) {
		updateNodes();
	}

	@Override
	public void componentHidden(ComponentEvent e) {
		updateNodes();
	}
	
	
	
	class MouseHandler extends MouseAdapter {
		
		public MouseHandler() {}
		
		private Point start = null, end = null;
		private NodePt startNode = null, endNode;
		
		@Override
		public void mousePressed(MouseEvent e) {
			super.mousePressed(e);
			
			for(NodePt node: nodes) {
				if(node.getDrawable().contains(e.getX(), e.getY())) {
					start = e.getPoint();
					end = start;
					startNode = node;
					break;
				}
			}
		}
		
		
		@Override
		public void mouseDragged(MouseEvent e) {
			super.mouseDragged(e);
			
			connectingNode = true;
			
			if( start != null ) {
				end = e.getPoint();
				connect = new Line2D.Double( start, end );
				repaint();
			}
		}
		
		
		@Override
		public void mouseReleased( MouseEvent e ) {
			super.mouseReleased( e );
			
			for( NodePt node: nodes ) {
				if( node.getDrawable().contains( end )) {
					if( !startNode.equals( node )) { //if start and end node are not same
						endNode = node;
					} else {
						return;
					}
				}
			}
			
			if( startNode != null && endNode != null ) {
				connectNodes( startNode, endNode );
			}
			
			start = null;
			end = null;
			connectingNode = false;
			connect = null;
			repaint();
		}	
		
		
		/**
		 * 
		 * Connect two nodes.
		 * @param startNode		Node to start from.
		 * @param endNode		Node to end with.
		 * 
		 * */
		private void connectNodes( NodePt startNode, NodePt endNode ) {
			
			if( startNode.getComponent() instanceof ControllerNode ) {
				
				if( endNode.getComponent() instanceof ControllableNode ) {
					
					if( !startNode.getConnections().contains((ControllableNode) endNode.getComponent() ) ) {
						
						startNode.addConnection((ControllableNode) endNode.getComponent() );
						var controllable = (ControllableNode) endNode.getComponent();
						controllable.addController( (ControllerNode) startNode.getComponent() );
					}
					else {
						JOptionPane.showMessageDialog( endNode.getComponent(), "Already connected!" );
					}
				}
				else {
					JOptionPane.showMessageDialog( endNode.getComponent(), "Connect with image!" );
				}
			}
			else if( startNode.getComponent() instanceof ControllableNode ) {
				
				if( endNode.getComponent() instanceof ControllerNode ) {
					
					if(!endNode.getConnections().contains( (ControllableNode) startNode.getComponent()) ) {
						
						endNode.addConnection((ControllableNode) startNode.getComponent());
						var controllable = (ControllableNode) startNode.getComponent();
						controllable.addController((ControllerNode) endNode.getComponent());
					}
					else {
						JOptionPane.showMessageDialog( endNode.getComponent(), "Already connected!" );
					}
				}
				else {
					JOptionPane.showMessageDialog( endNode.getComponent(), "Connect with filter!" );
				}
			}
		}	
	}
}
