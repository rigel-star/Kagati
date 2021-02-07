package org.lemon.filter.gui;

import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import org.lemon.filter.AbstractImageFilter;
import org.lemon.gui.ImageView;
import org.lemon.lang.NonNull;

public class TransformFilterPanel extends AbstractFilterPanel {

	/**
	 * Serial UID
	 */
	private static final long serialVersionUID = 1L;
	
	private JPanel titlePanel = null;
	private JPanel xPanel = null;
	private JPanel yPanel = null;
	private JPanel zPanel = null;
	
	public TransformFilterPanel( @NonNull final ImageView view ) {
		super( null, view );
		this.init();
		
		setLayout( new BoxLayout( this, BoxLayout.X_AXIS ));
		add( titlePanel );
		add( new JSeparator( SwingConstants.VERTICAL ) );
		add( xPanel );
		add( new JSeparator( SwingConstants.VERTICAL ) );
		add( yPanel );
		add( new JSeparator( SwingConstants.VERTICAL ) );
		add( zPanel );
	}
	
	private JTextField transXtxtFld = null;
	private JTextField transYtxtFld = null;
	private JTextField transZtxtFld = null;
	
	private JTextField rotateXtxtFld = null;
	private JTextField rotateYtxtFld = null;
	private JTextField rotateZtxtFld = null;
	
	private JTextField scaleXtxtFld = null;
	private JTextField scaleYtxtFld = null;
	private JTextField scaleZtxtFld = null;
	
	/**
	 * Initialize the widgets.
	 * */
	private void init() {
		
		this.titlePanel = new JPanel();
		titlePanel.setLayout( new GridLayout( 3, 1, 10, 18 ));
		titlePanel.setBorder( BorderFactory.createEmptyBorder( 2, 5, 2, 5 ));
		titlePanel.add( new JLabel( "Translate" ));
		titlePanel.add( new JLabel( "Rotate" ));
		titlePanel.add( new JLabel( "Scale" ));
		
		this.transXtxtFld = new JTextField( "1.0" );
		this.transYtxtFld = new JTextField( "1.0" );
		this.transZtxtFld = new JTextField( "1.0" );
		
		this.rotateXtxtFld = new JTextField( "1.0" );
		this.rotateYtxtFld = new JTextField( "1.0" );
		this.rotateZtxtFld = new JTextField( "1.0" );
		
		this.scaleXtxtFld = new JTextField( "1.0" );
		this.scaleYtxtFld = new JTextField( "1.0" );
		this.scaleZtxtFld = new JTextField( "1.0" );
		
		JTextField[] xFlds = new JTextField[3];
		xFlds[0] = transXtxtFld;
		xFlds[1] = rotateXtxtFld;
		xFlds[2] = scaleXtxtFld;
		
		JTextField[] yFlds = new JTextField[3];
		yFlds[0] = transYtxtFld;
		yFlds[1] = rotateYtxtFld;
		yFlds[2] = scaleYtxtFld;
		
		JTextField[] zFlds = new JTextField[3];
		zFlds[0] = transZtxtFld;
		zFlds[1] = rotateZtxtFld;
		zFlds[2] = scaleZtxtFld;
		
		Dimension txtFldSize = new Dimension( 30, 24 );
		
		this.xPanel = new JPanel();
		xPanel.setLayout( new GridLayout( 3, 2, 5, 10 ));
		xPanel.setBorder( BorderFactory.createEmptyBorder( 2, 5, 2, 5 ));
		
		for ( int i = 0; i < 3; i++ ) {
			xPanel.add( new JLabel( "X:" ));
			xFlds[i].setPreferredSize( txtFldSize );
			xPanel.add( xFlds[i] );
		}
		
		this.yPanel = new JPanel();
		yPanel.setLayout( new GridLayout( 3, 2, 5, 10 ));
		yPanel.setBorder( BorderFactory.createEmptyBorder( 2, 5, 2, 5 ));
		
		for ( int i = 0; i < 3; i++ ) {
			yPanel.add( new JLabel( "Y:" ));
			yFlds[i].setPreferredSize( txtFldSize );
			yPanel.add( yFlds[i] );
		}
		
		this.zPanel = new JPanel();
		zPanel.setLayout( new GridLayout( 3, 2, 5, 10 ));
		zPanel.setBorder( BorderFactory.createEmptyBorder( 2, 5, 2, 5 ));
		
		for ( int i = 0; i < 3; i++ ) {
			zPanel.add( new JLabel( "Z:" ));
			zFlds[i].setPreferredSize( txtFldSize );
			zPanel.add( zFlds[i] );
		}
	}

	@Override
	public AbstractImageFilter getFilter() {
		return null;
	}
	
	@Override
	public String getPanelName() {
		return "Transform";
	}
}
