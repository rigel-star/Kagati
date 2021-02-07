package org.lemon.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import org.lemon.gui.layers.ViewLayer;
import org.lemon.gui.node.ReceiverNode;
import org.lemon.gui.node.SenderNode;
import org.lemon.gui.layers.LayerList;
import org.lemon.gui.layers.NodeLayer;

public class LayerContainer extends JInternalFrame {
	
	/**
	 * Serial UID
	 * */
	private static final long serialVersionUID = 1L;
		
	private LayerList layerList = new LayerList();
	private JScrollPane scroll = new JScrollPane(layerList);

	private Layer background = null;
	
	/**
	 * @param
	 **/
	public LayerContainer() {
		
		setTitle( "Layers" );
		setClosable( false );
		setBorder( BorderFactory.createLineBorder( Color.black, 1 ) );
		setMaximizable( false );
		setIconifiable( false );
		setLayout( new BorderLayout() );
		
		add( scroll );
		add( new BottomMenu(), BorderLayout.SOUTH );
		setVisible( true );
	}
	
	/**
	 * Add layer to layer container.
	 * 
	 * @param layer 	{@link Layer} to add.
	 * */
	public void addLayer( Layer layer ) {
		
		if( layer instanceof ViewLayer ) {
			
			var vlay = (ViewLayer) layer;
			if( vlay.getProperty() == ViewLayer.BACKGROUND_LAYER || getLayerCount() == 0 ) {
				
				if( background != null ) 
					vlay.setTitle( "Layer" );
				else
					vlay.setTitle( "Background" );
				
				background = vlay;
			}
		}
		else if( layer instanceof NodeLayer )
			background = (NodeLayer) layer;
		
		layerList.add( layer );
		scroll.revalidate();
	}
	
	public void removeLayer( Layer ly ) {
		layerList.remove( ly );
	}
	
	public int getLayerCount() {
		return layerList.getLayerCount();
	}
	
	@Override
	public Dimension getPreferredSize() {
		return new Dimension( 150, 200 );
	}
	
	/**
	 * Lies in the bottom of LayerContainer. This class contains command like for deleting or copying layers.
	 * */
	private class BottomMenu extends JPanel {
		
		/**
		 * Serial UID
		 * */
		private static final long serialVersionUID = 1L;
		
		private JButton deleteLayerBtn;
		private JButton copyLayerBtn;
		private JButton newLayerBtn;
		
		public BottomMenu() {
			init();
			setLayout( new FlowLayout( FlowLayout.CENTER ));
			
			add( newLayerBtn );
			add( copyLayerBtn );
			add( deleteLayerBtn );
		}
		
		/**
		 * Init widgets for bottom menu.
		 * */
		private void init() {
			
			this.newLayerBtn = new JButton( "New" );
			
			this.copyLayerBtn = new JButton("Copy");
			copyLayerBtn.addActionListener(action -> {
				
			});	
			
			this.deleteLayerBtn = new JButton("Del");
			deleteLayerBtn.addActionListener(action -> {
				
				int option = JOptionPane.showConfirmDialog( layerList, "Delete this layer?" );
				
				switch( option ) {
				
				case 0: {
					Layer lay = layerList.getSelectedValue();
					if( lay.getLayerComponent() instanceof ReceiverNode ) {
						ReceiverNode fcon = (ReceiverNode) lay.getLayerComponent();
						/*
						 * Setting node position null cause workspace will 
						 * paint node even if the component is 
						 * deleted cause node is still in node list.
						 * So if we set node to null then workspace 
						 * wont get any position to paint node.
						 */
						fcon.getReceiverNodePt().start = null;
					}
					else if( lay.getLayerComponent() instanceof SenderNode ) {
						SenderNode fcon = (SenderNode) lay.getLayerComponent();
						//read upper comment to understand why im setting node positions null
						for( NodePt node: fcon.getSenderNodePts() ) {
							node.start = null;
						}
					}
					JComponent jc = lay.getLayerComponent();
					((JInternalFrame) jc).dispose();
					layerList.remove( lay );
				}
				break;
				}
				revalidate();
			});	
		}		
	}
}