package org.lemon.gui.menus;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenu;
import javax.swing.JMenuItem;

import org.lemon.gui.ApplicationFrame;
import org.lemon.gui.ImageView;
import org.lemon.gui.ImageViewSetup;
import org.lemon.gui.image.ChooseImage;
import org.lemon.gui.image.PanelMode;
import org.lemon.gui.layers.Layer;

/**
 * 
 * Contains commands for opening and saving images and creating new canvas.
 * 
 * */
public class FileMenu extends JMenu implements ActionListener {
	private static final long serialVersionUID = 1L;
	
	
	private JMenuItem openFile, saveFile, newFile;
	private ApplicationFrame frame;
	
	public FileMenu( ApplicationFrame frame ) {
		
		this.frame = frame;
		
		setText( "File" );
		init();
		addAll();
		actionEvents();
		
	}
	
	
	/**
	 * 
	 * init every component of this menu
	 * 
	 * */
	private void init() {
		this.newFile = new JMenuItem( "New..." );
		this.openFile = new JMenuItem( "Open..." );
		this.saveFile = new JMenuItem( "Save..." );
	}
	
	
	/**
	 * 
	 * add every menu item to this menu
	 * 
	 * */
	private void addAll() {
		add( newFile );
		add( openFile );
		add( saveFile );
	}

	
	/**
	 * 
	 * init action event for every component
	 * 
	 * */
	private void actionEvents() {
		newFile.addActionListener( this );
		openFile.addActionListener( this );
		saveFile.addActionListener( this );
	}
	

	@Override
	public void actionPerformed( ActionEvent e ) {
		
		if( e.getSource() == newFile )
			newCanvas();
		
		else if( e.getSource() == openFile )
			open();
	}
	
	
	/**
	 * 
	 * Create {@code CanvasView}.
	 * 
	 * */
	private void newCanvas() {
		new ImageViewSetup( frame.getWorkspace() );
	}
	
	
	/**
	 * 
	 * Open image from user computer.
	 * 
	 * */
	private void open() {
		var imgChoose = new ChooseImage();
		var img = imgChoose.getChoosenImage();
		var title = imgChoose.getChoosenFile().getName();
		var imgView = new ImageView(img, title, true, PanelMode.SNAP_MODE);

		frame.getWorkspace().add(imgView);
		frame.getWorkspace().fetchNodes();
		frame.getWorkspace().refresh();
		frame.getWorkspace().revalidate();
		frame.getImageStorage().put(imgView, img);
		frame.getLayerContainer().addLayer(new Layer(imgView, imgView.getTitle(), Layer.NORMAL_LAYER));
	}
	
}
