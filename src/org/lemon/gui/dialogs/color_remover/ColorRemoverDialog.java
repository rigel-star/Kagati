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

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JWindow;

import org.lemon.colors.ColorRemover;
import org.lemon.gui.image.ImagePanel;
import org.lemon.gui.image.ImagePanel.PanelMode;
import org.lemon.gui.image.MiniImageView;
import org.rampcv.utils.Tools;

public class ColorRemoverDialog extends JWindow implements ActionListener {

	private static final long serialVersionUID = 1L;
	
	//input fields
	private JLabel preferredColor;
	private JPanel imgPanel, btnPanel, undoRedoBtnPanel;
	
	//ok button
	private JButton processBttn, removeBttn, remakeBttn, closeBttn;
	private JButton undoBtn, redoBtn;
	
	
	private SelectedColorPreview preview;
	
	//src
	private BufferedImage imgOriginal, imgCopy, imgPreview;
	
	
	private ImagePanel panelOriginal, panelPreview;
	
	
	public ColorRemoverDialog(BufferedImage img) {
		this.imgOriginal = img;
		
		this.preview = new SelectedColorPreview(img);
		this.imgPreview = preview.getRenderedPreview();
		
		this.makeOriginalImageCopy();
		
		this.init();
		
		setSize((img.getWidth() * 2) + 250, img.getHeight() + 100);
		setVisible(true);
		setLocation(new Point(200, 100));
		makeBorder();
		
		Container c = this.getContentPane();
		c.add(this.preferredColor, BorderLayout.NORTH);
		c.add(this.imgPanel, BorderLayout.CENTER);
		c.add(this.undoRedoBtnPanel, BorderLayout.SOUTH);
		
		addAll();
		
	}
	
	
	
	/**
	 * Makes copy of original image in new thread
	 * */
	private void makeOriginalImageCopy() {
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				imgCopy = Tools.copyImage(imgOriginal);
			}
		}).start();
	}
	
	
	
	/**
	 * Making border for this window
	 * */
	private void makeBorder() {
		var b = BorderFactory.createLineBorder(Color.black, 1);
		this.getRootPane().setBorder(b);
	}
	
	
	
	/**
	 * Adding all components to where it should be
	 * */
	private void addAll() {
		
		//adding bttn
		this.btnPanel.add(this.remakeBttn);
		this.btnPanel.add(this.removeBttn);
		this.btnPanel.add(this.closeBttn);
		
		
		var meh = new MouseEventHandler();
		panelOriginal = new ImagePanel(imgOriginal, PanelMode.defaultMode);
		panelOriginal.addMouseListener(meh);
		
		panelPreview = new ImagePanel(imgPreview, PanelMode.DEFAULT_MODE);
		
		new MiniImageView(panelOriginal);
		
		this.imgPanel.add(panelOriginal);
		this.imgPanel.add(processBttn);
		this.imgPanel.add(panelPreview);
		this.imgPanel.add(btnPanel);
		
	}
	
	
	
	//init widgets
	private void init() {
		this.preferredColor = new JLabel("Select color in image and click on process to see preview.");
		this.imgPanel = new JPanel(new FlowLayout());
		
		this.btnPanel = new JPanel();
		this.btnPanel.setLayout(new BoxLayout(this.btnPanel, BoxLayout.Y_AXIS));
		
		this.removeBttn = new JButton("Remove");
		this.closeBttn = new JButton("Close");
		
		this.processBttn = new JButton();
		this.processBttn.setIcon(new ImageIcon("icons/process.png"));
		
		this.remakeBttn = new JButton();
		this.remakeBttn.setIcon(new ImageIcon("icons/remake.png"));
		
		this.undoRedoBtnPanel = new JPanel(new FlowLayout());
		this.undoBtn = new JButton("Undo");
		this.redoBtn = new JButton("Redo");
		this.undoRedoBtnPanel.add(undoBtn);
		this.undoRedoBtnPanel.add(redoBtn);
		
		this.remakeBttn.addActionListener(this);
		this.removeBttn.addActionListener(this);
		this.closeBttn.addActionListener(this);
		this.processBttn.addActionListener(this);
		this.undoBtn.addActionListener(this);
		this.redoBtn.addActionListener(this);
	}
	
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == this.removeBttn) {
			
			//start remove thread
			//new Thread(new RemoverThread()).start();
			
		}
		
		else if(e.getSource() == this.remakeBttn) {
			imgPreview = Tools.createBlankImageLike(imgOriginal, BufferedImage.TYPE_3BYTE_BGR);
			panelPreview.setIcon(new ImageIcon(imgPreview));
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
			revalidate();
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
		imgPreview = Tools.createBlankImageLike(imgOriginal, BufferedImage.TYPE_3BYTE_BGR);
		preview.setImage(imgPreview);
		
		try {
			Thread.sleep(1000);
			showPreview();
			revalidate();
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
	}
	
	
	
	/**
	 * Runs different thread to find all the colors that user have selected and to paint
	 * on the preview image. Finding all colors in image takes a lot of time so i used different thread.
	 * WHATEVERRR -_-
	 * */
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
			System.out.printf("Adding to stack: %d\n", preview.getUndoRedoManager().getUndoStack().size());
		}
		
	}
	
	
	
}
