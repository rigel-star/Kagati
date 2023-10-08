package org.lemon.gui.image;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import javax.swing.BorderFactory;
import javax.swing.JPanel;

import org.lemon.tools.SelectionTool.SelectionToolListener;
import org.lemon.utils.Utils;

public class ImagePanel extends JPanel implements SelectionToolListener {
	private BufferedImage originalImage, copyImage;

	public ImagePanel(BufferedImage img) {
		this.originalImage = img;
		this.copyImage = Utils.getImageCopy(img);
		this.originalImage.getGraphics().dispose();
		setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));	
		setBackground(Color.WHITE);
	}
	
	public BufferedImage getOriginalImage() {
		return this.originalImage;
	}
	
	public BufferedImage getCurrentImage() {
		return this.copyImage;
	}
	
	@Override
	public Dimension getPreferredSize() {
		return new Dimension(originalImage.getWidth(), originalImage.getHeight());
	}
	
	public void setImage(BufferedImage imgg) {
		this.originalImage = imgg;
		this.copyImage = Utils.getImageCopy(imgg);
	}
	
	@Override
	protected void paintComponent( Graphics g ) {
		super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.drawImage(this.copyImage, 0, 0, this);

		if(!selectionFinished && selectionShape != null) {
			g2.draw(selectionShape);
		}
		else {
			if(selectionFinished) {
				g2.setStroke(new BasicStroke(3, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[] { 5, 5 }, 0));
				g2.draw(selectionShape);
			}
		}
	}

	private boolean selectionFinished;
	private Shape selectionShape;
	private Image selectedSubImage;
	private int selectedSubImageDrawX = 0;
	private int selectedSubImageDrawY = 0;

	@Override
	public void selectionResized(Shape shape2d) {
		selectionFinished = false;
		selectionShape = shape2d;
		repaint();
	}

	@Override
	public void selectionMoved(Shape shape2d) {
		selectionShape = shape2d;
		repaint();
	}

	@Override
	public void selectionReleased(Shape shape2d) {
		selectionFinished = true;
		selectionShape = shape2d;
		if(shape2d instanceof Rectangle2D.Float rect2d) {
			selectedSubImage = getCurrentImage().getSubimage(
				(int) rect2d.x, 
				(int) rect2d.y, 
				(int) rect2d.width, 
				(int) rect2d.height
			);
			selectedSubImageDrawX = (int) rect2d.x;
			selectedSubImageDrawY = (int) rect2d.y;
		}
		else if(shape2d instanceof Ellipse2D.Float ellipse) {
			selectedSubImageDrawX = (int) ellipse.x;
			selectedSubImageDrawY = (int) ellipse.y;
		}
		repaint();
	}
}