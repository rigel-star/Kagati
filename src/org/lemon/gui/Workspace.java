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
import java.util.ArrayList;
import java.util.List;

import javax.swing.JDesktopPane;
import javax.swing.JOptionPane;

import org.lemon.gui.node.ReceiverNode;
import org.lemon.gui.node.SenderNode;
import org.lemon.gui.node.SenderUtilityNode;
import org.lemon.lang.LemonObject;

/**
 * 
 * Main workspace of this application which contains all the 
 * {@link ImageView}s and {@link Node}s opened in application. 
 * Workspace is main requirement for all menus because menus need 
 * to access {@link ImageView} and {@link Node} currently selected 
 * by user.
 * 
 * */
@LemonObject( type = LemonObject.GUI_CLASS )
public class Workspace extends JDesktopPane implements ComponentListener {
	
	/**
	 * Serial UID
	 * */
	private static final long serialVersionUID = 1L;
	
	private List<NodePt> nodePts = new ArrayList<>();
	private List<ReceiverNode> receiverNodes = new ArrayList<>();
	private List<SenderNode> senderNodes = new ArrayList<>();
	private List<SenderUtilityNode> senderUtilNodes = new ArrayList<>();
	
	private Line2D.Double connect = null;
	private Color nodeColor = new Color(55, 164, 237);
	
	/**
	 * 
	 * While repainting the workspace, every node will be rechecked 
	 * and repainted on every repaint() method call and
	 * it is long process. So, to avoid the node reinitializing process, 
	 * this will check if the nodes are getting connected.
	 * If the nodes are getting connected this will avoid node reinitialization.
	 * 
	 * */
	private boolean connectingNode = false;
	
	private LayerContainer lyc;
	
	//TODO:: remove null nodes
	public Workspace( LayerContainer lyc ) {
		
		this.lyc = lyc;
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
		Graphics2D wpGraphics = (Graphics2D) g;
		
		receiverNodes.stream().forEach( fc -> {
			var node = fc.getReceiverNodePt();
			drawNodeLine( wpGraphics, node );
		});
		
		senderNodes.stream().forEach( fc -> {
			for( NodePt node: fc.getSenderNodePts() ) {
				drawNodeLine( wpGraphics, node );
			}
		});
		
		senderUtilNodes.stream().forEach( sunode -> {
			for( NodePt npt: sunode.getNodePts() ) {
				drawNodeLine( wpGraphics, npt );
			}
		});
		
		if( connect != null ) {
			wpGraphics.setPaint( Color.white );
			wpGraphics.setStroke( new BasicStroke( 1 ) );
			wpGraphics.draw( connect );
		}
	}
	
	/**
	 * Refresh's all the listeners.
	 **/
	public void refreshListeners() {
		
		for( Component c: getComponents() ) {
			if( c.getComponentListeners().length == 0 )
				c.addComponentListener( this );
		}
	}
	
	/**
	 * Fetch all the nodes.
	 * */
	public void fetchNodes() {
		
		for( Component c: getComponents() ) {
			
			if( c instanceof SenderNode ) {
				var con = (SenderNode) c;
				
				if( !senderNodes.contains( con ) )
					senderNodes.add( con );

				for( NodePt node: con.getSenderNodePts() ) {
					if( !nodePts.contains( node )) {
						nodePts.add( node );
					}		
				}
			}
			else if( c instanceof ReceiverNode ) {
				var fc = (ReceiverNode) c;
				
				if( !receiverNodes.contains( fc ) )
					receiverNodes.add( fc );
				
				if( !nodePts.contains( fc.getReceiverNodePt() )) {
					nodePts.add( fc.getReceiverNodePt() );
				}
			}
			else if( c instanceof SenderUtilityNode ) {
				var sunode = (SenderUtilityNode) c;
				
				for( NodePt npt: sunode.getNodePts() ) {
					if( !nodePts.contains( npt ))
						nodePts.add( npt );
				}
			}
		}
	}
	
	/**
	 * Draw {@link NodePt.getDrawable}.
	 * 
	 * @param g2d 		{@code Graphics2D} to draw on
	 * @param nodePt 	Node to draw.
	 * @return
	 * 
	 * */
	private void drawNodeLine( Graphics2D g2d, NodePt nodePt ) {
		g2d.setPaint( nodeColor );
		
		if( nodePt != null ) {
			if( nodePt.start != null ) {
				
				g2d.fillOval( (int) nodePt.start.x - 5, (int) nodePt.start.y - 5, 10, 10 );
				g2d.setPaint( Color.white );
				g2d.drawOval( (int) nodePt.start.x - 5, (int) nodePt.start.y - 5, 10, 10 );
				
				if( connectingNode )
					return;
				
				if( nodePt.getComponent() instanceof ReceiverNode ) {
					
				}
				else if( nodePt.getComponent() instanceof SenderNode ) {
				
					nodePt.getConnections().forEach( nd -> {
						
						ReceiverNode c = (ReceiverNode) nd;
						g2d.setPaint( Color.white );
						g2d.setStroke( new BasicStroke( 1 ));
						
						if( c.getReceiverNodePt().start != null ) {
							var line = new Line2D.Double( nodePt.start.x, nodePt.start.y, c.getReceiverNodePt().start.x, c.getReceiverNodePt().start.y);
							g2d.draw( line );
						}
					});
				}
				else if( nodePt.getComponent() instanceof SenderUtilityNode ) {
					
				}
			}
		}
	}
	
