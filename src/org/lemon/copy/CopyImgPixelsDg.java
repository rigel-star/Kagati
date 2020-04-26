package org.lemon.copy;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JWindow;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.lemon.colors.ColorPanel;
import org.lemon.image.ImagePanel;
import org.rampcv.utils.Tools;

public class CopyImgPixelsDg extends JWindow implements  MouseMotionListener, ChangeListener, ActionListener {

	private static final long serialVersionUID = 1L;

	//original and copy of image
	private BufferedImage original, copy;
	//main image panels to add all sub image panels
	private JPanel imgPanel, copyImgPanel, mainPanel;
	//panels to add buttons
	private JPanel btnPanel;
	//buttons
	private JButton okBttn, cnclBttn, copyFullImageBttn;
	//msg box on top of frame
	private JLabel msgLbl;
	//original and preview image panel
	private ImagePanel imgLbl, copyImgLbl;
	//color choosing list
	private JComboBox<String> colorChooser;
	//pen size changer
	private JSlider size;
	//drawing pen size
	private int pixSize = 5;
	//choosen color
	private Color choosenColor = null;
	//editing panel
	private JPanel editingPanel;
	
	//to give option to user to paint on different colors
	private Map<String, Color> colorMap;
	
	public CopyImgPixelsDg(BufferedImage img) {
		int h = img.getHeight();
		int w = img.getWidth();
		this.copy = Tools.createBlankImageLike(img);
		this.original = img;
		
		try {
			this.init();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		//frame properties
		setSize(((w * 2) + 200), (h + 200));
		setVisible(true);
		setLocation(200, 100);
		
		this.getRootPane().setBorder(BorderFactory.createLineBorder(Color.gray, 6));
		
		//images panel to main panel
		this.mainPanel.setBackground(Color.white);
		this.mainPanel.add(this.imgPanel);
		this.mainPanel.add(this.copyFullImageBttn);
		this.mainPanel.add(this.copyImgPanel);
		this.mainPanel.add(this.editingPanel);
		
		Container c = this.getContentPane();
		c.add(this.mainPanel, BorderLayout.CENTER);
		c.add(this.btnPanel, BorderLayout.SOUTH);
		c.add(this.msgLbl, BorderLayout.NORTH);
	}
	
	private void init() throws IOException {
		this.imgPanel = new JPanel();
		this.copyImgPanel = new JPanel();
		this.mainPanel = new JPanel(new FlowLayout());
		this.btnPanel = new JPanel(new FlowLayout());
		
		this.okBttn = new JButton("OK");
		this.okBttn.addActionListener(this);
		
		this.cnclBttn = new JButton("Cancel");
		this.cnclBttn.addActionListener(this);
		
		this.copyFullImageBttn = new JButton();
		this.copyFullImageBttn.setIcon(new ImageIcon("icons/copyIcon.png"));
		this.msgLbl = new JLabel("Draw on source image. Drawn objects will be copied to blank image.");
		
		//slider properties
		this.size = new JSlider(JSlider.HORIZONTAL, 0, 10, 5);
		this.size.setPaintTicks(true);
		this.size.setMajorTickSpacing(1);
		this.size.setPaintLabels(true);
		this.size.addChangeListener(this);
		
		this.colorMap = new HashMap<String, Color>();
		this.colorChooserInit();
		
		//editing panel
		this.initEditingPanel();
		
		//img views
		//directly adding image panel instead of image view cause
		//on adding image view mouseMouseListener doesn't work 
		this.imgLbl = new ImagePanel(this.original);
		this.copyImgLbl = new ImagePanel(this.copy);
		//mouse drag listener on original image
		this.imgLbl.addMouseMotionListener(this);
		//mouse drag listener on copy image
		this.copyImgLbl.addMouseMotionListener(this);
		
		//on image panel
		this.imgPanel.add(this.imgLbl);
		this.copyImgPanel.add(this.copyImgLbl);
		
		//on bttn panel
		//this.btnPanel.add(this.size);
		this.btnPanel.add(this.okBttn);
		this.btnPanel.add(this.cnclBttn);
	}

	//initing everything related to color chooser JComboBox
	private void colorChooserInit() {
		//color array for jcombobox
		String[] s = {
				"Image Pixels",
				"White",
				"Red",
				"Green",
				"Blue",
				"Others..."
		};
		//actual color list
		Color[] c = {
				null,
				new Color(255, 255, 255),
				new Color(255, 0, 0),
				new Color(0, 255, 0),
				new Color(0, 0, 255),
				null
		};
		
		//adding colors to hashMap
		for(int i=0;i<s.length;i++) {
			this.colorMap.put(s[i], c[i]);
		}
		this.colorChooser = new JComboBox<String>(s);
		this.colorChooser.addActionListener(this);
	}
	
	//all editing panel properties
	private void initEditingPanel() {
		this.editingPanel = new JPanel(new GridLayout(1, 1, 10, 10));
		this.editingPanel.add(this.colorChooser);
		this.editingPanel.add(this.size);
	}
	
	
	@Override
	public void mouseDragged(MouseEvent e) {
		
		//current mouse pos
		int x = e.getX();
		int y = e.getY();
		
		//if the mouse event generator is original image panel
		//then draw
		//last param is for command which tells if it is to copy or erase
		if(e.getComponent() == this.imgLbl) {
			this.applyColor(x, y, this.choosenColor, 0);
		}
		else if(e.getComponent() == this.copyImgLbl) {
			Color c = new Color(0, 0, 0);
			this.applyColor(x, y, c, 1);
		}
		this.repaint();
	}
	
	
	//applying color on blank image
	private void applyColor(int x, int y, Color col, int command) {
		try {
			for(int dx=x; dx<x + this.pixSize; dx++) {
				for(int dy=y; dy<y + this.pixSize; dy++) {
					//in case user choosen color is "image pixel" or "null"
					//this will draw same image pixels
					if(this.choosenColor == null && command == 0)
						col = new Color(this.original.getRGB(dx, dy));
					this.copy.setRGB(dx, dy, col.getRGB());
				}
			}
		} catch (ArrayIndexOutOfBoundsException ex) {
			System.out.println("Out of range.");
		}
	}

	@Override
	public void mouseMoved(MouseEvent e) {}

	@Override
	public void stateChanged(ChangeEvent e) {
		//changing pen size
		if(e.getSource() == this.size) {
			this.pixSize = this.size.getValue();
			this.revalidate();
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		//color chooser
		if(e.getSource() == this.colorChooser) {
			this.choosenColor = this.colorMap.get(this.colorChooser.getSelectedItem());
		}
		else if(e.getSource() == this.okBttn) {
			new ColorPanel();
		}
		else if(e.getSource() == this.cnclBttn) {
			this.dispose();
		}
	}
}
