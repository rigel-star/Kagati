// SPDX-License-Identifier: MIT
// Copyright (c) 2026 Kagati Foundation

package org.kagatifoundation.views.workspace.frames;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JPanel;

import org.kagatifoundation.views.workspace.WorkspaceFrame;

public class ImageFrame extends WorkspaceFrame {
	private final File openedFile;
	
	public ImageFrame(File file) {
		this.openedFile = file;
		setup();
	}

	private void setup() {
		try {
			BufferedImage imageSource = ImageIO.read(openedFile);
			ImagePanel panel = new ImagePanel(imageSource);

			setTitle(title);
			setClosable(true);
			setMaximizable(true);
			setIconifiable(true);

			setLayout(new BorderLayout());
			setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
			setSize(new Dimension(imageSource.getWidth(), imageSource.getHeight()));
			setLayout(new BorderLayout());

			add(panel, BorderLayout.CENTER);
			setVisible(true);
		}
		catch (IOException ioe) {
			System.err.println("Couldn't open image!");
		}
	}

	private class ImagePanel extends JPanel {
		private BufferedImage sourceImage;

		public ImagePanel(BufferedImage image) {
			sourceImage = image;
			setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));	
			setBackground(Color.WHITE);
		}

		@Override
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			Graphics2D g2 = (Graphics2D) g;
			g2.drawImage(sourceImage, 0, 0, this);
		}
	}
}