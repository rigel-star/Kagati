package org.lemon.gui;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JWindow;

/**
 * 
 * Edit (Resize, rotate or any other transformation) 
 * the image attached with specified {@code ImageView}.
 * 
 * */
public class ImageViewSetup extends JWindow {
	
	/**
	 * Serial UID
	 */
	private static final long serialVersionUID = 1L;

	
	private JLabel titleLbl = null;
	private JTextField titleFld = null;
	
	private JLabel widthLbl = null;
	private JTextField widthFld = null;
	
	private JLabel heightLbl = null;
	private JTextField heightFld = null;
	
	JButton okBttn = null;
	JButton cancelBttn = null;
	
	private JPanel fieldsContainer = null;
	private JPanel labelsContainer = null;
	
	
	/**
	 * 
	 * Constructs {@code ImageViewSetting} with specified {@code ImageView}.
	 * @param view		image container
	 * 
	 * */
	public ImageViewSetup( ImageView view ) {
		
		init();
		
		setSize( 400, 400 );
		setLocationRelativeTo( null );
		getRootPane().setBorder( BorderFactory.createLineBorder( Color.GRAY, 4 ) );
		
		var lblsLayout = new GroupLayout( labelsContainer );
		lblsLayout.setAutoCreateGaps( true );  
		lblsLayout.setAutoCreateContainerGaps( true ); 
		
		lblsLayout.setHorizontalGroup(
				lblsLayout.createSequentialGroup()
							.addComponent( titleLbl )
							.addComponent( widthLbl )
							.addComponent( heightLbl ));
		
		
		var fldsLayout = new GroupLayout( fieldsContainer );
		fldsLayout.setAutoCreateGaps( true );  
		fldsLayout.setAutoCreateContainerGaps( true ); 
		
		fldsLayout.setHorizontalGroup(
				fldsLayout.createSequentialGroup()
							.addComponent( titleFld )
							.addComponent( widthFld )
							.addComponent( heightFld ));
		
		var lblsBox = Box.createVerticalBox();
		lblsBox.add( titleLbl );
		lblsBox.add( widthLbl );
		lblsBox.add( heightLbl );
		
		var fldsBox = Box.createVerticalBox();
		fldsBox.add( titleFld );
		fldsBox.add( widthFld );
		fldsBox.add( heightFld );
		
		Container c = getContentPane();
		c.setLayout( new FlowLayout( FlowLayout.RIGHT, 10, 0 ) );
		
		c.add( labelsContainer );
		c.add( fieldsContainer );
		c.add( lblsBox );
		c.add( fldsBox );
		
		setVisible( true );
	}
	
	
	private void init() {
		
		titleLbl = new JLabel( "Title: " );
		titleFld = new JTextField( "500" );
		
		widthLbl = new JLabel( "Width: " );
		widthFld = new JTextField( "500" );
		
		heightLbl = new JLabel( "Height: " );
		heightFld = new JTextField( "500" );
		
		okBttn = new JButton( "OK" );
		cancelBttn = new JButton( "CANCEL" );
		
		fieldsContainer = new JPanel();
		labelsContainer = new JPanel();
	}
	
	
	@Override
	public Dimension getPreferredSize() {
		return new Dimension( 400, 400 );
	}
}
