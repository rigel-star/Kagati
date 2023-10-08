package org.lemon.gui;

import java.awt.Color;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JPanel;

import org.lemon.tools.LemonTool;
import org.lemon.tools.LemonTool.ToolType;
import org.lemon.utils.Utils;

public class ToolsContainer extends JInternalFrame implements ActionListener {
	private JButton handTool;
	private JButton pencilTool;
	private JButton brushTool;
	private JButton rectangleMarqueeTool;
	private JButton	colPickerTool;

	// to notify tool has changed
	private PropertyChangeSupport toolChangeSupport = new PropertyChangeSupport(this);

	// currently selected tool
	// default is "Brush Tool"
	private LemonTool.ToolType currentlySelectedTool = ToolType.BRUSH;
	
	public ToolsContainer() {
		JPanel right = new JPanel();
		right.setLayout(new BoxLayout(right, BoxLayout.Y_AXIS));
		handTool = createToolButton(new ImageIcon("icons/tools/move.png"), this);
		pencilTool = createToolButton(new ImageIcon("icons/tools/pencil.png"), this);
		brushTool = createToolButton(new ImageIcon("icons/tools/brush.png"), this);
		rectangleMarqueeTool = createToolButton(Utils.resizeIconToStdKagatiIconSize(new ImageIcon("icons/tools/select64.png")), this);
		colPickerTool = createToolButton("Color", this);
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
	
	private JButton createToolButton(String text, ActionListener listener) {
		JButton button = new JButton(text);
		button.addActionListener(listener);
		return button;
	}
	
	private JButton createToolButton(ImageIcon icon, ActionListener listener) {
		JButton button = new JButton(icon);
		button.addActionListener(listener);
		return button;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();
		if(source == brushTool) {
			currentlySelectedTool = ToolType.BRUSH;
		}
		else if(source == handTool) {
			currentlySelectedTool = ToolType.HAND;
		}
		else if(source == rectangleMarqueeTool) {
			currentlySelectedTool = ToolType.RECT_MARQUEE;
		}
		toolChangeSupport.firePropertyChange("currentlySelectedTool", null, currentlySelectedTool);
	}

	public void addToolChangeListener(ToolsContainer.ToolChangeListener listener) {
		toolChangeSupport.addPropertyChangeListener("currentlySelectedTool", listener);
	}

	public void removeToolChangeListener(ToolsContainer.ToolChangeListener listener) {
		toolChangeSupport.removePropertyChangeListener("currentlySelectedTool", listener);
	}

	public static interface ToolChangeListener extends PropertyChangeListener {
		@Override
		default void propertyChange(PropertyChangeEvent evt) {
			Object newTool = evt.getNewValue();
			if(newTool instanceof LemonTool.ToolType lemonTool) {
				toolChange(lemonTool);
			}
		}

		public void toolChange(LemonTool.ToolType newTool);
	}
}