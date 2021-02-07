package org.lemon.filter.gui;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
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
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import org.lemon.filter.ResizeImageFilter;
import org.lemon.filter.VanishingPointFilter.PerspectivePlane;
import org.lemon.gui.Gap;
import org.lemon.image.LImage;
import org.lemon.math.SimplePolygon;
import org.lemon.math.Vec2;
import org.lemon.utils.Utils;

public class VanishingPointFilterPanel extends JDialog {
	
	/**
	 * Serial UID
	 * */
	private static final long serialVersionUID = 1L;
	
	private BufferedImage mainSrc, src, copy;
	private Point2D startP = null, endP = null;
	private Point2D extraStartP = null, extraEndP = null;
	
	private List<Point2D> pts = new ArrayList<>();
	private List<Ellipse2D.Double> eps = new ArrayList<>();
	private List<LineLocation> lloc = new ArrayList<>();
	private List<PerspectivePlane> persPlanes = new ArrayList<>();
	
	private Graphics2D g2d = null;
	
	final int MAX_ZOOM_LEVEL = 700;
	final int MIN_ZOOM_LEVEL = 500;
	
	private final Dimension MAX_IMAGE_SIZE = new Dimension(600, 600);
	
	private Point2D draggedPoint;
	
	private boolean enableEraserTool = false;
	
	private Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
	private VanishingPointToolPanel toolPanel;
	
	/*buttons*/
	private JPanel btnPanel;
	private JButton saveBtn;
	private JButton cancelBtn;
	
	public static void main( String[] args ) {
		
		BufferedImage src = null;
		try {
			src = ImageIO.read( new FileInputStream( new File( "C:\\Users\\Ramesh\\Desktop\\opencv\\billboard.jpg" )));
		} catch( IOException ex ) {
			ex.printStackTrace();
		}
		
		new VanishingPointFilterPanel( src );
	}
	
	public VanishingPointFilterPanel( BufferedImage userSrc ) {
		
		mainSrc = userSrc;
		
		src = Utils.getImageCopy( mainSrc );
		copy = Utils.getImageCopy( src );
		
		if ( copy.getHeight() < 600 && copy.getWidth() < 600 )
			copy = new ResizeImageFilter( 600, 600 )
										.filter( new LImage( copy ))
										.getAsBufferedImage();
		
		setTitle( "Vanishing Point" );
		setResizable( true );
		setSize( screen.width, screen.height );
		setLayout( new BorderLayout() );
		setLocationRelativeTo( null );
		getRootPane().setBorder( BorderFactory.createLineBorder( Color.GRAY, 4 ) );
		
		var canvas = new CanvasPanel();
		toolPanel = new VanishingPointToolPanel( this, canvas, persPlanes );
		
		this.init();
		
		Container c = getContentPane();
		c.setLayout( new BorderLayout() );
		
		c.add( toolPanel, BorderLayout.WEST );
		c.add( btnPanel, BorderLayout.EAST );
		
		JPanel canvContainer = new JPanel();
		canvContainer.setLayout( new FlowLayout( FlowLayout.CENTER ));
		canvContainer.add( canvas );
		
		c.add( canvContainer );
		
		var mh = new MouseHandler( canvas, copy.createGraphics() );
		
		canvas.addMouseListener( mh );
		canvas.addMouseMotionListener( mh );
		
		pack();
		setVisible( true );
	}
	
	
	/**
	 * Initialize the widgets.
	 * */
	private void init() {
		this.btnPanel = new JPanel();
		btnPanel.setLayout( new BoxLayout( btnPanel, BoxLayout.Y_AXIS ));
		
		var border = BorderFactory.createLineBorder( Color.black, 1 );
		btnPanel.setBorder( border );
		
		this.saveBtn = new JButton( "Save" );
		saveBtn.setMaximumSize( new Dimension( 140, 32 ) );
		
		this.cancelBtn = new JButton( "Cancel" );
		cancelBtn.setMaximumSize( new Dimension( 140, 32 ));
		cancelBtn.addActionListener( action -> {
			dispose();
		});
		
		btnPanel.add( new Gap( 5 ));
		btnPanel.add( saveBtn );
		btnPanel.add( new Gap( 5 ));
		btnPanel.add( cancelBtn );
	}
	
	
	class CanvasPanel extends JPanel {
		
