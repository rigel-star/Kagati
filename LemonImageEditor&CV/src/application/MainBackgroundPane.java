package application;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.swing.JDesktopPane;

import org.lemon.drawing.DrawingPanel;
import org.lemon.frames.ImageView;


/*
 * Main background pane of application, which handles editing and drawing panels in application.
 * */
public class MainBackgroundPane extends JDesktopPane {

	private static final long serialVersionUID = 1L;

	public MainBackgroundPane(BufferedImage img, String title) {
		
		setSize(300, 300);
		setBackground(Color.white);
		setVisible(true);
		
		ImageView p = null;
		try {
			p = new ImageView(img, title);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		DrawingPanel d = new DrawingPanel(new Color(255, 0, 0), 350, 350);
		add(p);
		add(d);
		
//		JInternalFrame f = new JInternalFrame("Draggable");
//		f.setSize(500, 500);
//		f.setClosable(true);
//		f.setVisible(true);
//		
//		add(f);
	}
}
