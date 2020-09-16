package org.lemon.gui;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.JLabel;

public class Header extends JLabel {

	/**
	 * Serial UID
	 */
	private static final long serialVersionUID = 1L;
	
	private String title = null;
	
	public Header() {
		this( "Title" );
	}
	
	
	public Header( String title ) {
		this( title, 10 );
	}
	
	
	public Header( String title, int fontSize ) {
		this( title, new Color( 150, 150, 150 ), fontSize );
	}
	
	
	public Header( String title, Color bg, int fontSize ) {
		
		this.title = title;
		
		setLayout( new FlowLayout( FlowLayout.CENTER ));
		setBackground( bg );
		setText( title );
		
		var font = new Font( null, Font.TRUETYPE_FONT, fontSize );
		setFont( font );
	}
	
	
	public void setTitle( String text ) {
		super.setText( text );
		title = text;
	}
	
	
	public String getTitle() {
		return title;
	}
	
}