		/**
		 * Serial UID
		 * */
		private static final long serialVersionUID = 1L;
		
		public CanvasPanel() {
			setLocationRelativeTo( null );
			setBorder( BorderFactory.createLineBorder( Color.darkGray, 2 ) );
			setBackground( new Color( 100, 100, 100 ));
		}
		
		Color c = Color.blue;
		int imgWidth = copy.getWidth();
		int imgHeight = copy.getHeight();
		
		@Override
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			
			var dim = getPreferredSize();
			
			g2d = (Graphics2D) g;
			g2d.setStroke( new BasicStroke( 3 ) );
			g2d.setPaint( c );
			g2d.translate( ( dim.width >> 1 ) - ( imgWidth >> 1 ), ( dim.height >> 1 ) - ( imgHeight >> 1 ));
			g2d.drawImage( copy, 0, 0, null );
			g2d.translate( 0, 0 );
			
			if( startP != null && endP != null )
				g2d.draw( new Line2D.Double( startP, endP ) );
			
			if( pts.size() == 4 ) {
				g2d.setPaint( Color.yellow );
				g2d.draw( quadToRect( pts ) );
				g2d.setPaint( c );
			}
			
			/**
			 * When 3 points are selected
			 * */
			if( extraStartP != null && extraEndP != null )
				g2d.draw( new Line2D.Double( extraStartP, extraEndP ));
			
			/*lines to draw*/
			for( LineLocation llc: lloc ) {
				
				if( lloc.size() >= 4 ) {
					
					for( int i = 0; i < 4; i += 2) {
						var index = 0;
						
						var vc1 = new Vec2( (Point) lloc.get(i).getP1() );
						var firstDir = Math.toDegrees( vc1.dir( new Vec2( (Point) lloc.get(i).getP2() ) ) );
						
						if( i == 2 )
							index = i + 1;
						else
							index = i + 2;
						
						var vc2 = new Vec2((Point) lloc.get(index).getP1());
						var scndDir = Math.toRadians(vc2.dir(new Vec2((Point) lloc.get(index).getP2())));
						
						if((firstDir - scndDir) >= 15) {
							c = Color.red;
						}
						else {
							c = Color.blue;
						}
					}
				}
				
				g2d.draw( new Line2D.Double( llc.getP1(), llc.getP2() ) );
			}
			
			/*for every control point*/
			for( Ellipse2D.Double ep: eps ) {
				g2d.fill( ep );
			}
		}
		
