package org.lemon.gui;

import java.awt.Component;

import org.lemon.gui.Node.NodeType;
import org.lemon.gui.node.ReceiverNode;
import org.lemon.gui.node.SenderNode;

public class NodeManager {
	
	/**
	 * @param n1 First {@link NodePt}
	 * @param n2 Second {@link NodePt}
	 * @return {@code True} if two points can connect, else {@code False}.
	 * 
	 * */
	public static void connectNodes( NodePt n1, NodePt n2 ) {
		
		NodeType nodet1 = ((Node) n1.getComponent()).getNodeType();
		NodeType nodet2 = ((Node) n2.getComponent()).getNodeType();
		
		if( nodet1 == nodet2 ) {
			return;
		}
		else if( n1.getComponent().equals( n2.getComponent() ) ) {
			return;
		}
		else if( nodet1 == NodeType.SENDER ) {
			if( nodet2 == NodeType.RECEIVER ) {
				System.out.println("DEBUG: start -> sender \n end -> receiver");
				connect( n2, n1 );
			}
			else if( nodet2 == NodeType.SENDER_UTILITY ) {
				System.out.println("DEBUG: start -> sender \n end -> sender utility");
				connect( n2, n1 );
			}
		}
		else if( nodet1 == NodeType.RECEIVER ) {
			if( nodet2 == NodeType.SENDER ) {
				System.out.println("DEBUG: start -> receiver \n end -> sender");
				connect( n1, n2 );
			}
		}
		else if( nodet1 == NodeType.SENDER_UTILITY ) {
			if( nodet2 == NodeType.SENDER ){
				System.out.println("DEBUG: start -> sender utility \n end -> sender");
				connect( n1, n2 );
			}
		}
	}
	
	/**
	 * Set connection between two nodes.
	 * */
	public static void connect( NodePt n1, NodePt n2 ) {
		
		Component comp1 = n1.getComponent();
		Component comp2 = n2.getComponent();
		
		if( comp1 instanceof ReceiverNode && comp2 instanceof SenderNode ) {
			
			if( !n1.getConnections().contains( (Node) n2.getComponent() )) {
				n1.addConnection((ReceiverNode) n1.getComponent() );
				var controllable = (ReceiverNode) n1.getComponent();
				controllable.addSender( (SenderNode) n2.getComponent() );
			}
		}
	}
}
