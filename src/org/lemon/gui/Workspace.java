package org.lemon.gui;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.FlowLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

import org.lemon.gui.toolbars.FileToolbar;

public class Workspace extends Container {
	private static final long serialVersionUID = 1L;
	
	private JPanel toolbarContainer = null;
	private WorkspaceArena workspace = null;
	private ToolsContainer toolsContainer = null;
	private LayerContainer layerContainer = null;
	private JPanel toolInfoPanel = null;
	
	public Workspace()
	{
		workspace = new WorkspaceArena();
		toolsContainer = new ToolsContainer(this);
		layerContainer = new LayerContainer(workspace);
		toolbarContainer = new JPanel(new FlowLayout(FlowLayout.LEFT));
		toolInfoPanel = new JPanel();
		
		setLayout(new BorderLayout());
		
		toolbarContainer.add(new FileToolbar(workspace, layerContainer), 0);
		toolInfoPanel.add(new JLabel("File handling"), 0);
		
		add(toolsContainer, BorderLayout.WEST);
		add(layerContainer, BorderLayout.EAST);
		add(workspace, BorderLayout.CENTER);
		add(toolInfoPanel, BorderLayout.SOUTH);
		add(toolbarContainer, BorderLayout.NORTH);
	}
	
	public JPanel getToolbarContainer()
	{
		return this.toolbarContainer;
	}

	public WorkspaceArena getWorkspaceArena() {
		return workspace;
	}

	public ToolsContainer getToolsContainer() {
		return toolsContainer;
	}

	public LayerContainer getLayerContainer() {
		return layerContainer;
	}
}