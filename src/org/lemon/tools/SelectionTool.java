package org.lemon.tools;

import java.awt.Cursor;
import java.awt.Shape;
import java.awt.event.MouseEvent;

import org.lemon.gui.ImageView;
import org.lemon.gui.image.ImagePanel;

public abstract class SelectionTool extends MouseToolAdapter {
	private Shape selectedArea;
    protected boolean mouseIsInsidePreSelectedArea = false;
    protected SelectionToolListener selectionToolListener;

	public SelectionTool(ImageView context) {
		super(context);
        ImagePanel imagePanel = context.getImagePanel();
		imagePanel.setCursor(new Cursor(Cursor.CROSSHAIR_CURSOR));
        setSelectionToolListener(context.getImagePanel());
        imagePanel.addMouseListener(this);
        imagePanel.addMouseMotionListener(this);
        context.setCurrentTool(this);
        imageView.revalidate();
	}

    @Override
    public void mouseReleased(MouseEvent e) {
        if(!mouseIsInsidePreSelectedArea)
            selectionToolListener.selectionReleased(selectedArea);
        mouseIsInsidePreSelectedArea = false;
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

    public static interface SelectionToolListener {
        public void selectionResized(Shape shape2d);

        public void selectionMoved(Shape shape2d);

        public void selectionReleased(Shape shape2d);
    }
}