	/**
	 * Update all the Node components.
	 * */
	private void updateNodes() {
		
		receiverNodes.forEach( fc -> {
			if( fc.getReceiverNodePt().start != null )
				fc.updateReceiverNodePt();
		});
		
		senderNodes.forEach( fc -> {
			for( NodePt node: fc.getSenderNodePts() ) {
				if( node.start != null ) {
					fc.updateSenderNodePts();
				}
			}
		});
		
		senderUtilNodes.forEach( sutil -> {
			for( NodePt npt: sutil.getNodePts() ) {
				if(npt.start != null ) {
					sutil.updateNodePts();
				}
			}
		});
		
		repaint();
	}

	@Override
	public void componentResized( ComponentEvent e ) {
		updateNodes();
	}
	
	@Override
	public void componentMoved( ComponentEvent e ) {
		updateNodes();
	}
	
	@Override
	public void componentShown( ComponentEvent e ) {
		updateNodes();
	}

	@Override
	public void componentHidden( ComponentEvent e ) {
		updateNodes();
	}
	
	/**
	 * 
	 * Mouse handler for nodes.
	 * 
	 * */
	class MouseHandler extends MouseAdapter {
		
		public MouseHandler() {}
		
		private Point start = null, end = null;
		private NodePt startNodePt = null, endNodePt = null;
		
		@Override
		public void mousePressed( MouseEvent e ) {
			super.mousePressed( e );
			
			final int x = e.getX();
			final int y = e.getY();
			for( NodePt node: nodePts ) {
				if( node.getDrawable().contains( x, y )) {
					start = e.getPoint();
					end = start;
					startNodePt = node;
					break;
				}
			}
		}
		
		@Override
		public void mouseDragged( MouseEvent e ) {
			super.mouseDragged( e );
			
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
			
			for( NodePt node: nodePts ) {
				if( node.getDrawable().contains( end )) {
					if( !startNodePt.equals( node )) { //if start and end node are not same
						endNodePt = node;
					} else {
						return;
					}
				}
			}
			
			if( startNodePt != null && endNodePt != null ) {
				connectNodes( startNodePt, endNodePt );
			}
			
			start = null;
			end = null;
			connectingNode = false;
			connect = null;
			repaint();
		}	
		
		/**
		 * Connect two nodes.
		 * 
		 * @param startNodePt		Node to start from.
		 * @param endNodePt		Node to end with.
		 * */
		void connectNodes( NodePt startNodePt, NodePt endNodePt ) {
			
			if( startNodePt.getComponent() instanceof SenderNode ) {
				
				if( endNodePt.getComponent() instanceof ReceiverNode ) {
					
					if( !startNodePt.getConnections().contains( (Node) endNodePt.getComponent() ) ) {
						
						startNodePt.addConnection((ReceiverNode) endNodePt.getComponent() );
						var controllable = (ReceiverNode) endNodePt.getComponent();
						controllable.addSender( (SenderNode) startNodePt.getComponent() );
					}
					else {
						JOptionPane.showMessageDialog( endNodePt.getComponent(), "Already connected!" );
					}
				}
				else {
					JOptionPane.showMessageDialog( endNodePt.getComponent(), "Connect with image!" );
				}
			}
			else if( startNodePt.getComponent() instanceof ReceiverNode ) {
				
				if( endNodePt.getComponent() instanceof SenderNode ) {
					
					if( !endNodePt.getConnections().contains( (Node) startNodePt.getComponent()) ) {
						
						endNodePt.addConnection((ReceiverNode) startNodePt.getComponent());
						var receiver = (ReceiverNode) startNodePt.getComponent();
						receiver.addSender((SenderNode) endNodePt.getComponent());
					}
					else {
						JOptionPane.showMessageDialog( endNodePt.getComponent(), "Already connected!" );
					}
				}
				else {
					JOptionPane.showMessageDialog( endNodePt.getComponent(), "Connect with filter!" );
				}
			}
		}
	}
	
	/**
	 * 
	 * @return {@link LayerContainer}.
	 * */
	public LayerContainer getLayerContainer() {
		return lyc;
	}
}
