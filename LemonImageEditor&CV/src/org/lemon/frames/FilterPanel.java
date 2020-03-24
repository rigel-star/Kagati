package org.lemon.frames;


import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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

import org.lemon.colors.ColorRemover;
import org.lemon.frames.alert_dialogs.RemoveColorDialog;

public class FilterPanel extends JPanel {

	//version id
	private static final long serialVersionUID = 1L;

	JButton btnColorRemover  = new JButton();
	JButton btnMix2imgs = new JButton();
	
	public FilterPanel() {
	}
	
	private FilterPanel current = this;
	
	public FilterPanel(BufferedImage img) throws IOException {
		
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
        
        btnColorRemover.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				if(img == null)
					return;
				
				RemoveColorDialog rcd = new RemoveColorDialog(current);
				Color col = rcd.getPreferredColor();
				new ColorRemover(img, col);
				revalidate();
			}
		});
	}
}
