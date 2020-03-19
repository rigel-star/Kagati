package application;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import org.lemon.frames.DrawingPanel;
import org.lemon.frames.FilterPanel;
import org.lemon.frames.ImagePanel;
import org.lemon.frames.InternalImageEditingToolsPanel;
import org.lemon.frames.NewDrawingPanel;
import org.lemon.frames.ToolPanel;

public class MainFrame extends JFrame implements ActionListener{

	JMenu file, edit, filter, editSubMenu;
	JMenuItem newPage, itemOpenImage, saveImg, grayScale, cannyEdge, 
				silverEdge, sharpImg, blurImg, rotate180, rotate90, rotateNeg90;
	JMenuBar menuBar;
	
	ImagePanel impanel;

	
	public MainFrame() throws IOException {
		
		file = new JMenu("File");
		edit = new JMenu("Edit");
		filter = new JMenu("Filter");
		editSubMenu = new JMenu("Rotate");
		
		//file properties
		newPage = new JMenuItem("New");
		saveImg = new JMenuItem("Save");
		itemOpenImage = new JMenuItem("Open");
		
		//filter properties
		grayScale = new JMenuItem("B&W");
		cannyEdge = new JMenuItem("Edge Highlight");
		silverEdge = new JMenuItem("Silver Cypher");
		sharpImg = new JMenuItem("Sharp Img");
		blurImg = new JMenuItem("Blur Img");
		
		//edit properties
		rotate180 = new JMenuItem("Rotate 180");
		rotate90 = new JMenuItem("Rotate 90");
		
		menuBar = new JMenuBar();
	
		
		//sub menus
		editSubMenu.add(rotate180);
		editSubMenu.add(rotate90);
		
		//file options
		file.add(newPage);
		file.add(itemOpenImage);
		file.add(saveImg);
		//edit options
		edit.add(editSubMenu);
		edit.add(blurImg);
		//filter options
		filter.add(grayScale);
		filter.add(cannyEdge);
		filter.add(silverEdge);
		filter.add(sharpImg);
		
		//main menu options
		menuBar.add(file);
		menuBar.add(edit);
		menuBar.add(filter);
		
		events();
		
		//default img
		BufferedImage bi = ImageIO.read(new File("C:\\Users\\Ramesh\\Desktop\\styletransfer\\fruit.jpg"));
		
		//screen size
		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		
		//frame style
		setBackground(Color.GRAY);
		setTitle("Lemon Image Editor");
		setSize(screen.width, 800);
		setResizable(false);
		setLayout(new BorderLayout());
		
		setJMenuBar(menuBar);
		addPanelsToFrame(bi);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		

	}

	private void events(){
		newPage.addActionListener(this);
		grayScale.addActionListener(this);
		rotate90.addActionListener(this);
		saveImg.addActionListener(this);
		rotate180.addActionListener(this);
		cannyEdge.addActionListener(this);
		silverEdge.addActionListener(this);
		blurImg.addActionListener(this);
		sharpImg.addActionListener(this);
	}
	
	private BufferedImage getImageFromLabel() {
		
		JLabel img = new JLabel();
		Icon icon = img .getIcon();

        BufferedImage bi = new BufferedImage(
            icon.getIconWidth(),
            icon.getIconHeight(),
            BufferedImage.TYPE_INT_RGB);
        Graphics g = bi.createGraphics();
        // paint the Icon to the BufferedImage.
        icon.paintIcon(null, g, 0,0);
        
        g.dispose();
        
        return bi;
	}
	
	private void addPanelsToFrame(BufferedImage bi) throws IOException {
		
		impanel = new ImagePanel(bi);
		
		add(impanel, BorderLayout.CENTER);
		add(new FilterPanel(), BorderLayout.EAST);
		add(new ToolPanel(), BorderLayout.WEST);
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		if(e.getSource() == newPage) {
			System.out.println("New drawing pane.");
			remove(impanel);
			add(new NewDrawingPanel(), BorderLayout.CENTER);
			repaint();
		}
		
	}
}
