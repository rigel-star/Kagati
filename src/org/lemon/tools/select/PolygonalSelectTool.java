package org.lemon.tools.select;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Polygon;
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
import java.util.logging.Level;
import java.util.logging.Logger;

import org.lemon.tools.SelectionTool;


public class PolygonalSelectTool extends MouseAdapter implements SelectionTool {
	
	private static final Logger LOGGER = Logger.getLogger( PolygonalSelectTool.class.getName() );
	
	private Component context;
	
	private List<Shape> shapes = new ArrayList<Shape>();
	private List<Point> pts = new ArrayList<Point>();
	
	private Graphics2D g2d;
	private BufferedImage src;
	
	private boolean areaSelectionDone = false;
	private SelectedArea selectedArea;
	private Polygon selectionShape;
	
	/**
	 * 
	 * {@code PolygonalSelectTool} is a selection tool made to select the part from an image. The image container
	 * passed in parameter works as a painting background where the selected region will be highlighted. This class 
	 * implements the {@code interface} SelectionTool.
	 * 
	 * @param img Image to select from
	 * @param imgContainer The component which contains the image
	 * 
	 * */
	
	public PolygonalSelectTool( BufferedImage img, Component imgContainer ) {
		context = imgContainer;
		src = img;
		
		g2d = (Graphics2D) src.getGraphics();
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.setStroke(new BasicStroke(2));
		g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));	//transparency
		g2d.setPaint(Color.blue);
	}
	
	
	private void paintImage() {
		
		if(areaSelectionDone) {
			g2d.setStroke(new BasicStroke(1, BasicStroke.CAP_SQUARE, BasicStroke.JOIN_BEVEL, 5f, new float[] {5, 5},  2));
			this.g2d.setPaint( Color.gray );
			clearSelection();
			return;
		}
		
		for( int i=0; i<shapes.size(); i++ ) {
			g2d.fill( shapes.get(i) );
			
			if( i != shapes.size() - 1 ) {
				Point2D p1 = shapes.get(i).getBounds().getLocation();
				Point2D p2 = shapes.get(i + 1).getBounds().getLocation();
				g2d.draw( new Line2D.Double( p1.getX(), p1.getY(), p2.getX(), p2.getY() ) );
			}	
		}
		context.repaint();
	}
	
	
	@Override
	public void mouseClicked(MouseEvent e) {
		super.mouseClicked( e );
		
		if( e.getButton() == MouseEvent.BUTTON1 ) {
			
			if( areaSelectionDone ) {
				clearSelection();
			}
			
			int x = e.getX();
			int y = e.getY();
			Rectangle2D r = new Rectangle2D.Double( x, y, 5, 5 );
			
			for( Shape s: shapes ) {
				if( s instanceof Rectangle2D ) 
					if( r.intersects((Rectangle2D) s) ) {
						
						selectionShape = new Polygon();
						for( Point pt: pts )
							selectionShape.addPoint( pt.x, pt.y );
						
						//selectedArea = new SelectedArea((Area) selectionShape);
						areaSelectionDone = true;
					}
			}
			
			this.shapes.add( r );
			this.pts.add( new Point( x, y ) );
			this.paintImage();
		}
	}
	
	
	@Override
	public void clearSelection() {
		shapes = new ArrayList<>();
		pts = new ArrayList<>();
		areaSelectionDone = false;
		
		g2d.setStroke( new BasicStroke(2) );
		g2d.setPaint( Color.blue );
		
		LOGGER.log(Level.INFO, "Selection cleared from: " + this.getClass().getName());
		
	}
	
	
	@Override
	public BufferedImage getSelectedAreaImage() {
		/*result image*/
		BufferedImage res = new BufferedImage(src.getWidth(), src.getHeight(), BufferedImage.TYPE_INT_ARGB);
		
		Graphics2D g = res.createGraphics();
		g.setClip(selectionShape);
		g.drawImage(src, 0, 0, null);
		g.dispose();
		
		LOGGER.log(Level.INFO, "Getting selected area image.");
		
		return res;
	}
	
	
	@Override
	public SelectedArea getSelectedArea() {
		return selectedArea;
	}
	
	
	/**
	 * @return {@code true} if area is selected, otherwise {@code false}.
	 * */
	@Override
	public boolean isAreaSelected() {
		return areaSelectionDone;
	}
	
}
