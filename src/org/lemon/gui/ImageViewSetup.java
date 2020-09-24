package org.lemon.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JWindow;

import org.lemon.gui.image.PanelMode;
import org.lemon.lang.LemonObject;
import org.lemon.lang.NonNull;

import sround.awt.RoundJTextField;


@LemonObject( type = LemonObject.GUI_CLASS )
/**
 * 
 * Create new canvas.
 * 
 * */
public class ImageViewSetup extends JWindow implements ActionListener {
	
	/**
	 * Serial UID
	 */
	private static final long serialVersionUID = 1L;
	
	private final static int V_GAP = 10;
	private final static int H_GAP = 10;
	private final static int BORDER = 12;
	
	private JLabel titleLbl = null;
	private JTextField titleFld = null;
	
	private JLabel widthLbl = null;
	private JTextField widthFld = null;
	
	private JLabel heightLbl = null;
	private JTextField heightFld = null;
	
	private JButton okBttn = null;
	private JButton cancelBttn = null;
	
	private JLabel colModeLbl = null;
	private JComboBox<String> colModeComboBox = null;
	
	private JPanel labelsContainer = null;
	private JPanel buttonsContainer = null;
	private JPanel titleContainer = null;
	private JPanel measureContainer = null;
	
	GridBagConstraints gbc = new GridBagConstraints();
	
	private Workspace workspace = null;
	
