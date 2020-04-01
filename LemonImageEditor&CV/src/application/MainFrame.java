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
import org.lemon.drawing.NewDrawingPanel;
import org.lemon.edge.SobelEdge;
import org.lemon.filters.BlurImg;
import org.lemon.filters.GrayScale;
import org.lemon.filters.RotateImg;
import org.lemon.filters.SharpImg;
import org.lemon.frames.FilterPanel;
import org.lemon.frames.ImagePanel;
import org.lemon.frames.NewImagePanel;
import org.lemon.frames.ToolPanel;
import org.lemon.frames.alert_dialogs.DenoiseImgDg;
import org.lemon.frames.alert_dialogs.ImageCropDg;
import org.lemon.frames.alert_dialogs.InvertColorDg;
import org.lemon.image.ChooseImage;
import org.lemon.image.ResizeImg;
import org.rampcv.rampcv.RampCV;


public class MainFrame extends JFrame implements ActionListener{

	/**
	 * default version id
	 */
	private static final long serialVersionUID = 1L;
	
	private JMenu file, edit, filter, extras, editSubMenu, fileSubMenu, noiseSubMenu;
	private JMenuItem openImage, saveImg, grayScale, sobelEdge,
				sharpImg, blurImg, rotate180, rotate90, pixelateImg, cropImg,
				invertImg, denoiseImg, drawingPage;
	private JMenuBar menuBar;

	
	//middle panel where drawing and image panel will be added.
	//central panel
	private JPanel editingPanel = new JPanel();
	
	//test img
	private BufferedImage choosenImage = null;
	
	//default image panel
	private ImagePanel impanel = new ImagePanel();
	
	//default color
	private Color choosenColor = new Color(0, 255, 0);
	
