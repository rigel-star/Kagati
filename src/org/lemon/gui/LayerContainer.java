package org.lemon.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import org.lemon.gui.layers.ViewLayer;
import org.lemon.gui.layers.LayerList;
import org.lemon.gui.layers.NodeLayer;
import org.lemon.gui.node.NodePt;

public class LayerContainer extends JInternalFrame {
	
	/**
	 * Serial UID
	 * */
	private static final long serialVersionUID = 1L;
		
	private LayerList layerList = new LayerList();
	private JScrollPane scroll = new JScrollPane(layerList);
	private Workspace workspace = null;

	private Layer background = null;
	
	/**
	 * 
	 * @param workspace		Default workspace.
	 *
	 **/
	public LayerContainer( Workspace workspace ) {
		
		this.workspace = workspace;
		
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
	
	
	public void addLayer( Layer layer ) {
		
		if( layer instanceof ViewLayer ) {
			
			var vlay = (ViewLayer) layer;
			if( vlay.getProperty() == ViewLayer.BACKGROUND_LAYER || getLayerCount() == 0 ) {
				
				if( background != null ) {
					vlay.setTitle( "Layer" );
				}
				
				vlay.setTitle( "Background" );
				background = vlay;
			}
		}
		
		else if( layer instanceof NodeLayer ) {
			
			var nlay = (NodeLayer) layer;
			background = nlay;
		}
		
		layerList.add( layer );
		scroll.revalidate();
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
		 * 
		 * init widgets for bottom menu
		 * 
		 * */
		private void init() {
			
			this.newLayerBtn = new JButton( "New" );
			
			this.copyLayerBtn = new JButton("Cop");
			copyLayerBtn.addActionListener(action -> {
				
			});	
			
			this.deleteLayerBtn = new JButton("Del");
			deleteLayerBtn.addActionListener(action -> {
				
				int option = JOptionPane.showConfirmDialog( layerList, "Delete this layer?" );
				
				switch( option ) {
				
				case 0: {
					Layer lay = layerList.getSelectedValue();
					if( lay.getLayerComponent() instanceof ControllableNode ) {
						var fcon = (ControllableNode) lay.getLayerComponent();
						/*
						 * Setting node position null cause workspace will 
						 * paint node even if the component is 
						 * deleted cause node is still in node list.
						 * So if we set node to null then workspace 
						 * wont get any position to paint node.
						 */
						fcon.getNodePt().start = null;
					}
					else if( lay.getLayerComponent() instanceof ControllerNode ) {
						var fcon = (ControllerNode) lay.getLayerComponent();
						//read upper comment to understand why im setting node positions null
						for( NodePt node: fcon.getNodePts() ) {
							node.start = null;
						}
					}
					
					workspace.remove(lay.getLayerComponent());
					layerList.remove(lay);
					workspace.revalidate();
				}
				break;
				}
				
				revalidate();
			});
			
			
		}
		
	}
	
	
}
