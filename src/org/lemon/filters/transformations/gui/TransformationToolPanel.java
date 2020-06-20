package org.lemon.filters.transformations.gui;

import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
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
		
		var boxL = new BoxLayout(this, BoxLayout.Y_AXIS);
		var border = BorderFactory.createLineBorder(Color.black, 1);
		
		setLayout(boxL);
		setBorder(border);
		
		add(createOpenFileButton());
		add(createDrawPlaneButton());
		add(createEraserToolButton());
		add(createBrushToolButton());
	}
	
	
	
	private JButton createOpenFileButton() {
		this.openFileButton = new JButton("Open...");
		
		openFileButton.addActionListener(action -> {
			
		});
		
		return openFileButton;
	}
	
	
	
	private JButton createDrawPlaneButton() {
		this.drawPlaneButton = new JButton();
		drawPlaneButton.setIcon(new ImageIcon("icons/tools/transformation/perspective.png"));
		
		drawPlaneButton.addActionListener(action -> {
			
		});
		
		return drawPlaneButton;
	}
	
	
	
	private JButton createEraserToolButton() {
		this.eraserToolButton = new JButton();
		eraserToolButton.setIcon(new ImageIcon("icons/tools/eraser.png"));
		return eraserToolButton;
	}
	
	
	
	private JButton createBrushToolButton() {
		this.brushToolButton = new JButton();
		brushToolButton.setIcon(new ImageIcon("icons/tools/brush.png"));
		return brushToolButton;
	}

}
