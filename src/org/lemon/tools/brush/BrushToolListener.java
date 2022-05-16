package org.lemon.tools.brush;

import java.awt.Cursor;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JComponent;

import org.lemon.tools.BrushTool;

public class BrushToolListener extends MouseAdapter {
	
	private int newX, newY, oldX, oldY;
	private BrushTool btool = null;
	private JComponent component;
	
	public BrushToolListener(JComponent component, BrushTool btool)
	{
		this.btool = btool;
		this.component = component;
		this.component.addMouseMotionListener(this);
		this.component.addMouseListener(this);
		this.component.revalidate();
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
		newX = e.getX();
		newY = e.getY();
		
		btool.draw(newX, newY, oldX, oldY);
		component.repaint();
		
		oldX = newX;
		oldY = newY;
	}
	
	@Override
	public void mouseMoved(MouseEvent e) {
		this.component.setCursor(new Cursor(Cursor.CROSSHAIR_CURSOR));
	}
}