package org.lemon.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.border.Border;

import org.lemon.filter.ResizeImageFilter;
import org.lemon.gui.NodePt.NodePtType;
import org.lemon.gui.image.ImagePanel;
import org.lemon.gui.image.ImageViewMenu;
import org.lemon.gui.node.NodeComponent;
import org.lemon.gui.node.ReceiverNode;
import org.lemon.gui.node.SenderNode;
import org.lemon.image.LImage;
import org.lemon.lang.NonNull;
import org.lemon.math.Vec2;
import org.lemon.utils.Utils;

import org.rampcv.utils.Tools;

/**
 * 
 * ImageView is for holding image opened by user in application.
 * Parent of {@code ImageView} is {@code Workspace}. Calling {@code getParent} 
 * returns the {@code Workspace}.
 * 
 * */
public class ImageView extends NodeComponent implements Cloneable, ReceiverNode, ViewHolder {
	
	/**
	 * Serial UID
	 * */
	private static final long serialVersionUID = 1L;
	
	private ImageView 				connection;
	private ImageView 				self = this;
	private ImagePanel 				imgPan = null;
	
	private BufferedImage 			src, srcCopy;
	
	private String 					title = null;
	private boolean 				close;
	
	private ImageViewMenu			menu;
	
	/*filterly controllable node*/
	private NodePt controllableNode = null;
	private List<SenderNode> controllers = new ArrayList<>();
	
	/*Connections for this imageview*/
	private Map<String, ImageView> 	connections = new HashMap<String, ImageView>();
	private List<String> 			conOptionsTitles = new ArrayList<String>();
	private List<ImageView> 		conOptionsViews = new ArrayList<ImageView>();
	
	private MouseEventsHandler 		meh;
	
	private final Dimension MAX_IMG_SIZE = new Dimension(500, 500);
	
	private LayerContainer lycont = null;
	
	/**
	 * 
	 * To check if the {@code ImageView} is showing original image or
	 * image is resized or changed. If the image size is over than 500X500 then the image
	 * will be resized to 500X500 otherwise {@code ImageView} will show actual image.
	 * 
	 * */
	public boolean imageIsActual = true;
	
	public ImageView( @NonNull final LayerContainer lycont ) {
		this( LImage.createImage( 100, 100, BufferedImage.TYPE_INT_ARGB ).getAsBufferedImage(), lycont );
	}
	
	/*Note image can't be null*/
	public ImageView( BufferedImage img, @NonNull final LayerContainer lycont ) {
		this( img, null, false, ImagePanel.PanelMode.DEFAULT_MODE, lycont );
	}
	
	public ImageView( BufferedImage img, String title, @NonNull final LayerContainer lycont ) {
		this( img, title, false, ImagePanel.PanelMode.DEFAULT_MODE, lycont );
	}
	
	public ImageView( BufferedImage img, boolean closeable, @NonNull final LayerContainer lycont ) {
		this( img, null, closeable, ImagePanel.PanelMode.DEFAULT_MODE, lycont );
	}
	
	public ImageView( BufferedImage img, ImagePanel.PanelMode panelMode, @NonNull final LayerContainer lycont ) {
		this( img, null, false, panelMode, lycont );
	}
	
	public ImageView( BufferedImage img, String title, ImagePanel.PanelMode panelMode, @NonNull final LayerContainer lycont ) {
		this( img, title, false, panelMode, lycont );
	}
	
	public ImageView( BufferedImage img, boolean closeable, ImagePanel.PanelMode panelMode, @NonNull final LayerContainer lycont ) {
		this( img, null, closeable, panelMode, lycont );
	}
	
	public ImageView( BufferedImage img, String title, boolean closeable, @NonNull final LayerContainer lycont ) {
		this(img, title, closeable, ImagePanel.PanelMode.DEFAULT_MODE, lycont );
	}
	
	public ImageView( BufferedImage img, String title, boolean closeable, ImagePanel.PanelMode panelMode, @NonNull final LayerContainer lycont )  {
        super( lycont );
        this.lycont = lycont;
        
		if( img == null )
			throw new NullPointerException( "Image can't be null." );
		else {
			this.src = img;
			this.srcCopy = Utils.getImageCopy( src );
			
			if( src.getHeight() > MAX_IMG_SIZE.height && src.getWidth() > MAX_IMG_SIZE.width ) {
				srcCopy = new ResizeImageFilter( MAX_IMG_SIZE.width, MAX_IMG_SIZE.height )
												.filter( new LImage( srcCopy ))
												.getAsBufferedImage();
			}
			else if( src.getHeight() > MAX_IMG_SIZE.height ) {
				srcCopy = new ResizeImageFilter( src.getWidth(), MAX_IMG_SIZE.height )
												.filter( new LImage( srcCopy ))
												.getAsBufferedImage();
			}
			else if( src.getWidth() > MAX_IMG_SIZE.width ) {
				srcCopy = new ResizeImageFilter( MAX_IMG_SIZE.width, src.getHeight() )
												.filter( new LImage( srcCopy ))
												.getAsBufferedImage();
			}
			
			srcCopy.getGraphics().dispose();
		}
		
		this.imgPan = new ImagePanel( srcCopy, panelMode );
		this.title = title;
		this.close = closeable;
		
		final Border layoutBorder = BorderFactory.createLineBorder( Color.BLACK, 2 );
		
		setLayout( new BorderLayout() );
		setBorder( layoutBorder );
		setTitle( this.title );
		setClosable( closeable );
		setVisible( true );
		setSize( new Dimension( srcCopy.getWidth(), srcCopy.getHeight() ));
		setLayout( new BorderLayout() );
		setMaximizable( true );
		setIconifiable( true );
		add( imgPan, BorderLayout.CENTER );
		
		/* 
		 * Revalidatng simply re-adds the listeners to this ImageView.
		 * I'm calling revalidate here even im not revalidating is cause 
		 * even for the first time i have to first of all add listener
		 * and if all of the listeners removed from this ImageView, on
		 * calling this method, again default mouse listeners will be applied.
		 * BADDDDDDDDD ENGLISSSSSSSSSSHHHHHHHHHH
		 * 
		 * */
		revalidateListeners();
		
		var pt = new Point( this.getLocation().x, this.getLocation().y + 40 );
		controllableNode = new NodePt( new Vec2(pt), null, this, NodePtType.RECEIVER );
	}
	
