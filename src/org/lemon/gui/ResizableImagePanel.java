package org.lemon.gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;


public class ResizableImagePanel extends JPanel {
	private static final long serialVersionUID = 1L;
	
	
	public static void main(String[] args) {
		
		BufferedImage image = null;
		
		try {
			image = ImageIO.read(new File("C:\\Users\\Ramesh\\Desktop\\opencv\\fruit.png"));
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		
		var f = new JFrame("Frame");
		f.setSize(600, 600);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setResizable(false);
		f.getContentPane().setLayout(new FlowLayout(FlowLayout.LEFT));
		f.getContentPane().add(new ResizableImagePanel(image));
		f.setVisible(true);
	}

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

	private Point offset = null;


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

		Graphics2D g2 = (Graphics2D) g;
		g2.drawImage(img, 0, 0, null);
	}

	
	public BufferedImage getImage() {
		return imgCopy;
	}

	
	public void setImage(BufferedImage img) {
		this.imgCopy = img;
	}

	
	@Override
	public Dimension getPreferredSize() {
		return img == null ? new Dimension(200, 200) : new Dimension(img.getWidth(this), img.getHeight(this));
	}

}
