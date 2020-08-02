package org.lemon.gui.dialogs;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JWindow;

import org.lemon.colors.ColorRemover;
import org.lemon.filters.ResizeImage;
import org.lemon.utils.UndoRedo;
import org.lemon.utils.Utils;
import org.lemon.gui.image.ImagePreviewPanel;
import org.lemon.gui.image.MiniImageView;
import org.rampcv.color.Range;
import org.rampcv.utils.Tools;

public class ColorReplaceDialog extends JWindow implements ActionListener {

	private static final long serialVersionUID = 1L;
	
	private final int DEFAULT_RESIZE_HEIGHT = 500;
	private final int DEFAULT_RESIZE_WIDTH = 500;
	
	//input fields
	private JLabel preferredColor;
	private JPanel mainPanel, btnPanel;
	
	//ok button
	private JButton processBttn, remakeBttn, closeBttn;
	private JButton undoBtn, redoBtn;
	
	
	private SelectedColorPreview preview;
	
	//src
	BufferedImage imgOriginal, imgCopy, imgPreview;
	
	
	private ImagePreviewPanel panelOriginal;
	private ImagePreviewPanel panelPreview;
	
	
	/*size of screen*/
	private final Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
	
	
	public ColorReplaceDialog(BufferedImage img) {
		this.imgOriginal = img;
		
		imgCopy = Utils.getImageCopy(imgOriginal);
		imgCopy = new ResizeImage(imgCopy).getImageSizeOf(DEFAULT_RESIZE_WIDTH, DEFAULT_RESIZE_HEIGHT);
		
		this.preview = new SelectedColorPreview(img);
		this.imgPreview = preview.getRenderedPreview();
		this.imgPreview = new ResizeImage(imgPreview).getImageSizeOf(DEFAULT_RESIZE_WIDTH, DEFAULT_RESIZE_HEIGHT);
		
		this.init();
		
		setSize(screen.width - 100, screen.height - 200);
		setVisible(true);
		setLocationRelativeTo(null);
		makeBorder();
		
		Container c = this.getContentPane();
		c.add(this.preferredColor, BorderLayout.NORTH);
		c.add(this.mainPanel, BorderLayout.CENTER);
		
		addAll();
		
	}
	
	
	
	/**
	 * Making border for this window
	 * */
	private void makeBorder() {
		var b = BorderFactory.createLineBorder(Color.white, 1);
		this.getRootPane().setBorder(b);
	}
	
	
	
	/**
	 * Adding all components to where it should be
	 * */
	private void addAll() {
		
		var meh = new MouseEventHandler();
		panelOriginal = new ImagePreviewPanel(imgOriginal, 500, 500);
		panelOriginal.addMouseListener(meh);
		
		panelPreview = new ImagePreviewPanel(imgPreview, 500, 500);
		
		new MiniImageView(panelOriginal);
		
		this.mainPanel.add(panelOriginal);
		this.mainPanel.add(processBttn);
		this.mainPanel.add(panelPreview);
		this.mainPanel.add(btnPanel);
		
	}
	
	
	
	//init widgets
	private void init() {
		this.preferredColor = new JLabel("Select color in image and click on process to see preview.");
		this.mainPanel = new JPanel(new FlowLayout(FlowLayout.TRAILING));
		
		this.btnPanel = new JPanel();
		this.btnPanel.setLayout(new BoxLayout(this.btnPanel, BoxLayout.X_AXIS));
		
		this.remakeBttn = new JButton("Clear");
		this.closeBttn = new JButton("Close");
		
		this.processBttn = new JButton();
		this.processBttn.setIcon(new ImageIcon("icons/process.png"));
		
		this.btnPanel = new JPanel(new GridLayout(4, 1));
		this.undoBtn = new JButton("Undo");
		this.redoBtn = new JButton("Redo");
		this.btnPanel.add(undoBtn);
		this.btnPanel.add(redoBtn);
		this.btnPanel.add(remakeBttn);
		this.btnPanel.add(closeBttn);
		
		this.remakeBttn.addActionListener(this);
		this.closeBttn.addActionListener(this);
		this.processBttn.addActionListener(this);
		this.undoBtn.addActionListener(this);
		this.redoBtn.addActionListener(this);
	}
	
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		if(e.getSource() == this.remakeBttn) {
			preview.remakePreview();
			panelPreview.revalidate();
		}
		
		else if(e.getSource() == this.processBttn) {
			/*stop main thread and start preview process thread.
			 * making preview takes a lot of time so we have to stop main/UI thread
			 * for sometime so user can't do other task while processing. Same 
			 * goes for undo and redo action.
			 * */
			try {
				Thread.sleep(1000);
				showPreview();
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
		}
		
		else if(e.getSource() == this.undoBtn) {
			doUndo();
		}
		
		else if(e.getSource() == this.redoBtn) {
			doRedo();
		}
		
		else if(e.getSource() == this.closeBttn) {
			this.dispose();
		}
	}

	
	
