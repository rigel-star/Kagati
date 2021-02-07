package org.lemon.gui.toolbars;

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
import org.lemon.gui.JPlainButton;
import org.lemon.gui.Layer;
import org.lemon.gui.Workspace;
import org.lemon.gui.image.ChooseImage;
import org.lemon.gui.image.ImagePanel;
import org.lemon.gui.layers.ViewLayer;
import org.lemon.lang.NonNull;

/**
 * 
 * File handling {@code ToolBar}.
 * 
 * */
public class FileToolbar extends JToolBar {

	/**
	 * Serial UID
	 */
	private static final long serialVersionUID = 1L;
	
	private static JButton openBttn = null;
	private static JButton saveBttn = null;
	private static JButton newBttn = null;
	
	private static Workspace wks = null;
	
	/**
	 * Constructs {@link FileToolbar} with specified 
	 * {@link Workspace}.
	 * @param wk 	Main {@code Workspace}.
	 * */
	public FileToolbar( @NonNull final Workspace wk ) {
		wks = wk;
		
		init();
		
		JPanel bttnPanel = new JPanel();
		bttnPanel.setLayout( new FlowLayout( FlowLayout.LEFT, 2, 2 ));
		
		bttnPanel.add( openBttn );
		bttnPanel.add( saveBttn );
		bttnPanel.add( newBttn );
		
		add( bttnPanel );
	}
	
	/**
	 * Init the widgets.
	 * */
	private void init() {
		
		ActionListener al = new ActionHandler();
		Color cl = new Color( 50, 50, 50 );
		
		openBttn = new JPlainButton( "Open" );
		openBttn.setIcon( new ImageIcon( "icons/button/open.png" ));
		openBttn.setBackground( cl );
		openBttn.addActionListener( al );
		
		saveBttn = new JPlainButton( "Save" );
		saveBttn.setIcon( new ImageIcon( "icons/button/save.png" ));
		saveBttn.setBackground( cl );
		saveBttn.addActionListener( al );
		
		newBttn = new JPlainButton( "New Page" );
		newBttn.setIcon( new ImageIcon( "icons/button/new.png" ));
		newBttn.setBackground( cl );
		newBttn.addActionListener( al );
	}
	
	/**
	 * Action handler of {@link FileToolbar}.
	 * */
	static class ActionHandler implements ActionListener {

		@Override
		public void actionPerformed( ActionEvent e ) {
			
			if ( e.getSource() == openBttn ) {
				openFile();
			}
			else if ( e.getSource() == saveBttn ) {
				saveFile();
			}
			else if ( e.getSource() == newBttn ) {
				newFile();
			}
		}
		
		/**
		 * Open file from computer.
		 * */
		private void openFile() {
			
			ChooseImage imgChoose = new ChooseImage();
			BufferedImage img = imgChoose.getChoosenImage();
			
			String title = imgChoose.getChoosenFile().getName();
			ImageView imgView = new ImageView( img, title, true, ImagePanel.PanelMode.SNAP_MODE, wks.getLayerContainer() );
			
			wks.add( imgView );
			wks.fetchNodes();
			wks.refreshListeners();
			wks.revalidate();
			
			Layer ly = new ViewLayer( imgView, imgView.getTitle(), ViewLayer.BACKGROUND_LAYER );
			wks.getLayerContainer().addLayer( ly );
		}
		
		/**
		 * Save file in computer.
		 * */
		private void saveFile() {
			
		}
		
		/**
		 * Create new file.
		 * */
		private void newFile() {
			new ImageViewSetup( wks );
		}
	}
}
