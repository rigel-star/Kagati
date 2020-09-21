package org.lemon.gui.image;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.image.BufferedImage;
import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.border.Border;

import org.lemon.filter.ResizeImageFilter;
import org.lemon.gui.canvas.DrawingCanvasOnImage;
import org.lemon.image.LImage;
import org.lemon.lang.LemonObject;
import org.lemon.tools.select.PolygonalSelectTool;
import org.lemon.utils.Utils;

/**
 * 
 * {@code ImagePanel} holds the specified image with specified
 * {@code PanelMode} within it. Mainly, {@code ImagePanel} is used 
 * as a image holder for {@code ImageView}. {@code ImagePanel} 
 * can do many different tasks by applying different types of
 * mouse listeners on it. For e.g. {@code SelectionTool}, 
 * {@code BrushTool} and other tool listeners are the listeners 
 * that {@code ImagePanel} recognises. For specifiying the mouse 
 * listener for {@code ImagePanel}, pass {@code PanelMode} while 
 * creating object or call {@code setPanelMode()}.
 * 
 * */
@LemonObject( type = LemonObject.GUI_CLASS )
public class ImagePanel extends JPanel {
	
	/**
	 * Serial UID
	 * */
	private static final long serialVersionUID = 1L;
	
	private BufferedImage src, srcCopy;;
	private PanelMode panelMode;
	
	private DrawingCanvasOnImage brushToolListener;
	
	/**
	 * current mouse listener in this panel
	 * */
	private MouseAdapter currentMouseListsner;
	
	private final Dimension MAX_IMG_SIZE = new Dimension(500, 500); 
	
	
	/**
	 * 
	 * Constructs {@code ImagePanel} with null image and 
	 * DEFAULT {@code PanelMode}.
	 * 
	 * */
	public ImagePanel() {
		
	}
	
	
	/**
	 * 
	 * Constructs {@code ImagePanel} with image.
	 * @param img 		Image for {@code this ImagePanel}.
	 * 
	 * */
	public ImagePanel( BufferedImage img ) {
		this( img, PanelMode.DEFAULT_MODE );
	}
	
	
	/**
	 * 
	 * Constructs {@code ImagePanel} with image and {@code PanelMode}.
	 * @param img 		Image for {@code this ImagePanel}.
	 * @param mode		{@code PanelMode} for {@code this ImagePanel}.
	 * 
	 * */
	public ImagePanel(BufferedImage img, PanelMode mode) {
		this.src = img;
		this.panelMode = mode;
		
		
		if( src != null ) {
			this.src = img;
			this.srcCopy = Utils.getImageCopy(src);
			
			var resize = new ResizeImageFilter();
			
			if(src.getHeight() > MAX_IMG_SIZE.height && src.getWidth() > MAX_IMG_SIZE.width) {
				
				resize.setNewWidth( MAX_IMG_SIZE.width );
				resize.setNewHeight( MAX_IMG_SIZE.height );
			}
			else if(src.getHeight() > MAX_IMG_SIZE.height) {
				
				resize.setNewWidth( src.getWidth() );
				resize.setNewHeight( MAX_IMG_SIZE.height );
			}
			else if(src.getWidth() > MAX_IMG_SIZE.width) {
				
				resize.setNewWidth( MAX_IMG_SIZE.width );
				resize.setNewHeight( src.getHeight() );
			}
			
			srcCopy = resize.filter( new LImage( srcCopy )).getAsBufferedImage();
			srcCopy.getGraphics().dispose();
		}
				
		Border border = BorderFactory.createLineBorder( Color.GRAY, 1 );
		setBorder( border );	
		
		setBackground( Color.WHITE );
		
		this.panelMode = mode;
		initPanelMode( mode );
	}
	
	
	@Override
	protected void paintComponent( Graphics g ) {
		super.paintComponent(g);
		
        Graphics2D g2 = (Graphics2D) g;

        g2.drawImage( src, 0, 0, this );
        g2.dispose();
	}
	
	
	/**
	 * 
	 * Check and set the specified {@code PanelMode} on this {@code ImagePanel}.
	 * @param mode 		{@code PanelMode}
	 * 
	 * */
	private void initPanelMode( PanelMode mode ) {
		switch( mode ) {
		
		case CANVAS_MODE: {
			currentMouseListsner = new DrawingCanvasOnImage( this, Color.black );
			addMouseMotionListener( currentMouseListsner );
			addMouseListener( currentMouseListsner );
		}
		break;	
		
		case SNAP_MODE: {
			currentMouseListsner = new PolygonalSelectTool( src, this );
			addMouseMotionListener( currentMouseListsner );
			addMouseListener( currentMouseListsner );
		}
		break;
		
		default: {
			break;
		}
		
		}
	}
	
	
	/**
	 * 
	 * Returns currently applied canvas mouse listener if applied else returns {@code null}.
	 * @return {@code DrawingCanvasOnImage} current canvas mouse listener.
	 * 
	 * */
	public DrawingCanvasOnImage getCanvasModeListener() {
		return brushToolListener;
	}
	
	
	/**
	 * 
	 * @return {@code MouseAdapter} currently applied mouse listener.
	 * 
	 * */
	public MouseAdapter getCurrentMouseListener() {
		return currentMouseListsner;
	}
	
	
	/**
	 * 
	 * @param panelMode Mode for this {@code ImagePanel}
	 * 
	 * */
	public void setPanelMode( PanelMode panelMode ) {
		this.panelMode = panelMode;
	}
	
	
	/** 
	 * 
	 * @return Current {@code PanelMode}.
	 * 
	 * */
	public PanelMode getPanelMode() {
		return panelMode;
	}
	
	
	/**
	 * 
	 * @return	Current image which is applied to {@code this} {@code ImagePanel}.
	 * 
	 * */
	public BufferedImage getImage() {
		return src;
	}
	
	
	/**
	 * 
	 * Get non-edited or non-transformed original image.
	 * @return img original image
	 * 
	 * */
	public BufferedImage getActualImage() {
		return src;
	}
	
	
	/**
	 * 
	 * Get edited or transformed image.
	 * @return img copied image from original
	 * 
	 * */
	public BufferedImage getCurrentImage() {
		return srcCopy;
	}
	
	
	@Override
	public Dimension getPreferredSize() {
		return new Dimension( src.getWidth(), src.getHeight() );
	}
	
	
	/**
	 * 
	 * Sets the new image to {@code this ImagePanel}.
	 * @param img	{@code BufferedImage} object.
	 * 
	 * */
	public void setImage( BufferedImage imgg ) {
		this.src = imgg;
		//this.imgContainer.setIcon( new ImageIcon( imgg ));
	}
	
	
	public boolean hasAreaSelected() {
		boolean has = false;
		if( currentMouseListsner instanceof PolygonalSelectTool ) {
			has = ( (PolygonalSelectTool) currentMouseListsner ).isAreaSelected();
		}
		return has;
	}
	
	
	@Override
	public String toString() {
		return "Image info: H = " + this.src.getHeight() + " W = " + this.src.getWidth();
	}
}
