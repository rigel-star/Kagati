package org.lemon.gui.node;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Stack;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.lemon.composite.AddComposite;
import org.lemon.filter.CompositeFilter;
import org.lemon.filter.ResizeImageFilter;
import org.lemon.graphics.Texture;
import org.lemon.graphics.texture.WoodTexture;
import org.lemon.gui.ImageView;
import org.lemon.gui.LayerContainer;
import org.lemon.gui.Node;
import org.lemon.gui.NodePt;
import org.lemon.gui.NodePt.NodePtType;
import org.lemon.image.LImage;
import org.lemon.lang.NonNull;
import org.lemon.gui.TextureChooser;
import org.lemon.math.Vec2;
import org.lemon.utils.Utils;

public class TextureNodeComponent extends NodeComponent implements SenderNode {
	
	/**
	 * Serial UID
	 * */
	private static final long serialVersionUID = 1L;
	
	private static NodePt imgNode = null;
	
	private final int nodeCount = 1;
	private NodePt[] senderNodePts = new NodePt[nodeCount];
	
	private static JLabel txtrHolder = null;
	
	private static JSlider zoomSlider = null;
	private static JLabel zoomTxtHolder = null;
	
	private static JSlider alphaSlider = null;
	private static JLabel alphaTxtHolder = null;
	
	private static JLabel blendModeTxt = null;
	private static JComboBox<String> blendModeCombo = null;
	
	private Texture texture = new WoodTexture();
	
	private JPanel sliderContainer = null;
	
	public TextureNodeComponent( @NonNull LayerContainer lycont ) {
		super( lycont );
		this.init();
		
		setSize( 200, 300 );
		setTitle( "Texture" );
		setResizable( false );
		setVisible( true );
		setClosable( true );
		setLocation( 20, 50 );
		
		var start = new Vec2( getLocation().x + getWidth(), getLocation().y + (getHeight() - 10) );
		imgNode = new NodePt( start, null, this, NodePtType.SENDER );
		senderNodePts[0] = imgNode;
		
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
	public NodePt[] getSenderNodePts() {
		return senderNodePts;
	}

	@Override
	public void updateSenderNodePts() {
		imgNode.start.x = getLocation().x + getWidth();
		imgNode.start.y = getLocation().y + (getHeight() - 10);
	}
	
	@Override
	public NodeType getNodeType() {
		return NodeType.SENDER;
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
	
	private float alpha = .5f;
	/**
	 * Change handler for texture node component.
	 * */
	class ChangeHandler implements ChangeListener {

		@Override
		public void stateChanged( ChangeEvent e ) {
			
			if ( e.getSource() == alphaSlider ) {
				float value = alphaSlider.getValue();
				alpha = value / 10.0f;
			}
		}
	}
	
	/**
	 * Action Listener for texture node component.
	 * */
	class ActionHandler implements ActionListener {

		@Override
		public void actionPerformed( ActionEvent e ) {
			
			if ( e.getSource() == blendModeCombo ) {
				NodePt[] ndpts = getSenderNodePts();
				
				for ( NodePt pt: ndpts ) {
					Stack<Node> nds = pt.getConnections();
					for ( Node nd: nds ) {
						
						if ( nd instanceof ImageView ) {
							
							final CompositeFilter cf;
							final ImageView view;
							final LImage src;
							if ( blendModeCombo.getSelectedItem().equals( "Add" )) {
								cf = new CompositeFilter( new AddComposite( alpha ));
								view = ((ImageView) nd);
								src = new LImage( view.getCurrentImage() );
								
								if ( cf != null && view != null && src != null ) {
									
									new Thread( new Runnable() {
										
										@Override
										public void run() {
											LImage out = applyTexture( src, texture, cf );
											view.getImagePanel().setImage( out.getAsBufferedImage() );
											view.getImagePanel().repaint();
											view.revalidate();
										}
									}).start();
									
								}
								else {
									JOptionPane.showConfirmDialog( view, "Image might be missing!" );
								}
							}
						}
					}
				}
			}
		}
	}
	
	/**
	 * Apply {@link Texture} to image.
	 * 
	 * @param src 		Source image.
	 * @param txtr		{@link Texture} to apply.
	 * @param cf 		Blend mode.
	 * @return 			Textured image.
	 * */
	private LImage applyTexture( LImage src, Texture texture, CompositeFilter cf ) {
		
		final int width = src.width;
		final int height = src.height;
		final LImage txtr = new ResizeImageFilter( width, height )
										.filter( texture.getDrawable() );
		LImage out = cf.compose( src, txtr );
		
		return out;
	}

	@Override
	public ImageIcon getNodeIcon() {
		BufferedImage bicon = null;
		try {
			bicon = ImageIO.read( new FileInputStream( new File( "icons/layer/texture.png" )));
		} catch ( IOException ex ) {
			ex.printStackTrace();
		}
		ImageIcon icon = new ImageIcon( bicon );
		return icon;
	}
}
