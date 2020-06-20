package org.lemon.filters.transformations.gui;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JWindow;

import org.lemon.filters.ResizeImage;
import org.lemon.filters.transformations.VanishingPointFilter;
import org.lemon.utils.Utils;


public class VanishingPointFilterGUI extends JWindow {
	private static final long serialVersionUID = 1L;
	
	
	private BufferedImage mainSrc, src, copy, computedImage;
	private Point startP = null, endP = null;
	private Point extraStartP = null, extraEndP = null;
	private List<Line2D> actLns = new ArrayList<Line2D>();
	private List<Point> pts = new ArrayList<Point>();
	private List<Ellipse2D> eps = new ArrayList<Ellipse2D>();
	private List<LineLocation> lloc = new ArrayList<LineLocation>();
	
	private Point draggedPoint;
	
	private BufferedImage target;
	
	private boolean enableEraserTool = false;
	
	
	private Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
	private TransformationToolPanel toolPanel = new TransformationToolPanel();
	
	private JPanel btnPanel;
	private JButton saveBtn;
	private JButton cancelBtn;
	
	
	public VanishingPointFilterGUI() {
		
		setSize(screen.width - 100, screen.height - 100);
		setLayout(new BorderLayout());
		setLocation(50, 50);
		getRootPane().setBorder(BorderFactory.createLineBorder(Color.GRAY, 2));
		
		this.init();
		
		Container c = getContentPane();
		c.add(toolPanel, BorderLayout.WEST);
		c.add(btnPanel, BorderLayout.EAST);
		
		try {
			mainSrc = ImageIO.read(new File("C:\\Users\\Ramesh\\Desktop\\pers_homog\\boxes.jpg"));
			target = ImageIO.read(new File("C:\\Users\\Ramesh\\Desktop\\pers_homog\\book2.jpg"));
			
			src = Utils.getImageCopy(mainSrc);
			copy = new ResizeImage(src).getImageSizeOf(800, 800);
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		
		var ppanel = new CanvasPanel();
		c.add(ppanel, BorderLayout.CENTER);
		
		var mh = new MouseHandler(ppanel, copy.createGraphics());
		
		ppanel.addMouseListener(mh);
		ppanel.addMouseMotionListener(mh);
		
		setVisible(true);
	}
	
	
	private void init() {
		this.btnPanel = new JPanel();
		btnPanel.setLayout(new BoxLayout(btnPanel, BoxLayout.Y_AXIS));
		
		this.saveBtn = new JButton("Save");
		this.cancelBtn = new JButton("Cancel");
		
		btnPanel.add(saveBtn);
		btnPanel.add(cancelBtn);
	}
	
	
	
	class CanvasPanel extends JPanel {
		private static final long serialVersionUID = 1L;
		
		
		@Override
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			g.drawImage(copy, 0, 0, null);
			
			var g2d = (Graphics2D) g;
			g2d.setStroke(new BasicStroke(3));
			g2d.setPaint(Color.yellow);
			
			if(startP != null && endP != null)
				g2d.draw(new Line2D.Double(startP, endP));
			
			if(pts.size() == 4) {
				var pp1 = pts.get(0);
				var pp2 = pts.get(1);
				var pp3 = pts.get(2);
				var pp4 = pts.get(3);
				computedImage = VanishingPointFilter.Pseudo3D.computeImage(target,
						pp1, pp2, pp3, pp4);
				g2d.drawImage(computedImage, 0, 0, null);
			}
			
			/*when 3 points are selected*/
			if(extraStartP != null && extraEndP != null)
				g2d.draw(new Line2D.Double(extraStartP, extraEndP));

			
			/*lines to draw*/
			for(LineLocation llc: lloc) {
				g2d.draw(new Line2D.Double(llc.getP1(), llc.getP2()));
			}
			
			/*for every control point*/
			for(Point p: pts) {
				g2d.fill(new Ellipse2D.Double(p.x - 7, p.y - 7, 15, 15));
			}
		}
		
	}
	
	
	
	
	class LineLocation {
			
			private Point p1;
			private Point p2;
			
			public LineLocation(Point p1, Point p2) {
				this.p1 = p1;
				this.p2 = p2;
			}
			
