package application;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import org.lemon.drawing.NewDrawingPanel;
import org.lemon.edge.SobelEdge;
import org.lemon.filters.BlurImg;
import org.lemon.filters.GrayScale;
import org.lemon.filters.SharpImg;
import org.lemon.frames.FilterPanel;
import org.lemon.frames.ImagePanel;
import org.lemon.frames.NewImagePanel;
import org.lemon.frames.ToolPanel;
import org.lemon.image.ChooseImage;
import org.lemon.rotate.RotateImg;


public class MainFrame extends JFrame implements ActionListener{

	/**
	 * default version id
	 */
	private static final long serialVersionUID = 1L;
	
	private JMenu file, edit, filter, editSubMenu, fileSubMenu;
	private JMenuItem openImage, saveImg, grayScale, sobelEdge,
				sharpImg, blurImg, rotate180, rotate90,
				drawingPage;
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
		
		editingPanel.setLayout(new BorderLayout());
		
		fileSubMenu = new JMenu("New");
		file = new JMenu("File");
		edit = new JMenu("Edit");
		filter = new JMenu("Filter");
		editSubMenu = new JMenu("Rotate");
		
		//file properties
		saveImg = new JMenuItem("Save");
		openImage = new JMenuItem("Open");
		drawingPage = new JMenuItem("New Page");
		
		//filter properties
		grayScale = new JMenuItem("B&W");
		sobelEdge = new JMenuItem("Edge Highlight");
		sharpImg = new JMenuItem("Sharp Img");
		blurImg = new JMenuItem("Blur Img");
		
		//edit properties
		rotate180 = new JMenuItem("Rotate 180");
		rotate90 = new JMenuItem("Rotate 90");
		
		menuBar = new JMenuBar();
	
		
		//sub menus
		editSubMenu.add(rotate180);
		editSubMenu.add(rotate90);
		//file sub menu
		fileSubMenu.add(drawingPage);
		
		//file options
		//file
		file.add(fileSubMenu);
		file.add(openImage);
		file.add(saveImg);
		//edit options
		edit.add(editSubMenu);
		edit.add(blurImg);
		//filter options
		filter.add(grayScale);
		filter.add(sobelEdge);
		filter.add(sharpImg);
		
		//main menu options
		menuBar.add(file);
		menuBar.add(edit);
		menuBar.add(filter);
		
		//all buttons events are intialized in this method
		events();
		
		//screen size
		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		
		//frame style and properties
		setBackground(Color.GRAY);
		setTitle("Lemon Image Editor");
		setSize(screen.width - 200, screen.height - 200);
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
	}
	
	//adding all required panels to frame
	private void addPanelsToFrame(BufferedImage bi) throws IOException {
		
		//editingPanel.add(new NewDrawingPanel());
		
		Container c = getContentPane();
		
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
			//this.removeExistingPanel();
			if(this.choosenImage == null) {
				this.noImgSelectedDialog();
				return;
			}
			try {
				this.editingPanel.add(new NewImagePanel(new GrayScale(this.choosenImage).getGrayScaledImg()));
			} catch (IOException e1) {
				e1.printStackTrace();
			} finally {
				this.revalidate();
			}
		}
		
		//detecting edges of image
		else if(action.getSource() == this.sobelEdge) {
			//this.removeExistingPanel();
			if(this.choosenImage == null) {
				this.noImgSelectedDialog();
				return;
			}
			try {
				this.editingPanel.add(new NewImagePanel(new SobelEdge(this.choosenImage).getFinalImg()));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		//choosing image from pc
		else if(action.getSource() == this.openImage) {
			this.removeExistingPanel();
			
			//image panel
			//where user can edit his/her photos
			NewImagePanel impanel;
			
			//img chooser which returns choosen file using JFileChooser class
			ChooseImage cimg = new ChooseImage();
			
			try {			
				BufferedImage img = ImageIO.read(cimg.getChoosenFile());
				
				this.impanel.img = img;
				this.choosenImage = img;
				
				impanel = new NewImagePanel(img);			
				editingPanel.add(impanel, BorderLayout.CENTER);
				editingPanel.repaint();
				this.add(editingPanel, BorderLayout.CENTER);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		
	}
	
	//if image is selected, this dialog will pop up
	private void noImgSelectedDialog() {
		JOptionPane.showMessageDialog(this, "No image selected!");
	}
	
	//removing existing panel before adding new
	private void removeExistingPanel() {
		Component[] comps = this.editingPanel.getComponents();
		
		//deleting all components attached to editing panel
		for(Component c: comps) {
			this.editingPanel.remove(c);
		}
		//finally removing the editing panel
		this.remove(this.editingPanel);
	}
	
}
