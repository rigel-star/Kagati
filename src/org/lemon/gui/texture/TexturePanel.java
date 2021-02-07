package org.lemon.gui.texture;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;

import org.lemon.graphics.Texture;
import org.lemon.image.LImage;

public class TexturePanel extends JPanel {

	/**
	 * Serial UID
	 */
	private static final long serialVersionUID = 1L;
	
	private Texture texture = null;
	
	private JTextArea nameField = null;
	private JLabel imgField = null;
	
	public TexturePanel( Texture tx ) {
		
		this.texture = tx;
		this.init();
		
		setLayout( new BorderLayout() );
		setSize( 90, 85 );
		setBorder( new EmptyBorder( 5, 5, 5, 5 ));
		
		LImage img = tx.getDrawable();
		
		imgField.setIcon( new ImageIcon( img.getAsBufferedImage() ));
		nameField.setText( tx.getName() );
		
		add( imgField, BorderLayout.CENTER );
		add( nameField, BorderLayout.SOUTH );
	}	
	
	private void init() {
		
		nameField = new JTextArea();
		
		imgField = new JLabel() {
			private static final long serialVersionUID = 1L;
			
			@Override
			public Dimension getPreferredSize() {
				return new Dimension( 75, 75 );
			}
		};
	}
	
	public Texture getTexture() {
		return texture;
	}
}