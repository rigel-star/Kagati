package org.lemon.gui.layers;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import org.lemon.filter.ResizeImageFilter;
import org.lemon.gui.ImageView;
import org.lemon.image.LImage;
import org.lemon.lang.Nullable;

public class ViewLayerPanel extends JPanel {
	
	/**
	 * Texture UID
	 * */
	private static final long serialVersionUID = 1L;
	
	private ViewLayer layer = null;
	private BufferedImage indiImg = null;
	private String title = null;
	
	private JTextArea nameField = null;
	private JLabel imgField = null;
	
	
	/**
	 * 
	 * If you want new empty {@code Layer}, pass {@code EmptyLayer}.
	 * @param layer
	 * 
	 * */
	public ViewLayerPanel( @Nullable ViewLayer layer ) {
		
		this.layer = layer;
		this.init();
		
		if( layer == null )
			layer = new EmptyLayer();
		
		if( layer instanceof EmptyLayer ) {
			
			var empcanv = ( EmptyLayer.EmptyCanvas ) layer.getLayerComponent();
			title = layer.getTitle();
			indiImg = empcanv.getEmptyImage();
		}
		else if( layer.getLayerComponent() instanceof ImageView ) {
			
			var view = ((ImageView) layer.getLayerComponent() );//.getImage();
			title = layer.getTitle();
			indiImg = view.getCurrentImage();
		}
		
		if( indiImg != null ) {
			indiImg = new ResizeImageFilter( 50, 50 )
													.filter( new LImage( indiImg ))
													.getAsBufferedImage();
		}
		
		nameField.setText( title );
		imgField.setIcon( new ImageIcon( indiImg ) );
		
		setLayout( new BorderLayout() );
		add( imgField, BorderLayout.WEST );
		add( nameField, BorderLayout.EAST );
	}
	
	
	
	private void init() {
		this.nameField = new JTextArea();
		
		this.imgField = new JLabel() {
			
			/**
			 * Serial UID
			 * */
			private static final long serialVersionUID = 1L;
			
			@Override
			public Dimension getPreferredSize() {
				return new Dimension(50, 50);
			}
		};
	}
	
	
	public ViewLayer getLayer() {
		return layer;
	}
	
	
	@Override
	public Dimension getPreferredSize() {
		return new Dimension(150, 60);
	}
		
}
