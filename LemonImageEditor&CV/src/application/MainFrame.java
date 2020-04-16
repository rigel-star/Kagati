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
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import org.lemon.accessories.AccessoriesRemover;
import org.lemon.copy.CopyImgPixelsDg;
import org.lemon.drawing.NewDrawingPanelSetup;
import org.lemon.edge.SobelEdge;
import org.lemon.filters.GrayScale;
import org.lemon.filters.RotateImg;
import org.lemon.filters.SharpImg;
import org.lemon.frames.FilterPanel;
import org.lemon.frames.ToolPanel;
import org.lemon.frames.alert_dialogs.BlurImgDg;
import org.lemon.frames.alert_dialogs.ColorRemoverDg;
import org.lemon.frames.alert_dialogs.DenoiseImgDg;
import org.lemon.frames.alert_dialogs.ImageCropDg;
import org.lemon.frames.alert_dialogs.InvertColorDg;
import org.lemon.frames.alert_dialogs.PixelateImgDg;
import org.lemon.image.ChooseImage;
import org.lemon.image.ImageInfoPanel;
import org.lemon.image.ImageView;
import org.lemon.image.ResizeImg;
import org.piksel.piksel.PPInternalWindow;

public class MainFrame extends JFrame implements ActionListener{
	private static final long serialVersionUID = 1L;
	
	//menus and submenus
	private JMenu file, edit, filter, extras, editSubMenu, fileSubMenu, noiseSubMenu, shapesSubMenu;
	
	//filters
	private JMenuItem openImage, saveImg, grayScale, sobelEdge,
				sharpImg, blurImg, rotate180, rotate90, pixelateImg, cropImg,
				invertImg, denoiseImg, colorRemover, copyAndDrawImg, plainDrawingPage, pixelDrawingPage;
	
	//blend modes
	//To read more about blend mode go to text_files -> BlendModes.txt
	//or wikipedia page https://en.wikipedia.org/wiki/Blend_Modes
	private JMenu blendModes;
	private JMenuItem multiplyBmode, addBmode, subtractBmode;
	
	private JMenuBar menuBar;

	//shapes
	private JMenuItem shapeRing, shapeRect, shapeLine;
	
	//middle panel where drawing and image panel will be added.
	//central panel
	private JPanel editingPanel = new JPanel();
	
	//test img
	private BufferedImage choosenImage = null;
	private String choosenImgName = null;
	
	//default filter panel
	private FilterPanel filterPanel;
	
	//main editing panel
	private MainBackgroundPane mainPanel;
	
	//image panel
	//where user can edit his/her photos
	ImageView impanel = null;
	
	//default color
	private Color choosenColor = new Color(0, 255, 0);
	
	public MainFrame() throws IOException {
		File f = new File("C:\\Users\\Ramesh\\Desktop\\opencv\\flow.jpg");
		//test images: dog.jpg, flow.jpg, (color) mack.jpg
		this.choosenImage = ImageIO.read(f);
		//this.editingPanel.setLayout(new BorderLayout());
		this.choosenImgName = f.getName();
		this.mainPanel = new MainBackgroundPane(choosenImage, this.choosenImgName);
		
		//shapes
		shapeRing = new JMenuItem("Circle");
		shapeRect = new JMenuItem("Rectangle");
		shapeLine = new JMenuItem("Line");
		
		//filter panel properties
		filterPanel = new FilterPanel(this.choosenImage);
		filterPanel.setLayout(new BorderLayout());
		
		//main sub menus
		fileSubMenu = new JMenu("New");
		editSubMenu = new JMenu("Rotate");
		noiseSubMenu = new JMenu("Noise");
		shapesSubMenu = new JMenu("Shapes");
		
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
		copyAndDrawImg = new JMenuItem("Copy & Draw");
		
		//main menu bar
		menuBar = new JMenuBar();
	
		
		//sub menus
		editSubMenu.add(rotate180);
		editSubMenu.add(rotate90);
		//file sub menu
		fileSubMenu.add(plainDrawingPage);
		fileSubMenu.add(pixelDrawingPage);
		//noise sub menu
		noiseSubMenu.add(denoiseImg);
		//shapes sub menu
		shapesSubMenu.add(shapeRect);
		shapesSubMenu.add(shapeRing);
		shapesSubMenu.add(shapeLine);
		//file options
		//file
		file.add(fileSubMenu);
		file.add(openImage);
		file.add(saveImg);
		//edit options
		edit.add(editSubMenu);
		edit.add(blurImg);
		edit.add(cropImg);
		edit.add(noiseSubMenu);
		edit.add(copyAndDrawImg);
		//filter options
		filter.add(grayScale);
		filter.add(sobelEdge);
		filter.add(sharpImg);
		filter.add(pixelateImg);
		filter.add(invertImg);
		filter.add(blendModes);
		//extras option
		extras.add(shapesSubMenu);
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
		copyAndDrawImg.addActionListener(this);
		pixelDrawingPage.addActionListener(this);
	}
	