	/**
	 * 
	 * Constructs {@code ImageViewSetup} with specified {@code Workspace}.
	 * @param wk		{@View} container
	 * 
	 * */
	public ImageViewSetup( @NonNull Workspace wk ) {
		
		init();
		this.workspace = wk;
		
		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		Dimension size = getPreferredSize();
		Point loc = new Point( (screen.width >> 1) - (size.width >> 1), (screen.height >> 1) - (size.height >> 1) );
		setLocation( loc );
		getRootPane().setBorder( BorderFactory.createLineBorder( Color.GRAY, 4 ) );

		var main = new JPanel();
		main.setBorder( BorderFactory.createEmptyBorder( BORDER, BORDER, BORDER, BORDER ) );
		
		var pos = new GridBagConstraintsHelper();
		
		var widgsContainer = new JPanel();
		widgsContainer.setLocation( 0, 0 );
		widgsContainer.setBorder( BorderFactory.createLineBorder( Color.gray, 2 ));
		
		widgsContainer.add( createLabelPanel(), pos.nextCol() );
		widgsContainer.add( new Gap(), pos.nextCol() );
		widgsContainer.add( createMeasurementPanel(), pos.nextCol() );
		
		main.add( createTitlePanel(), pos.nextRow() );
		main.add(new Gap(), pos.nextRow() );
		main.add( widgsContainer, pos.nextCol() );
		main.add( new Gap(), pos.nextCol() );
		main.add( createButtonPanel(), pos.nextCol() );
		
		var header = new Header( "New", 20 );
		
		header.setTitle( "New" );
		titleFld.setText( "New" );
		widthFld.setText( "300" );
		heightFld.setText( "300" );
		
		Container c = getContentPane();
		
		c.setLayout( new BorderLayout() );
		c.add( main, BorderLayout.CENTER );
		c.add( header, BorderLayout.NORTH );
		
		setVisible( true );
		pack();
	}
	
	
	/**
	 * 
	 * {@code JPanel} which holds the title of specific {@ImageView}.
	 * 
	 * */
	private JPanel createTitlePanel() {
		
		titleContainer.setLayout( new FlowLayout( FlowLayout.LEFT ));
		
		titleContainer.add( titleLbl );
		titleContainer.add( titleFld );
		
		return titleContainer;
	}
	
	
	/**
	 * 
	 * {@code JPanel} which holds the measurement fields.
	 * 
	 * */
	private JPanel createMeasurementPanel() {
		
		measureContainer.setLayout( new GridLayout( 3, 2, 5, 5 ));
		
		measureContainer.add( widthFld );
		measureContainer.add( createMeasureTypeComboBox() );
		measureContainer.add( heightFld );
		measureContainer.add( createMeasureTypeComboBox() );
		measureContainer.add( colModeComboBox );
		measureContainer.add( createImageTypeComboBox() );
		
		return measureContainer;
	}
	
	
	/**
	 * 
	 * {@code JPanel} which holds the labels.
	 * 
	 * */
	private JPanel createLabelPanel() {
		
		labelsContainer.setLayout( new GridLayout( 3, 1, H_GAP, V_GAP + 5 ));

		//labelsContainer.add( titleLbl );
		labelsContainer.add( widthLbl );
		labelsContainer.add( heightLbl );
		labelsContainer.add( colModeLbl );
		
		return labelsContainer;
	}
	
	
	/**
	 * 
	 * {@code JPanel} which holds the buttons.
	 * 
	 * */
	private JPanel createButtonPanel() {
		
		buttonsContainer.setLayout( new GridLayout( 2, 1, V_GAP, H_GAP ));

		buttonsContainer.add( okBttn );
		buttonsContainer.add( cancelBttn );
		
		return buttonsContainer;
	}
	
	
	/**
	 * 
	 * Initialize the widgets.
	 * 
	 * */
	private void init() {
		
		var font = new Font( null, Font.PLAIN, 15 );
		
		titleLbl = new JLabel( "Title: " );
		titleLbl.setPreferredSize( new Dimension( 100, 20 ));
		titleLbl.setFont( font );
		
		widthLbl = new JLabel( "Width: " );
		widthLbl.setPreferredSize( new Dimension( 100, 20 ));
		widthLbl.setFont( font );
		
		heightLbl = new JLabel( "Height: " );
		heightLbl.setPreferredSize( new Dimension( 100, 20 ));
		heightLbl.setFont( font );
		
		colModeLbl = new JLabel( "Color mode: " );
		colModeLbl.setPreferredSize( new Dimension( 100, 20 ));
		colModeLbl.setFont( font );
		
		colModeComboBox = createColorModeComboBox();
		
		var font2 = new Font( null, Font.PLAIN, 12 );
		
		titleFld = new RoundJTextField( "img", 150, 25 );
		titleFld.setFont( font2 );
		
		widthFld = new RoundJTextField( "300", 150, 25 );
		widthFld.setFont( font2 );
		
		heightFld = new RoundJTextField( "300", 150, 25 );
		heightFld.setFont( font2 );
		
		okBttn = new JButton( "Ok" );
		okBttn.setPreferredSize( new Dimension( 100, 30 ));
		okBttn.setFont( font2 );
		okBttn.addActionListener( this );
		
		cancelBttn = new JButton( "Cancel" );
		cancelBttn.setPreferredSize( new Dimension( 100, 30 ));
		cancelBttn.setFont( font2 );
		cancelBttn.addActionListener( this );
		
		labelsContainer = new JPanel();
		buttonsContainer = new JPanel();
		titleContainer = new JPanel();
		measureContainer = new JPanel();
	}
	
	
	private JComboBox<String> createColorModeComboBox() {
		
		final String[] colModes = {
				"RGB",
				"HSB"
		};
		
		var combo = new JComboBox<String>( colModes );
		combo.setPreferredSize( new Dimension( 150, 30 ));
		return combo;
	}
	
	
	private JComboBox<String> createMeasureTypeComboBox() {
		
		final String[] measureModes = {
				"Pixels"
		};
		
		var combo = new JComboBox<String>( measureModes );
		combo.setPreferredSize( new Dimension( 100, 30 ));
		return combo;
	}
	
	
	private JComboBox<String> createImageTypeComboBox() {
		
		final String[] measureModes = {
				"Default",
				"Gray scale",
		};
		
		var combo = new JComboBox<String>( measureModes );
		combo.setPreferredSize( new Dimension( 100, 30 ));
		return combo;
	}
	
	
	@Override
	public Dimension getPreferredSize() {
		return new Dimension( 580, 300 );
	}
	
	
	@Override
	public void actionPerformed( ActionEvent e ) {
		
		if( e.getSource() == okBttn ) {

			int width = Integer.parseInt( widthFld.getText() );
			int height = Integer.parseInt( heightFld.getText() );
			String title = titleFld.getText();
			
			var view = new CanvasView( width, height, Color.white, title, true, PanelMode.CANVAS_MODE );
			workspace.add( view );
			workspace.revalidate();
			
			dispose();
		}
		else if( e.getSource() == cancelBttn ) {
			dispose();
		}
	}
}
