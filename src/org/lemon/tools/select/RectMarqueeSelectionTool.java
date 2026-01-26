package org.lemon.tools.select;

import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;

import javax.swing.SwingUtilities;

import org.lemon.gui.ImageView;
import org.lemon.tools.SelectionTool;

public class RectMarqueeSelectionTool extends SelectionTool {
    private Rectangle2D.Float rectSelectedArea = new Rectangle2D.Float();

    public RectMarqueeSelectionTool(ImageView context) {
        super(context);
        setSelectedArea(rectSelectedArea);
    }

    private Point initial, offset;

    @Override 
    public void mousePressed(MouseEvent e) {
        super.mousePressed(e);
        initial = e.getPoint();
        offset = new Point(initial.x - (int) rectSelectedArea.getX(), initial.y - (int) rectSelectedArea.getY());
        if(rectSelectedArea.contains(e.getX(), e.getY())) {
            mouseIsInsidePreSelectedArea = true;
        }
        else {
            if(SwingUtilities.isLeftMouseButton(e)) {
                rectSelectedArea.x = e.getX();
                rectSelectedArea.y = e.getY();
                mouseIsInsidePreSelectedArea = false;
            }
        }
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        if(mouseIsInsidePreSelectedArea) {
            int deltaX = e.getX() - offset.x;
            int deltaY = e.getY() - offset.y;
            rectSelectedArea.x = deltaX;
            rectSelectedArea.y = deltaY;
            selectionToolListener.selectionMoved(rectSelectedArea);
        }
        else {
            rectSelectedArea.width = e.getX() - (int) rectSelectedArea.getX();
            rectSelectedArea.height = e.getY() - (int) rectSelectedArea.getY();
            selectionToolListener.selectionResized(rectSelectedArea);
        }
    }
}