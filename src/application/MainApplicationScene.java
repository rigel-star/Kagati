package application;

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

import javax.swing.JComponent;
import javax.swing.JDesktopPane;

import org.lemon.image.ImageView;
import org.lemon.math.Vec2d;
import org.lemon.utils.Pair;

/**
 * Main background pane of application, which handles editing and drawing panels in application.
 * */
public class MainApplicationScene extends JDesktopPane implements ComponentListener {
	private static final long serialVersionUID = 1L;
	

	/*Self pointing*/
	MainApplicationScene 			self = this;
	
	public MainApplicationScene() {
		
		setSize(300, 300);
		setBackground(Color.gray);
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
	public void paint(Graphics g) {
		super.paint(g);
		
		for(Component c: getComponents()) {
			if(c instanceof ImageView) {
				ImageView view = (ImageView) c;
				if(view.getConnection() != null) {
					draw(g, new Pair(view, view.getConnection()));
				}
			}
		}
		
	}
	
	
	void draw(Graphics g, org.lemon.utils.Pair p) {
		Graphics2D g2d = (Graphics2D) g;
		g2d.setPaint(Color.white);
		g2d.setStroke(new BasicStroke(3));
		
		Vec2d v1 = p.getFirstPoint();
		Vec2d v2 = p.getSecondPoint();
		JComponent start = p.getFirstComponent(), end = p.getSecondComponent();
		
		Point begin = new Point(v1.x + start.getWidth(),
				v1.y + (start.getHeight() / 2));
		Point stop = new Point(v2.x,
				v2.y + (end.getHeight() / 2));
		
		Vec2d ctrlPoint = v1.midpoint(v2);
		
		QuadCurve2D.Double curve = new QuadCurve2D.Double(begin.x, begin.y,
															ctrlPoint.x, ctrlPoint.y,
																stop.x, stop.y);
		g2d.fillOval(begin.x - 5, begin.y, 10, 10);
		g2d.fillOval(stop.x - 5, stop.y, 10, 10);
		g2d.draw(curve);
		
	}
	
	

	@Override
	public void componentResized(ComponentEvent e) {
		
	}

	@Override
	public void componentMoved(ComponentEvent e) {
		repaint();
	}

	@Override
	public void componentShown(ComponentEvent e) {
		
	}

	@Override
	public void componentHidden(ComponentEvent e) {
		
	}
	
	
}
