package application;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import org.lemon.drawing.NewDrawingPanelSetup;
import org.lemon.edge.SobelEdge;
import org.lemon.filters.GrayScale;
import org.lemon.filters.RotateImg;
import org.lemon.filters.SharpImg;
import org.lemon.frames.ImageAnalyzeMenu;
import org.lemon.frames.ToolPanel;
import org.lemon.frames.alert_dialogs.BlurImageDialog;
import org.lemon.frames.alert_dialogs.ColorRemoverDialog;
import org.lemon.frames.alert_dialogs.DenoiseImageDialog;
import org.lemon.frames.alert_dialogs.CropImageDialog;
import org.lemon.frames.alert_dialogs.NegativeImageDialog;
import org.lemon.frames.alert_dialogs.PixelateImageDialog;
import org.lemon.image.ChooseImage;
import org.lemon.image.ImageInfoPanel;
import org.lemon.image.ImageOpacityController;
import org.lemon.image.ImagePanel;
import org.lemon.image.ImagePanel.PanelMode;
import org.lemon.utils.AccessoriesRemover;
import org.lemon.image.ImageView;
import org.lemon.image.ResizeImg;
import org.piksel.piksel.PPInternalWindow;

public class MainFrame extends JFrame implements ActionListener {
	private static final long serialVersionUID = 1L;
	
	//menus and submenus
	private JMenu 				file, edit, filter, extras, rotateSubMenu, fileSubMenu, noiseSubMenu;
	
	//filters
	private JMenuItem 			openImage, saveImg, grayScale, sobelEdge,
								sharpImg, blurImg, rotate180, rotate90, pixelateImg, cropImg,
								invertImg, denoiseImg, colorRemover, imgOpacity, plainDrawingPage, pixelDrawingPage;
	
	
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
	private ImageAnalyzeMenu 	analyzeMenu;
	
	//main editing panel
	private MainBackgroundPane 	mainPanel;
	
	//image panel
	//where user can edit his/her photos
	private ImageView 			impanel = null;
	
	//default color
	Color 						choosenColor = new Color(0, 255, 0);
	
	//image controllers and analyze panels
	private ImageOpacityController 	opacityPanel;
	private ImageInfoPanel 		imgInfoPanel;
	private ImageView 			imageView;
	
	/*All tools container*/
	private ToolPanel 			mainToolPanel;
	
	/*
	 * Map<ImageView, BufferedImage> selectedImgsStorage = new HashMap<>();
	 * Stores currently opened images of mainPanel with their corresponding ImageView object.
	 * When applying any filter, program needs to know which image is selected currently.
	 * */
	private Map<ImageView, BufferedImage> selectedImgsStorage = new HashMap<>();
	