	public MainFrame() throws IOException {
		
		this.choosenImage = ImageIO.read(new File("C:\\Users\\Ramesh\\Desktop\\opencv\\mack.jpg"));
		editingPanel.setLayout(new BorderLayout());
		
		//main sub menus
		fileSubMenu = new JMenu("New");
		editSubMenu = new JMenu("Rotate");
		noiseSubMenu = new JMenu("Noise");
		
		//main menu items
		file = new JMenu("File");
		edit = new JMenu("Edit");
		filter = new JMenu("Filter");
		extras = new JMenu("Extras");
		
		//file properties
		saveImg = new JMenuItem("Save");
		openImage = new JMenuItem("Open");
		drawingPage = new JMenuItem("New Page");
		
		//filter properties
		grayScale = new JMenuItem("B&W");
		sobelEdge = new JMenuItem("Edge Highlight");
		sharpImg = new JMenuItem("Sharp Img");
		pixelateImg = new JMenuItem("Pixelate");
		invertImg = new JMenuItem("Invert");
		denoiseImg = new JMenuItem("Denoise");
		
		//edit properties
		rotate180 = new JMenuItem("Rotate 180");
		rotate90 = new JMenuItem("Rotate 90");
		cropImg = new JMenuItem("Crop");
		blurImg = new JMenuItem("Blur Img");
		
		menuBar = new JMenuBar();
	
		
		//sub menus
		editSubMenu.add(rotate180);
		editSubMenu.add(rotate90);
		//file sub menu
		fileSubMenu.add(drawingPage);
		//noise sub menu
		noiseSubMenu.add(denoiseImg);
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
		//filter options
		filter.add(grayScale);
		filter.add(sobelEdge);
		filter.add(sharpImg);
		filter.add(pixelateImg);
		filter.add(invertImg);
		
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

	private void events(){
		openImage.addActionListener(this);
		drawingPage.addActionListener(this);
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
	}
	
	//adding all required panels to frame
	private void addPanelsToFrame(BufferedImage bi) throws IOException {
		
		//editingPanel.add(new NewDrawingPanel());
		
		Container c = getContentPane();
		editingPanel.add(new NewImagePanel(this.choosenImage));
		c.add(editingPanel, BorderLayout.CENTER);
		c.add(new FilterPanel(this.impanel.getCurrentImg()), BorderLayout.EAST);
		c.add(new ToolPanel(this.editingPanel, this.choosenColor), BorderLayout.WEST);
		
	}

	//frame action performed
	@Override
	public void actionPerformed(ActionEvent action) {
		
		//new page in the editingPanel
		if(action.getSource() == drawingPage) {
			
			//removing pre existing panel from frame to add new
			this.removeExistingPanel();
			//panel
			//where user can draw paintings
			NewDrawingPanel drawPanel = new NewDrawingPanel(this.choosenColor);
			this.editingPanel.add(drawPanel, BorderLayout.CENTER);
			this.add(editingPanel, BorderLayout.CENTER);
			this.revalidate();
		}
		
		//rotate existing image to 180 degree
		else if(action.getSource() == rotate180) {
			this.removeExistingPanel();
			if(this.choosenImage == null) {
				this.noImgSelectedDialog();
				return;
			}
			try {
				this.editingPanel.add(new NewImagePanel(new RotateImg(180, choosenImage).getRotatedImg()));
				this.add(this.editingPanel, BorderLayout.CENTER);
				this.revalidate();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		
		//rotate existing image to 90 degree
		else if(action.getSource() == rotate90) {
			this.removeExistingPanel();
			if(this.choosenImage == null) {
				this.noImgSelectedDialog();
				return;
			}
			try {
				this.editingPanel.add(new NewImagePanel(new RotateImg(90, choosenImage).getRotatedImg()));
				this.add(this.editingPanel, BorderLayout.CENTER);
				this.revalidate();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		
		//sharping the image
		else if(action.getSource() == this.sharpImg) {
			this.removeExistingPanel();
			if(this.choosenImage == null) {
				noImgSelectedDialog();
				return;
			}
			try {
				SharpImg simg = new SharpImg(this.choosenImage);
				this.editingPanel.add(new NewImagePanel(simg.getSharpedImg()));
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
			this.removeExistingPanel();
			if(this.choosenImage == null) {
				this.noImgSelectedDialog();
				return;
			}
			try {
				BlurImg bimg = new BlurImg(this.choosenImage);
				this.editingPanel.add(new NewImagePanel(bimg.getBlurredImg()));
				this.choosenImage = bimg.getBlurredImg();
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				this.revalidate();
			}
		}
		
		//gray scaling the image
		else if(action.getSource() == this.grayScale) {
			this.removeExistingPanel();
			if(this.choosenImage == null) {
				this.noImgSelectedDialog();
				return;
			}
			try {
				this.editingPanel.add(new NewImagePanel(new GrayScale(this.choosenImage).getGrayScaledImg()));
				this.add(editingPanel, BorderLayout.CENTER);
			} catch (IOException e1) {
				e1.printStackTrace();
			} finally {
				this.revalidate();
			}
		}
		
		//detecting edges of image
		else if(action.getSource() == this.sobelEdge) {
			this.removeExistingPanel();
			if(this.choosenImage == null) {
				this.noImgSelectedDialog();
				return;
			}
			try {
				this.editingPanel.add(new NewImagePanel(new SobelEdge(this.choosenImage).getFinalImg()));
				this.add(editingPanel, BorderLayout.CENTER);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		//pixelating the img
		else if(action.getSource() == this.pixelateImg) {
			this.removeExistingPanel();
			if(this.choosenImage == null) {
				this.noImgSelectedDialog();
				return;
			}
			try {
				BufferedImage pimg = RampCV.pixelate(this.choosenImage, 10);
				this.editingPanel.add(new NewImagePanel(pimg));
				this.add(editingPanel, BorderLayout.CENTER);
				this.choosenImage = pimg;
			} catch (IOException e) {
				e.printStackTrace();
			}
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
		
		//choosing image from pc
		else if(action.getSource() == this.openImage) {
			//image panel
			//where user can edit his/her photos
			NewImagePanel impanel;
			//img chooser which returns choosen file using JFileChooser class
			ChooseImage cimg = new ChooseImage();
			
			try {			
				BufferedImage img = cimg.getChoosenFile();
				this.choosenImage = img;
				
				
				if(img.getWidth() > 600) {
					this.choosenImage = this.resizingImage(this.choosenImage);
					impanel = new NewImagePanel(this.choosenImage);
					this.removeExistingPanel();
					this.setImageOnLabel(impanel);
				}
				else {
					impanel = new NewImagePanel(img);
					this.removeExistingPanel();
					this.setImageOnLabel(impanel);
				}		
				
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		
	}
	
	//setting image on label
	private void setImageOnLabel(NewImagePanel impanel) {
		editingPanel.add(impanel, BorderLayout.CENTER);
		this.revalidate();
		this.add(editingPanel, BorderLayout.CENTER);
	}
	//cropping image
	private BufferedImage resizingImage(BufferedImage img) {
		ResizeImg rimg = new ResizeImg(img);
		return rimg.getScaledImage();
	}
	
	//if image is selected, this dialog will pop up
	private void noImgSelectedDialog() {
		JOptionPane.showMessageDialog(this, "No image selected!");
	}
	
	//removing existing panel before adding new
	private void removeExistingPanel() {
		new AccessoriesRemover(this.editingPanel);
		//finally removing the editing panel
		this.remove(this.editingPanel);
	}
	
}
