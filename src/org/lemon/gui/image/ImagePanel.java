package org.lemon.gui.image;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.image.BufferedImage;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;

import org.lemon.filter.basic.ResizeImage;
import org.lemon.gui.canvas.DrawingCanvasOnImage;
import org.lemon.tools.select.PolygonalSelectTool;
import org.lemon.utils.Utils;

/**
 * 
 * Class description: This class extends JLabel and takes image as param and apply it as its icon.
 * 
 * */
public class ImagePanel extends JPanel {
	
	/**
	 * Serial UID
	 * */
	private static final long serialVersionUID = 1L;
	
	private BufferedImage src, srcCopy;;
	private PanelMode panelMode;
	
	private JLabel imgContainer;
	
	private DrawingCanvasOnImage brushToolListener;
	
	/*current mouse listener in this panel*/
	private MouseAdapter currentMouseListsner;
	
	private final Dimension MAX_IMG_SIZE = new Dimension(500, 500); 
	
	//default constructor
	public ImagePanel() {}
	
	
	//if only image is passed as param then by default set canvas false.
	public ImagePanel(BufferedImage img) {
		this(img, PanelMode.DEFAULT_MODE);
	}
	
	
	public ImagePanel(BufferedImage img, PanelMode panelMode) {
		this.src = img;
		this.panelMode = panelMode;
		
		
		if( src != null ) {
			this.src = img;
			this.srcCopy = Utils.getImageCopy(src);
			
			var resize = new ResizeImage(srcCopy); 
			
			if(src.getHeight() > MAX_IMG_SIZE.height && src.getWidth() > MAX_IMG_SIZE.width) {
				srcCopy = resize.getImageSizeOf(MAX_IMG_SIZE.width, MAX_IMG_SIZE.height);
			}
			else if(src.getHeight() > MAX_IMG_SIZE.height) {
				srcCopy = resize.getImageSizeOf(src.getWidth(), MAX_IMG_SIZE.height);
			}
			else if(src.getWidth() > MAX_IMG_SIZE.width) {
				srcCopy = resize.getImageSizeOf(MAX_IMG_SIZE.width, src.getHeight());
			}
			
			if(resize.isDone()) {
				srcCopy.getGraphics().dispose();
			}
		}
		
		//layout for panel
		//BoxLayout layout = new BoxLayout(getContentPane(), BoxLayout.Y_AXIS);
				
		Border border = BorderFactory.createLineBorder(Color.GRAY, 1);
		setBorder(border);	
		
		setBackground(Color.WHITE);
		
		this.panelMode = PanelMode.SNAP_MODE;
		initCurrentTool(this.panelMode);
	}
	
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		
        Graphics2D g2 = (Graphics2D) g;

        g2.drawImage(src, 0, 0, this);
        g2.dispose();
	}
	
	
	private void initCurrentTool( PanelMode tool ) {
		switch( tool ) {
		
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
	 * @return {@code this} {@code ImagePanel} 's panelMode (int).
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
		this.imgContainer.setIcon( new ImageIcon( imgg ));
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
