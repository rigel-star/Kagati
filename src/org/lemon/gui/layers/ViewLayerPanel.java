package org.lemon.gui.layers;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import org.lemon.filter.basic.ResizeImage;
import org.lemon.gui.ImageView;

public class LayerPanel extends JPanel {
	
	/**
	 * Texture UID
	 * */
	private static final long serialVersionUID = 1L;
	
	private Layer layer = null;
	private BufferedImage indiImg = null;
	private String title;
	
	private JTextArea nameField = null;
	private JLabel imgField = null;
		
	
	/**
	 * If you want new empty {@code Layer}, pass {@code EmptyLayer}.
	 * @param layer
	 * */
	public LayerPanel( Layer layer ) {
		
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
		
		if( indiImg != null )
			indiImg = new ResizeImage( indiImg ).getImageSizeOf( 50, 50 );
		
		nameField.setText( title );
		imgField.setIcon( new ImageIcon( indiImg ) );
		
		setLayout( new BorderLayout() );
		add( imgField, BorderLayout.WEST );
		add( nameField, BorderLayout.EAST );
	}
	
	
	
	private void init() {
		this.nameField = new JTextArea();
		
		this.imgField = new JLabel() {
			private static final long serialVersionUID = 1L;
			
			@Override
			public Dimension getPreferredSize() {
				return new Dimension(50, 50);
			}
		};
	}
	
	
	public Layer getLayer() {
		return layer;
	}
	
	
	@Override
	public Dimension getPreferredSize() {
		return new Dimension(150, 60);
	}
		
}
