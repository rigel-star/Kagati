package org.lemon.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.swing.BorderFactory;

import org.lemon.gui.image.ImagePanel;
import org.lemon.tools.LemonTool;

public class ImageView extends NodeView {
	private ImagePanel imagePanel = null;
	private BufferedImage imageSource = null;

	private LemonTool currentTool;
	
	public ImageView(BufferedImage img) {
		this(img, null);
	}
	
	public ImageView(BufferedImage img, final String title) {
		super(null, null);
		if(img == null) {
			throw new NullPointerException( "Image can't be null." );
		}
		this.imageSource = img;
		this.imagePanel = new ImagePanel(imageSource);
		setLayout(new BorderLayout());
		setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
		setTitle(title);
		setClosable(true);
		setSize(new Dimension(imageSource.getWidth(), imageSource.getHeight()));
		setLayout(new BorderLayout());
		setMaximizable(true);
		setIconifiable(true);
		add(imagePanel, BorderLayout.CENTER);
		setVisible(true);
	}

	public void setCurrentTool(LemonTool tool) {
		this.currentTool = tool;
	}

	public LemonTool getCurrentTool() {
		return currentTool;
	}
	
	public ImagePanel getImagePanel() {
		return imagePanel;
	}
	
	public BufferedImage getOriginalImage() {
		return imagePanel.getOriginalImage();
	}
	
	public BufferedImage getCurrentImage() {
		return imagePanel.getCurrentImage();
	}
	
	public Graphics2D getDrawable() {
		return this.getCurrentImage().createGraphics();
	}
}