	@Override
	public Object clone() throws CloneNotSupportedException {
		var newImg = Tools.copyImage( getActualImage() );
		ImageView duplicate = new ImageView( newImg, getTitle(), getCloseableState(), 
												getImagePanel().getPanelMode(), lycont );
		return duplicate;
	}
	
	/**
	 * 
	 * Revalidate every listener applied to this ImageView
	 * 
	 * */
	public void revalidateListeners() {
		meh = new MouseEventsHandler();
		getImagePanel().addMouseListener( meh );
		getImagePanel().addMouseMotionListener( meh );
	}
	
	/**
	 * Connect two ImageViews.
	 * 
	 * @param {@code ImageView} to connect with
	 * @return {@code false} if connectTo is null else {@code true}.
	 * */
	public boolean setConnection( ImageView connectTo ) {
		/*set this imageviews connection*/
		this.connection = connectTo;
		return true;
	}
	
	/**
	 * Get connected ImageView object.
	 * 
	 * @return Connection
	 * */
	public ImageView getConnection() {
		return this.connection;
	}
	
	/**
	 * Set connection options for this ImageView.
	 * 
	 * @param {{@code List<String>}  of options
	 * */
	public void setConOptions( List<String> optionsTitle, List<ImageView> views ) {
		connections.clear();
		this.conOptionsTitles = optionsTitle;
		this.conOptionsViews = views;
		
		for( int i=0; i<views.size(); i++ ) {
			connections.put( optionsTitle.get(i), views.get(i) );
		}
	}
	
	/**
	 * Get all the available connections titles for this ImageView.
	 * 
	 * @return list of connections titles
	 * */
	public List<String> getConOptionsTitles() {
		return this.conOptionsTitles;
	}
	
	/**
	 * Get all the available connections of this {@code ImageView}.
	 * 
	 * @return list of available imageviews to connect with.
	 * */
	public List<ImageView> getConOptionsViews(){
		return this.conOptionsViews;
	}
	
	/**
	 * Sets the custom {@code ImagePanel} object for {@code this} {@code ImageView}.<p>
	 *
	 * @param imgPan 	{@code ImagePanel} object.
	 * */
	public void setImagePanel( ImagePanel imgPan ) {
		this.imgPan.setImage( imgPan.getImage() );
		this.imgPan.setPanelMode( imgPan.getPanelMode() );
		revalidate();
	}
	
	/**
	 * Get {@code ImagePanel} attached with this {@code ImageView} in a 
	 * manner that the modification of the {@code LImage} attached with
	 * {@code ImagePanel} brings changes in {@code this ImageView}.
	 * 
	 * @return 			{@code ImagePanel} attached with this {@code ImageView}
	 * */
	public ImagePanel getImagePanel() {
		return imgPan;
	}
	
	/**
	 * Get non-edited or non-transformed original image.
	 * 
	 * @return img original image
	 * */
	public BufferedImage getActualImage() {
		return src;
	}
	
	/**
	 * Get edited or transformed image.
	 * 
	 * @return img copied image from original
	 * */
	public BufferedImage getCurrentImage() {
		return srcCopy;
	}
	
	/**
	 * @return		{@code True} if {@code this ImageView} is closeable else returns {@code false}
	 * */
	public boolean getCloseableState() {
		return this.close;
	}
	
	@Override
	public String getTitle() {
		return this.title;
	}
	
	@Override
	public void setTitle( String title ) {
		this.title = title;
	}
	
	
	/**
	 * @return Properties of this {@code ImageView}.
	 * */
	public ImageViewProperties getProperties() {
		return new ImageViewProperties( this );
	}
	
	/**
	 * Mouse events handler for ImageView.
	 * */
	private class MouseEventsHandler extends MouseAdapter {
		
		@Override
		public void mouseClicked( MouseEvent e ) {
			super.mouseClicked( e );
			
			/*BUTTON3 = Right Mouse Button*/
			if( e.getButton() == MouseEvent.BUTTON3 ) {
				
				//show available options
				menu = new ImageViewMenu( self );
				menu.show( self, e.getX(), e.getY() );
			}
		}	
	}

	@Override
	public void addSender( SenderNode controller ) {
		controllers.add( controller );
	}
	
	@Override
	public List<SenderNode> getSenders() {
		return controllers;
	}
	
	@Override
	public void updateReceiverNodePt() {
		controllableNode.start.x = this.getLocation().x;
		controllableNode.start.y = this.getLocation().y + 30;
	}
	
	@Override
	public NodePt getReceiverNodePt() {
		return controllableNode;
	}
	
	@Override
	public NodeType getNodeType() {
		return NodeType.RECEIVER;
	}

	@Override
	public ImageIcon getNodeIcon() {
		return new ImageIcon( src );
	}
}