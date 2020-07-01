package org.lemon.filters.transformations.gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.lemon.filters.transformations.PerspectivePlane;
import org.lemon.filters.transformations.VanishingPointFilter;
import org.lemon.gui.image.ChooseImage;
import org.lemon.utils.Utils;

public class TransformationToolPanel extends JPanel implements Runnable {
	private static final long serialVersionUID = 1L;

	private JButton openFileButton;
	private JButton drawPlaneButton;
	private JButton eraserToolButton;
	private JButton brushToolButton;

	private JComponent context = null;

	private Rectangle imgBounds = null;
	private List<PerspectivePlane> persPlanes = null;
	private PerspectivePlane overlappedPersPlane = null;

	/* Image which user is currently dragging around perspective */
	private BufferedImage target = null;
	private BufferedImage targetCopy = null;

	/* check if image is already in perspective */
	private boolean alreadyInPerspective = false;

	Graphics2D g2d;

	private Map<Integer, BufferedImage> allImgs = new HashMap<>();

	private BufferedImage computedImg = null;

	private Thread computingThread = null;

	private ResizableImagePanel resizableImgPanel = null;

	
	
	public TransformationToolPanel(JComponent context, Graphics2D g2d, List<PerspectivePlane> persPlanes) {

		this.g2d = g2d;
		this.context = context;
		this.persPlanes = persPlanes;

		this.computingThread = new Thread(this);
		computingThread.setPriority(Thread.MAX_PRIORITY);

//		try {
//			var img = ImageIO.read(new FileInputStream(new File("C:\\Users\\Ramesh\\Desktop\\opencv\\frame.jpeg")));
//			target = Utils.getImageCopy(img);
//			targetCopy = Utils.getImageCopy(img);
//			
//			var lbl = new JLabel(new ImageIcon(img));
//			
//			var dml = new DragMouseHandler();
//			lbl.addMouseListener(dml);
//			lbl.addMouseMotionListener(dml);
//			
//			context.add(lbl);
//			context.revalidate();
//			
//		} catch(Exception ex) {
//			ex.printStackTrace();
//		}

		var boxL = new FlowLayout(FlowLayout.TRAILING, 0, 10);
		var border = BorderFactory.createLineBorder(Color.black, 1);

		setLayout(boxL);
		setBorder(border);

		add(createOpenFileButton());
		add(createDrawPlaneButton());
		add(createEraserToolButton());
		add(createBrushToolButton());
	}

	
	
	private JButton createOpenFileButton() {
		this.openFileButton = new JButton("Open...");

		openFileButton.addActionListener(action -> {

			var chooser = new ChooseImage();
			target = chooser.getChoosenImage();

			var mh = new DragMouseHandler();

			var lbl = new JLabel(new ImageIcon(target));
			lbl.setBorder(BorderFactory.createDashedBorder(Color.black, 5, 2));
			
			lbl.setLocation(0, 0);
			lbl.addMouseListener(mh);
			lbl.addMouseMotionListener(mh);

			resizableImgPanel = new ResizableImagePanel(target);

			allImgs.put(resizableImgPanel.hashCode(), target);

			context.add(lbl);
			//context.add(resizableImgPanel);

			context.revalidate();
		});

		return openFileButton;
	}

	
	
	private JButton createDrawPlaneButton() {
		this.drawPlaneButton = new JButton();
		drawPlaneButton.setIcon(new ImageIcon("icons/tools/transformation/perspective.png"));

		drawPlaneButton.addActionListener(action -> {

		});

		return drawPlaneButton;
	}

	
	
	private JButton createEraserToolButton() {
		this.eraserToolButton = new JButton();
		eraserToolButton.setIcon(new ImageIcon("icons/tools/eraser.png"));
		return eraserToolButton;
	}

	
	
	private JButton createBrushToolButton() {
		this.brushToolButton = new JButton();
		brushToolButton.setIcon(new ImageIcon("icons/tools/brush.png"));
		return brushToolButton;
	}

	
	
	@Override
	public Dimension getPreferredSize() {
		return new Dimension(65, 500);
	}

	
	
	private class DragMouseHandler extends MouseAdapter {

		private Point offset = null;

		@Override
		public void mousePressed(MouseEvent e) {
			super.mousePressed(e);

			offset = e.getPoint();
		}

		@Override
		public void mouseMoved(MouseEvent e) {
			super.mouseMoved(e);
			var cursor = new Cursor(Cursor.MOVE_CURSOR);
			e.getComponent().setCursor(cursor);
		}

