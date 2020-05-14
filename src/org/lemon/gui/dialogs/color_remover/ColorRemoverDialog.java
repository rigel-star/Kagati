package org.lemon.gui.dialogs.color_remover;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JWindow;

import org.lemon.colors.ColorRemover;
import org.lemon.gui.image.ImagePanel;
import org.lemon.gui.image.ImagePanel.PanelMode;
import org.lemon.gui.image.MiniImageView;

public class ColorRemoverDialog extends JWindow implements ActionListener {

	private static final long serialVersionUID = 1L;
	
	//input fields
	private JLabel preferredColor;
	private JPanel imgPanel, btnPanel;
	
	//ok button
	private JButton processBttn, removeBttn, closeBttn;
	
	
	private SelectedColorPreview preview;
	
	//src
	private BufferedImage imgOriginal, imgCopy, imgPreview;
	
	
	private ImagePanel panelOriginal, panelPreview;
	
	
	public ColorRemoverDialog(BufferedImage img) {
		
		this.imgOriginal = img;
		
		preview = new SelectedColorPreview(img);
		this.imgPreview = preview.getRenderedPreview();
		
		this.init();
		
		setSize((img.getWidth() * 2) + 100, img.getHeight() + 100);
		setVisible(true);
		setLocation(new Point(200, 100));
		
		Container c = this.getContentPane();
		c.add(this.preferredColor, BorderLayout.NORTH);
		c.add(this.imgPanel);
		c.add(this.btnPanel, BorderLayout.SOUTH);
		
		//adding bttn
		this.btnPanel.add(this.processBttn);
		this.btnPanel.add(this.removeBttn);
		this.btnPanel.add(this.closeBttn);
		
		
		var meh = new MouseEventHandler();
		panelOriginal = new ImagePanel(img, PanelMode.defaultMode);
		panelOriginal.addMouseListener(meh);
		
		panelPreview = new ImagePanel(imgPreview, PanelMode.DEFAULT_MODE);
		
		new MiniImageView(panelOriginal);
		
		this.imgPanel.add(panelOriginal);
		this.imgPanel.add(panelPreview);
		
	}
	
	
	
	
	//init widgets
	private void init() {
		this.preferredColor = new JLabel("Select color in image and click on process to see preview.");
		this.imgPanel = new JPanel(new FlowLayout());
		this.btnPanel = new JPanel(new FlowLayout());
		this.removeBttn = new JButton("Remove");
		this.closeBttn = new JButton("Close");
		this.processBttn = new JButton("Process");
		this.removeBttn.addActionListener(this);
		this.closeBttn.addActionListener(this);
		this.processBttn.addActionListener(this);
	}

	
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == this.removeBttn) {
			
			//start remove thread
			//new Thread(new RemoverThread()).start();
			
		}
		
		else if(e.getSource() == this.processBttn) {
			showPreview();
		}
		
		else if(e.getSource() == this.closeBttn) {
			this.dispose();
		}
	}

	
	
	private void showPreview() {
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				
				boolean updated = preview.updatePreview();
				
				if(updated)
					imgPreview = preview.getRenderedPreview();
				else
					System.out.println("Problem updating preview");
				
				panelPreview.setIcon(new ImageIcon(imgPreview));
				
			}
		}).start();
	}
	
	
	
	
	//helper class to implement thread based color remover
	class RemoverThread implements Runnable {
		
		@Override
		public void run() {
			String[] col = preferredColor.getText().toString().split(",");
			
			var r = Integer.parseInt(col[0].trim());
			var g = Integer.parseInt(col[1].trim());
			var b = Integer.parseInt(col[2].trim());
			
			new ColorRemover(imgOriginal, new Color(r, g, b));
			
			imgPanel.repaint();
		}
	}
	
	
	class MouseEventHandler extends MouseAdapter {
		
		@Override
		public void mouseClicked(MouseEvent e) {
			super.mouseClicked(e);
			
			var x = e.getX();
			var y = e.getY();
			
			var color = new Color(imgOriginal.getRGB(x, y));
			
			preview.addNewColor(color);
			
			System.out.println("X: " + x + "___ Y: " + y);
		}
		
	}
	
	
	
}
