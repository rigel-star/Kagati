package org.lemon.gui.toolbar;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

import org.lemon.gui.AbstractToolBar;
import org.lemon.tools.LemonTool.ToolType;

public class FileToolbar extends AbstractToolBar {
	private static final long serialVersionUID = 1L;
	
	private JButton openBttn = null;
	private JButton saveBttn = null;
	private JButton newBttn = null;
	
	public FileToolbar() {
		super();
		ActionListener al = new ActionHandler();
		Color cl = new Color(50, 50, 50);
		
		openBttn = new JButton("Open");
		openBttn.setIcon( new ImageIcon("icons/button/open.png"));
		openBttn.setBackground(cl);
		openBttn.addActionListener(al);
		
		saveBttn = new JButton("Save");
		saveBttn.setIcon(new ImageIcon("icons/button/save.png"));
		saveBttn.setBackground(cl);
		saveBttn.addActionListener( al);
		
		newBttn = new JButton("New Page");
		newBttn.setIcon(new ImageIcon( "icons/button/new.png" ));
		newBttn.setBackground(cl);
		newBttn.addActionListener(al);
		
		JPanel bttnPanel = new JPanel();
		bttnPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		bttnPanel.add(openBttn);
		bttnPanel.add(saveBttn);
		bttnPanel.add(newBttn);
		add(bttnPanel);
	}
	
	private class ActionHandler implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == openBttn) {
				toolChangeSupport.firePropertyChange("currentlySelectedTool", null, ToolType.OPEN);
			}
			else if (e.getSource() == saveBttn) {
			}
			else if (e.getSource() == newBttn) {
				toolChangeSupport.firePropertyChange("currentlySelectedTool", null, ToolType.NEW);
			}
		}
	}
}