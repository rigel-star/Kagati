package org.lemon.drawing;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class NewDrawingPanelSetup extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1L;

	//height and width text field
	private JTextField width, height;
	//ok and cancel button
	private JButton okBttn, cnclBtn;
	//button panel
	private JPanel btnPanel;
	//edit panel
	private JPanel controlPanel, pixelControlPanel;
	private JTextField pixHeight, pixWidth;
	//combo box
	private JComboBox<String> colors;
	//pixel checkbox
	private JCheckBox showPixels;
	
	//msg
	private JLabel msg;
	
	//canvas
	private JDesktopPane pane;
	
	public NewDrawingPanelSetup(JDesktopPane pane) {
		this.pane = pane;
		this.init();
		setTitle("New Drawing Page");
		setResizable(false);
		setSize(new Dimension(400, 200));
		setDefaultCloseOperation(NewDrawingPanelSetup.DISPOSE_ON_CLOSE);
		setVisible(true);
		
		Container c = this.getContentPane();
		c.setLayout(new BorderLayout());
		
		//adding buttons
		this.btnPanel.add(this.showPixels);
		this.btnPanel.add(this.colors);
		this.btnPanel.add(this.okBttn);
		this.btnPanel.add(this.cnclBtn);
		
		//adding text fields
		this.controlPanel.add(this.width);
		this.controlPanel.add(this.height);
		
		this.pixelControlPanel.add(this.pixHeight);
		this.pixelControlPanel.add(this.pixWidth);
		
		c.add(this.msg, BorderLayout.NORTH);
		c.add(this.btnPanel, BorderLayout.SOUTH);
		c.add(this.controlPanel, BorderLayout.CENTER);
		//c.add(this.pixelControlPanel, BorderLayout.CENTER);
	}
	
	//init widgets
	private void init() {
		this.msg = new JLabel("Enter pixel width and height");
		this.width = new JTextField("Enter width here");
		this.height = new JTextField("Enter height here");
		this.pixHeight = new JTextField("Height");
		this.pixWidth = new JTextField("Width");
		this.okBttn = new JButton("OK");
		this.cnclBtn = new JButton("Cancel");
		this.btnPanel = new JPanel(new FlowLayout());
		this.controlPanel = new JPanel(new FlowLayout());
		this.pixelControlPanel = new JPanel(new FlowLayout());
		this.okBttn.addActionListener(this);
		
		//pixels check box
		this.showPixels = new JCheckBox("Show Pixels");
		this.showPixels.addActionListener(this);
		//color list to choose from
		String[] colors = {"Black", "White", "Red", "Blue", "Pink"};
		this.colors = new JComboBox<String>(colors);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == this.okBttn) {
			int h = Integer.parseInt(this.width.getText());
			int w = Integer.parseInt(this.height.getText());
			this.pane.add(new PlainDrawingPanel(new Color(255, 0, 0), w, h));
			this.pane.revalidate();
			this.dispose();
		}
		else if(e.getSource() == this.showPixels) {
			this.add(this.pixelControlPanel, BorderLayout.CENTER);
			this.revalidate();
		}
	}
	
}
