package org.lemon.image;

import java.io.File;

import javax.swing.JFileChooser;

public class ChooseImage {

	
	private File file;
	
	public ChooseImage() {
		JFileChooser fileC = new JFileChooser();
		
		int result = fileC.showOpenDialog(null);
        if (result == JFileChooser.APPROVE_OPTION) {
            file = fileC.getSelectedFile();
        }
		
	}
	
	public File getChoosenFile() {
		return file;
	}
}
