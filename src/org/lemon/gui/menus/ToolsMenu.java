package org.lemon.gui.menus;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenu;
import javax.swing.JMenuItem;

import org.lemon.filter.gui.AbstractFilterPanel;
import org.lemon.filter.gui.FilterPanelWindow;
import org.lemon.filter.gui.TransformFilterPanel;
import org.lemon.gui.ImageView;
import org.lemon.gui.Workspace;
import org.lemon.lang.NonNull;

public class ToolsMenu extends JMenu implements ActionListener {

	/**
	 * Serial UID
	 */
	private static final long serialVersionUID = 1L;
	
	private static JMenu transform = null;
	private static Workspace wks;
	
	public ToolsMenu( @NonNull Workspace wk ) {
		
		wks = wk;
		this.init();
		setText( "Tools" );
		
		add( transform );
	}
	
	
	private JMenuItem translate, rotate, scale;
	
	/**
	 * Initialize the widgets.
	 * */
	private void init() {
		
		transform = new JMenu( "Transform" );
		
		translate = new JMenuItem( "Translate" );
		translate.addActionListener( this );
		
		rotate = new JMenuItem( "Rotate" );
		rotate.addActionListener( this );
		
		scale = new JMenuItem( "Scale" );
		scale.addActionListener( this );
		
		transform.add( translate );
		transform.add( rotate );
		transform.add( scale );
	}
	
	@Override
	public void actionPerformed( ActionEvent e ) {
		
		if ( e.getSource() == translate ) {
			AbstractFilterPanel pan = new TransformFilterPanel( (ImageView) wks.getSelectedFrame() );
			new FilterPanelWindow( pan );
		}	
	}
}
