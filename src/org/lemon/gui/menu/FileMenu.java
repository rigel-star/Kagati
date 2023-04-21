package org.lemon.gui.menu;

import java.awt.Composite;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

import javax.swing.JMenu;
import javax.swing.JMenuItem;

import org.lemon.composite.AddComposite;
import org.lemon.filter.CompositeFilter;
import org.lemon.filter.ResizeImageFilter;
import org.lemon.graphics.Texture;
import org.lemon.gui.ImageView;
import org.lemon.gui.ImageViewSetup;
import org.lemon.gui.WorkspaceArena;
import org.lemon.gui.image.ChooseImage;
import org.lemon.image.LImage;

public class FileMenu extends JMenu implements ActionListener {
	private static final long serialVersionUID = 1L;
	
	private JMenuItem openFile, saveFile, newFile;
	
	private WorkspaceArena wks;
	
	public FileMenu(final WorkspaceArena workspace) {
		this.wks = workspace;
		setText("File");
		
		this.newFile = new JMenuItem("New..." );
		this.openFile = new JMenuItem("Open..." );
		this.saveFile = new JMenuItem("Save..." );
		
		add(newFile);
		add(openFile);
		add(saveFile);
		
		newFile.addActionListener(this);
		openFile.addActionListener(this);
		saveFile.addActionListener(this);
	}

	@Override
	public void actionPerformed( ActionEvent e ) {
		if( e.getSource() == newFile )
			newFile();
		else if( e.getSource() == openFile )
			openFile();
	}
	
	private void newFile() 
	{
		new ImageViewSetup(wks);
	}
	
	private void openFile() 
	{
		ChooseImage imgChoose = new ChooseImage();
		BufferedImage img = imgChoose.getChoosenImage();
		Composite cc = new AddComposite(.5f);
		CompositeFilter cf = new CompositeFilter(cc);
		LImage out = cf.compose(new ResizeImageFilter(img.getWidth(), img.getHeight()).filter(Texture.WOOD.getDrawable()), new LImage(img));
		img = out.getAsBufferedImage();
		ImageView imgView = new ImageView(img, imgChoose.getChoosenFile().getName());
		wks.addView(imgView);
	}
}