		@Override
		public Dimension getPreferredSize() {
			return new Dimension( imgWidth, imgHeight );
		};
	}
	
	
	/*
	 * Makes rectangle out of given points.
	 * */
	private Rectangle quadToRect( final List<Point2D> pts ) {
		Polygon poly = new Polygon();
		
		for( Point2D pt: pts ) {
			poly.addPoint( (int) pt.getX(), (int) pt.getY() );
		}
		
		var bound = poly.getBounds();
		return bound;
	}
	
	
	/**
	 * Mouse handler for drawing plane.
	 * */
	class MouseHandler extends MouseAdapter {
			
			JComponent context;
			Graphics2D g;
			
			public MouseHandler(JComponent context, Graphics2D g) {
				this.context = context;
				
				this.g = g;
			}
			
			@Override
			public void mousePressed( MouseEvent e ) {
				super.mousePressed( e );
				
				Point p = e.getPoint();
				int r = 8;
				
				if( pts.size() < 4 )
					return;
				
				/**
				 * Check for every point if the current mouse press
				 * locations dist to any of the point is less than 8 
				 * then drag that point
				 * */
				if( p.distance( pts.get( 0 )) < r ) draggedPoint = pts.get( 0 );
				if( p.distance( pts.get( 1 )) < r ) draggedPoint = pts.get( 1 );
				if( p.distance( pts.get( 2 )) < r ) draggedPoint = pts.get( 2 );
				if( p.distance( pts.get( 3 )) < r ) draggedPoint = pts.get( 3 );
			}
			
			@Override
			public void mouseDragged( MouseEvent e ) {
				super.mouseDragged( e );
				
				var pp = e.getPoint();
				
				if( enableEraserTool ) {
					
					for( int x = pp.x; x < pp.x + 5; x++ ) {
						for( int y = pp.y; y < pp.y + 5; y++ ){
							var c = mainSrc.getRGB( x, y );
							copy.setRGB( x, y, c );
						}
					}
					repaint();
				}
				else {
					if( pts.size() < 4 )
						return;
					if ( draggedPoint != null ) {
			            draggedPoint.setLocation( e.getX(), e.getY() );
			            context.repaint();
			        }
				}
			}
			
			@Override
			public void mouseClicked( MouseEvent e ) {
				super.mouseClicked( e );
				
				Point pt = e.getPoint();
				
				if( e.getButton() == MouseEvent.BUTTON3 ) {
					clearAll();
				}
				
				else if( e.getButton() == MouseEvent.BUTTON1 ) {
					
					pts.add( pt );
					
					Ellipse2D.Double el = new Ellipse2D.Double( pt.x - 7, pt.y - 7, 15, 15 );
					eps.add( el );
					
					if( pts.size() == 4 ) {
						
						List<Vec2> coords = new ArrayList<>();
						
						for( Point2D p: pts ) {
							coords.add( new Vec2( p.getX(), p.getY() ) );
						}
						
						SimplePolygon poly = new SimplePolygon( coords );
						double area = poly.area();
						
						System.out.println( "DEBUG:: VanishingPointGUI:: Area of polygon: " + area );
						
						if ( area > 0 ) {
							pts.clear();
							repaint();
							showMessageDialog( "You are supposed to create plane anti-clockwise!" );
							return;
						}
						persPlanes.add( new PerspectivePlane( pts ) );
					}
					
					if( pts.size() == 2 ) {
						lloc.add( new LineLocation( pts.get( 0 ), pts.get( 1 ) ) );
					}
					else if( pts.size() == 3 ) {
						lloc.add( new LineLocation( pts.get( 1 ), pts.get( 2 ) ) );
					}
					else if( pts.size() == 4 ) {
						lloc.add( new LineLocation( pts.get( 3 ), pts.get( 0) ) );
						lloc.add( new LineLocation( pts.get( 2 ), pts.get( 3 ) ) );
					}
				}
				context.repaint();
			}
			
			@Override
			public void mouseMoved( MouseEvent e ) {
				super.mouseMoved( e );
				
				var cpt = e.getPoint();
				var cursor = new Cursor( Cursor.CROSSHAIR_CURSOR );
				context.setCursor( cursor );
				
				if( pts.size() == 1 ) {
					startP = pts.get( 0 );
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
				copy = new ResizeImageFilter( MAX_IMAGE_SIZE.width, MAX_IMAGE_SIZE.height )
											.filter( new LImage( src ))
											.getAsBufferedImage();
				
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
	 * For giving warnings and guidings.
	 * */
	private void showMessageDialog( String msg ) {
		JOptionPane.showMessageDialog( this, msg );
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
		
		/**
		 * Create location of new line.
		 * */
		public LineLocation( Point2D p1, Point2D p2 ) {
			this.p1 = p1;
			this.p2 = p2;
		}
		
		/**
		 * @return First point.
		 * */
		public Point2D getP1() {
			return p1;
		}
		
		/**
		 * @return Second point.
		 * */
		public Point2D getP2() {
			return p2;
		}
	}
	
	public List<Point2D> getCoords(){
		return pts;
	}
}
