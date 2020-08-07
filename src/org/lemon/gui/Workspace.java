package org.lemon.gui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.geom.QuadCurve2D;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JDesktopPane;

import org.lemon.utils.Pair;

/**
 * Main background pane of application, which handles editing and drawing panels in application.
 * */
public class Workspace extends JDesktopPane implements ComponentListener {
	private static final long serialVersionUID = 1L;
	
	
	private List<Node> nodes = new ArrayList<Node>();
	
	
	public Workspace() {
		
		setSize(5000, 5000);
		setBackground(new Color(160, 160, 160));
		setVisible(true);
		
		addComponentListener(this);
	}
	
	
	
	/**
	 * Refresh's all the available ImageViews.
	 * */
	public void refresh() {
		
		List<ImageView> views = new ArrayList<ImageView>();
		List<String> titles = new ArrayList<String>();
		
		for(Component c: getComponents()) {
			if(c instanceof ImageView) {
				
				ImageView view = (ImageView) c;
				
				view.addComponentListener(this);
				views.add((ImageView) view);
				titles.add(((ImageView) view).getTitle());
			}
		}
		
		for(ImageView v: views) {
			v.setConOptions(titles, views);
		}
		
	}
	
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		for(Component c: getComponents()) {
			if(c instanceof ImageView) {
				var view = (ImageView) c;
				if(view.getConnection() != null) {
					draw(g, new Pair(view, view.getConnection()));
				}
			}
			if(c instanceof FilterController) {
				var con = (FilterController) c;
				for(Node node: con.getNodes()) {
					if(!nodes.contains(node))
						nodes.add(node);
				}
			}
		}
		
		drawNodes(g);
	}
	
	
	
	private void drawNodes(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		g2d.setPaint(Color.white);
		g2d.setStroke(new BasicStroke(3));
		
		for(Node node: nodes) {
			g2d.fillOval((int) node.start.x, (int) node.start.y, 10, 10);
			//g2d.fillOval((int) node.end.x, (int) node.end.y, 10, 10);
			
			//var curve = new QuadCurve2D.Double((int) node.start.x, (int) node.start.y,
			//									(int) node.mid.x, (int) node.mid.y,
			//									(int) node.end.x, (int) node.end.y);
			
			//g2d.draw(curve);
		}
	}
	
	
	
	/*drawing curve between two connections*/
	private void draw(Graphics g, Pair p) {
		Graphics2D g2d = (Graphics2D) g;
		g2d.setPaint(Color.white);
		g2d.setStroke(new BasicStroke(3));
		
		var v1 = p.getFirstPoint();
		var v2 = p.getSecondPoint();
		var start = p.getFirstComponent();
		var end = p.getSecondComponent();
		
		var begin = new Point((int) v1.x + start.getWidth(),
				(int) v1.y + (start.getHeight() / 2));
		
		var stop = new Point((int) v2.x,
				(int) v2.y + (end.getHeight() / 2));
		
		var ctrlPoint = v1.midpoint(v2);
		
		var curve = new QuadCurve2D.Double(begin.x, begin.y,
															ctrlPoint.x, ctrlPoint.y,
																stop.x, stop.y);
		g2d.fillOval(begin.x - 5, begin.y, 10, 10);
		g2d.fillOval(stop.x - 5, stop.y, 10, 10);
		g2d.draw(curve);
		
	}
	
	

	@Override
	public void componentResized(ComponentEvent e) {
		repaint();
	}

	@Override
	public void componentMoved(ComponentEvent e) {
		repaint();
	}

	@Override
	public void componentShown(ComponentEvent e) {
		repaint();
	}

	@Override
	public void componentHidden(ComponentEvent e) {
		repaint();
	}
	

	
}
