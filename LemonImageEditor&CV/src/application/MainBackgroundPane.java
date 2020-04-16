package application;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.swing.JDesktopPane;

import org.lemon.drawing.PlainDrawingPanel;
import org.lemon.image.ImagePanel;
import org.lemon.image.ImageView;
import org.piksel.piksel.PPInternalWindow;

/**
 * Main background pane of application, which handles editing and drawing panels in application.
 * */
public class MainBackgroundPane extends JDesktopPane {

	private static final long serialVersionUID = 1L;

	public MainBackgroundPane(BufferedImage img, String title) {
		
		setSize(300, 300);
		setBackground(Color.white);
		setVisible(true);
		
		PPInternalWindow pp = new PPInternalWindow(300, 300, title);
		pp.setClosable(true);
		add(pp);
		
		ImageView v = null;
		try {
			v = new ImageView(img, title, true, ImagePanel.STATE_SNAP);
		} catch (IOException e) {
			e.printStackTrace();
		}
		add(v);
		
		PlainDrawingPanel p = new PlainDrawingPanel(null, 400, 400);
		add(p);
	}
}
