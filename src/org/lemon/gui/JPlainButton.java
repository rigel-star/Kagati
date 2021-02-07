package org.lemon.gui;

import javax.swing.Icon;
import javax.swing.JButton;

public class JPlainButton extends JButton {

	/**
	 * Serial UID
	 */
	private static final long serialVersionUID = 1L;
	
	public JPlainButton( String text ) {
		this( text, null );
	}
	
	public JPlainButton( Icon icon ) {
		this( null, icon );
	}
	
	public JPlainButton( String text, Icon icon ) {
		super( text, icon );
		
		setOpaque( false );
		//setContentAreaFilled( false );
		//setBorderPainted( true );
	}
}
