package org.lemon.filter.gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.lemon.filter.VanishingPointFilter;
import org.lemon.filter.VanishingPointFilter.PerspectivePlane;
import org.lemon.gui.image.ChooseImage;
import org.lemon.image.LImage;
import org.lemon.utils.Utils;

public class VanishingPointToolPanel extends JPanel {
	
	/**
	 * Serial UID
	 */
	private static final long serialVersionUID = 1L;

	private JButton openFileButton = null;
	private JButton drawPlaneButton = null;
	private JButton eraserToolButton = null;
	private JButton brushToolButton = null;
	private JButton textureToolButton = null;

	private JComponent context = null;
	
	private List<PerspectivePlane> persPlanes = null;
	private PerspectivePlane overlap = null;

	/* Image which user is currently dragging around perspective */
	private BufferedImage target = null;
	private BufferedImage targetCopy = null;

	private Map<Integer, BufferedImage> allImgs = new HashMap<>();
	
	public VanishingPointToolPanel( VanishingPointFilterPanel parent, JComponent context, 
									List<PerspectivePlane> persPlanes ) {

		this.context = context;
		this.persPlanes = persPlanes;

		var boxL = new FlowLayout( FlowLayout.TRAILING, 0, 10 );
		var border = BorderFactory.createLineBorder( Color.black, 1 );

		setLayout( boxL );
		setBorder( border );

		add( createOpenFileButton());
		add( createDrawPlaneButton());
		add( createBrushToolButton());
		add( createEraserToolButton());
		add( createTextureToolButton());
	}
	
	
	private JButton createOpenFileButton() {
		this.openFileButton = new JButton();
		openFileButton.setIcon( new ImageIcon( "icons/insert_img.png" ));
		openFileButton.setToolTipText( "Open Image" );
		openFileButton.addActionListener( action -> {

			var chooser = new ChooseImage();
			target = chooser.getChoosenImage();

			var mh = new DragMouseHandler();

			var lbl = new JLabel( new ImageIcon( target ));
			lbl.setBorder( BorderFactory.createDashedBorder( Color.black, 5, 2 ));
			
			lbl.setLocation( 0, 0 );
			lbl.addMouseListener( mh );
			lbl.addMouseMotionListener( mh );
			
			if( target != null ) {
				allImgs.put( lbl.hashCode(), target );
			}
			
			context.add( lbl );
			context.revalidate();
		});

		return openFileButton;
	}
	
	
	private JButton createDrawPlaneButton() {
		this.drawPlaneButton = new JButton();
		drawPlaneButton.setIcon( new ImageIcon("icons/tools/transformation/perspective.png") );
		drawPlaneButton.setToolTipText( "Create Perspective Tool" );
		drawPlaneButton.addActionListener( action -> {

		});
		return drawPlaneButton;
	}
	
	
	private JButton createEraserToolButton() {
		this.eraserToolButton = new JButton();
		eraserToolButton.setToolTipText( "Eraser Tool" );
		eraserToolButton.setIcon( new ImageIcon( "icons/tools/eraser.png" ));
		return eraserToolButton;
	}
	
	
	private JButton createBrushToolButton() {
		this.brushToolButton = new JButton();
		brushToolButton.setToolTipText( "Brush Tool" );
		brushToolButton.setIcon( new ImageIcon( "icons/tools/brush.png" ));
		return brushToolButton;
	}
	
	
	private JButton createTextureToolButton() {
		this.textureToolButton = new JButton();
		textureToolButton.setToolTipText( "Textures" );
		textureToolButton.setIcon( new ImageIcon( "icons/tools/brush.png" ));
		return textureToolButton;
	}
	
	
	@Override
	public Dimension getPreferredSize() {
		return new Dimension( 65, 500 );
	}

	
	private class DragMouseHandler extends MouseAdapter {

		private Point offset = null;
		private Component component = null;
		
		public DragMouseHandler() {}

		@Override
		public void mousePressed( MouseEvent e ) {
			offset = e.getPoint();
			component = e.getComponent();
			
			Icon icon = ((JLabel) component).getIcon();
			target = new BufferedImage( icon.getIconWidth(), icon.getIconHeight(), BufferedImage.TYPE_INT_ARGB );
			icon.paintIcon( null, target.createGraphics(), 0, 0 );
			
			if ( !inPerspective ) {
				((JLabel) component).setIcon( icon );
			}
			
			super.mousePressed( e );
		}
		
		
		@Override
		public void mouseMoved( MouseEvent e ) {
			var cursor = new Cursor( Cursor.MOVE_CURSOR );
			e.getComponent().setCursor( cursor );
			super.mouseMoved( e );
		}
		
		/* Check if image is already in perspective */
		private boolean inPerspective = false;
		
		/* Perspective warped image */
		private BufferedImage computedImg = null;
		
		@Override
		public void mouseDragged( MouseEvent e ) {

			var cursor = new Cursor( Cursor.MOVE_CURSOR );
			e.getComponent().setCursor( cursor );

			int x = e.getPoint().x - offset.x;
			int y = e.getPoint().y - offset.y;
			
			Component component = e.getComponent();
			Point location = component.getLocation();
			
			location.x += x;
			location.y += y;
			component.setLocation( location );
			
			boolean overlaps = checkOverlap( component.getBounds(), persPlanes );
			if ( overlaps ) {
				
				/**
				 * This condition make sures image panel currently 
				 * lies outside any of the perspective plane.
				 * */
				if ( !inPerspective ) {
					targetCopy = Utils.getImageCopy( target );
					
					new Thread( new Runnable() {
						
						@Override
						public void run() {
							VanishingPointFilter vpfilter = new VanishingPointFilter( 
																					overlap.p0, overlap.p1,
																					overlap.p2, overlap.p3 );
							
							computedImg = vpfilter.filter( new LImage( targetCopy )).getAsBufferedImage();
						}
					}).start();
					
					inPerspective = true;
				}	
			} 
			else {
				inPerspective = false;
				computedImg = null;
			}
			component.repaint();
			super.mouseDragged( e );
		}
		
		
		@Override
		public void mouseReleased( MouseEvent e ) {
			
			if ( inPerspective ) {
				if ( computedImg != null ) {
					
					component.setPreferredSize(
							new Dimension( overlap.getArea().width,
											overlap.getArea().height ));
					
					((JLabel) component).setIcon( new ImageIcon( computedImg ));
				}
				else {
					((JLabel) component).setIcon( new ImageIcon( target ));
					component.setPreferredSize( new Dimension( target.getWidth(), target.getHeight() ));
				}
			}
			else {
				if ( allImgs.containsKey( component.hashCode() )) {
					((JLabel) component ).setIcon( new ImageIcon( allImgs.get( component.hashCode() )));
					component.setPreferredSize( new Dimension( target.getWidth(), target.getHeight() ));
				}
			}
			component.repaint();
			super.mouseReleased( e );
		}
		
		
		/* check if imgBounds overlaps any user defined perspective plane */
		private boolean checkOverlap( Rectangle r1, List<PerspectivePlane> pplanes ) {

			for ( PerspectivePlane pp : pplanes ) {
				if ( r1.intersects( pp.getArea() ) ) {
					overlap = pp;
					return true;
				}
			}
			return false;
		}
	}
}
