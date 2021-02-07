package org.lemon.gui.layers;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.lemon.filter.ResizeImageFilter;
import org.lemon.gui.Layer;
import org.lemon.gui.Node;
import org.lemon.image.LImage;
import org.lemon.lang.NonNull;

public class NodeLayerPanel extends JPanel {

	/**
	 * Serial UID
	 */
	private static final long serialVersionUID = 1L;
	
	private NodeLayer layer = null;
	
	private JLabel nameField = null;
	private JLabel imgField = null;
	
	public NodeLayerPanel( @NonNull NodeLayer layer ) {
		this.layer = layer;
		
		this.init();
		
		ImageIcon ic = ((Node) layer.getLayerComponent()).getNodeIcon();
		final int icW = ic.getIconWidth();
		final int icH = ic.getIconHeight();
		BufferedImage ig = new BufferedImage( icW, icH, BufferedImage.TYPE_INT_RGB );
		Graphics2D g2 = ig.createGraphics();
		ic.paintIcon( null, g2, 0, 0 );
		g2.dispose();
		
		ig = new ResizeImageFilter( 50, 50 ).filter( new LImage( ig )).getAsBufferedImage();
		
		nameField.setText( layer.getTitle() );
		imgField.setIcon( new ImageIcon( ig ) );
		
		setLayout( new BorderLayout() );
		add( imgField, BorderLayout.WEST );
		add( nameField, BorderLayout.EAST );
	}
	
	/**
	 * Initialize the widgets.
	 * */
	private void init() {
		this.nameField = new JLabel( "Node Layer" );
		this.imgField = new JLabel();
		imgField.setPreferredSize( new Dimension( 50, 50 ));
	}
	
	public Layer getLayer() {
		return layer;
	}
}