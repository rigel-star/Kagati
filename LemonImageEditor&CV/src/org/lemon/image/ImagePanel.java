package org.lemon.image;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.border.Border;

import org.lemon.drawing.DrawingCanvasOnImage;
import org.lemon.tools.LazySnappingTool;

/**
 * Class description: This class extends JLabel and takes image as param and apply it as its icon.
 * */
public class ImagePanel extends JLabel implements MouseMotionListener {
	
	private static final long serialVersionUID = 1L;
	
	public BufferedImage img;
	private int panelState;
	
	//flags
	//for canvas on image
	public static final int STATE_CANVAS = 0;
	
	//for snapping tool on image
	public static final int STATE_SNAP = 1;
	
	//default state
	public static final int STATE_DEFAULT = 3;
	
	public ImagePanel() {}
	
	//if only image is passed as param then by default set canvas false.
	public ImagePanel(BufferedImage img) {
		this(img, false);
	}
	
	public ImagePanel(BufferedImage img, boolean canvas) {
		this(img, canvas, STATE_DEFAULT);
	}
	
	public ImagePanel(BufferedImage img, boolean canvas, int panelState) {
		this.img = img;
		this.panelState = panelState;
		
		//layout for panel
		//BoxLayout layout = new BoxLayout(getContentPane(), BoxLayout.Y_AXIS);
				
		Border border = BorderFactory.createLineBorder(Color.GRAY, 1);
		setBorder(border);
		setSize(400, 400);
		
		setIcon(new ImageIcon(img));
		setBackground(Color.WHITE);
		
		switch(this.panelState) {
		
		case STATE_CANVAS:
			DrawingCanvasOnImage dcoi = new DrawingCanvasOnImage(this);
			addMouseMotionListener(dcoi);
			break;
		case STATE_SNAP:
			new LazySnappingTool(img, this);
			break;
		case STATE_DEFAULT:
			addMouseMotionListener(this);
			break;
		}
	}
	
	public void setPanelState(int panelState) {
		this.panelState = panelState;
	}
	
	@Override
	public String toString() {
		return "Image info: H=" + this.img.getHeight() + " W=" + this.img.getWidth();
	}
	
	//returns the current image which is applied to image panel currently
	public BufferedImage getCurrentImg() {
		return this.img;
	}

	@Override
	public void mouseDragged(MouseEvent e) {}

	@Override
	public void mouseMoved(MouseEvent e) {
		Cursor cursor = new Cursor(Cursor.CROSSHAIR_CURSOR);
		setCursor(cursor);
	}
}