	public MainFrame() throws IOException {
		
		File f = new File("C:\\Users\\Ramesh\\Documents\\3D Images\\dog2.jpg");
		//test images: dog.jpg, flow.jpg, (color) mack.jpg
		this.choosenImage = ImageIO.read(f);
		this.choosenImgName = f.getName();
		
		/*important panels in app*/
		this.mainPanel = new MainBackgroundPane();
		this.mainToolPanel = new ToolPanel(mainPanel);
		
		this.imageView = new ImageView(this.choosenImage, this.choosenImgName, true, PanelMode.SNAP_MODE);
		this.imgInfoPanel = new ImageInfoPanel(this.choosenImage);
		this.opacityPanel = new ImageOpacityController(this.choosenImage);
		
		//analyze panel properties
		analyzeMenu = new ImageAnalyzeMenu();
		
		//main sub menus
		fileSubMenu = new JMenu("New");
		rotateSubMenu = new JMenu("Rotate");
		noiseSubMenu = new JMenu("Noise");
		
		//different blend modes
		blendModes = new JMenu("Blend");
		this.initBlendModes();
		
		//main menu items
		file = new JMenu("File");
		edit = new JMenu("Edit");
		filter = new JMenu("Filter");
		extras = new JMenu("Extras");
		
		//file properties
		saveImg = new JMenuItem("Save");
		openImage = new JMenuItem("Open");
		plainDrawingPage = new JMenuItem("New Page");
		pixelDrawingPage = new JMenuItem("New Pixel Page");

		//filter properties
		grayScale = new JMenuItem("B&W");
		sobelEdge = new JMenuItem("Edge Highlight");
		sharpImg = new JMenuItem("Sharp Img");
		pixelateImg = new JMenuItem("Pixelate");
		invertImg = new JMenuItem("Invert");
		
		//edit properties
		rotate180 = new JMenuItem("Rotate 180");
		rotate90 = new JMenuItem("Rotate 90");
		cropImg = new JMenuItem("Crop");
		blurImg = new JMenuItem("Blur");
		denoiseImg = new JMenuItem("Denoise");
		colorRemover = new JMenuItem("Smart Color Remover");
		imgOpacity = new JMenuItem("Opacity");
		
		//main menu bar
		menuBar = new JMenuBar();
	
		
		//sub menus
		rotateSubMenu.add(rotate180);
		rotateSubMenu.add(rotate90);
		//file sub menu
		fileSubMenu.add(plainDrawingPage);
		fileSubMenu.add(pixelDrawingPage);
		//noise sub menu
		noiseSubMenu.add(denoiseImg);
		//file options
		//file
		file.add(fileSubMenu);
		file.add(openImage);
		file.add(saveImg);
		//edit options
		edit.add(rotateSubMenu);
		edit.add(blurImg);
		edit.add(cropImg);
		edit.add(noiseSubMenu);
		edit.add(imgOpacity);
		//filter options
		filter.add(grayScale);
		filter.add(sobelEdge);
		filter.add(sharpImg);
		filter.add(pixelateImg);
		filter.add(invertImg);
		filter.add(blendModes);
		//filter.add(specialEffects);
		//filter.add(clipArts);
		//extras option
		extras.add(colorRemover);
		//main menu options
		menuBar.add(file);
		menuBar.add(edit);
		menuBar.add(filter);
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
		addPanelsToFrame(choosenImage);
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
		openImage.addActionListener(this);
		plainDrawingPage.addActionListener(this);
		grayScale.addActionListener(this);
		rotate90.addActionListener(this);
		saveImg.addActionListener(this);
		rotate180.addActionListener(this);
		sobelEdge.addActionListener(this);
		blurImg.addActionListener(this);
		sharpImg.addActionListener(this);
		pixelateImg.addActionListener(this);
		cropImg.addActionListener(this);
		invertImg.addActionListener(this);
		denoiseImg.addActionListener(this);
		colorRemover.addActionListener(this);
		pixelDrawingPage.addActionListener(this);
	}
	
	//adding all required panels to frame
	private void addPanelsToFrame(BufferedImage bi) throws IOException {
		Container c = this.getContentPane();
		
		/***********************************TESTING**************************************/
		this.mainPanel.add(this.imageView);
		this.analyzeMenu.add(this.imgInfoPanel);
		this.analyzeMenu.add(this.opacityPanel);
		/*************************************************************************/
		
		c.add(this.mainPanel, BorderLayout.CENTER);
		c.add(this.analyzeMenu, BorderLayout.EAST);
		c.add(this.mainToolPanel, BorderLayout.WEST);
		
	}

