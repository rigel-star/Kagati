package application;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JDesktopPane;

import org.lemon.image.ImageView;
import org.lemon.utils.ConnectionManager;

/**
 * Main background pane of application, which handles editing and drawing panels in application.
 * */
public class MainBackgroundPane extends JDesktopPane implements ComponentListener {
	private static final long serialVersionUID = 1L;
	
	/*storing pairs*/
	private List<ConnectionManager.Pair> connections = new ArrayList<>();

	public MainBackgroundPane() {
		this(null, null);
	}
	
	public MainBackgroundPane(BufferedImage img, String title) {
		
		setSize(300, 300);
		setBackground(Color.gray);
		setVisible(true);
		addComponentListener(this);
		
	}
	
	
	/**
	 * Make connection between pair of nodes.
	 * This method used to draw line or curve between two connected nodes.
	 * <p>
	 * @param pair of nodes
	 * */
	public void makeConnection(ConnectionManager.Pair pair) {
		connections.add(pair);
		new ConnectionManager(this, connections);
	}
	
	public void refreshConnections() {
		System.out.println("Refreshing");
	}
	
	/**
	 * Refresh's all the available ImageViews.
	 * */
	public void refresh() {
		
		List<ImageView> views = new ArrayList<ImageView>();
		List<String> titles = new ArrayList<String>();
		
		for(Component c: getComponents()) {
			if(c instanceof ImageView) {
				views.add((ImageView) c);
				titles.add(((ImageView)c).getTitle());
			}
		}
		
		for(ImageView v: views) {
			v.setConOptions(titles, views);
		}
		
	}

	@Override
	public void componentResized(ComponentEvent e) {
		
	}

	@Override
	public void componentMoved(ComponentEvent e) {
		refreshConnections();
	}

	@Override
	public void componentShown(ComponentEvent e) {
		
	}

	@Override
	public void componentHidden(ComponentEvent e) {
		
	}
	
	
}
