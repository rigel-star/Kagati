package org.lemon.gui.menu;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenu;
import javax.swing.JMenuItem;

import org.lemon.filter.gui.FilterPanelWindow;
import org.lemon.filter.gui.LightFilterPanel2;
import org.lemon.gui.ImageView;
import org.lemon.gui.WorkspaceArena;

public class Menu3D extends JMenu implements ActionListener {
	
	/**
	 * Serial UID
	 * */
	private static final long serialVersionUID = 1L;
	
	private JMenu light3d;
	private JMenu object3d;
	
	/*types of lights*/
	private JMenuItem spotLight;
	private JMenuItem infiniteLight;
	
	/*types of objects*/
	private JMenuItem circle3d;
	private JMenuItem square3d;
	
	private WorkspaceArena wks = null;
	
	public Menu3D( WorkspaceArena wk ) {
		
		this.wks = wk;
		setText( "3D" );
		
		light3d = createLight3dMenu();
		object3d = createObject3dMenu();
		
		add( light3d );
		add( object3d );
	}
	
	/**
	 * Create menu which contains options for lights.
	 * 
	 * @return {@code JMenu} containing all lights.
	 * */
	private JMenu createLight3dMenu() {
		var l3d = new JMenu( "Light" );
		
		spotLight = new JMenuItem( "Spot" );
		spotLight.addActionListener( this );
		
		infiniteLight = new JMenuItem( "Infinite" );
		infiniteLight.addActionListener( this );
		
		l3d.add( spotLight );
		l3d.add( infiniteLight );
		
		return l3d;
	}
	
	/**
	 * Create menu which contains options for 3d objects.
	 * 
	 * @return {@code JMenu} containing all objects.
	 * */
	private JMenu createObject3dMenu() {
		var o3d = new JMenu( "Object" );
		
		circle3d = new JMenuItem( "Circle" );
		square3d = new JMenuItem( "Square" );
		
		o3d.add( circle3d );
		o3d.add( square3d );
		
		return o3d;
	}

	@Override
	public void actionPerformed( ActionEvent e ) {
		
		Component comp = wks.getSelectedFrame();
		
		if ( e.getSource() == spotLight ) {
			LightFilterPanel2 lip = new LightFilterPanel2( ( ImageView ) comp );
			new FilterPanelWindow( lip );
		}
	}
}
