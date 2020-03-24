package org.lemon.frames;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.swing.BorderFactory;
import javax.swing.JInternalFrame;
import javax.swing.JPanel;
import javax.swing.border.Border;

public class NewImagePanel extends JPanel{

	private static final long serialVersionUID = 1L;
	
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
		
		add(iframe, BorderLayout.CENTER);
	}

	
}