	//frame action performed
	@Override
	public void actionPerformed(ActionEvent action) {
		
		if(this.choosenImage == null) {
			this.noImgSelectedDialog();
			return;
		}
		
		//new page in the editingPanel
		if(action.getSource() == plainDrawingPage) {
			//panel where user can draw
			new NewDrawingPanelSetup(this.mainPanel);
			this.revalidate();
		}
		
		//rotate existing image to 180 degree
		else if(action.getSource() == rotate180) {
			this.removeExistingPanel(action);
			
			try {
				this.editingPanel.add(new ImageView(new RotateImg(180, choosenImage).getRotatedImg(), this.choosenImgName));
				this.add(this.editingPanel, BorderLayout.CENTER);
				this.revalidate();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		
		//rotate existing image to 90 degree
		else if(action.getSource() == rotate90) {
			this.removeExistingPanel(action);
			try {
				this.editingPanel.add(new ImageView(new RotateImg(90, choosenImage).getRotatedImg(), this.choosenImgName));
				this.add(this.editingPanel, BorderLayout.CENTER);
				this.revalidate();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		
		//sharping the image
		else if(action.getSource() == this.sharpImg) {
			this.removeExistingPanel(action);
			try {
				SharpImg simg = new SharpImg(this.choosenImage);
				this.editingPanel.add(new ImageView(simg.getSharpedImg(), this.choosenImgName));
				this.choosenImage = simg.getSharpedImg();
			} catch (IOException e1) {
				e1.printStackTrace();
			} finally {
				this.add(this.editingPanel, BorderLayout.CENTER);
				this.revalidate();
			}
		}
		
		//blurring the image
		else if(action.getSource() == this.blurImg) {
			this.removeExistingPanel(action);
			new BlurImageDialog(this.choosenImage);
		}
		
		//gray scaling the image
		else if(action.getSource() == this.grayScale) {
			this.removeExistingPanel(action);
			try {
				this.editingPanel.add(new ImageView(new GrayScale(this.choosenImage).getGrayScaledImg(), this.choosenImgName));
				this.add(editingPanel, BorderLayout.CENTER);
			} catch (IOException e1) {
				e1.printStackTrace();
			} finally {
				this.revalidate();
			}
		}
		
		//detecting edges of image
		else if(action.getSource() == this.sobelEdge) {
			this.removeExistingPanel(action);
			try {
				this.editingPanel.add(new ImageView(new SobelEdge(this.choosenImage).getFinalImg(), this.choosenImgName));
				this.add(editingPanel, BorderLayout.CENTER);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		//pixelating the img
		else if(action.getSource() == this.pixelateImg) {
			this.removeExistingPanel(action);
			//if only ImageView frame selected
			if(this.mainPanel.getSelectedFrame() instanceof ImageView) {
				/*Gets the currently selected ImageView object from selectedImgsStorage 
				 * and pass its corresponding selected image*/
				new PixelateImageDialog(this.selectedImgsStorage.get(this.mainPanel.getSelectedFrame()));
				return;
			}
			else
				JOptionPane.showMessageDialog(this, "Please choose image panel");
		}
		
		//cropping image dialog
		else if(action.getSource() == this.cropImg) {
			if(this.mainPanel.getSelectedFrame() instanceof ImageView) {
				new CropImageDialog(this.selectedImgsStorage.get(this.mainPanel.getSelectedFrame()), this.editingPanel);
			}
			else
				JOptionPane.showMessageDialog(this, "Please choose image panel");
		}
		
		else if(action.getSource() == this.invertImg) {
			new NegativeImageDialog(this.choosenImage, this.editingPanel);
		}
		
		//removing noise from image
		else if(action.getSource() == this.denoiseImg) {
			new DenoiseImageDialog(this.imageView, this.choosenImage);
		}
		
		//removing color of image using simple AI
		else if(action.getSource() == this.colorRemover) {
			new ColorRemoverDialog(this.choosenImage);
		}
		
		
		//pixel drawing panel using PikselPainter Library
		else if(action.getSource() == this.pixelDrawingPage) {
			this.mainPanel.add(new PPInternalWindow(300, 300, "Pixel Drawing"));
			this.mainPanel.repaint();
		}
		
		//choosing image from pc
		else if(action.getSource() == this.openImage) {
			//img chooser returns choosen file using JFileChooser class
			var cimg = new ChooseImage();
			
			new Thread(new Runnable() {	
				@Override
				public void run() {
					try {			
						BufferedImage img = cimg.getChoosenImage();
						choosenImage = img;
						choosenImgName = cimg.getChoosenFile().getName();
						
						if(img.getWidth() > 600) {
							choosenImage = resizeImage(choosenImage);
						}
						
						impanel = new ImageView(img, choosenImgName, true, ImagePanel.CANVAS_MODE);
						addNewImageView(impanel);
						
						revalidate();
						mainPanel.refresh();
						mainPanel.revalidate();
						
						/*Add new ImageView and BufferedImage to storage*/
						selectedImgsStorage.put(impanel, choosenImage);
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}
			}).start();
			
		}
		
	}
	
	//setting image on label
	private void addNewImageView(ImageView impanel) {
		this.mainPanel.add(impanel);
		this.analyzeMenu.add(new ImageInfoPanel(this.choosenImage), BorderLayout.CENTER);
	}
	
	//resizing image
	private BufferedImage resizeImage(BufferedImage img) {
		var rimg = new ResizeImg(img, 0.7f);
		return rimg.getScaledImage();
	}
	
	//if image is selected, this dialog will pop up
	private void noImgSelectedDialog() {
		JOptionPane.showMessageDialog(this, "No image selected!");
	}
	
	//removing existing panel before adding new
	private void removeExistingPanel(ActionEvent e) {
		//removing image info panel
		//only while opening new image
		if(e.getSource() == this.openImage || e.getSource() == this.plainDrawingPage) {
			new AccessoriesRemover(analyzeMenu);
			this.remove(this.analyzeMenu);
		}
	}
	
}
