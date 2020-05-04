package org.lemon.gui.image;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFileChooser;

public class ChooseImage {

	
	private File file;
	private BufferedImage img = null;
	
	public ChooseImage() {
		JFileChooser fileC = new JFileChooser();
		
		int result = fileC.showOpenDialog(null);
        if (result == JFileChooser.APPROVE_OPTION) {
            file = fileC.getSelectedFile();
        }
		
	}
	
	public BufferedImage getChoosenImage() {
		try {
			this.img = ImageIO.read(this.file);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return this.img;
	}
	
	public File getChoosenFile() {
		return this.file;
	}

}
