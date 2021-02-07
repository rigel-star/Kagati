package org.lemon.gui.menus;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComponent;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

import org.lemon.gui.Layer;
import org.lemon.gui.LayerContainer;
import org.lemon.gui.Workspace;
import org.lemon.gui.layers.NodeLayer;
import org.lemon.gui.node.BlurFilterNodeComponent;
import org.lemon.gui.node.HSBAdjustNodeComponent;
import org.lemon.gui.node.TextureNodeComponent;

public class NodeMenu extends JMenu implements ActionListener {
	
	/**
	 * Serial UID
	 * */
	private static final long serialVersionUID = 1L;
	
	private JMenuItem blur, hsbControl, texture;
	private Workspace workspace;
	
	/**
	 * 
	 * @param 	workspace
	 * */
	public NodeMenu( Workspace workspace ) {
		
		this.workspace = workspace;
		
		setText( "Node" );
		init();
		addAll();
	}
	
	private void init() {
		this.blur = new JMenuItem( "Blur" ) ;
		this.hsbControl = new JMenuItem( "HSB" );
		this.texture = new JMenuItem( "Texture" );
	}
	
	private void addAll() {
		
		hsbControl.addActionListener( this );
		add( hsbControl );
		
		blur.addActionListener( this );
		add( blur );
		
		texture.addActionListener( this );
		add( texture );
		
	}

	@Override
	public void actionPerformed( ActionEvent e ) {
		
		JComponent node = null;
		Layer layer = null;
		LayerContainer lycont = workspace.getLayerContainer();
		
		if( e.getSource() == hsbControl ) {
			node = new HSBAdjustNodeComponent( lycont );
			layer = new NodeLayer( node, "HSB Node" );
		}
		else if( e.getSource() == blur ) {
			node = new BlurFilterNodeComponent( lycont );
			layer = new NodeLayer( node, "Blur Node" );
		}
		else if( e.getSource() == texture ) {
			node = new TextureNodeComponent( lycont );
			layer = new NodeLayer( node, "Texture Node" );
		}
		
		lycont.addLayer( layer );
		lycont.revalidate();
		
		workspace.add( node );
		workspace.fetchNodes();
		workspace.refreshListeners();
		workspace.revalidate();
	}	
}
