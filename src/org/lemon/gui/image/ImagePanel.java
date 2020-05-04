package org.lemon.gui.image;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.border.Border;

import org.lemon.gui.drawing.DrawingCanvasOnImage;
import org.lemon.tools.select.PolygonalSelectTool;

/**
 * Class description: This class extends JLabel and takes image as param and apply it as its icon.
 * */
public class ImagePanel extends JLabel implements MouseMotionListener {
	
	private static final long serialVersionUID = 1L;
	
	//flags
	//default state
	public static final int 	DEFAULT_MODE = 0;
		
	//for canvas on image
	public static final int 	CANVAS_MODE = 1;
	
	//for snapping tool on image
	public static final int 	SNAP_MODE = 2;
	
	//for drawing shapes in image
	public static final int 	SHAPES_MODE = 3;
	
	public static final int 	defaultMode = DEFAULT_MODE;
	public static final int 	canvasMode = CANVAS_MODE;
	public static final int 	snapMode = SNAP_MODE;
	public static final int 	shapesMode = SHAPES_MODE;
	
	private BufferedImage img;
	private int panelMode;
	
	
	/*Different types of mouseListeners*/
	private DrawingCanvasOnImage dcoi;
	private PolygonalSelectTool snap;
	
	//default constructor
	public ImagePanel() {}
	
	
	
	/**
	 * Select panel mode for {@code ImagePanel}
	 * */
	public class PanelMode {
		//flags
		//default state
		public static final int 	DEFAULT_MODE = 0;
			
		//for canvas on image
		public static final int 	CANVAS_MODE = 1;
		
		//for snapping tool on image
		public static final int 	SNAP_MODE = 2;
		
		//for drawing shapes in image
		public static final int 	SHAPES_MODE = 3;
		
		public static final int 	defaultMode = DEFAULT_MODE;
		public static final int 	canvasMode = CANVAS_MODE;
		public static final int 	snapMode = SNAP_MODE;
		public static final int 	shapesMode = SHAPES_MODE;
	}
	
	
	
	//if only image is passed as param then by default set canvas false.
	public ImagePanel(BufferedImage img) {
		this(img, PanelMode.DEFAULT_MODE);
	}
	
	public ImagePanel(BufferedImage img, int panelMode) {
		this.img = img;
		this.panelMode = panelMode;
		
		//layout for panel
		//BoxLayout layout = new BoxLayout(getContentPane(), BoxLayout.Y_AXIS);
				
		Border border = BorderFactory.createLineBorder(Color.GRAY, 1);
		setBorder(border);
		setSize(img.getWidth(), img.getHeight());
		
		setIcon(new ImageIcon(img));
		setBackground(Color.WHITE);
		
		//panelMode switching
		switch(this.panelMode) {
		
		case CANVAS_MODE: {
			//canvas mode on image i.e drawing on image
			dcoi = new DrawingCanvasOnImage(this);
			addMouseMotionListener(dcoi);
		}
		break;
		
		case SNAP_MODE: {
			//snapping mode in image i.e grab area of image
			snap = new PolygonalSelectTool(img, this);
			snap.apply();
		}
		break;
		
		case DEFAULT_MODE: {
			//default mode. do nothing
			addMouseMotionListener(this);
		}
		break;
		
		}
	}
	
	
	/**
	 * Returns currently applied canvas mouse listener if applied else returns {@code null}.
	 * @return {@code DrawingCanvasOnImage} current canvas mouse listener.
	 * */
	public DrawingCanvasOnImage getCanvasModeListener() {
		return this.dcoi;
	}
	
	
	/**
	 * @param panelMode Mode for this ImagePanel.<p>
	 * eg. PanelMode.SNAP_MODE, PanelMode.CANVAS_MODE etc.
	 * */
	public void setPanelMode(int panelMode) {
		this.panelMode = panelMode;
	}
	
	
	/** 
	 * @return {@code this} {@code ImagePanel} 's panelMode (int).
	 * */
	public int getPanelMode() {
		return this.panelMode;
	}
	
	
	/**
	 * @return	Current image which is applied to {@code this} {@code ImagePanel}.
	 * */
	public BufferedImage getImage() {
		return this.img;
	}
	
	
	/**
	 * Sets the new image to {@code this ImagePanel}.
	 * @param img	{@code BufferedImage} object.
	 * */
	public void setImage(BufferedImage img) {
		this.img = img;
	}
	
	
	@Override
	public String toString() {
		return "Image info: H=" + this.img.getHeight() + " W=" + this.img.getWidth();
	}
	
	
	
	@Override
	public void mouseDragged(MouseEvent e) {}

	@Override
	public void mouseMoved(MouseEvent e) {
		var cursor = new Cursor(Cursor.CROSSHAIR_CURSOR);
		setCursor(cursor);
	}
}
