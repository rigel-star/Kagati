package org.lemon.gui.toolbar;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JToolBar;
import org.lemon.gui.ImageView;
import org.lemon.gui.ImageViewSetup;
import org.lemon.gui.Layer;
import org.lemon.gui.LayerContainer;
import org.lemon.gui.WorkspaceArena;
import org.lemon.gui.image.ChooseImage;
import org.lemon.gui.image.ImagePanel;
import org.lemon.gui.layer.ViewLayer;
import org.lemon.tools.BrushTool;
import org.lemon.tools.brush.BrushToolListener;

public class FileToolbar extends JToolBar {
	private static final long serialVersionUID = 1L;
	
	private JButton openBttn = null;
	private JButton saveBttn = null;
	private JButton newBttn = null;
	
	private WorkspaceArena workspace = null;
	private LayerContainer layerContainer = null;
	
	public FileToolbar(final WorkspaceArena wk, final LayerContainer layerContainer) {
		this.workspace = wk;
		this.layerContainer = layerContainer;
		
		ActionListener al = new ActionHandler();
		Color cl = new Color( 50, 50, 50 );
		
		openBttn = new JButton( "Open" );
		openBttn.setIcon( new ImageIcon( "icons/button/open.png" ));
		openBttn.setBackground( cl );
		openBttn.addActionListener( al );
		
		saveBttn = new JButton( "Save" );
		saveBttn.setIcon( new ImageIcon( "icons/button/save.png" ));
		saveBttn.setBackground( cl );
		saveBttn.addActionListener( al );
		
		newBttn = new JButton( "New Page" );
		newBttn.setIcon( new ImageIcon( "icons/button/new.png" ));
		newBttn.setBackground( cl );
		newBttn.addActionListener( al );
		
		JPanel bttnPanel = new JPanel();
		bttnPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 2, 2));
		
		bttnPanel.add(openBttn);
		bttnPanel.add(saveBttn);
		bttnPanel.add(newBttn);
		
		add(bttnPanel);
	}
	
	private class ActionHandler implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == openBttn) {
				actionOpenFile();
			}
			else if (e.getSource() == saveBttn) {
				actionSaveFile();
			}
			else if (e.getSource() == newBttn) {
				actionNewFile();
			}
		}
		
		private void actionOpenFile() {
			ChooseImage imgChoose = new ChooseImage();
			BufferedImage img = imgChoose.getChoosenImage();
			
			String title = imgChoose.getChoosenFile().getName();
			ImageView imageView = new ImageView(img, title, layerContainer);
			ImagePanel panel = imageView.getImagePanel();
			
			switch(workspace.getGlobalLemonTool())
			{
				case BRUSH:
					new BrushToolListener(panel, new BrushTool.Builder(imageView.getDrawable(), BrushTool.BrushType.NORMAL).build());
					break;
					
				case HAND:
					break;
					
				case SELECT:
					break;
					
				case CROP:
					break;
			}
			
			workspace.add(imageView);
			workspace.revalidate();
			
			Layer ly = new ViewLayer(imageView, imageView.getTitle());
			layerContainer.addLayer(ly);
		}
		
		private void actionSaveFile() {
			
		}
		
		private void actionNewFile() {
			new ImageViewSetup(workspace, layerContainer);
		}
	}
}
