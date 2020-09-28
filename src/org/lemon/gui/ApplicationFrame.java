package org.lemon.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JToolBar;

import org.lemon.filter.basic.GrayScale;
import org.lemon.filter.basic.SharpImage;
import org.lemon.filter.basic.SobelEdge;
import org.lemon.filter.gui.VanishingPointFilterGUI;
import org.lemon.gui.dialogs.ColorReplaceDialog;
import org.lemon.gui.dialogs.DenoiseImageDialog;
import org.lemon.gui.dialogs.NegativeImageDialog;
import org.lemon.gui.dialogs.PixelateImageDialog;
import org.lemon.gui.image.ImageInfoPanel;
import org.lemon.gui.layers.NodeLayer;
import org.lemon.gui.layers.ViewLayer;
import org.lemon.gui.image.PanelMode;
import org.lemon.gui.menus.NodeMenu;
import org.lemon.gui.node.BlurFilterNode;
import org.lemon.gui.menus.FileMenu;
import org.lemon.gui.menus.Menu3D;
import org.lemon.gui.panels.ImageAnalyzePanel;
import org.lemon.gui.panels.ToolInfoPanel;
import org.lemon.gui.toolbars.SaveChangesToolBar;
import org.lemon.tools.LemonTool;
import org.lemon.gui.panels.LemonToolPanel;

import org.piksel.piksel.PPInternalWindow;


public class ApplicationFrame extends JFrame implements ActionListener {
	private static final long serialVersionUID = 1L;
	
	
	private JMenu fileMenu, editMenu, threeDMenu;
	
	
	//menus and submenus
	private JMenu 				filter, extras, fileSubMenu, noiseSubMenu;
	
	//filters
	private JMenuItem 			grayScale, sobelEdge,
								sharpImg, pixelateImg, cropImg,
								invertImg, denoiseImg, colorRange, plainDrawingPage, pixelDrawingPage, vanishingPoint;
	
	
	//blend modes
	//To read more about blend mode go to text_files -> BlendModes.txt
	//or wikipedia page https://en.wikipedia.org/wiki/Blend_Modes
	private JMenu 				blendModes;
	private JMenuItem 			multiplyBmode, addBmode, subtractBmode;
	
	//main menu bar
	private JMenuBar 			menuBar;
	
	//middle panel where drawing and image panel will be added.
	//central panel
	private JPanel 				editingPanel = new JPanel();
	
	//test img
	private BufferedImage 		choosenImage = null;
	private String 				choosenImgName = null;
	
	//default filter panel
	private ImageAnalyzePanel 			analyzeMenu;
	
	//main editing panel
	private Workspace 				mainWorkspace;
	
	
	//image controllers and analyze panels
	private ImageInfoPanel 				imgInfoPanel;
	private ImageView 					imageView;
	private BlurFilterNode blurNode 	= new BlurFilterNode();
	
	/*All tools container*/
	private LemonToolPanel 				mainToolPanel;
	
	
	/*current tool*/
	private JPanel toolBarsContainer = new JPanel();
	private SaveChangesToolBar saveChngsToolBar = new SaveChangesToolBar();
	
	
	/**
	 * Contains every layer that is created in application.
	 * */
	private LayerContainer layerContainer = null;
	
	/*
	 * Map<ImageView, BufferedImage> selectedImgsStorage = new HashMap<>();
	 * Stores currently opened images of mainPanel with their corresponding ImageView object.
	 * When applying any filter, program needs to know which image is selected currently.
	 * */
	private Map<ImageView, BufferedImage> selectedImgsStorage = new HashMap<>();
	
	
	
