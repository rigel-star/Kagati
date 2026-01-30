// SPDX-License-Identifier: MIT
// Copyright (c) 2026 Kagati Foundation

package org.kagatifoundation;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JMenuBar;

import org.kagatifoundation.views.EditorView;

public class AppFrame extends JFrame {
	private static final long serialVersionUID = 1L;
	
	private final JMenuBar menuBar;

	private final EditorView editorView;
	
	public AppFrame() {
		this.editorView = new EditorView(AppContext.defaultContext());
		this.menuBar = new JMenuBar();
		
		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		
		setBackground(Color.GRAY);
		setTitle("Lemon Image Editor");
		setSize(screen.width - 50, screen.height - 50);
		setExtendedState( MAXIMIZED_BOTH);
		setResizable(true);
		setLayout(new BorderLayout());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		Container c = getContentPane();
		c.add(editorView, BorderLayout.CENTER);
		
		setJMenuBar(menuBar);
		setVisible(true);
	}
}