package org.lemon.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;

import org.lemon.gui.menu.NodeMenu;
import org.lemon.gui.menu.ToolsMenu;
import org.lemon.gui.menu.FileMenu;
import org.lemon.gui.menu.FilterMenu;
import org.lemon.gui.menu.Menu3D;

public class ApplicationFrame extends JFrame {
	private static final long serialVersionUID = 1L;
	
	private JMenuBar menuBar = null;
	private Workspace workspace = new Workspace();
	
	public ApplicationFrame() {
		menuBar = new JMenuBar();
		JMenu fileMenu = new FileMenu(workspace.getWorkspaceArena(), workspace.getLayerContainer());
		JMenu filterMenu = new FilterMenu(workspace.getWorkspaceArena());
		JMenu toolsMenu = new ToolsMenu(workspace.getWorkspaceArena());
		JMenu nodeMenu = new NodeMenu(workspace.getWorkspaceArena(), workspace.getLayerContainer());
		JMenu threeDMenu = new Menu3D(workspace.getWorkspaceArena());
		
		menuBar.add(fileMenu);
		menuBar.add(nodeMenu);
		menuBar.add(toolsMenu);
		menuBar.add(filterMenu);
		menuBar.add(threeDMenu);
		
		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		
		setBackground(Color.GRAY);
		setTitle("Lemon Image Editor");
		setSize(screen.width - 50, screen.height - 50);
		setExtendedState( MAXIMIZED_BOTH);
		setResizable(true);
		setLayout(new BorderLayout());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		Container c = getContentPane();
		c.add(workspace, BorderLayout.CENTER);
		
		setJMenuBar(menuBar);
		setVisible(true);
	}
}