package org.lemon.gui.image;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.geom.AffineTransform;
import java.awt.geom.NoninvertibleTransformException;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.border.Border;

import org.lemon.gui.drawing.image.DrawingCanvasOnImage;
import org.lemon.tools.select.LassoSelectionTool;
import org.lemon.tools.select.PolygonalSelectTool;
import org.lemon.utils.AppGlobalProperties;

/**
 * Class description: This class extends JLabel and takes image as param and apply it as its icon.
 * */
public class ImagePanel extends JPanel {
	
	private static final long serialVersionUID = 1L;
	
	
	private BufferedImage img;
	private int panelMode;
	
	private ImagePanel self;
	
	/*Different types of mouseListeners for different tools*/
	private DrawingCanvasOnImage brushToolListener;
	private PolygonalSelectTool polySelectionToolListener;
	private LassoSelectionTool lassoSelectionToolListener;
	private ImageZoomAndPanListener zoomAndPanListener;
	
	private ImageKeyEventListener keyEvents;
	
	
	private boolean init = true;
    private int zoomLevel = 0;
    private int minZoomLevel = -20;
    private int maxZoomLevel = 10;
    private double zoomMultiplicationFactor = 1.2;

    private Point dragStartScreen;
    private Point dragEndScreen;
    
    private AffineTransform coordTransform = new AffineTransform();
	
	
	
