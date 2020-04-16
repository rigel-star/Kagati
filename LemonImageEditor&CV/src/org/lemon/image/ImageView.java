package org.lemon.image;

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
	
	/**Note image can't be null and canvas is false by default*/
	/*if only image provided, set title null and closeable false*/
	public ImageView(BufferedImage img) throws IOException {
		this(img, null, false, false);
	}
	
	/*if image and title provided, set closeable false*/
	public ImageView(BufferedImage img, String title) throws IOException {
		this(img, title, false, false);
	}
	
	/*if image and closeable state provided, set title null*/
	public ImageView(BufferedImage img, boolean closeable) throws IOException {
		this(img, null, closeable, false);
	}
	
	/*if image, title and closeable state provided*/
	public ImageView(BufferedImage img, String title, boolean closeable) throws IOException {
		this(img, title, closeable, false);
	}
	
	/*if panelState provided*/
	public ImageView(BufferedImage img, String title, boolean closeable, boolean canvas) throws IOException {
		this(img, title, closeable, canvas, ImagePanel.STATE_DEFAULT);
	}
	
	/*if image, title and imageState provided*/
	public ImageView(BufferedImage img, String title, boolean closeable, int panelState) throws IOException {
		this(img, title, closeable, false, panelState);
	}
	
	/*if every param provided, simply apply*/
	public ImageView(BufferedImage img, String title, boolean closeable, boolean canvas, int panelState) throws IOException {
        
		//border for panel
		Border layoutBorder = BorderFactory.createLineBorder(Color.BLACK, 2);
		
		setLayout(new BorderLayout());
		setBorder(layoutBorder);
		setTitle(title);
		setClosable(closeable);
		setVisible(true);
		setSize(new Dimension(img.getWidth(), img.getHeight()));
		setLayout(new BorderLayout());
		add(new ImagePanel(img, canvas, panelState), BorderLayout.CENTER);
		//addInternalFrameListener(new ImagePanelWindowListener(img));
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