	//adding all required panels to frame
	private void addPanelsToFrame(BufferedImage bi) throws IOException {
		
		//editingPanel.add(new NewDrawingPanel());
		
		Container c = getContentPane();
		this.editingPanel.add(new ImageView(this.choosenImage, this.choosenImgName));
		this.filterPanel.add(new ImageInfoPanel(this.choosenImage));
		c.add(this.mainPanel, BorderLayout.CENTER);
		c.add(this.filterPanel, BorderLayout.EAST);
		c.add(new ToolPanel(this.editingPanel, this.choosenColor), BorderLayout.WEST);
		
	}

	//frame action performed
	@Override
	public void actionPerformed(ActionEvent action) {
		
		//new page in the editingPanel
		if(action.getSource() == plainDrawingPage) {
			//panel where user can draw
			new NewDrawingPanelSetup(this.mainPanel);
			this.revalidate();
		}
		
		//rotate existing image to 180 degree
		else if(action.getSource() == rotate180) {
			this.removeExistingPanel(action);
			if(this.choosenImage == null) {
				this.noImgSelectedDialog();
				return;
			}
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
			if(this.choosenImage == null) {
				this.noImgSelectedDialog();
				return;
			}
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
			if(this.choosenImage == null) {
				noImgSelectedDialog();
				return;
			}
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
			if(this.choosenImage == null) {
				this.noImgSelectedDialog();
				return;
			}
			new BlurImgDg(this.choosenImage);
		}
		
		//gray scaling the image
		else if(action.getSource() == this.grayScale) {
			this.removeExistingPanel(action);
			if(this.choosenImage == null) {
				this.noImgSelectedDialog();
				return;
			}
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
			if(this.choosenImage == null) {
				this.noImgSelectedDialog();
				return;
			}
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
			if(this.choosenImage == null) {
				this.noImgSelectedDialog();
				return;
			}
			new PixelateImgDg(this.choosenImage);
		}
		
		//cropping image dialog
		else if(action.getSource() == this.cropImg) {
			if(this.choosenImage == null) {
				this.noImgSelectedDialog();
				return;
			}
				new ImageCropDg(this.choosenImage, this.editingPanel);
		}
		
		else if(action.getSource() == this.invertImg) {
			if(this.choosenImage == null) {
				this.noImgSelectedDialog();
				return;
			}
			new InvertColorDg(this.choosenImage, this.editingPanel);
		}
		
		//brighting the image
		else if(action.getSource() == this.denoiseImg) {
			if(this.choosenImage == null) {
				this.noImgSelectedDialog();
				return;
			}
			new DenoiseImgDg(this.choosenImage);
		}
		
		//removing color of image using simple AI
		else if(action.getSource() == this.colorRemover) {
			if(this.choosenImage == null) {
				this.noImgSelectedDialog();
				return;
			}
			new ColorRemoverDg(this.choosenImage);	
		}
		
		//drawing or copying image pixels on another image
		else if(action.getSource() == this.copyAndDrawImg) {
			if(this.choosenImage == null) {
				this.noImgSelectedDialog();
				return;
			}
			new CopyImgPixelsDg(this.choosenImage);	
		}
		
		//pixel drawing panel using PikselPainter Library
		else if(action.getSource() == this.pixelDrawingPage) {
			if(this.choosenImage == null) {
				this.noImgSelectedDialog();
				return;
			}
			this.mainPanel.add(new PPInternalWindow(300, 300, "Pixel Drawing"));
			this.mainPanel.repaint();
		}
		
		//choosing image from pc
		else if(action.getSource() == this.openImage) {
			//img chooser which returns choosen file using JFileChooser class
			ChooseImage cimg = new ChooseImage();
			
			new Thread(new Runnable() {	
				@Override
				public void run() {
					try {			
						BufferedImage img = cimg.getChoosenImage();
						choosenImage = img;
						choosenImgName = cimg.getChoosenFile().getName();
						
						if(img.getWidth() > 600) {
							choosenImage = resizingImage(choosenImage);
							impanel = new ImageView(choosenImage, choosenImgName, true, true);
							removeExistingPanel(action);
							setImageOnLabel(impanel);
						}
						else {
							impanel = new ImageView(img, choosenImgName);
							removeExistingPanel(action);
							setImageOnLabel(impanel);
						}		
						
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}
			}).start();
			
		}
		
	}
	
	//setting image on label
	private void setImageOnLabel(ImageView impanel) {
		//editingPanel.add(impanel, BorderLayout.CENTER);
		//this.add(editingPanel, BorderLayout.CENTER);
		this.mainPanel.add(impanel);
		this.filterPanel.add(new ImageInfoPanel(this.choosenImage), BorderLayout.CENTER);
		this.add(this.filterPanel, BorderLayout.EAST);
		this.revalidate();
	}
	//cropping image
	private BufferedImage resizingImage(BufferedImage img) {
		ResizeImg rimg = new ResizeImg(img, 0.7f);
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
			new AccessoriesRemover(filterPanel);
			this.remove(this.filterPanel);
		}
	}
	
}
