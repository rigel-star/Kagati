package org.lemon.gui.drawing.image;

import java.awt.Cursor;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

import org.lemon.gui.image.ImagePanel;
import org.lemon.tools.brush.BrushTool;
import org.lemon.tools.brush.NormalBrushTool;

/**
 * Class description: This class takes an image as param and applies canvas in it.<p>
 * This class is mainly used for drawing in image.<p>
 * Canvas is java class which is mainly used for drawing paint purpose.<p>
 * */
public class DrawingCanvasOnImage implements MouseMotionListener{
	
	//global
	private Graphics2D g2d;
	private ImagePanel ip;
	
	private BrushTool brushTool;
	
	
	public DrawingCanvasOnImage(ImagePanel panel) {
		//assigning globals
		this.ip = panel;
		this.g2d = ip.getImage().createGraphics();
		brushTool = new NormalBrushTool(g2d);
	}
	
	
	
	public void setBrush(BrushTool brush) {
		this.brushTool = brush;
	}
	
	
	public BrushTool getBrush() {
		return brushTool;
	}
	

	@Override
	public void mouseDragged(MouseEvent e) {
		
		var x = e.getX();
		var y = e.getY();
		
		brushTool.setStrokeSize(10);
		
		//filling oval of size 10X10 wherever the mouse goes
		brushTool.draw(x, y);
		//this.g2d.dispose();
		this.ip.repaint();
		
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		var cursor = new Cursor(Cursor.CROSSHAIR_CURSOR);
		this.ip.setCursor(cursor);
	}
	
}
