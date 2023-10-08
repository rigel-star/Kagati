package org.lemon.gui;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import javax.swing.JPanel;

import org.lemon.tools.LemonTool;
import org.lemon.tools.LemonTool.ToolType;

public abstract class AbstractToolBar extends JPanel {
    protected PropertyChangeSupport toolChangeSupport = new PropertyChangeSupport(this);
    protected LemonTool.ToolType currentlySelectedTool = ToolType.BRUSH;

    public AbstractToolBar() {
    } 
 
	public void addToolChangeListener(AbstractToolBar.ToolChangeListener listener) {
		toolChangeSupport.addPropertyChangeListener(listener);
	}

	public void removeToolChangeListener(AbstractToolBar.ToolChangeListener listener) {
		toolChangeSupport.removePropertyChangeListener(listener);
	}

    public static interface ToolChangeListener extends PropertyChangeListener {
        @Override
        default void propertyChange(PropertyChangeEvent evt) {
			Object newTool = evt.getNewValue();
			if(newTool instanceof LemonTool.ToolType lemonTool) {
				toolChange(lemonTool);
			}
        }

        public void toolChange(LemonTool.ToolType newTool);
    }
}