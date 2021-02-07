package org.lemon.tools.select;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Line2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JComponent;

import org.lemon.tools.SelectionTool;

public class LassoSelectionTool extends MouseAdapter implements SelectionTool {
	
	/*image container*/
	private final JComponent container;
	/*context to draw selection*/
	private Graphics2D context;
	
	/*cursor image*/
	private BufferedImage curImg = null;
	
	/*start and end point of line*/
	private int newX, newY, oldX, oldY;
	
	public LassoSelectionTool(BufferedImage src, final JComponent container) {
		
		this.context = src.createGraphics();
		initContext();
		
		this.container = container;
		
		try {
			this.curImg = ImageIO.read(new File("icons/tools/lasso2.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * Init the drawing context of this lasso tool with custom strokes, transparency, custom cursor etc..
	 * */
	private void initContext() {
		
		var dash = new float[] { 10.0f };
	    var stroke = new BasicStroke(3.0f, BasicStroke.CAP_BUTT,
	        BasicStroke.JOIN_MITER, 10.0f, dash, 0.0f);

		context.setStroke(stroke);
		context.setPaint(new Color(128, 128, 128, 128));
		context.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));	//transparency
		//context.setPaint(Color.black);
	}	
	
	
	@Override
	public void mousePressed(MouseEvent e) {
		super.mousePressed(e);
		
		newX = e.getX();
		newY = e.getY();
		oldX = newX;
		oldY = newY;
	}
	
	
	@Override
	public void mouseDragged(MouseEvent e) {
		super.mouseDragged(e);
		
		newX = e.getX();
		newY = e.getY();
		
		context.draw(new Line2D.Double(newX + 15, newY + 20, oldX + 15, oldY + 20));
		container.repaint();
		
		oldX = newX;
		oldY = newY;
	}
		
	
	@Override
	public void mouseMoved(MouseEvent e) {
		super.mouseMoved(e);
		
		var point  = new Point(0, 0);
		var cursor = Toolkit
							.getDefaultToolkit()
							.createCustomCursor(curImg,
							point, "lasso_cursor");

		
		container.setCursor(cursor);
	}
	
	
	@Override
	public SelectedArea getSelectedArea() {
		return null;
	}


	@Override
	public void clearSelection() {
	}


	@Override
	public BufferedImage getSelectedAreaImage() {
		return null;
	}


	@Override
	public boolean isAreaSelected() {
		return false;
	}
	
}
