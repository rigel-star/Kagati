package org.lemon.filters.gui;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JWindow;

import org.lemon.filters.ResizeImage;
import org.lemon.filters.transform.PerspectivePlane;
import org.lemon.gui.LayerContainer;
import org.lemon.math.Vec2d;
import org.lemon.utils.Utils;


public class VanishingPointFilterGUI extends JWindow {
	private static final long serialVersionUID = 1L;
	
	
	private BufferedImage mainSrc, src, copy;
	private Point2D startP = null, endP = null;
	private Point2D extraStartP = null, extraEndP = null;
	private List<Point2D> pts = new ArrayList<>();
	private List<Ellipse2D> eps = new ArrayList<Ellipse2D>();
	private List<LineLocation> lloc = new ArrayList<LineLocation>();
	
	private List<PerspectivePlane> persPlanes = new ArrayList<>();
	
	private Graphics2D g2d = null;
	
	final int MAX_ZOOM_LEVEL = 700;
	final int MIN_ZOOM_LEVEL = 500;
	
	private final Dimension DEFAULT_RESIZED_IMAGE_SIZE = new Dimension(600, 600);
	
	private Point2D draggedPoint;
	
	
	private boolean enableEraserTool = false;
	
	
	private Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
	private TransformationToolPanel toolPanel;
	
	
	/*buttons*/
	private JPanel btnPanel;
	private JButton saveBtn;
	private JButton cancelBtn;
	
	/*application main LayerContainer*/
	private LayerContainer lycont;
	
	
	public VanishingPointFilterGUI(BufferedImage userSrc, LayerContainer lycont) {
		this.lycont = lycont;
		
		mainSrc = userSrc;
			
		src = Utils.getImageCopy(mainSrc);
		copy = Utils.getImageCopy(src);//new ResizeImage(src).getImageSizeOf(DEFAULT_RESIZED_IMAGE_SIZE.width, DEFAULT_RESIZED_IMAGE_SIZE.height);
		copy = new ResizeImage(copy).getImageSizeOf(600, 600);
		
		setSize(screen.width - 50, 650);
		setLayout(new BorderLayout());
		setLocationRelativeTo(null);
		getRootPane().setBorder(BorderFactory.createLineBorder(Color.GRAY, 4));
		
		var ppanel = new CanvasPanel();
		toolPanel = new TransformationToolPanel(this, this.lycont, ppanel, persPlanes);
		
		this.init();
		
		Container c = getContentPane();
		c.setLayout(new BorderLayout());
		
		c.add(toolPanel, BorderLayout.WEST);
		c.add(ppanel, BorderLayout.CENTER);
		c.add(btnPanel, BorderLayout.EAST);
		
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
		cancelBtn.addActionListener(action -> {
			dispose();
		});
		
		btnPanel.add(saveBtn);
		btnPanel.add(cancelBtn);
	}
	
	
	
	class CanvasPanel extends JPanel {
		private static final long serialVersionUID = 1L;
		
		
		public CanvasPanel() {
			setLocationRelativeTo(null);
			setBorder(BorderFactory.createLineBorder(Color.darkGray, 2));
		}
		
		
		Color c = Color.blue;
		int imgWidth = copy.getWidth();
		int imgHeight = copy.getHeight();
		
		
		@Override
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			
			var dim = getPreferredSize();
			
			g2d = (Graphics2D) g;
			g2d.setStroke(new BasicStroke(3));
			g2d.setPaint(c);
			g2d.translate((dim.width / 2) - (imgWidth / 2), (dim.height / 2) - (imgHeight / 2));
			g2d.drawImage(copy, 0, 0, null);
			g2d.translate(0, 0);
			
			if(startP != null && endP != null)
				g2d.draw(new Line2D.Double(startP, endP));
			
			if(pts.size() == 4) {
				
				persPlanes.add(new PerspectivePlane(pts));
				
				g2d.setPaint(Color.yellow);
				g2d.draw(quadToRect(pts));
				g2d.setPaint(c);
			}
			