	public ApplicationFrame() throws IOException {
		
		/*important panels in app*/
		this.mainWorkspace = new Workspace();
		this.mainToolPanel = new LemonToolPanel(this);
		this.layerContainer = new LayerContainer(mainWorkspace);
		
		fileMenu = new FileMenu(this);
		editMenu = new NodeMenu(mainWorkspace);
		threeDMenu = new Menu3D(this);
		
		File f = new File("C:\\Users\\Ramesh\\Documents\\3D Images\\dog2.jpg");
		//test images: dog.jpg, flow.jpg, (color) mack.jpg
		this.choosenImage = ImageIO.read(f);
		this.choosenImgName = f.getName();
		
		toolBarsContainer.setLayout( new FlowLayout( FlowLayout.TRAILING ));
		toolBarsContainer.add( saveChngsToolBar );
		
		this.imageView = new ImageView( choosenImage, choosenImgName, true, PanelMode.CANVAS_MODE );
		
		this.imgInfoPanel = new ImageInfoPanel( choosenImage );
		
		//layers
		layerContainer.addLayer( new ViewLayer( imageView, imageView.getTitle() ) );
		layerContainer.addLayer( new NodeLayer( blurNode, new ImageIcon( "icons/layer/blur.png" ), "Blur" ));
		layerContainer.addLayer( new NodeLayer( blurNode, new ImageIcon( "icons/layer/texture.png" ), "Texture" ));
		layerContainer.addLayer( new NodeLayer( blurNode, new ImageIcon( "icons/layer/adjust.png" ), "HSB Adjust" ));
		
		//analyze panel properties
		analyzeMenu = new ImageAnalyzePanel();
		
		//main sub menus
		fileSubMenu = new JMenu("New");
		noiseSubMenu = new JMenu("Noise");
		
		//different blend modes
		blendModes = new JMenu("Blend");
		initBlendModes();
		
		//main menu items
		filter = new JMenu("Filter");
		extras = new JMenu("Utils");
		
		//file properties
		plainDrawingPage = new JMenuItem("New Page");
		pixelDrawingPage = new JMenuItem("New Pixel Page");

		//filter properties
		grayScale = new JMenuItem("B&W");
		vanishingPoint = new JMenuItem("Vanishing Point");
		sobelEdge = new JMenuItem("Find Edges");
		sharpImg = new JMenuItem("Sharp");
		pixelateImg = new JMenuItem("Pixelate");
		invertImg = new JMenuItem("Invert");
		
		//edit properties
		cropImg = new JMenuItem("Crop");
		denoiseImg = new JMenuItem("Denoise");
		colorRange = new JMenuItem("Selective Color");
		
		//main menu bar
		menuBar = new JMenuBar();
		
		//file sub menu
		fileSubMenu.add(plainDrawingPage);
		fileSubMenu.add(pixelDrawingPage);
		//noise sub menu
		noiseSubMenu.add(denoiseImg);
		//file options
		//file
		fileMenu.add(fileSubMenu);
		//filter options
		filter.add(grayScale);
		filter.add(sharpImg);
		filter.add(vanishingPoint);
		filter.add(pixelateImg);
		filter.add(noiseSubMenu);
		filter.add(sobelEdge);
		filter.add(invertImg);
		filter.add(blendModes);
		//filter.add(specialEffects);
		//filter.add(clipArts);
		//extras option
		extras.add(colorRange);
		//main menu options
		menuBar.add(fileMenu);
		menuBar.add(editMenu);
		menuBar.add(filter);
		menuBar.add(threeDMenu);
		menuBar.add(extras);
		
		//all buttons events are intialized in this method
		events();
		
		//screen size
		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		
		//frame style and properties
		setBackground(Color.GRAY);
		setTitle("Lemon Image Editor");
		setSize(screen.width - 50, screen.height - 50);
		setResizable(true);
		setLayout(new BorderLayout());
		setJMenuBar(menuBar);
		addPanelsToFrame();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}
	
	
	private void initBlendModes() {
		multiplyBmode = new JMenuItem("Multiply");
		addBmode = new JMenuItem("Add");
		subtractBmode = new JMenuItem("Difference");
		blendModes.add(multiplyBmode);
		blendModes.add(addBmode);
		blendModes.add(subtractBmode);
	}
	
	
	//applying click event to every JMenuItem
	private void events(){
		plainDrawingPage.addActionListener(this);
		grayScale.addActionListener(this);
		sobelEdge.addActionListener(this);
		sharpImg.addActionListener(this);
		pixelateImg.addActionListener(this);
		cropImg.addActionListener(this);
		invertImg.addActionListener(this);
		denoiseImg.addActionListener(this);
		colorRange.addActionListener(this);
		pixelDrawingPage.addActionListener(this);
		vanishingPoint.addActionListener(this);
	}
	
	
	
