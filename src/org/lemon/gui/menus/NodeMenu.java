package org.lemon.gui.menus;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenu;
import javax.swing.JMenuItem;

import org.lemon.gui.ImageView;
import org.lemon.gui.Workspace;
import org.lemon.gui.node.BlurFilterNode;
import org.lemon.gui.node.HSBAdjustNode;
import org.lemon.gui.node.TextureNode;

public class NodeMenu extends JMenu implements ActionListener {
	
	/**
	 * Serial UID
	 * */
	private static final long serialVersionUID = 1L;
	
	private JMenuItem blur, hsbControl, texture;
	
	private Workspace workspace;
	
	
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
		
		Component node = null;
		Component selected = workspace.getSelectedFrame();;

		if( e.getSource() == hsbControl ) {
			
			if( selected instanceof ImageView ) {
				var v = (ImageView) workspace.getSelectedFrame();
				node = new HSBAdjustNode( v, v.getImagePanel().getImage() );
			}
		}
		
		else if( e.getSource() == blur ) {
			node = new BlurFilterNode();
		}
		
		else if( e.getSource() == texture ) {
			node = new TextureNode();
		}
		
		workspace.add( node );
		workspace.fetchNodes();
		workspace.refresh();
		workspace.revalidate();
	}	
}
