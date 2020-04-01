package org.lemon.frames;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.swing.BorderFactory;
import javax.swing.JInternalFrame;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.border.Border;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;

public class NewImagePanel extends JPanel{

	private static final long serialVersionUID = 1L;
	
	//scroll bar
	@SuppressWarnings("unused")
	private JSlider scrlBar = new JSlider(JSlider.HORIZONTAL);
	
	public NewImagePanel(BufferedImage file) throws IOException {
		
		//layout for panel
		//BoxLayout layout = new BoxLayout(this, BoxLayout.Y_AXIS);
        
		//border for panel
		Border layoutBorder = BorderFactory.createLineBorder(Color.BLACK, 2);
		
		setLayout(new BorderLayout());
		setBorder(layoutBorder);
		
		JInternalFrame iframe = new JInternalFrame();
		iframe.setVisible(true);
		iframe.setSize(500, 500);
		iframe.setLayout(new BorderLayout());
		iframe.add(new ImagePanel(file), BorderLayout.CENTER);
		iframe.setClosable(true);
		iframe.addInternalFrameListener(new ImagePanelWindowListener(file));
		add(iframe, BorderLayout.CENTER);
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


