package org.lemon.gui;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import org.lemon.gui.toolbar.BrushToolbar;
import org.lemon.gui.toolbar.FileToolbar;
import org.lemon.gui.toolbar.MarqueeToolbar;
import org.lemon.tools.LemonTool.ToolType;

public class ToolBarContainer extends JPanel {
    private AbstractToolBar toolBar = new FileToolbar();
    private ToolsContainer toolsContainer;

    public ToolBarContainer(ToolsContainer toolsContainer) {
        assert toolsContainer != null;
        this.toolsContainer = toolsContainer;
        ToolsContainerToolChangeListener toolChangeListener = new ToolsContainerToolChangeListener();
        this.toolsContainer.addToolChangeListener(toolChangeListener);
		setBorder(BorderFactory.createLineBorder(Color.black, 1));
		setLayout(new BorderLayout());
        add(toolBar, BorderLayout.CENTER);
    }

    private class ToolsContainerToolChangeListener implements ToolsContainer.ToolChangeListener {
        @Override
        public void toolChange(ToolType newTool) {
            remove(toolBar);
            if(newTool == ToolType.BRUSH) {
                toolBar = new BrushToolbar(null);
            }
            else if(newTool == ToolType.RECT_MARQUEE) {
                toolBar = new MarqueeToolbar();
            }
            else {
                toolBar = new FileToolbar();
            }
            add(toolBar, BorderLayout.CENTER);
            toolBar.addToolChangeListener(WorkspaceArena.toolChangeListener);
            revalidate();
        }
    }

    public AbstractToolBar getToolBar() {
        return toolBar;
    }
}