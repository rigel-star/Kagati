package org.lemon.frames;


import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.border.Border;

public class FilterPanel extends JPanel {

	//version id
	private static final long serialVersionUID = 1L;

	JButton btnColorRemover  = new JButton();
	JButton btnMix2imgs = new JButton();
	
	public FilterPanel() throws IOException {
		
		//color remover
		BufferedImage btnRemoverIcon = ImageIO.read(new File("icons\\eraserIcon.png"));
		btnColorRemover.setIcon(new ImageIcon(btnRemoverIcon));
		
		//img mix
		BufferedImage btnMixIcon = ImageIO.read(new File("icons\\mixImgsIcon.png"));
		btnMix2imgs.setIcon(new ImageIcon(btnMixIcon));
		
		//layout for panel
		BoxLayout layout = new BoxLayout(this, BoxLayout.Y_AXIS);
        
		//border for panel
		Border layoutBorder = BorderFactory.createLineBorder(Color.BLACK, 2);
		
        setLayout(layout);
        setBorder(layoutBorder);
        
        add(btnColorRemover);
        add(btnMix2imgs);
	}
}