	/*current mouse listener in this panel*/
	private MouseAdapter currentMouseListsner;
	
	
	private AppGlobalProperties agp;
	
	
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
		this(img, null, PanelMode.DEFAULT_MODE);
	}
	
	
	public ImagePanel(BufferedImage img, AppGlobalProperties agp, int panelMode) {
		this.img = img;
		this.panelMode = panelMode;
		this.agp = agp;
		
		//layout for panel
		//BoxLayout layout = new BoxLayout(getContentPane(), BoxLayout.Y_AXIS);
				
		Border border = BorderFactory.createLineBorder(Color.GRAY, 1);
		setBorder(border);
		setSize(img.getWidth(), img.getHeight());
		
		setBackground(Color.WHITE);
		setFocusable(true);
		
		initCurrentTool(this.panelMode);
		
		self = this;
		keyEvents = new ImageKeyEventListener();
		addKeyListener(keyEvents);
		
	}
	
	
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		
        Graphics2D g2 = (Graphics2D) g;
        int x = (int) (this.getWidth() - (img.getWidth() * .2)) / 2;
        int y = (int) (this.getHeight() - (img.getHeight() * .2)) / 2;
        
        x = 0;
        y = 0;

        AffineTransform at = new AffineTransform();
        at.translate(x, y);
        at.scale(1, 1);
        
        if (init) {
            g2.setTransform(at);
            init = false;
            coordTransform = g2.getTransform();
        } else {
            g2.setTransform(coordTransform);
        }

        g2.drawImage(img, 0, 0, this);

        g2.dispose();
	}
	
	
	
	private void initCurrentTool(int tool) {
		//panelMode switching
		switch(tool) {
		
		case PanelMode.CANVAS_MODE: {
			//canvas mode on image i.e drawing on image
			brushToolListener = new DrawingCanvasOnImage(this, agp.getGLobalColor());
			addMouseMotionListener(brushToolListener);
			addMouseListener(brushToolListener);
			
			currentMouseListsner = brushToolListener;
		}
		break;
		
		case PanelMode.SNAP_MODE: {
			//snapping mode in image i.e grab area of image
			//polySelectionToolListener = new PolygonalSelectTool(img, this);
			//lassoSelectionToolListener = new LassoSelectionTool(img, this);
			addMouseListener(lassoSelectionToolListener);
			addMouseMotionListener(lassoSelectionToolListener);
			currentMouseListsner = lassoSelectionToolListener;
		}
		break;
		
		
		
		}
	}
	
	
	
	/*pan image on mouse drag*/
	private void pan(MouseEvent e) {
        try {
            dragEndScreen = e.getPoint();
            Point2D.Float dragStart = transformPoint(dragStartScreen);
            Point2D.Float dragEnd = transformPoint(dragEndScreen);
            double dx = dragEnd.getX() - dragStart.getX();
            double dy = dragEnd.getY() - dragStart.getY();
            coordTransform.translate(dx, dy);
            dragStartScreen = dragEndScreen;
            dragEndScreen = null;
            repaint();
        } catch (NoninvertibleTransformException ex) {
            ex.printStackTrace();
        }
    }
	
	
	/*
	 * Zoom image on mouse wheel change
	 * */
	private void zoom(MouseWheelEvent e) {
		try {
            int wheelRotation = e.getWheelRotation();
            Point p = e.getPoint();
            if (wheelRotation > 0) {
                if (zoomLevel < maxZoomLevel) {
                    zoomLevel++;
                    Point2D p1 = transformPoint(p);
                    coordTransform.scale(1 / zoomMultiplicationFactor, 1 / zoomMultiplicationFactor);
                    Point2D p2 = transformPoint(p);
                    coordTransform.translate(p2.getX() - p1.getX(), p2.getY() - p1.getY());
                    repaint();
                }
            } else {
                if (zoomLevel > minZoomLevel) {
                    zoomLevel--;
                    Point2D p1 = transformPoint(p);
                    coordTransform.scale(zoomMultiplicationFactor, zoomMultiplicationFactor);
                    Point2D p2 = transformPoint(p);
                    coordTransform.translate(p2.getX() - p1.getX(), p2.getY() - p1.getY());
                    repaint();
                }
            }
        } catch (NoninvertibleTransformException ex) {
            ex.printStackTrace();
        }
	}
	
	
	private Point2D.Float transformPoint(Point p1) throws NoninvertibleTransformException {
        AffineTransform inverse = coordTransform.createInverse();
        Point2D.Float p2 = new Point2D.Float();
        inverse.transform(p1, p2);
        return p2;
    }
	
	
	
	
	
	private class ImageKeyEventListener extends KeyAdapter {
		
		MouseAdapter current = getCurrentMouseListener();
		
		@Override
		public void keyPressed(KeyEvent e) {
			super.keyPressed(e);
			self.removeMouseListener(current);
			self.removeMouseMotionListener(current);
			self.removeMouseWheelListener(current);
			self.addMouseListener(zoomAndPanListener);
			self.addMouseMotionListener(zoomAndPanListener);
			self.addMouseWheelListener(zoomAndPanListener);
			self.revalidate();
		}
		
		
		@Override
		public void keyReleased(KeyEvent e) {
			super.keyReleased(e);
			self.removeMouseListener(zoomAndPanListener);
			self.removeMouseMotionListener(zoomAndPanListener);
			self.removeMouseWheelListener(zoomAndPanListener);
			self.addMouseListener(current);
			self.addMouseMotionListener(current);
			self.addMouseWheelListener(current);
			self.revalidate();
		}
	}
	
	
	
	/*
	 * Class which exetnds Abstract class MouseAdapter and overrides some functions.
	 * This class is used for zooming and panning the image on the screen.
	 * */
	private class ImageZoomAndPanListener extends MouseAdapter {
		
		
		/*init starrting and ending point of affine transformation*/
		@Override
		public void mousePressed(MouseEvent e) {
			super.mousePressed(e);
			dragStartScreen = e.getPoint();
            dragEndScreen = null;
		}
		
		
		/*pan the image*/
		@Override
		public void mouseDragged(MouseEvent e) {
			super.mouseDragged(e);
			pan(e);
		}
		
		
		/*zoom the image*/
		@Override
        public void mouseWheelMoved(MouseWheelEvent e) {
            if (e.isControlDown()) {
                zoom(e);
            }
        }
	}
	
	
	
	
	/**
	 * Returns currently applied canvas mouse listener if applied else returns {@code null}.
	 * @return {@code DrawingCanvasOnImage} current canvas mouse listener.
	 * */
	public DrawingCanvasOnImage getCanvasModeListener() {
		return this.brushToolListener;
	}
	
	
	
	/**
	 * @return {@code MouseAdapter} currently applied mouse listener.
	 * */
	public MouseAdapter getCurrentMouseListener() {
		return currentMouseListsner;
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
		return "Image info: H = " + this.img.getHeight() + " W = " + this.img.getWidth();
	}
	

}