			public Point getP1() {
				return p1;
			}
			public Point getP2() {
				return p2;
			}
	}
	
	
	public List<Point> getCoords(){
		return pts;
	}
	
	
	
	class MouseHandler extends MouseAdapter {
			
			JComponent context;
			Graphics2D g;
			
			public MouseHandler(JComponent context, Graphics2D g) {
				this.context = context;
				
				this.g = g;
			}
			
			
			public void mousePressed(MouseEvent e) {
				super.mousePressed(e);
				
				var p = e.getPoint();
				var r = 8;
				
				if(pts.size() < 4)
					return;
				
				/*check for every point if the current mouse press
				 *  locations dist to any of the point is less than 8 then drag that point*/
				if(p.distance(pts.get(0)) < r) draggedPoint = pts.get(0);
				if(p.distance(pts.get(1)) < r) draggedPoint = pts.get(1);
				if(p.distance(pts.get(2)) < r) draggedPoint = pts.get(2);
				if(p.distance(pts.get(3)) < r) draggedPoint = pts.get(3);
			}
			
			
			public void mouseDragged(MouseEvent e) {
				super.mouseDragged(e);
				
				var pp = e.getPoint();
				
				if(!enableEraserTool) {
					if(pts.size() < 4)
						return;
					if (draggedPoint != null) {
			            draggedPoint.setLocation(e.getX(), e.getY());
			            context.repaint();
			        }
				}
				else {
					for(int x = pp.x; x < pp.x + 5; x++) {
						for(int y = pp.y; y < pp.y + 5; y++){
							var c = mainSrc.getRGB(x, y);
							copy.setRGB(x, y, c);
						}
					}
					repaint();
				}
			}
			
			
			@Override
			public void mouseClicked(MouseEvent e) {
				super.mouseClicked(e);
				
				if(e.getButton() == MouseEvent.BUTTON3) {
					clearAll();
				}
				
				else if(e.getButton() == MouseEvent.BUTTON1) {
					if(pts.size() >= 4) {
						clearAll();
						return;
					}
				
					
					var pt = new Point(e.getX(), e.getY());
					
					var el = new Ellipse2D.Double(pt.x - 7, pt.y - 7, 15, 15);
					
					pts.add(pt);
					eps.add(el);
					
					if(pts.size() == 2) {
						actLns.add(new Line2D.Double(pts.get(0), pts.get(1)));
						lloc.add(new LineLocation(pts.get(0), pts.get(1)));
					}
					else if(pts.size() == 3) {
						actLns.add(new Line2D.Double(pts.get(1), pts.get(2)));
						lloc.add(new LineLocation(pts.get(1), pts.get(2)));
					}
					else if(pts.size() == 4) {
						actLns.add(new Line2D.Double(pts.get(0), pts.get(3)));
						actLns.add(new Line2D.Double(pts.get(2), pts.get(3)));
						lloc.add(new LineLocation(pts.get(0), pts.get(3)));
						lloc.add(new LineLocation(pts.get(2), pts.get(3)));
						
						//pplane.add(new PerspectivePlane(pts.get(0), pts.get(1), pts.get(2), pts.get(3)));
					}
					
				}
				
				context.repaint();
			}
			
			
			@Override
			public void mouseMoved(MouseEvent e) {
				super.mouseMoved(e);
				
				var cpt = e.getPoint();
				
				if(pts.size() == 1) {
					startP = pts.get(0);
					endP = cpt;
				}
				else if(pts.size() == 2) {
					startP = pts.get(1);
					endP = cpt;
					extraStartP = pts.get(0);
					extraEndP = cpt;
				}
				else if(pts.size() == 3) {
					startP = pts.get(2);
					endP = cpt;
					extraStartP = pts.get(0);
					extraEndP = cpt;
				}
				else {
					startP = null;
					endP = null;
				}
				
				context.repaint();
			}
			
			
			private void clearAll() {
				copy = new ResizeImage(src).getImageSizeOf(800, 800);
				startP = null;
				endP = null;
				extraEndP = null;
				extraStartP = null;
				
				g = copy.createGraphics();
				g.setPaint(Color.yellow);
				g.setStroke(new BasicStroke(3));
				
				pts.clear();
				actLns.clear();
				eps.clear();
				lloc.clear();
			}
			
			
		}
	
	
	
}
