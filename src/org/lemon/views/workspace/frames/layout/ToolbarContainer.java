// SPDX-License-Identifier: MIT
// Copyright (c) 2026 Kagati Foundation

package org.lemon.views.workspace.frames.layout;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JPanel;

import java.awt.Color;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import org.lemon.AppContext;
import org.lemon.tools.ToolType;
import org.lemon.utils.Utils;

public class ToolbarContainer extends JInternalFrame implements ActionListener {
	private JButton handTool;
	private JButton pencilTool;
	private JButton brushTool;
	private JButton rectangleMarqueeTool;
	private JButton	colPickerTool;

	private ToolType currentToolType = ToolType.Brush;
	
	private final AppContext ctx;
	
	public ToolbarContainer(AppContext ctx) {
		this.ctx = ctx;

		JPanel right = new JPanel();
		right.setLayout(new BoxLayout(right, BoxLayout.Y_AXIS));

		handTool = createToolButton(new ImageIcon("icons/tools/move.png"));
		pencilTool = createToolButton(new ImageIcon("icons/tools/pencil.png"));
		brushTool = createToolButton(new ImageIcon("icons/tools/brush.png"));
		rectangleMarqueeTool = createToolButton(
			Utils.resizeIconToStdKagatiIconSize(
				new ImageIcon("icons/tools/select64.png")
			)
		);
		colPickerTool = createToolButton("Color");
		colPickerTool.setBackground(Color.BLACK);
		colPickerTool.setForeground(Color.WHITE);
		
		right.add(handTool);
		right.add(pencilTool);
		right.add(brushTool);
		right.add(rectangleMarqueeTool);
		right.add(colPickerTool);
		
		Container c = getContentPane();
		c.add(right);

		setLayout(new BoxLayout(getContentPane(), BoxLayout.X_AXIS));
		setResizable(false);
		setClosable(false);
		setSize(200, 300);
		setBackground(Color.WHITE);
		setVisible(true);
	}

	private JButton createToolButton(String text) {
		JButton button = new JButton(text);
		button.addActionListener(this);
		return button;
	}
	
	private JButton createToolButton(ImageIcon icon) {
		JButton button = new JButton(icon);
		button.addActionListener(this);
		return button;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();
		if(source == brushTool) {
			currentToolType = ToolType.Brush;
		}
		else if(source == handTool) {
			currentToolType = ToolType.Hand;
		}
		else if(source == rectangleMarqueeTool) {
			currentToolType = ToolType.RectangleMarquee;
		}
		
		ctx.setTool(currentToolType);
	}
}