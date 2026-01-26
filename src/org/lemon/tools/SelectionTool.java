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

package org.lemon.tools;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.SwingUtilities;

import org.lemon.gui.ImageView;
import org.lemon.gui.image.ImagePanel;

public abstract class SelectionTool extends MouseToolAdapter {
	private Shape selectedArea;
    protected boolean mouseIsInsidePreSelectedArea = false;
    protected SelectionToolListener selectionToolListener;
    private ImagePanel imagePanel;

    private JPopupMenu popupMenu = new JPopupMenu("Marquee Options");
	private JMenuItem deselectItem = new JMenuItem("Deselect...");
	private JMenuItem deleteSelectedItem = new JMenuItem("Delete Selected...");
	private JMenuItem newLayerItem = new JMenuItem("Render to New Layer...");

	public SelectionTool(ImageView context) {
		super(context);
        imagePanel = context.getImagePanel();
		imagePanel.setCursor(new Cursor(Cursor.CROSSHAIR_CURSOR));
        imagePanel.setToolIndicatingCursor(imagePanel.getCursor().getType());
		popupMenu.add(deselectItem);
		popupMenu.add(deleteSelectedItem);
		popupMenu.add(newLayerItem);
        SelectionToolPopupMenuActionHandler actionHandler = new SelectionToolPopupMenuActionHandler();
        deleteSelectedItem.addActionListener(actionHandler);
        deselectItem.addActionListener(actionHandler);
        newLayerItem.addActionListener(actionHandler);
        setSelectionToolListener(context.getImagePanel());
        imagePanel.addMouseListener(this);
        imagePanel.addMouseMotionListener(this);
        context.setCurrentTool(this);
        imageView.revalidate();
	}

    @Override
    public void mousePressed(MouseEvent e) {
        if(SwingUtilities.isRightMouseButton(e)) {
            if(selectedArea != null) {
                if(selectedArea.contains(e.getX(), e.getY())) {
                    popupMenu.show(imagePanel, e.getX(), e.getY());
                }
            }
        }    
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if(!mouseIsInsidePreSelectedArea) {
            if(deselectingItem) {
                selectedArea = null;
            }
            selectionToolListener.selectionReleased(selectedArea);
        }
        mouseIsInsidePreSelectedArea = false;
        System.out.println("mouse released");
    }

    public void setSelectionToolListener(SelectionToolListener listener) {
        this.selectionToolListener = listener;
    }

    public SelectionToolListener getSelectionToolListener() {
        return selectionToolListener;
    }

    public void setSelectedArea(Shape shape) {
        this.selectedArea = shape;
    }

    public Shape getSelectedArea() {
        return selectedArea;
    }

    private boolean deselectingItem = false;
    
    private class SelectionToolPopupMenuActionHandler implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			if(e.getSource() == deselectItem) {
				restoreToDefaults();
                selectionToolListener.selectionReleased(null);
                selectionToolListener.actionPerformed(0);
                deselectingItem = true;
                System.out.println("action performed");
			}
			else if(e.getSource() == deleteSelectedItem) {
				Graphics2D g2d = (Graphics2D) imagePanel.getCurrentImage().createGraphics();
				g2d.setColor(Color.WHITE);
				g2d.fill(selectedArea);
				restoreToDefaults();
                selectionToolListener.actionPerformed(1);
			}
		}

		private void restoreToDefaults() {
			selectedArea = null;
		}
	}

    public static interface SelectionToolListener {
        public void selectionResized(Shape shape2d);

        public void selectionMoved(Shape shape2d);

        public void selectionReleased(Shape shape2d);

        public void actionPerformed(int action);
    }
}