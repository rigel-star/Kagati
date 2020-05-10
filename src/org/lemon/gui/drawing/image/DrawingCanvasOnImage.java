package org.lemon.gui.drawing.image;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

import org.lemon.gui.image.ImagePanel;
import org.lemon.tools.brush.LemonBrushTool;
import org.lemon.tools.brush.NormalBrushTool;

/**
 * Class description: This class takes an image as param and applies canvas in it.<p>
 * This class is mainly used for drawing in image.<p>
 * Canvas is java class which is mainly used for drawing paint purpose.<p>
 * */
public class DrawingCanvasOnImage implements MouseMotionListener{
	
	//global
	private Graphics g2d;
	private ImagePanel ip;
	
	private LemonBrushTool brushTool = new NormalBrushTool();
	
	
	public DrawingCanvasOnImage(ImagePanel panel) {
		//assigning globals
		this.ip = panel;
		this.g2d = ip.getImage().createGraphics();
	}
	
	
	
	public void setBrush(LemonBrushTool brush) {
		this.brushTool = brush;
	}
	
	
	public LemonBrushTool getBrush() {
		return brushTool;
	}
	

	@Override
	public void mouseDragged(MouseEvent e) {
		
		var x = e.getX();
		var y = e.getY();
		
		var c = new Color(255, 0, 0);
		this.g2d.setColor(c);
		
		//filling oval of size 10X10 wherever the mouse goes
		this.g2d.fillOval(x, y, this.brushTool.getStrokeSize(), this.brushTool.getStrokeSize());
		//this.g2d.dispose();
		this.ip.repaint();
		
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		var cursor = new Cursor(Cursor.CROSSHAIR_CURSOR);
		this.ip.setCursor(cursor);
	}
	
}