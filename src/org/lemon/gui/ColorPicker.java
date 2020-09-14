package org.lemon.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class ColorPicker extends JFrame {
	private static final long serialVersionUID = 1L;


	private ColorWheelPanel colWheelPanel;
	private JPanel colWheelAndBtnPanel;
	
	private JPanel rgbControlPanel;
	private JButton colNotifier;
	private Color selectedCol = Color.blue;
	
	private BufferedImage colWheelImage;
	
	/*tf = text filed*/
	JLabel rlbl, glbl, blbl;
	private JTextField rtf, gtf, btf;
	
	GridBagConstraints gbc = new GridBagConstraints();
	
	
	public static void main(String[] args) {
		new ColorPicker();
	}
	
	
	public ColorPicker() {
		
		try {
			colWheelImage = ImageIO.read(new File("icons/utils/col_sq.jpg"));
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		
		init();
		Container c = getContentPane();
		c.setLayout(new BorderLayout());
		
		rgbControlPanel.setLayout(new FlowLayout());
		rgbControlPanel.add(rtf);
		rgbControlPanel.add(gtf);
		rgbControlPanel.add(btf);
		
		colWheelAndBtnPanel.add(colWheelPanel);
		colWheelAndBtnPanel.add(colNotifier);
		
		colWheelPanel = new ColorWheelPanel();
		c.add(colWheelAndBtnPanel, BorderLayout.WEST);
		//c.add(rgbControlPanel, BorderLayout.SOUTH);
		
		/*frame properties*/
		setTitle("Color Picker");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(600, 280);
		setVisible(true);
		setResizable(false);
	}
	
	
	private void init() {
		this.colWheelPanel = new ColorWheelPanel();
		this.rgbControlPanel = new JPanel();
		
		this.rlbl = new JLabel("R");
		this.rtf = new JTextField(255);
		this.glbl = new JLabel("G");
		this.gtf = new JTextField(255);
		this.blbl = new JLabel("B");
		this.btf = new JTextField(255);
		this.colWheelAndBtnPanel = new JPanel(new FlowLayout());
		
		this.colNotifier = new JButton();
		colNotifier.setBackground(selectedCol);
		colNotifier.setPreferredSize(new Dimension(50, 256));
	}
	
	
	
	/*color wheel container*/
	class ColorWheelPanel extends JPanel {
		private static final long serialVersionUID = 1L;
		
		int colPtrX = 0, colPtrY = 0;
		
		
		public ColorWheelPanel() {
			var mListener = new ColorWheelMouseHandler();
			
			setLayout(null);
			setBorder(BorderFactory.createLineBorder(Color.black, 2));
			addMouseListener(mListener);
			addMouseMotionListener(mListener);
		}
		
		
		public Color getSeletedColor() {
			return new Color(colWheelImage.getRGB(colPtrX, colPtrY));
		}
		
		@Override
		public Dimension getPreferredSize() {
			return new Dimension(256, 256);
		}
		
		
		@Override
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			var g2d = (Graphics2D) g;
			
			var rhs = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			g2d.setRenderingHints(rhs);
			g2d.drawImage(colWheelImage, 0, 0, null);
			g2d.setPaint(Color.black);
			g2d.fill(new Ellipse2D.Double(colPtrX, colPtrY, 12, 12));
		}
		
		
		class ColorWheelMouseHandler extends MouseAdapter {
			
			@Override
			public void mouseClicked(MouseEvent e) {
				super.mouseClicked(e);
				changeColor(e);
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				super.mousePressed(e);
				changeColor(e);
			}
			
			@Override
			public void mouseDragged(MouseEvent e) {
				super.mouseDragged(e);
				changeColor(e);
			}
			
			private void changeColor(MouseEvent e) {
				colPtrX = e.getX();
				colPtrY = e.getY();
				
				try {
					selectedCol = new Color(colWheelImage.getRGB(colPtrX, colPtrY));
					colNotifier.setBackground(selectedCol);
					repaint();
				} catch(ArrayIndexOutOfBoundsException ex) {
					//System.out.println("Index");
				}
			}
			
		}
	}

}
