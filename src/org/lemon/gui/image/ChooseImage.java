package org.lemon.gui.image;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFileChooser;

public class ChooseImage {
	private File chosenFile;
	
	public ChooseImage() {
		JFileChooser fileChooser = new JFileChooser();
		int result = fileChooser.showOpenDialog(null);
        if(result == JFileChooser.APPROVE_OPTION) {
            chosenFile = fileChooser.getSelectedFile();
        }
	}
	
	public BufferedImage getChoosenImage() {
		try {
			return ImageIO.read(this.chosenFile);
		} 
		catch(IOException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public File getChoosenFile() {
		return this.chosenFile;
	}
}