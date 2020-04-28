package org.lemon.tools;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JLabel;

//Basic image snapping tool
public class LazySnappingTool {
	
	private JLabel context;
	private BufferedImage img;
	
	public LazySnappingTool(BufferedImage img, Object context) {
		this.context = (JLabel) context;
		this.img = img;
	}
	
	public void apply() {
		this.context.addMouseListener(new SnappingCanvas(img));
	}
	
	//for drawing canvas
	class SnappingCanvas extends MouseAdapter {
		
		List<Shape> shapes = new ArrayList<Shape>();
		
		BufferedImage img;
		Graphics2D g2d;
		
		public SnappingCanvas(BufferedImage img) {
			this.img = img;
			g2d = (Graphics2D) img.getGraphics();
			g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			g2d.setStroke(new BasicStroke(2));
			g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));	//transparency
			g2d.setPaint(Color.blue);
		}
		
		public void paintImage() {
			for(int i=0; i<shapes.size(); i++) {
				g2d.fill(shapes.get(i));
				
				if(i != shapes.size() - 1) {
					Point2D p1 = shapes.get(i).getBounds().getLocation();
					Point2D p2 = shapes.get(i+1).getBounds().getLocation();
					g2d.draw(new Line2D.Double(p1.getX(), p1.getY(), p2.getX(), p2.getY()));
				}	
			}
			context.repaint();
		}
		
		@Override
		public void mouseClicked(MouseEvent e) {
			super.mouseClicked(e);
			
			if(e.getButton() == MouseEvent.BUTTON1) {
				int x = e.getX();
				int y = e.getY();
				Rectangle2D r = new Rectangle2D.Double(x, y+15, 5, 5);
				
				for(Shape s: shapes) {
					if(s instanceof Rectangle2D) 
						if(r.intersects((Rectangle2D) s))
							this.g2d.setPaint(Color.white);
				}
				
				this.shapes.add(r);
				this.paintImage();
			}
		}
	}

}
