package application;

import java.awt.Color;
import java.awt.Component;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JDesktopPane;

import org.lemon.image.ImageView;

/**
 * Main background pane of application, which handles editing and drawing panels in application.
 * */
public class MainBackgroundPane extends JDesktopPane {
	private static final long serialVersionUID = 1L;

	public MainBackgroundPane() {
		this(null, null);
	}
	
	public MainBackgroundPane(BufferedImage img, String title) {
		
		setSize(300, 300);
		setBackground(Color.gray);
		setVisible(true);
		
	}
	
	/**
	 * Refresh's all the available ImageViews.
	 * */
	public void refresh() {
		
		List<ImageView> view = new ArrayList<ImageView>();
		List<String> titles = new ArrayList<String>();
		
		for(Component c: getComponents()) {
			if(c instanceof ImageView) {
				view.add((ImageView) c);
				titles.add(((ImageView)c).getTitle());
			}
		}
		
		for(ImageView v: view) {
			v.setConOptions(titles);
		}
		
	}
	
}
