package org.lemon.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Array;
import java.lang.reflect.Field;

import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JSpinner;
import javax.swing.WindowConstants;
import javax.swing.colorchooser.AbstractColorChooserPanel;

public final class ColorPicker extends JDialog implements ActionListener {
	
	/**
	 * Serial UID
	 */
	private static final long serialVersionUID = 1L;
	
	private JColorChooser colChooser = null;
	private JPanel panel = null;
	
	private final JPanel bttnPanel = new JPanel();
	private JButton doneBttn = null;
	
	public static void main(String[] args) {
		new ColorPicker( "Color Picker" );
	}
	
	public ColorPicker( final String title ) {
		init();
		setTitle( title );
		modifyJColorChooser();
		
		bttnPanel.setLayout( new FlowLayout( FlowLayout.RIGHT, 5, 5 ));
		bttnPanel.add( doneBttn );
		
		panel.setLayout( new BorderLayout() );
		panel.add( colChooser, BorderLayout.CENTER );
		panel.add( bttnPanel, BorderLayout.SOUTH );
		
		colChooser.setOpaque( false );
		colChooser.setPreviewPanel( new JPanel() );
		colChooser.setColor( 120, 20, 57 );
		
		add( panel, BorderLayout.CENTER );
		setDefaultCloseOperation( WindowConstants.HIDE_ON_CLOSE );
		pack();
		
		final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		setLocation((screenSize.width - this.getWidth()) / 2, (screenSize.height - this.getHeight()) / 2);
		setResizable( false );
		setVisible( true );
	}
	
	
	/**
	 * Init the widgets.
	 * */
	private void init() {

		colChooser = new JColorChooser();
		
		doneBttn = new JButton( "Done" );
		doneBttn.addActionListener( this );
		
		panel = new JPanel() {

			/**
			 * Serial UID
			 */
			private static final long serialVersionUID = 1L;

			@Override
			protected void paintComponent(final Graphics g) {
				final Graphics2D g2d = (Graphics2D) g;
				g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
				g2d.fillRect(0, 0, this.getWidth(), this.getHeight());
				super.paintComponent(g);
			}
		};
	}

	
	@Override
	public void actionPerformed( final ActionEvent e ) {
		
		if( e.getSource() == doneBttn ) {
			dispose();
		}
	}
	
	
	/**
	 * @return Selected color.
	 * */
	public Color getColor() {
		return colChooser.getColor();
	}
	
	
	/**
	 * Modify the panel.
	 * */
	private void modifyJColorChooser() {
		final AbstractColorChooserPanel[] panels = this.colChooser.getChooserPanels();
		for( final AbstractColorChooserPanel accp : panels ) {
			if( !accp.getDisplayName().equals("RGB") ) {
				this.colChooser.removeChooserPanel(accp);
			}
		}

		final AbstractColorChooserPanel[] colorPanels = this.colChooser.getChooserPanels();
		final AbstractColorChooserPanel cp = colorPanels[0];
		
		Field f = null;
		try {
			f = cp.getClass().getDeclaredField("panel");
		} catch ( NoSuchFieldException | SecurityException e ) {
			e.printStackTrace();
		} finally {
			f.setAccessible( true );
		}
		
		
		Object colorPanel = null;
		try {
			colorPanel = f.get(cp);
		} catch ( IllegalArgumentException | IllegalAccessException e ) {
			e.printStackTrace();
		}

		Field f2 = null;
		try {
			f2 = colorPanel.getClass().getDeclaredField("spinners");
		} catch ( NoSuchFieldException | SecurityException e4 ) {
			e4.printStackTrace();
		} finally {
			f2.setAccessible( true );
		}
		
		Object rows = null;
		try {
			rows = f2.get( colorPanel );
		} catch ( IllegalArgumentException | IllegalAccessException e3 ) {
			e3.printStackTrace();
		}

		final Object transpSlispinner = Array.get(rows, 3);
		Field f3 = null;
		try {
			f3 = transpSlispinner.getClass().getDeclaredField( "slider" );
		} catch ( NoSuchFieldException | SecurityException e ) {
			e.printStackTrace();
		} finally {
			f3.setAccessible( true );
		}
		
		JSlider slider = null;
		try {
			slider = (JSlider) f3.get( transpSlispinner );
		} catch ( IllegalArgumentException | IllegalAccessException e2 ) {
			e2.printStackTrace();
		}
		
		slider.setVisible( false );
		
		Field f4 = null;
		try {
			f4 = transpSlispinner.getClass().getDeclaredField( "spinner" );
		} catch ( NoSuchFieldException | SecurityException e1 ) {
			e1.printStackTrace();
		}
		
		f4.setAccessible( true );
		
		JSpinner spinner = null;
		try {
			spinner = (JSpinner) f4.get( transpSlispinner );
		} catch ( IllegalArgumentException | IllegalAccessException e ) {
			e.printStackTrace();
		}
		
		spinner.setVisible( false );
		
		Field f5 = null;
		try {
			f5 = transpSlispinner.getClass().getDeclaredField("label");
		} catch ( NoSuchFieldException | SecurityException e1 ) {
			e1.printStackTrace();
		}
		
		f5.setAccessible( true );
		
		JLabel label = null;
		try {
			label = (JLabel) f5.get( transpSlispinner );
		} catch( IllegalArgumentException | IllegalAccessException e ) {
			e.printStackTrace();
		}
		
		label.setVisible( false );
		
		Field f6 = null;
		try {
			f6 = transpSlispinner.getClass().getDeclaredField( "value" );
		} catch( NoSuchFieldException | SecurityException e1 ) {
			e1.printStackTrace();
		}
		
		f6.setAccessible( true );
		
		float value = 0;
		try {
			value = (float) f6.get( transpSlispinner );
			System.out.println( value );
		} catch( IllegalArgumentException | IllegalAccessException e ) {
			e.printStackTrace();
		}
	}
}