// SPDX-License-Identifier: MIT
// Copyright (c) 2026 Kagati Foundation

package org.lemon.views;

import java.awt.BorderLayout;
import java.awt.Container;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JLabel;
import javax.swing.JPanel;

import org.lemon.AppContext;
import org.lemon.gui.LayerContainer;
import org.lemon.gui.ToolBarContainer;
import org.lemon.gui.ToolsContainer;
import org.lemon.gui.WorkspaceArena;
import org.lemon.views.workspace.EditorWorkspace;

public class EditorView extends Container implements PropertyChangeListener {
	private final ToolBarContainer toolbarContainer;
	private final ToolsContainer toolsContainer;
	private final LayerContainer layerContainer;
	private final WorkspaceArena workspaceArena;
	private final JPanel toolInfoPanel;

	private final AppContext ctx;
	private final EditorWorkspace editorWorkspace;
	
	public EditorView(AppContext ctx) {
		this.ctx = ctx;

		layerContainer = new LayerContainer();
		toolsContainer = new ToolsContainer();
		toolbarContainer = new ToolBarContainer(toolsContainer);

		this.editorWorkspace = new EditorWorkspace(ctx, layerContainer, toolbarContainer);
		this.ctx.addPropertyChangeListener(this);

		new WorkspaceArena(); // to initialize the variable toolChangeListener; no other purpose of this call
		workspaceArena = new WorkspaceArena(layerContainer, toolbarContainer);
		WorkspaceArena.toolChangeListener.changeWorkspaceArena(workspaceArena);
		toolInfoPanel = new JPanel();
		toolInfoPanel.add(new JLabel("File handling"), 0);
		
		setLayout(new BorderLayout());
		add(toolsContainer, BorderLayout.WEST);
		add(layerContainer, BorderLayout.EAST);
		add(editorWorkspace, BorderLayout.CENTER);
		add(toolInfoPanel, BorderLayout.SOUTH);
		add(toolbarContainer, BorderLayout.NORTH);
	}
	
	public ToolBarContainer getToolbarContainer() {
		return this.toolbarContainer;
	}

	public WorkspaceArena getWorkspaceArena() {
		return workspaceArena;
	}

	public ToolsContainer getToolsContainer() {
		return toolsContainer;
	}

	public LayerContainer getLayerContainer() {
		return layerContainer;
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		throw new UnsupportedOperationException("Unimplemented method 'propertyChange'");
	}
}