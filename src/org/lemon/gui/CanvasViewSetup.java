package org.lemon.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.lemon.gui.image.ImagePanel.PanelMode;

public class CanvasViewSetup extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1L;

	//height and width text field
	private JTextField name, width, height;
	//ok and cancel button
	private JButton okBttn, cnclBtn;
	//button panel
	private JPanel btnPanel;
	//edit panel
	private JPanel controlPanel;
	//combo box
	private JComboBox<String> colors;
	
	//msg
	private JLabel msg;
	
	//canvas
	private Workspace workspace;
	
	public CanvasViewSetup( Workspace workspace) {
		this.workspace = workspace;
		this.init();
		setTitle( "New Page" );
		setResizable( false );
		setSize(new Dimension( 400, 150 ));
		setDefaultCloseOperation( DISPOSE_ON_CLOSE );
		setVisible( true );
		setLocation( 100, 100 );
		
		Container c = this.getContentPane();
		c.setLayout( new BorderLayout() );
		
		this.btnPanel.add( colors );
		this.btnPanel.add( okBttn );
		this.btnPanel.add( cnclBtn );
		
		//adding text fields
		this.controlPanel.add(this.name);
		this.controlPanel.add(this.width);
		this.controlPanel.add(this.height);
		
		c.add(this.msg, BorderLayout.NORTH);
		c.add(this.btnPanel, BorderLayout.SOUTH);
		c.add(this.controlPanel, BorderLayout.CENTER);
		//c.add(this.pixelControlPanel, BorderLayout.CENTER);
	}
	
	//init widgets
	private void init() {
		this.name = new JTextField("Name");
		this.msg = new JLabel("Enter info");
		this.width = new JTextField("Enter width here");
		this.height = new JTextField("Enter height here");
		this.okBttn = new JButton("OK");
		this.cnclBtn = new JButton("Cancel");
		this.btnPanel = new JPanel(new FlowLayout());
		this.controlPanel = new JPanel(new FlowLayout());
		
		//setting action listeners
		this.okBttn.addActionListener(this);
		this.cnclBtn.addActionListener(this);
		
		//color list to choose from
		String[] colors = {"Black", "White", "Red", "Blue", "Pink"};
		this.colors = new JComboBox<String>(colors);
	}

	@Override
	public void actionPerformed( ActionEvent e ) {
		
		if( e.getSource() == okBttn ) {
			
			int h = Integer.parseInt( width.getText() );
			int w = Integer.parseInt( height.getText() );
			String title = name.getText().toString();
			
			CanvasView cv = new CanvasView( w, h, Color.white, title, true, PanelMode.canvasMode );
			
			this.workspace.add( cv );
			this.workspace.revalidate();
			
			dispose();
		}
		
		else if( e.getSource() == cnclBtn ) {
			dispose();
		}
		
	}

}
