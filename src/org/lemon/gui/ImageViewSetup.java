package org.lemon.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import sround.awt.RoundJTextField;

public class ImageViewSetup extends JDialog implements ActionListener {
	private final static int V_GAP = 10;
	private final static int H_GAP = 10;
	private final static int BORDER = 12;
	
	private JTextField titleFld = null;
	private JTextField widthFld = null;
	private JTextField heightFld = null;
	
	private JButton okBttn = null;
	private JButton cancelBttn = null;
	
	private JComboBox<String> colModeComboBox = null;
	
	private JPanel labelsContainer = null;
	private JPanel buttonsContainer = null;
	private JPanel titleContainer = null;
	private JPanel measureContainer = null;
	
	private WorkspaceArena workspace = null;
	
	/**
	 * Constructs {@code ImageViewSetup} with specified {@code Workspace}.
	 * @param wk		{@View} container
	 * */
	public ImageViewSetup(WorkspaceArena wk) {
		
		init();
		this.workspace = wk;
		
		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		Dimension size = getPreferredSize();
		Point loc = new Point((screen.width >> 1) - (size.width >> 1), (screen.height >> 1) - (size.height >> 1) );
		setLocation(loc);
		getRootPane().setBorder( BorderFactory.createLineBorder( Color.GRAY, 4 ) );

		JPanel main = new JPanel();
		main.setBorder( BorderFactory.createEmptyBorder( BORDER, BORDER, BORDER, BORDER ) );
		
		GridBagConstraintsHelper pos = new GridBagConstraintsHelper();
		
		JPanel widgsContainer = new JPanel();
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
		
		Header header = new Header("New", 20);
		header.setTitle("New");
		titleFld.setText("New");
		widthFld.setText("300");
		heightFld.setText("300");
		
		Container c = getContentPane();
		c.setLayout( new BorderLayout() );
		c.add( main, BorderLayout.CENTER );
		c.add( header, BorderLayout.NORTH );
		
		setVisible( true );
		pack();
	}
	
	private JPanel createTitlePanel() {
		titleContainer.setLayout(new FlowLayout(FlowLayout.LEFT));
		titleContainer.add(new JLabel("Title:"));
		titleContainer.add(titleFld);
		return titleContainer;
	}
	
	private JPanel createMeasurementPanel() {
		measureContainer.setLayout( new GridLayout( 3, 2, 5, 5 ));
		measureContainer.add(widthFld);
		measureContainer.add(createMeasureTypeComboBox() );
		measureContainer.add(heightFld);
		measureContainer.add(createMeasureTypeComboBox() );
		measureContainer.add(colModeComboBox);
		measureContainer.add(createImageTypeComboBox() );
		return measureContainer;
	}
	
	private JPanel createLabelPanel() {
		labelsContainer.setLayout(new GridLayout(3, 1, H_GAP, V_GAP + 5));
		labelsContainer.add(new JLabel("Width:"));
		labelsContainer.add(new JLabel("Height:"));
		labelsContainer.add(new JLabel("Color model:"));
		return labelsContainer;
	}
	
	private JPanel createButtonPanel() {
		buttonsContainer.setLayout(new GridLayout(2, 1, V_GAP, H_GAP));
		buttonsContainer.add(okBttn);
		buttonsContainer.add(cancelBttn);
		return buttonsContainer;
	}
	
	private void init() {
		colModeComboBox = createColorModeComboBox();
		
		Font font2 = new Font( null, Font.PLAIN, 12 );
		
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
		
		JComboBox<String> combo = new JComboBox<String>(colModes);
		combo.setPreferredSize(new Dimension(150, 30));
		return combo;
	}
	
	private JComboBox<String> createMeasureTypeComboBox() {
		final String[] measureModes = {
				"Pixels"
		};
		
		JComboBox<String> combo = new JComboBox<String>(measureModes);
		combo.setPreferredSize(new Dimension(100, 30));
		return combo;
	}
	
	
	private JComboBox<String> createImageTypeComboBox() {
		final String[] measureModes = {
				"Default",
				"Gray scale",
		};
		
		JComboBox<String> combo = new JComboBox<String>(measureModes);
		combo.setPreferredSize(new Dimension(100, 30));
		return combo;
	}
	
	@Override
	public Dimension getPreferredSize() {
		return new Dimension(580, 300);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == okBttn) {
			int width = Integer.parseInt(widthFld.getText());
			int height = Integer.parseInt(heightFld.getText());
			String title = titleFld.getText();
			ImageView view = new CanvasView(width, height, Color.yellow, title);
			workspace.addView(view);
			dispose();
		}
		else if(e.getSource() == cancelBttn) {
			dispose();
		}
	}
}