		@Override
		public void mouseDragged(MouseEvent e) {
			super.mouseDragged(e);

			var cursor = new Cursor(Cursor.MOVE_CURSOR);
			e.getComponent().setCursor(cursor);

			int x = e.getPoint().x - offset.x;
			int y = e.getPoint().y - offset.y;
			Component component = e.getComponent();
			Point location = component.getLocation();
			location.x += x;
			location.y += y;
			component.setLocation(location);
			imgBounds = component.getBounds();

			var icon = ((JLabel) component).getIcon();
			target = new BufferedImage(icon.getIconWidth(), icon.getIconHeight(), BufferedImage.TYPE_INT_ARGB);
			icon.paintIcon(null, target.createGraphics(), 0, 0);

			if (checkOverlap(imgBounds, persPlanes)) {

				targetCopy = Utils.getImageCopy(target);

				if (!alreadyInPerspective) {

					try {
						computingThread.start();
					} catch (Exception ex) {
						System.out.println("Exception...");

						if (!Thread.currentThread().isAlive())
							computingThread.start();
					}
					
					alreadyInPerspective = true;

				}

				if (computedImg != null) {
					
					component.setPreferredSize(
							new Dimension(overlappedPersPlane.getArea().width,
											overlappedPersPlane.getArea().height));
					
					((JLabel) component).setIcon(new ImageIcon(computedImg));
				}

			} else {
				if (allImgs.containsKey(component.hashCode())) {
					((JLabel) component).setIcon(new ImageIcon(allImgs.get(component.hashCode())));
					alreadyInPerspective = false;
					computedImg = null;
				}
			}

			component.repaint();

		}

		
		
		/* check if imgBounds overlaps any user defined perspective plane */
		private boolean checkOverlap(Rectangle r1, List<PerspectivePlane> pplanes) {

			for (PerspectivePlane pp : pplanes) {
				if (r1.intersects(pp.getArea())) {
					overlappedPersPlane = pp;
					return true;
				}
			}

			return false;
		}

	}

	
	
	@Override
	public void run() {
		computedImg = VanishingPointFilter.Pseudo3D.computeImage(targetCopy, overlappedPersPlane.p0,
				overlappedPersPlane.p1, overlappedPersPlane.p2, overlappedPersPlane.p3);

		System.out.println("Computing image");
		return;
	}
	
	

	
	/**
	 * Resizable Image Panel which can only contain images 
	 * and can be resized using 8 points given alongside of the panel.
	 * */
	private class ResizableImagePanel extends JPanel {
		private static final long serialVersionUID = 1L;

		private BufferedImage img;
		private BufferedImage imgCopy;

		public Point leftTop;
		public Point leftCenter;
		public Point leftBottom;
		public Point centerTop;
		public Point centerBottom;
		public Point rightTop;
		public Point rightCenter;
		public Point rightBottom;

		public Point draggedPoint = null;

		private final int POINT_WIDTH = 10;
		private final int POINT_HEIGHT = 10;

		private boolean init = true;
		private Point offset = null;
		
	    private AffineTransform coordTransform = new AffineTransform();

		public ResizableImagePanel(BufferedImage image) {
			setBorder(BorderFactory.createDashedBorder(Color.black, 4, 2));
			this.img = image;

			var w = img.getWidth(null);
			var h = img.getHeight(null);

			leftTop = new Point(0, 0);
			leftCenter = new Point(0, h / 2);
			leftBottom = new Point(0, h);
			centerTop = new Point(w / 2, 0);
			centerBottom = new Point(w / 2, h);
			rightTop = new Point(w, 0);
			rightCenter = new Point(w, h / 2);
			rightBottom = new Point(w, h);
			
			

			addMouseListener(new MouseAdapter() {

				@Override
				public void mousePressed(MouseEvent e) {
					super.mousePressed(e);

					var p = e.getPoint();
					var r = 8;

					offset = e.getPoint();

					if (p.distance(leftTop) < r)
						draggedPoint = leftTop;
					if (p.distance(leftCenter) < r)
						draggedPoint = leftCenter;
					if (p.distance(leftBottom) < r)
						draggedPoint = leftBottom;
					if (p.distance(centerTop) < r)
						draggedPoint = centerTop;
					if (p.distance(centerBottom) < r)
						draggedPoint = centerBottom;
					if (p.distance(rightTop) < r)
						draggedPoint = rightTop;
					if (p.distance(rightCenter) < r)
						draggedPoint = rightCenter;
					if (p.distance(rightBottom) < r)
						draggedPoint = rightBottom;
				}

			});

			
			
			addMouseMotionListener(new MouseAdapter() {

				@Override
				public void mouseDragged(MouseEvent e) {
					super.mouseDragged(e);

					if (draggedPoint != null) {
						draggedPoint.setLocation(e.getPoint());
					}

					var cursor = new Cursor(Cursor.MOVE_CURSOR);
					e.getComponent().setCursor(cursor);

					int x = e.getPoint().x - offset.x;
					int y = e.getPoint().y - offset.y;
					Component component = e.getComponent();
					Point location = component.getLocation();
					location.x += x;
					location.y += y;
					component.setLocation(location);
					imgBounds = component.getBounds();

					computeSqToQuadImage(component);
				}

				
				
				@Override
				public void mouseMoved(MouseEvent e) {
					super.mouseMoved(e);

					var cursor = new Cursor(Cursor.MOVE_CURSOR);
					e.getComponent().setCursor(cursor);
				}

			});
		}

		
		
