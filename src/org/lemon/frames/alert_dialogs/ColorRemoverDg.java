package org.lemon.frames.alert_dialogs;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.lemon.colors.ColorRemover;
import org.lemon.image.ImagePanel;
import org.lemon.image.ImageView;
import org.lemon.image.MiniImageView;

public class ColorRemoverDg extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1L;
	
	//input fields
	private JTextField preferredColor;
	private JPanel imgPanel, btnPanel;
	
	//ok button
	private JButton okBttn, closeBttn;
	
	//src
	private BufferedImage img;
	
	public ColorRemoverDg(BufferedImage img) {
		this.img = img;
		this.init();
		setSize(img.getWidth() + 100, img.getHeight() + 100);
		setTitle("Smart Color Remover");
		setDefaultCloseOperation(ColorRemoverDg.DISPOSE_ON_CLOSE);
		setResizable(false);
		setVisible(true);
		
		Container c = this.getContentPane();
		c.add(this.preferredColor, BorderLayout.NORTH);
		c.add(this.imgPanel);
		c.add(this.btnPanel, BorderLayout.SOUTH);
		
		//adding bttn
		this.btnPanel.add(this.okBttn);
		this.btnPanel.add(this.closeBttn);
		
		try {
			var view = new ImageView(img);
			
			var motion = new RemoverMouseListener();
			ImagePanel pan = view.getImagePanel();
			pan.addMouseListener(motion);
			pan.addMouseMotionListener(motion);
			
			this.imgPanel.add(view);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public static void main(String[] args) throws IOException {
		new ColorRemoverDg(ImageIO.read(new File("C:\\Users\\Ramesh\\Desktop\\opencv\\mack.jpg")));
	}
	
	//init widgets
	private void init() {
		this.preferredColor = new JTextField("Click on image to remove specific color or type here. eg. 255, 255, 255");
		this.imgPanel = new JPanel();
		this.btnPanel = new JPanel(new FlowLayout());
		this.okBttn = new JButton("Remove");
		this.closeBttn = new JButton("Close");
		this.okBttn.addActionListener(this);
		this.closeBttn.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == this.okBttn) {
			
			//start remove thread
			new Thread(new RemoverThread()).start();
			
		}
		else if(e.getSource() == this.closeBttn) {
			this.dispose();
		}
	}

	//helper class to implement thread based color remover
	private class RemoverThread implements Runnable {
		
		@Override
		public void run() {
			String[] col = preferredColor.getText().toString().split(",");
			
			new ColorRemover(img, new Color(Integer.parseInt(col[0].trim()),
					Integer.parseInt(col[1].trim()), Integer.parseInt(col[1].trim())));
			imgPanel.repaint();
		}
	}
	
	/*Class which implements MouseAdapter.
	 * Helper class for color remover.*/
	private class RemoverMouseListener extends MouseAdapter {
		
		@Override
		public void mouseClicked(MouseEvent e) {
			super.mouseClicked(e);
			
			var x = e.getX();
			var y = e.getY();
			
			var c = new Color(img.getRGB(x, y));
			var col = String.valueOf(c.getRed() + ", " + c.getGreen() + ", " + c.getBlue());
			preferredColor.setText(col);
		}
		
		int mx = 0, my = 0;
		MiniImageView mini = new MiniImageView(img, mx, my, 100, 100);
		
		@Override
		public void mouseMoved(MouseEvent e) {
			super.mouseMoved(e);
			
			//mx = e.getX();
			//my = e.getY();
			
			if(mx != e.getX() && my != e.getY()) {
				
				mini.dispose();
				mini = new MiniImageView(img, e.getX(), e.getY(), 100, 100);
			}
			
			try {
				TimeUnit.SECONDS.sleep(1 / 2);
			}
			catch (InterruptedException ex) {
				ex.printStackTrace();
			}
			finally {
				mx = e.getX();
				my = e.getY();
			}
		}
	}
	
}
