package application;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.swing.JDesktopPane;

import org.lemon.frames.NewImagePanel;


/*
 * Main background pane of application, which handles editing and drawing panels in application.
 * */
public class MainBackgroundPane extends JDesktopPane {

	private static final long serialVersionUID = 1L;

	public MainBackgroundPane(BufferedImage img, String title) {
		
		setSize(300, 300);
		setBackground(Color.white);
		setVisible(true);
		
		NewImagePanel p = null;
		try {
			p = new NewImagePanel(img, title);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		add(p);
		
//		JInternalFrame f = new JInternalFrame("Draggable");
//		f.setSize(500, 500);
//		f.setClosable(true);
//		f.setVisible(true);
//		
//		add(f);
	}
}
