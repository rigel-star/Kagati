// SPDX-License-Identifier: MIT
// Copyright (c) 2026 Kagati Foundation

package org.kagatifoundation.views;

import java.awt.BorderLayout;
import java.awt.Container;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JLabel;
import javax.swing.JPanel;

import org.kagatifoundation.AppContext;
import org.lemon.gui.LayerContainer;
import org.lemon.gui.ToolsContainer;
import org.kagatifoundation.views.workspace.EditorWorkspace;
import org.kagatifoundation.views.workspace.frames.layout.ToolbarContainer;

public class EditorView extends Container implements PropertyChangeListener {
	private final ToolsContainer toolsContainer;
	private final JPanel toolInfoPanel;

	private final AppContext ctx;
	private final EditorWorkspace editorWorkspace;

	private final LayerContainer layerContainer;
	private final ToolbarContainer toolbarContainer;
	
	public EditorView(AppContext ctx) {
		this.ctx = ctx;

		layerContainer = new LayerContainer();
		toolsContainer = new ToolsContainer();
		toolbarContainer = new ToolbarContainer(ctx);

		this.editorWorkspace = new EditorWorkspace(ctx);
		this.ctx.addPropertyChangeListener(this);

		toolInfoPanel = new JPanel();
		toolInfoPanel.add(new JLabel("File handling"), 0);
		
		setLayout(new BorderLayout());
		add(toolsContainer, BorderLayout.WEST);
		add(layerContainer, BorderLayout.EAST);
		add(editorWorkspace, BorderLayout.CENTER);
		add(toolInfoPanel, BorderLayout.SOUTH);
		add(toolbarContainer, BorderLayout.NORTH);
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
	}
}