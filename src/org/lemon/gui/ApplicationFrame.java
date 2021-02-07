package org.lemon.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JToolBar;

import org.lemon.gui.image.ImageInfoPanel;
import org.lemon.gui.image.ImagePanel;
import org.lemon.gui.layers.ViewLayer;
import org.lemon.gui.menus.NodeMenu;
import org.lemon.gui.menus.ToolsMenu;
import org.lemon.gui.menus.FileMenu;
import org.lemon.gui.menus.FilterMenu;
import org.lemon.gui.menus.Menu3D;
import org.lemon.gui.panels.ImageAnalyzePanel;
import org.lemon.gui.panels.ToolInfoPanel;
import org.lemon.gui.toolbars.FileToolbar;
import org.lemon.gui.toolbars.SaveChangesToolbar;
import org.lemon.tools.LemonTool;
import org.lemon.gui.panels.LemonToolPanel;

public class ApplicationFrame extends JFrame {
	
	/**
	 * Serial UID
	 * */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Main menus...
	 * */
	private JMenu fileMenu, nodeMenu, toolsMenu, threeDMenu, filterMenu;
	
	/**main menu bar*/
	private JMenuBar menuBar = null;
	
	//test img
	private BufferedImage choosenImage = null;
	private String choosenImgName = null;
	
	private ImageAnalyzePanel analyzeMenu;
	
	private Workspace mainWorkspace = null;
	
	private ImageInfoPanel imgInfoPanel = null;
	private ImageView imageView = null;
	
	/**
	 * Tools menu container.
	 * */
	private LemonToolPanel mainToolPanel;
	
	/**
	 * Current tool bar
	 * */
	private JPanel toolBarsContainer = new JPanel();
	private SaveChangesToolbar saveChngsToolBar = new SaveChangesToolbar();
	
	/**
	 * Contains every layer that is created in application.
	 * */
	private LayerContainer layerContainer = null;
	
	/**
	 * Map<ImageView, BufferedImage> selectedImgsStorage = new HashMap<>();
	 * Stores currently opened images of mainPanel with their corresponding ImageView object.
	 * When applying any filter, program needs to know which image is selected currently.
	 * */
	private Map<ImageView, BufferedImage> selectedImgsStorage = new HashMap<>();
	
	public ApplicationFrame() {
		
		init();
		
		File f = null;
		
		try {
			f = new File("C:\\Users\\Ramesh\\Documents\\3D Images\\dog2.jpg");
			choosenImage = ImageIO.read( f );
		} catch( IOException ex ) {
			ex.printStackTrace();
		}
		
		this.choosenImgName = f.getName();
		
		toolBarsContainer.setLayout( new FlowLayout( FlowLayout.LEFT ));
		toolBarsContainer.add( new FileToolbar( mainWorkspace ));
		
		this.imageView = new ImageView( choosenImage, choosenImgName, true, ImagePanel.PanelMode.CANVAS_MODE, layerContainer );
		
		this.imgInfoPanel = new ImageInfoPanel( choosenImage );
		
		//layers
		layerContainer.addLayer( new ViewLayer( imageView, imageView.getTitle() ) );
		
		analyzeMenu = new ImageAnalyzePanel();
		
		menuBar.add( fileMenu );
		menuBar.add( nodeMenu );
		menuBar.add( toolsMenu );
		menuBar.add( filterMenu );
		menuBar.add( threeDMenu );
		
		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		
		setBackground( Color.GRAY );
		setTitle( "Lemon Image Editor" );
		setSize(screen.width - 50, screen.height - 50);
		setExtendedState( MAXIMIZED_BOTH );
		setResizable( true );
		setLayout( new BorderLayout() );
		setJMenuBar( menuBar );
		
		Container c = getContentPane();
		
		/***********************************TESTING**************************************/
		mainWorkspace.add( imageView );
		selectedImgsStorage.put( imageView, choosenImage );
		
		analyzeMenu.add( imgInfoPanel );
		analyzeMenu.add( layerContainer );
		
		c.add( toolBarsContainer, BorderLayout.NORTH );
		c.add( mainWorkspace, BorderLayout.CENTER );
		c.add( analyzeMenu, BorderLayout.EAST );
		c.add( mainToolPanel, BorderLayout.WEST );
		c.add( new ToolInfoPanel( "Drag on image to draw something." ), BorderLayout.SOUTH );
		
		setDefaultCloseOperation( JFrame.DISPOSE_ON_CLOSE );
		setVisible( true );
	}
	
	/**
	 * Init the widgets.
	 **/
	private void init() {
		this.layerContainer = new LayerContainer();
		this.mainWorkspace = new Workspace( layerContainer );
		this.mainToolPanel = new LemonToolPanel( this );
		this.menuBar = new JMenuBar();
		this.fileMenu = new FileMenu( mainWorkspace, layerContainer );
		this.filterMenu = new FilterMenu( mainWorkspace );
		this.toolsMenu = new ToolsMenu( mainWorkspace );
		this.nodeMenu = new NodeMenu( mainWorkspace );
		this.threeDMenu = new Menu3D( mainWorkspace );
	}
	
	/**
	 * Get current tool which has been selected.
	 * @return {@code String} current tool
	 * */
	public LemonTool getTool() {
		return null;
	}
	
	/**
	 * Set new tool.
	 * @param newTool new tool
	 * */
	public void setTool(LemonTool newTool) {
		
	}
	
	/**
	 * Get the main workspace of application. Main workspace is handled by the main frame of application.
	 * @return mainScene {@code Workspace}
	 * */
	public Workspace getWorkspace() {
		return this.mainWorkspace;
	}
	
	/**
	 * Get main tool bar of application. Main tool bar of application is also handled by main frame.
	 * @return mainToolBar {@code MainToolBar}
	 * */
	public JToolBar getCurrentToolBar() {
		return this.saveChngsToolBar;
	}
	
	public Map<ImageView, BufferedImage> getImageStorage(){
		return this.selectedImgsStorage;
	}
	
	/**
	 * Get {@code LayerContainer}.
	 * @return {@code LayerContainer}
	 * */
	public LayerContainer getLayerContainer() {
		return layerContainer;
	}
}
