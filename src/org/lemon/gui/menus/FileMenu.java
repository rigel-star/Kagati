package org.lemon.gui.menus;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenu;
import javax.swing.JMenuItem;

import org.lemon.gui.ApplicationFrame;
import org.lemon.gui.image.ChooseImage;
import org.lemon.gui.image.ImagePanel.PanelMode;
import org.lemon.gui.image.ImageView;
import org.lemon.gui.layers.Layer;

/**
 * FileMenu is for opening, saving etc files in application, saving files etc.
 * FileMenu's container is MainApplicationFrame.
 * */
public class FileMenu extends JMenu implements ActionListener {
	private static final long serialVersionUID = 1L;
	
	
	private JMenuItem openFile, saveFile;
	private Object container;
	
	public FileMenu(Object container) {
		
		this.container = container;
		
		setText("File");
		this.init();
		this.addAll();
		this.actionEvents();
		
	}
	
	
	/*init every component of this menu*/
	private void init() {
		this.openFile = new JMenuItem("Open...");
		this.saveFile = new JMenuItem("Save...");
	}
	
	
	/*add every menu item to this menu*/
	private void addAll() {
		add(this.openFile);
		add(this.saveFile);
	}

	
	/*init action event for every component*/
	private void actionEvents() {
		this.openFile.addActionListener(this);
		this.saveFile.addActionListener(this);
	}
	

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == this.openFile) {
			this.open();
		}
	}
	
	
	/*open image*/
	private void open() {
		var imgChoose = new ChooseImage();

		var img = imgChoose.getChoosenImage();
		var title = imgChoose.getChoosenFile().getName();
		
		var imgView = new ImageView(img, null, title, true, PanelMode.snapMode);

		var frame = (ApplicationFrame) this.container;
		frame.getMainWorkspace().add(imgView);
		frame.getMainWorkspace().refresh();
		frame.getImageStorage().put(imgView, img);
		frame.getLayerContainer().addLayer(new Layer(imgView, imgView.getTitle(), Layer.NORMAL_LAYER));
	}
	
	
	
}
