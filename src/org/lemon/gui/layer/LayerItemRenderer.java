package org.lemon.gui.layer;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JComponent;
import javax.swing.JList;
import javax.swing.ListCellRenderer;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;

import org.lemon.gui.Layer;

public class LayerItemRenderer implements ListCellRenderer<Layer> {
	private Color bg = null, fg = null;
	private static final Border SAFE_NO_FOCUS_BORDER = new EmptyBorder(1, 1, 1, 1);
    private static final Border DEFAULT_NO_FOCUS_BORDER = new EmptyBorder(1, 1, 1, 1);
    protected static Border noFocusBorder = DEFAULT_NO_FOCUS_BORDER;
    
    public LayerItemRenderer() {
	}
    
    public Border getNoFocusBorder() {	
        Border border = UIManager.getBorder("List.cellNoFocusBorder");
        if(System.getSecurityManager() != null) {
            if(border != null) 
            	return border;
            return SAFE_NO_FOCUS_BORDER;
        } 
        else {
            if(border != null && (noFocusBorder == null || noFocusBorder == DEFAULT_NO_FOCUS_BORDER)) 
                return border;
            return noFocusBorder;
        }
    }
	
	@Override
	public Component getListCellRendererComponent(JList<? extends Layer> list, Layer value, int index,
			boolean isSelected, boolean cellHasFocus) {
		JComponent pan = null;
		
		if(value instanceof ViewLayer) {
			pan =  new ViewLayerPanel((ViewLayer) value);
		}
		else if(value instanceof FilterLayer) {
			pan = new FilterLayerPanel((FilterLayer) value);
		}

        JList.DropLocation dropLocation = list.getDropLocation();
        if((dropLocation != null) && !(dropLocation.isInsert()) && (dropLocation.getIndex() == index)) {
            bg = UIManager.getColor( "List.dropCellBackground" );
            fg = UIManager.getColor( "List.dropCellForeground" );
            isSelected = true;
        }

        if(isSelected) {
            pan.setBackground( bg == null ? list.getSelectionBackground() : bg );
            pan.setForeground( fg == null ? list.getSelectionForeground() : fg );
        } 
        else {
        	pan.setBackground( list.getBackground() );
        	pan.setForeground( list.getForeground() );
        }
		
        Border border = null;
        if(cellHasFocus) {
            if(isSelected) {
                border = UIManager.getBorder("List.focusSelectedCellHighlightBorder");
            }
            if(border == null)
                border = UIManager.getBorder("List.focusCellHighlightBorder");
        } 
        else
            border = getNoFocusBorder();
        
        pan.setBorder(border);
		return pan;
	}
	
	public void setSelectionBackground(Color c) {
		this.bg = c;
	}
	
	public void setSelectionForeground(Color c) {
		this.fg = c;
	}
}