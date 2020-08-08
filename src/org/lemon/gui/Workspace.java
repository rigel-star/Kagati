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

import org.lemon.LemonObject;
import org.lemon.utils.Pair;

/**
 * Main background pane of application, which handles editing and drawing panels in application.
 * */
@LemonObject(type = LemonObject.GUI_CLASS)
public class Workspace extends JDesktopPane implements ComponentListener {
	private static final long serialVersionUID = 1L;
	
	
	private List<Node> nodes = new ArrayList<Node>();
	private List<FilterControllable> fcontrollables = new ArrayList<>();
	private List<FilterController> fcontrollers = new ArrayList<>();
	
	
	public Workspace() {
		
		setSize(5000, 5000);
		setBackground(new Color(160, 160, 160));
		setVisible(true);
		
		fetchNodes();
		
		addComponentListener(this);
	}
	
	
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		var g2d = (Graphics2D) g;
		
		for(Component c: getComponents()) {
			if(c instanceof ImageView) {
				var view = (ImageView) c;
				if(view.getConnection() != null) {
					draw(g2d, new Pair(view, view.getConnection()));
				}
			}
		}
		
		fcontrollables.stream().forEach(fc -> {
			var node = fc.getNode();
			drawNode(g2d, node);
		});
		
		fcontrollers.stream().forEach(fc -> {
			for(Node node: fc.getNodes()) {
				drawNode(g2d, node);
			}
		});
	}
	
	
	
	/**
	 * Refresh's all the available ImageViews.
	 * */
	public void refresh() {
		
		for(Component c: getComponents()) {
			c.addComponentListener(this);	
		}
		
		if(fcontrollables.size() > 0) {
			fcontrollers.stream().forEach(fc -> {
				for(Node node: fc.getNodes()) {
					node.addConnection(fcontrollables.get(0));
				}
			});
		}
		
	}
	
	
	/*fetch all the nodes*/
	public void fetchNodes() {
		for(Component c: getComponents()) {
			if(c instanceof FilterController) {
				var con = (FilterController) c;
				
				if(!fcontrollers.contains(con))
					fcontrollers.add(con);

				for(Node node: con.getNodes()) {
					if(!nodes.contains(node)) {
						nodes.add(node);
					}
					
				}
			}
			else if(c instanceof FilterControllable) {
				var fc = (FilterControllable) c;
				
				if(!fcontrollables.contains(fc))
					fcontrollables.add(fc);
				
				if(!nodes.contains(fc.getNode())) {
					nodes.add(fc.getNode());
				}
			}
		}
		System.out.println("Nodes: " + nodes.size());
		System.out.println("Filter controllables: " + fcontrollables.size());
		System.out.println("Filter controllers: " + fcontrollers.size());
	}

	
	
	private void drawNode(Graphics2D g2d, Node node) {
		g2d.setPaint(Color.yellow);
		
		g2d.fillOval((int) node.start.x - 7, (int) node.start.y - 7, 15, 15);
		
		if(node.getParent() instanceof FilterControllable) {
			
		}
		else if(node.getParent() instanceof FilterController) {
		
			node.getConnections().forEach(c -> {
					
				g2d.setPaint(Color.white);
				g2d.setStroke(new BasicStroke(2));
				node.setEnd(node.start.midpoint(c.getNode().start));
				var curve = new QuadCurve2D.Double((int) node.start.x, (int) node.start.y,
													(int) node.mid.x, (int) node.mid.y,
													(int) node.end.x, (int) node.end.y);
				g2d.draw(curve);
			});
		}
		
	}
	
	
	private void updateNodes() {
		fcontrollables.forEach(fc -> {
			fc.updateNode();
		});
		
		fcontrollers.forEach(fc -> {
			fc.updateNodes();
		});
		repaint();
	}
	
	
	/*drawing curve between two connections*/
	private void draw(Graphics2D g2d, Pair p) {
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
		updateNodes();
	}

	@Override
	public void componentMoved(ComponentEvent e) {
		updateNodes();
	}

	@Override
	public void componentShown(ComponentEvent e) {
		updateNodes();
	}

	@Override
	public void componentHidden(ComponentEvent e) {
		updateNodes();
	}
	
	
	
}
