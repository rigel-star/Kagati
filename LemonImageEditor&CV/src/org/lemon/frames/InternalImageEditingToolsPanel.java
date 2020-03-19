package org.lemon.frames;

import java.awt.Color;
import java.awt.Container;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JPanel;

public class InternalImageEditingToolsPanel extends JInternalFrame{

	private static final long serialVersionUID = 1L;
	
	JButton button0 = new JButton("Pen");
	JButton button1 = new JButton("Pencil");
	JButton button2 = new JButton("Fill");
	JButton button3 = new JButton("Eraser");
	
	JPanel right, left;
	
	public InternalImageEditingToolsPanel() {
		
		right = new JPanel();
		left = new JPanel();
		
		right.setLayout(new BoxLayout(right, BoxLayout.Y_AXIS));
		left.setLayout(new BoxLayout(left, BoxLayout.Y_AXIS));
		
		setLayout(new BoxLayout(getContentPane(), BoxLayout.X_AXIS));
		
		Container c = getContentPane();
		
		setResizable(true);
		setClosable(false);
		setSize(200, 300);
		setVisible(true);
		setBackground(Color.WHITE);
		setLocation(30, 40);
		
		c.add(right);
		c.add(left);
		
		right.add(button0);
		right.add(button1);
		left.add(button2);
		left.add(button3);
		
	}
	

}
