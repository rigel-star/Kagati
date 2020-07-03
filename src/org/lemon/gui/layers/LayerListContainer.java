package org.lemon.gui.layers;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

import org.lemon.gui.image.ImageView;

public class LayerListContainer extends JPanel{
	private static final long serialVersionUID = 1L;
	

	public LayerListContainer() {
		
		BufferedImage img = null, img2 = null;
		ImageView view = null, view2 = null;
		
		try {
			img = ImageIO.read(new FileInputStream(new File("C:\\Users\\Ramesh\\Desktop\\opencv\\fruit.png")));
			img2 = ImageIO.read(new FileInputStream(new File("C:\\Users\\Ramesh\\Desktop\\opencv\\mack.jpg")));
			view = new ImageView(img);
			view2 = new ImageView(img2);
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		
		LayerList list = new LayerList();
		list.add(new Layer(view));
		list.add(new Layer(view2));
		
		add(list);
		
		var frame = new JFrame();
		frame.getContentPane().add(this);
		frame.setResizable(false);
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
	
	
	public static void main(String[] args) {
		new LayerListContainer();
	}
}
