package org.lemon.gui;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JWindow;

import org.lemon.lang.LemonObject;
import org.lemon.lang.Nullable;

import sround.awt.RoundJTextField;


@LemonObject( type = LemonObject.GUI_CLASS )
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

	private final static int V_GAP = 10;
	private final static int H_GAP = 10;
	private final static int GAP = 5;
	private final static int BORDER = 12;
	
	private JLabel titleLbl = null;
	private JTextField titleFld = null;
	
	private JLabel widthLbl = null;
	private JTextField widthFld = null;
	
	private JLabel heightLbl = null;
	private JTextField heightFld = null;
	
	private JButton okBttn = null;
	private JButton cancelBttn = null;
	
	private JPanel fieldsContainer = null;
	private JPanel labelsContainer = null;
	private JPanel buttonsContainer = null;
	
	GridBagConstraints gbc = new GridBagConstraints();
	
	/**
	 * 
	 * Constructs {@code ImageViewSetting} with specified {@code ImageView}.
	 * @param view		image container
	 * 
	 * */
	public ImageViewSetup( @Nullable ImageView view ) {
		
		init();
		
		setSize( 400, 400 );
		setLocationRelativeTo( null );
		getRootPane().setBorder( BorderFactory.createLineBorder( Color.GRAY, 4 ) );

		var main = new JPanel();
		main.setBorder( BorderFactory.createEmptyBorder( BORDER, BORDER, BORDER, BORDER ) );
		
		var pos = new GridBagConstraintsHelper();
		
		main.add( createLabelPanel(), pos.nextCol() );
		main.add( new Gap(), pos.nextCol() );
		main.add( createFieldPanel(), pos.nextCol() );
		main.add( new Gap(), pos.nextCol() );
		main.add( createButtonPanel(), pos.nextCol() );
		
		Container c = getContentPane();
		
		c.setLayout( new GridLayout( 1, 1 ) );
		c.add( main );
		
		setVisible( true );
		pack();
	}
	
	
	public static void main(String[] args) {
		new ImageViewSetup( null );
	}
	
	
	/**
	 * 
	 * {@code JPanel} which holds the labels.
	 * 
	 * */
	private JPanel createLabelPanel() {
		
		labelsContainer.setLayout( new GridLayout( 3, 1, V_GAP, H_GAP ));

		labelsContainer.add( titleLbl );
		labelsContainer.add( widthLbl );
		labelsContainer.add( heightLbl );
		
		return labelsContainer;
	}
	
	
	/**
	 * 
	 * {@code JPanel} which holds the fields.
	 * 
	 * */
	private JPanel createFieldPanel() {
		
		fieldsContainer.setLayout( new GridLayout( 3, 1, GAP, GAP ));

		fieldsContainer.add( titleFld );
		fieldsContainer.add( widthFld );
		fieldsContainer.add( heightFld );
		
		return fieldsContainer;
	}
	
	
	/**
	 * 
	 * {@code JPanel} which holds the fields.
	 * 
	 * */
	private JPanel createButtonPanel() {
		
		buttonsContainer.setLayout( new GridLayout( 3, 1, V_GAP, H_GAP ));

		buttonsContainer.add( okBttn );
		buttonsContainer.add( cancelBttn );
		
		return buttonsContainer;
	}
	
	
	private void init() {
		
		var font = new Font( null, Font.PLAIN, 15 );
		
		titleLbl = new JLabel( "Title: ", JLabel.LEFT );
		titleFld = new RoundJTextField( "500", 100, 20 );
		
		widthLbl = new JLabel( "Width: " );
		widthFld = new RoundJTextField( "500", 100, 20 );
		
		heightLbl = new JLabel( "Height: " );
		heightFld = new RoundJTextField( "500", 100, 20 );
		
		okBttn = new JButton( "Ok" );
		okBttn.setPreferredSize( new Dimension( 100, 30 ));
		okBttn.setFont( font );
		
		cancelBttn = new JButton( "Cancel" );
		cancelBttn.setPreferredSize( new Dimension( 100, 30 ));
		cancelBttn.setFont( font );
		
		fieldsContainer = new JPanel();
		labelsContainer = new JPanel();
		buttonsContainer = new JPanel();
	}
	
	
	@Override
	public Dimension getPreferredSize() {
		return new Dimension( 250, 200 );
	}
}