	/**Undoing the image edit*/
	private void doUndo() {
		/*if undo stack 0, return*/
		if(preview.getUndoRedoManager().getUndoStack().size() == 0)
			return;

		preview.getUndoRedoManager().undo();
		
		/*creating new blank image to paint undone colors*/
		imgPreview = Tools.createBlankImageLike(imgOriginal, BufferedImage.TYPE_3BYTE_BGR);
		preview.setImage(imgPreview);
		
		try {
			Thread.sleep(1000);
			showPreview();
			panelPreview.repaint();
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
	}
	
	
	
	/**Undoing the image edit*/
	private void doRedo() {
		/*if redo stack 0, return*/
		if(preview.getUndoRedoManager().getRedoStack().size() == 0)
			return;

		preview.getUndoRedoManager().redo();
		
		/*creating new blank image to paint undone colors*/
		imgPreview = Tools.createBlankImageLike(imgOriginal, BufferedImage.TYPE_INT_ARGB);
		preview.setImage(imgPreview);
		
		try {
			Thread.sleep(1000);
			showPreview();
			panelPreview.repaint();
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
	}
	
	
	
	/**
	 * Runs different thread to find all the colors on image that user have selected and to paint on the preview image.
	 * Finding all colors in image takes a lot of time so i used different thread.
	 * WHATEVERRR -_-
	 * */
	private void showPreview() {
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				
				boolean updated = preview.updatePreview();
				
				if(updated) {
					imgPreview = preview.getRenderedPreview();
					imgPreview = new ResizeImage(imgPreview).getImageSizeOf(DEFAULT_RESIZE_WIDTH, DEFAULT_RESIZE_HEIGHT);
				}
				else
					System.out.println("Problem updating preview");
				
				panelPreview.update(imgPreview);
				
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
			
			mainPanel.repaint();
		}
	}
	
	
	class MouseEventHandler extends MouseAdapter {
		
		@Override
		public void mouseClicked(MouseEvent e) {
			super.mouseClicked(e);
			
			var x = e.getX();
			var y = e.getY();
			
			var color = new Color(imgCopy.getRGB(x, y));
			
			preview.addNewColor(color);
			System.out.printf("Adding to stack: %d\n", preview.getUndoRedoManager().getUndoStack().size());
		}
		
	}
	
	
	
	private class SelectedColorPreview  {
		
		private BufferedImage preview;
		private final BufferedImage source;
		
		
		private UndoRedo undoRedoManager = new UndoRedo();
		
		
		public SelectedColorPreview(final BufferedImage src) {
			
			preview = Tools.createBlankImageLike(src, BufferedImage.TYPE_3BYTE_BGR);
			source = src;
		}
		
		
		
		/**
		 * Add new color to change or remove from image. This method takes {@code Color} as parameter and
		 * tries to find that color in image in specific range.
		 * @param color to find in image
		 * */
		public void addNewColor(Color c) {
			undoRedoManager.addToUndo(c);
		}
		
		
		
		/**
		 * Update the preview with new colors.
		 * */
		public boolean updatePreview() {
			
			final Range range = new Range();
			
			
			for(int x=0; x<preview.getWidth(); x++) {
				for(int y=0; y<preview.getHeight(); y++) {
					
					Color curr = new Color(source.getRGB(x, y));
					Color prev = new Color(preview.getRGB(x, y));
					
					/*if point is already highlighted, then skip it.*/
					if(prev.getRGB() != Color.black.getRGB()) {
						continue;
					}
					
					final int dx = x;
					final int dy = y;
					
					int r1 = curr.getRed();
					int g1 = curr.getGreen();
					int b1 = curr.getBlue();
					
					
					/*undo stack stores all the previous colors selected in it so just calling
					 * the getUndoStack gives all the previous selected colors and we can process it.*/
					undoRedoManager.getUndoStack().forEach(obj -> {
						
						var color = (Color) obj;
						
						int r2 = color.getRed();
						int g2 = color.getGreen();
						int b2 = color.getBlue();
						
						double dist = org.rampcv.math.BasicMath.dist(r1, g1, b1, r2, g2, b2);
						
						if(range.is(dist).inRange(0, 30)) {
							preview.setRGB(dx, dy, new Color(255, 255, 255).getRGB());
						}
						
					});
					
				}
			}
			
			return true;
		}
			
		
		
		/**
		 * Make new preview erasing all previous data.
		 * */
		public void remakePreview() {
			undoRedoManager.getRedoStack().clear();
			undoRedoManager.getUndoStack().clear();
			preview = Tools.createBlankImageLike(source, BufferedImage.TYPE_3BYTE_BGR);
		}
		
		
		/**
		 * Get final rendered preview image.
		 * */
		public BufferedImage getRenderedPreview() {
			return preview;
		}
		
		
		public UndoRedo getUndoRedoManager() {
			return undoRedoManager;
		}
		
		
		public void setImage(BufferedImage newImg) {
			this.preview = newImg;
		}
		
		
		public BufferedImage getImage() {
			return source;
		}
		
		
	}

	
	
}
