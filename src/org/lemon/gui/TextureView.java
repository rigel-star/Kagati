package org.lemon.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.lemon.filter.ResizeImageFilter;
import org.lemon.graphics.Texture;
import org.lemon.graphics.texture.WoodTexture;
import org.lemon.image.LImage;
import org.lemon.lang.NonNull;
import org.lemon.utils.Utils;

public class TextureView extends NodeView {
	private static final long serialVersionUID = 1L;
	
	private static JLabel txtrHolder = null;
	
	private static JSlider zoomSlider = null;
	private static JLabel zoomTxtHolder = null;
	
	private static JSlider alphaSlider = null;
	private static JLabel alphaTxtHolder = null;
	
	private static JLabel blendModeTxt = null;
	private static JComboBox<String> blendModeCombo = null;
	
	private Texture texture = new WoodTexture();
	
	private JPanel sliderContainer = null;
	
	public TextureView( @NonNull LayerContainer lycont ) {
		super( lycont );
		this.init();
		
		setSize( 200, 300 );
		setTitle( "Texture" );
		setResizable( false );
		setVisible( true );
		setClosable( true );
		setLocation( 20, 50 );
				
		LImage img = texture.getDrawable();
		BufferedImage copy = Utils.getImageCopy( img.getAsBufferedImage() );
		img = new ResizeImageFilter( 200, 150 ).filter( new LImage( copy ));
		
		txtrHolder.setBorder( new EmptyBorder( 15, 15, 15, 15 ));
		txtrHolder.setIcon( new ImageIcon( copy ));
		
		setLayout( new BorderLayout( 5, 15 ) );
		
		sliderContainer.setLayout( new GridLayout( 2, 2, 2, 2 ));
		sliderContainer.add( zoomTxtHolder );
		sliderContainer.add( zoomSlider );
		sliderContainer.add( alphaTxtHolder );
		sliderContainer.add( alphaSlider );
		
		JPanel blendComboPan = new JPanel();
		blendComboPan.setLayout( new FlowLayout( FlowLayout.LEFT, 12, 12 ));
		blendComboPan.add( blendModeTxt );
		blendComboPan.add( blendModeCombo );
		
		add( txtrHolder, BorderLayout.NORTH );
		add( blendComboPan, BorderLayout.CENTER );
		add( sliderContainer, BorderLayout.SOUTH );
	}
	
	private MouseHandler mouse = new MouseHandler();
	private ChangeHandler change = new ChangeHandler();
	/**
	 * Initialize the widgets.
	 * */
	private void init() {
		
		zoomTxtHolder = new JLabel( "Zoom:" );
		alphaTxtHolder = new JLabel( "Alpha:" );
		
		txtrHolder = new JLabel();
		txtrHolder.setPreferredSize( new Dimension( 200, 150 ));
		txtrHolder.addMouseListener( mouse );
		
		zoomSlider = new JSlider();
		zoomSlider.setPreferredSize( new Dimension( 120, 20 ) );
		zoomSlider.addChangeListener( change );
		
		alphaSlider = new JSlider( SwingConstants.HORIZONTAL, 0, 10, 5 );
		alphaSlider.setPreferredSize( new Dimension( 120, 20 ) );
		alphaSlider.addChangeListener( change );
		
		blendModeTxt = new JLabel( "Composite" );
		blendModeCombo = new JComboBox<String>( new String[] {
													"Select",
													"Add",
													"Multiply"
		});
		ActionHandler ah = new ActionHandler();
		blendModeCombo.addActionListener( ah );
		
		sliderContainer = new JPanel();
	}
	
	@Override
	public Dimension getPreferredSize() {
		return new Dimension( 60, 100 );
	}
	
	/**
	 * Get currently selected {@link Texture}.
	 * @return Selected {@link Texture}.
	 * */
	public Texture getSelectedTexture() {
		return texture;
	}
	
	/**
	 * Mouse handler for texture node component.
	 * */
	class MouseHandler extends MouseAdapter {
		
		@Override
		public void mouseClicked( MouseEvent e ) {
			super.mouseClicked( e );
			
			if( e.getSource() == txtrHolder ) {
				TextureChooser ch = new TextureChooser( txtrHolder );
				texture = ch.getChoosenTexture();
			}	
		}	
	}
	
	class ChangeHandler implements ChangeListener {

		@Override
		public void stateChanged( ChangeEvent e ) {
			
		}
	}
	
	class ActionHandler implements ActionListener {

		@Override
		public void actionPerformed( ActionEvent e ) {
			
			if ( e.getSource() == blendModeCombo ) {
				
			}
		}
	}
}
