package org.lemon.tools.select;

import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.geom.Ellipse2D;

import org.lemon.gui.ImageView;
import org.lemon.tools.SelectionTool;

public class CircleMarqueeSelectionTool extends SelectionTool {
    private Ellipse2D.Float circlularSelectedArea = new Ellipse2D.Float();

    public CircleMarqueeSelectionTool(ImageView imageView) {
        super(imageView);
        setSelectedArea(circlularSelectedArea);
    }

    private Point initial, offset;

    @Override
    public void mousePressed(MouseEvent e) {
        initial = e.getPoint();
        offset = new Point(initial.x - (int) circlularSelectedArea.getX(), initial.y - (int) circlularSelectedArea.getY());
        if(circlularSelectedArea.contains(e.getX(), e.getY())) {
            mouseIsInsidePreSelectedArea = true;
        }
        else {
            circlularSelectedArea.x = e.getX();
            circlularSelectedArea.y = e.getY();
            mouseIsInsidePreSelectedArea = false;
        }
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        if(mouseIsInsidePreSelectedArea) {
            int deltaX = e.getX() - offset.x;
            int deltaY = e.getY() - offset.y;
            circlularSelectedArea.x = deltaX;
            circlularSelectedArea.y = deltaY;
            selectionToolListener.selectionMoved(circlularSelectedArea);
        }
        else {
            circlularSelectedArea.width = (int) Math.abs(e.getX() - circlularSelectedArea.getX());
            circlularSelectedArea.height = (int) Math.abs(e.getY() - circlularSelectedArea.getY());
            selectionToolListener.selectionResized(circlularSelectedArea);
        }
    }
}