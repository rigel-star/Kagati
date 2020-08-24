package org.lemon.gui.canvas;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JComboBox;
import javax.swing.JInternalFrame;
import javax.swing.JPanel;

public class DrawingPanel extends JInternalFrame implements ActionListener {

	private static final long serialVersionUID = 1L;

	private DrawingCanvas drawPanel;
	private JPanel shapesPanel;
	private JComboBox<String> shapes;
	private JComboBox<String> colors;
	
	private Map<String, Integer> shapesMap;
	private Map<String, Color> colorsMap;
	
	public DrawingPanel(String title, int w, int h) {
		this.shapesMap = new HashMap<String, Integer>();
		this.colorsMap = new HashMap<String, Color>();
		this.shapesPanel = new JPanel(new FlowLayout());
		this.init();
		
		setTitle(title);
		setSize(w, h);
		setBackground(Color.WHITE);
		setVisible(true);
		setClosable(true);
		
		drawPanel = new DrawingCanvas(this.colorsMap.get(this.colors.getSelectedItem()),
									this.shapesMap.get(this.shapes.getSelectedItem()));
		this.drawPanel.setShape(1);
		
		this.shapesPanel.setLayout(new FlowLayout());
		this.shapesPanel.add(this.shapes);
		this.shapesPanel.add(this.colors);
		
		Container c = this.getContentPane();
		c.setLayout(new BorderLayout());
		c.add(drawPanel, BorderLayout.CENTER);
		c.add(this.shapesPanel, BorderLayout.SOUTH);
	}
	
	private void init() {
		String[] s = {
				"Rectangle", "Line"
		};
		int[] sh = {
				1, 2
		};
		
		String[] c = {
				"Black", "Red", "Green", "Blue"
		};
		Color[] ch = {
				new Color(0, 0, 0),
				new Color(255, 0, 0),
				new Color(0, 255, 0),
				new Color(0, 0, 255)
		};
		
		this.shapes = new JComboBox<String>(s);
		this.colors = new JComboBox<String>(c);
		
		this.shapes.addActionListener(this);
		
		for(int i=0; i<s.length; i++) {
			this.shapesMap.put(s[i], sh[i]);
		}
		for(int i=0; i<c.length; i++) {
			this.colorsMap.put(c[i], ch[i]);
		}
	}

	
	
	public Canvas getCanvas() {
		return drawPanel;
	}
	
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == this.shapes) {
			this.drawPanel.setShape(1);
			//this.drawPanel.setShape(this.shapesMap.get(this.shapes.getSelectedItem()));
			revalidate();
		}
		else if(e.getSource() == this.colors) {
			//this.drawPanel.setColor(this.colorsMap.get(this.colors.getSelectedItem()));
			revalidate();
		}
	}

}
