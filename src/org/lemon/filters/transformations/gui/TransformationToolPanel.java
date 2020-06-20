package org.lemon.filters.transformations.gui;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

public class TransformationToolPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	
	
	private JButton openFileButton;
	private JButton drawPlaneButton;
	private JButton eraserToolButton;
	private JButton brushToolButton;
	
	
	public TransformationToolPanel() {
		
		openFileButton = createOpenFileButton();
		drawPlaneButton = createDrawPlaneButton();
		eraserToolButton = createEraserToolButton();
		brushToolButton = createBrushToolButton();
	}
	
	
	private JButton createOpenFileButton() {
		var op = new JButton("Open...");
		
		op.addActionListener(action -> {
			
		});
		
		return op;
	}
	
	private JButton createDrawPlaneButton() {
		var pl = new JButton();
		pl.setIcon(new ImageIcon("icons/transformations/perspective.png"));
		
		pl.addActionListener(action -> {
			
		});
		
		return pl;
	}
	
	private JButton createEraserToolButton() {
		var er = new JButton();
		er.setIcon(new ImageIcon("icons/eraser.png"));
		return er;
	}
	
	private JButton createBrushToolButton() {
		var br = new JButton();
		br.setIcon(new ImageIcon("icons/brush.png"));
		return br;
	}

}