	//adding all required panels to frame
	private void addPanelsToFrame() throws IOException {
		Container c = this.getContentPane();
		
		/***********************************TESTING**************************************/
		this.mainWorkspace.add(this.imageView);
		this.mainWorkspace.add(this.layerContainer);
		this.selectedImgsStorage.put(this.imageView, this.choosenImage);
		this.analyzeMenu.add(this.imgInfoPanel);
		this.analyzeMenu.add(this.layerContainer);
		/*************************************************************************/
		

		c.add(this.toolBarsContainer, BorderLayout.NORTH);
		c.add(this.mainWorkspace, BorderLayout.CENTER);
		c.add(this.analyzeMenu, BorderLayout.EAST);
		c.add(this.mainToolPanel, BorderLayout.WEST);
		c.add(new ToolInfoPanel("Drag on image to draw something."), BorderLayout.SOUTH);
		
	}

	
	
	//frame action performed
	@Override
	public void actionPerformed(ActionEvent action) {
		
		if(this.choosenImage == null) {
			this.noImgSelectedDialog();
			return;
		}
		
		
		//sharping the image
		if(action.getSource() == this.sharpImg) {
			try {
				SharpImage simg = new SharpImage(this.choosenImage);
				this.editingPanel.add(new ImageView(simg.getSharpedImg(), this.choosenImgName));
				this.choosenImage = simg.getSharpedImg();
			} catch (IOException e1) {
				e1.printStackTrace();
			} finally {
				this.add(this.editingPanel, BorderLayout.CENTER);
				this.revalidate();
			}
		}

		
		//gray scaling the image
		else if(action.getSource() == this.grayScale) {
			this.editingPanel.add(new ImageView(new GrayScale(this.choosenImage).getGrayScaledImg(), this.choosenImgName));
			this.add(editingPanel, BorderLayout.CENTER);
			this.revalidate();
		}
		
		//vanishing point filter
		else if(action.getSource() == this.vanishingPoint) {
			if(this.mainWorkspace.getSelectedFrame() instanceof ImageView) {
				/*Gets the currently selected ImageView object from selectedImgsStorage 
				 * and pass its corresponding selected image*/
				new VanishingPointFilterGUI(this.selectedImgsStorage.get(this.mainWorkspace.getSelectedFrame()),
												layerContainer);
				return;
			}
		}
		
		//detecting edges of image
		else if(action.getSource() == this.sobelEdge) {
			try {
				this.editingPanel.add(new ImageView(new SobelEdge(this.choosenImage).getFinalImg(), this.choosenImgName));
				this.add(editingPanel, BorderLayout.CENTER);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		//pixelating the img
		else if(action.getSource() == this.pixelateImg) {
			//if only ImageView frame selected
			if(this.mainWorkspace.getSelectedFrame() instanceof ImageView) {
				/*Gets the currently selected ImageView object from selectedImgsStorage 
				 * and pass its corresponding selected image*/
				new PixelateImageDialog(this.selectedImgsStorage.get(this.mainWorkspace.getSelectedFrame()));
				return;
			}
		}
		
		
		else if(action.getSource() == this.invertImg) {
			new NegativeImageDialog(this.choosenImage, this.editingPanel);
		}
		
		//removing noise from image
		else if(action.getSource() == this.denoiseImg) {
			if(this.mainWorkspace.getSelectedFrame() instanceof ImageView) {
				new DenoiseImageDialog(this.imageView, this.selectedImgsStorage.get(this.mainWorkspace.getSelectedFrame()));
			}
		}
		
		//removing color of image using simple AI
		else if(action.getSource() == this.colorRange) {
			BufferedImage ii = null;
			
			try {
				ii = ImageIO.read(new File("C:\\Users\\Ramesh\\Documents\\3D Images\\pink.png"));
			} catch (IOException ex) {
				ex.printStackTrace();
			}
			if(this.mainWorkspace.getSelectedFrame() instanceof ImageView) {
				new ColorReplaceDialog(this.selectedImgsStorage.get(this.mainWorkspace.getSelectedFrame()), ii);
			}
		}
		
		
		//pixel drawing panel using PikselPainter Library
		else if(action.getSource() == this.pixelDrawingPage) {
			this.mainWorkspace.add(new PPInternalWindow(300, 300, "Pixel Drawing"));
			this.mainWorkspace.repaint();
		}
		
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
	
	
	/*if image is not selected, this dialog will pop up*/
	private void noImgSelectedDialog() {
		JOptionPane.showMessageDialog(this, "No image selected!");
	}
	
	
	/**
	 * Get {@code LayerContainer}.
	 * @return {@code LayerContainer}
	 * */
	public LayerContainer getLayerContainer() {
		return layerContainer;
	}
	
	
}