			/*when 3 points are selected*/
			if(extraStartP != null && extraEndP != null)
				g2d.draw(new Line2D.Double(extraStartP, extraEndP));

			
			/*lines to draw*/
			for(LineLocation llc: lloc) {
				
				if(lloc.size() >= 4) {
					
					for(int i=0; i<4; i+=2) {
						
						var index = 0;
						
						var vc1 = new Vec2d((Point) lloc.get(i).getP1());
						var firstDir = Math.toDegrees(vc1.dir(new Vec2d((Point) lloc.get(i).getP2())));
						
						if(i == 2)
							index = i + 1;
						else
							index = i + 2;
						
						var vc2 = new Vec2d((Point) lloc.get(index).getP1());
						var scndDir = Math.toRadians(vc2.dir(new Vec2d((Point) lloc.get(index).getP2())));
						
						if((firstDir - scndDir) >= 15) {
							c = Color.red;
						}
						else {
							c = Color.blue;
						}
					}
					
				}
				
				g2d.draw(new Line2D.Double(llc.getP1(), llc.getP2()));
				
			}
			
			/*for every control point*/
			for(Point2D p: pts) {
				g2d.fill(new Ellipse2D.Double((int) p.getX() - 7, (int) p.getY() - 7, 15, 15));
			}
		}
		
		
		@Override
		public Dimension getPreferredSize() {
			return new Dimension(imgWidth, imgHeight);
		};
		
	}
	
	
	/*
	 * Makes rectangle out of given points.
	 * */
	private Rectangle quadToRect(final List<Point2D> pts) {
		Polygon poly = new Polygon();
		
		for(Point2D pt: pts) {
			poly.addPoint((int) pt.getX(), (int) pt.getY());
		}
		
		var bound = poly.getBounds();
		return bound;
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
						lloc.add(new LineLocation(pts.get(0), pts.get(1)));
					}
					else if(pts.size() == 3) {
						lloc.add(new LineLocation(pts.get(1), pts.get(2)));
					}
					else if(pts.size() == 4) {
						lloc.add(new LineLocation(pts.get(3), pts.get(0)));
						lloc.add(new LineLocation(pts.get(2), pts.get(3)));
						
					}
					
				}
				
				context.repaint();
			}
			
			
			@Override
			public void mouseMoved(MouseEvent e) {
				super.mouseMoved(e);
				
				var cpt = e.getPoint();
				var cursor = new Cursor(Cursor.CROSSHAIR_CURSOR);
				context.setCursor(cursor);
				
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
				copy = new ResizeImage(src).getImageSizeOf(DEFAULT_RESIZED_IMAGE_SIZE.width,
						DEFAULT_RESIZED_IMAGE_SIZE.height);
				startP = null;
				endP = null;
				extraEndP = null;
				extraStartP = null;
				
				g = copy.createGraphics();
				g.setPaint(Color.yellow);
				g.setStroke(new BasicStroke(3));
				
				pts.clear();
				eps.clear();
				lloc.clear();
				persPlanes.clear();
			}
			
			
		}
	
	
	/**
	 * Returns the image from under the quad area which user has selected.
	 * */
	public BufferedImage createUntransformedImage() {
		
		if(persPlanes.size() == 0)
			return null;
		
		var poly = new Polygon();
		
		for(Point2D p: pts) {
			poly.addPoint((int) p.getX(), (int) p.getY());
		}
		
		var bound = poly.getBounds();
		poly.translate(-bound.x, -bound.y);
		var out = new BufferedImage(bound.width, bound.height, BufferedImage.TYPE_INT_ARGB);
		
		Graphics2D g = out.createGraphics();
		
		g.setClip(poly);
		g.drawImage(copy, -bound.x, -bound.y, null);
		g.dispose();
		
		return out;
	}
	
	
	class LineLocation {
		
		private Point2D p1;
		private Point2D p2;
		
		public LineLocation(Point2D p1, Point2D p2) {
			this.p1 = p1;
			this.p2 = p2;
		}
		
		public Point2D getP1() {
			return p1;
		}
		public Point2D getP2() {
			return p2;
		}
	}


	public List<Point2D> getCoords(){
		return pts;
	}
	
}
