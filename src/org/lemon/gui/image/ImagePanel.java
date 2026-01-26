// MIT License
//
// Copyright (c) 2023 Ramesh Poudel
//
// Permission is hereby granted, free of charge, to any person obtaining a copy
// of this software and associated documentation files (the "Software"), to deal
// in the Software without restriction, including without limitation the rights
// to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
// copies of the Software, and to permit persons to whom the Software is
// furnished to do so, subject to the following conditions:
//
// The above copyright notice and this permission notice shall be included in
// all copies or substantial portions of the Software.
//
// THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
// IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
// FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
// AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
// LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
// OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
// THE SOFTWARE.

package org.lemon.gui.image;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.image.BufferedImage;
import javax.swing.BorderFactory;
import javax.swing.JPanel;

import org.lemon.tools.SelectionTool.SelectionToolListener;
import org.lemon.utils.Utils;

public class ImagePanel extends JPanel implements SelectionToolListener {
	private BufferedImage originalImage, copyImage;
	private Cursor toolCursor = new Cursor(Cursor.DEFAULT_CURSOR);

	public ImagePanel(BufferedImage img) {
		originalImage = img;
		copyImage = Utils.getImageCopy(img);
		originalImage.getGraphics().dispose();
		setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));	
		setBackground(Color.WHITE);
	}

	public void setToolIndicatingCursor(int cursorType) {
		this.toolCursor = new Cursor(cursorType);
	}

	public int getToolIndicatingCursor() {
		return toolCursor.getType();
	}
	
	public BufferedImage getOriginalImage() {
		return originalImage;
	}
	
	public BufferedImage getCurrentImage() {
		return copyImage;
	}
	
	@Override
	public Dimension getPreferredSize() {
		return new Dimension(originalImage.getWidth(), originalImage.getHeight());
	}
	
	public void setImage(BufferedImage imgg) {
		originalImage = imgg;
		copyImage = Utils.getImageCopy(imgg);
	}
	
	@Override
	protected void paintComponent( Graphics g ) {
		super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.drawImage(copyImage, 0, 0, this);
		if(selectionFinished) {
			g2.setStroke(new BasicStroke(3, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[] { 5, 5 }, 0));
		}
		if(selectionShape != null) {
			g2.draw(selectionShape);
		}
	}

	private boolean selectionFinished;
	private Shape selectionShape;

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
		if(shape2d != null) {
			selectionFinished = true;
			selectionShape = shape2d;
		}
		else {
			selectionFinished = false;
			selectionShape = null;
		}
		repaint();
	}

	@Override
	public void actionPerformed(int action) {
		if(action == 0) {
			selectionShape = null;
			selectionFinished = false;
		}
		repaint();
	}
}