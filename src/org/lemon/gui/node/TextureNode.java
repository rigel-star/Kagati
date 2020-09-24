package org.lemon.gui.node;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.border.EmptyBorder;

import org.lemon.filter.ResizeImageFilter;
import org.lemon.gui.ControllerNode;
import org.lemon.gui.TextureChooser;
import org.lemon.image.Texture;
import org.lemon.image.WoodTexture;
import org.lemon.math.Vec2d;

public class TextureNode extends JInternalFrame implements ControllerNode {
	
	/**
	 * Serial UID
	 * */
	private static final long serialVersionUID = 1L;
	
	private final int nodeCount = 1;
	private NodePt imgNode = null;
	private NodePt[] nodes = new NodePt[nodeCount];
	
	private JLabel txtrHolder = null;
	private JSlider zoomSlider = null;
	private JLabel zoomTxtHolder = null;
	
	private Texture texture = new WoodTexture();
	
	private JPanel zoomPan = null;
	
	public TextureNode() {
		this.init();
		
		setSize( 200, 300 );
		setTitle( "Texture" );
		setResizable( false );
		setVisible( true );
		setClosable( true );
		setLocation( 20, 50 );
		
		var start = new Vec2d( getLocation().x + getWidth(), getLocation().y + (getHeight() - 10) );
		this.imgNode = new NodePt( start, null, this );
		nodes[0] = imgNode;
		
		var img = texture.getDrawable();
		img = new ResizeImageFilter( 200, 150 ).filter( img );
		
		txtrHolder.setBorder( new EmptyBorder( 15, 15, 15, 15 ));
		txtrHolder.setIcon( new ImageIcon( img.getAsBufferedImage() ));
		
		zoomPan.setLayout( new FlowLayout( FlowLayout.LEFT, 12, 12 ));
		zoomPan.add( zoomTxtHolder );
		zoomPan.add( zoomSlider );
		
		setLayout( new BorderLayout( 5, 15 ) );
		
		add( txtrHolder, BorderLayout.NORTH );
		add( zoomPan, BorderLayout.SOUTH );
		
	}
	
	
	MouseHandler mouse = new MouseHandler();
	private void init() {
		
		zoomTxtHolder = new JLabel( "Zoom: " );
		
		txtrHolder = new JLabel();
		txtrHolder.setPreferredSize( new Dimension( 200, 150 ));
		txtrHolder.addMouseListener( mouse );
		
		zoomSlider = new JSlider();
		zoomSlider.setPreferredSize( new Dimension( 120, 20 ) );
		
		zoomPan = new JPanel();
	}
	
	
	@Override
	public NodePt[] getNodePts() {
		return nodes;
	}

	
	@Override
	public void updateNodePts() {
		this.imgNode.start.x = getLocation().x + getWidth();
		this.imgNode.start.y = getLocation().y + (getHeight() / 2);
	}
	
	
	@Override
	public Dimension getPreferredSize() {
		return new Dimension( 60, 100 );
	}	
	
	
	class MouseHandler extends MouseAdapter {
		
		@Override
		public void mouseClicked( MouseEvent e ) {
			super.mouseClicked( e );
			
			if( e.getSource() == txtrHolder ) {
				new TextureChooser();
			}	
		}	
	}	
}
