package org.lemon.frames;

import java.awt.Color;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JPanel;


public class InternalImageEditingToolsPanel extends JInternalFrame implements ActionListener{

	private static final long serialVersionUID = 1L;
	
	JButton penTool = new JButton("Pen");
	JButton button1 = new JButton("Pencil");
	JButton button2 = new JButton("Fill");
	JButton button3 = new JButton("Eraser");
	JButton colPicker = new JButton();
	
	//panels
	JPanel right;
	
	JPanel drawingCanvas;
	
	private Color col = new Color(0, 255, 0);
	
	public InternalImageEditingToolsPanel(JPanel drawingCanvas, Color col) {
		
		//this.col = col;
		this.drawingCanvas = drawingCanvas;
		
		right = new JPanel();
		
		right.setLayout(new BoxLayout(right, BoxLayout.Y_AXIS));
		
		setLayout(new BoxLayout(getContentPane(), BoxLayout.X_AXIS));
		
		Container c = getContentPane();
		
		setResizable(true);
		setClosable(false);
		setSize(200, 300);
		setVisible(true);
		setBackground(Color.WHITE);
		setLocation(30, 40);
		
		c.add(right);
		
		this.actions();
		this.settings();
		
		right.add(penTool);
		right.add(button1);
		right.add(this.colPicker);
		right.add(button2);
		right.add(button3);
		
	}

	private void settings() {
		this.colPicker.setBackground(this.col);
		this.colPicker.setSize(100, 100);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == colPicker) {
			this.col = new Color(255, 0, 0);
			this.settings();
			this.revalidate();
		}
	}
	
	private void actions() {
		colPicker.addActionListener(this);
	}

}
