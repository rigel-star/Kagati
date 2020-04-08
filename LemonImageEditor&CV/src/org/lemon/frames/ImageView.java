package org.lemon.frames;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.swing.BorderFactory;
import javax.swing.JInternalFrame;
import javax.swing.border.Border;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;

public class ImageView extends JInternalFrame {

	private static final long serialVersionUID = 1L;
	
	//closable window or not
	private boolean close = false;
	
	public ImageView(BufferedImage img, boolean closeable) throws IOException {
		this(img, null);
		this.close = closeable;
		this.setClosable(this.close);
	}
	
	public ImageView(BufferedImage img) throws IOException {
		this(img, null);
	}
	
	public ImageView(BufferedImage file, String title) throws IOException {
		
		//layout for panel
		//BoxLayout layout = new BoxLayout(this, BoxLayout.Y_AXIS);
        
		//border for panel
		Border layoutBorder = BorderFactory.createLineBorder(Color.BLACK, 2);
		
		setLayout(new BorderLayout());
		setBorder(layoutBorder);
		setTitle(title);
		setVisible(true);
		setSize(new Dimension(file.getWidth(), file.getHeight()));
		setLayout(new BorderLayout());
		add(new ImagePanel(file), BorderLayout.CENTER);
		addInternalFrameListener(new ImagePanelWindowListener(file));
	}

	//helper class for jinternalframe
	class ImagePanelWindowListener extends InternalFrameAdapter {
		
		BufferedImage img;
		
		public ImagePanelWindowListener(BufferedImage img) {
			this.img = img;
		}
		
		@Override
		public void internalFrameClosing(InternalFrameEvent e) {
			super.internalFrameClosing(e);
			this.img = null;
			
		}
	}
	
}