		@Override
		public void invalidate() {
			super.invalidate();

			var width = getWidth();
			var height = getHeight();

			if (width > 0 && height > 0) {
				imgCopy = img;// new ResizeImage(img).getImageSizeOf(width, height);
			}
		}

		
		
		@Override
		public void paintComponent(Graphics g) {
			super.paintComponent(g);

			if(!alreadyInPerspective) {
				g.setColor(Color.black);
				g.drawImage(getImage(), 0, 0, null);
			}
			else {
				Graphics2D g2 = (Graphics2D) g;
		        int x = (int) (this.getWidth() - (img.getWidth() * .2)) / 2;
		        int y = (int) (this.getHeight() - (img.getHeight() * .2)) / 2;
		        
		        x = 0;
		        y = 0;

		        AffineTransform at = new AffineTransform();
		        at.translate(x, y);
		        at.scale(1, 1);
		        if (init) {
		            g2.setTransform(at);
		            init = false;
		            coordTransform = g2.getTransform();
		        } else {
		            g2.setTransform(coordTransform);
		        }

		        g2.drawImage(img, 0, 0, this);

		        g2.dispose();
			}

			g.fillOval(leftTop.x - 5, leftTop.y - 5, this.POINT_WIDTH, this.POINT_HEIGHT);
			g.fillOval(leftCenter.x - 5, leftCenter.y - 5, this.POINT_WIDTH, this.POINT_HEIGHT);
			g.fillOval(leftBottom.x - 5, leftBottom.y - 5, this.POINT_WIDTH, this.POINT_HEIGHT);
			g.fillOval(centerTop.x - 5, centerTop.y - 5, this.POINT_WIDTH, this.POINT_HEIGHT);
			g.fillOval(centerBottom.x - 5, centerBottom.y - 5, this.POINT_WIDTH, this.POINT_HEIGHT);
			g.fillOval(rightTop.x - 5, rightTop.y - 5, this.POINT_WIDTH, this.POINT_HEIGHT);
			g.fillOval(rightCenter.x - 5, rightCenter.y - 5, this.POINT_WIDTH, this.POINT_HEIGHT);
			g.fillOval(rightBottom.x - 5, rightBottom.y - 5, this.POINT_WIDTH, this.POINT_HEIGHT);
		}
		
		
		
		private void computeSqToQuadImage(Component component) {

			target = img;

			if (checkOverlap(imgBounds, persPlanes)) {

				targetCopy = Utils.getImageCopy(target);

				if (!alreadyInPerspective) {

					try {
						computingThread.start();
					} catch (Exception ex) {
						
						ex.printStackTrace();
						
						if (!Thread.currentThread().isAlive())
							computingThread.start();
					}

					alreadyInPerspective = true;

				}

				if (computedImg != null) {
					setImage(computedImg);
				}

			} else {
				setImage(target);
				alreadyInPerspective = false;
				computedImg = null;
			}

			component.repaint();
		}

		
		
		/* check if imgBounds overlaps any user defined perspective plane */
		private boolean checkOverlap(Rectangle r1, List<PerspectivePlane> pplanes) {

			for (PerspectivePlane pp : pplanes) {
				if (r1.intersects(pp.getArea())) {
					overlappedPersPlane = pp;
					return true;
				}
			}

			return false;
		}

		
		
		private BufferedImage getImage() {
			return imgCopy;
		}

		
		
		private void setImage(BufferedImage img) {
			this.imgCopy = img;
		}

		
		
		@Override
		public Dimension getPreferredSize() {
			return img == null ? new Dimension(200, 200) : new Dimension(img.getWidth(this), img.getHeight(this));
		}

	}

}
