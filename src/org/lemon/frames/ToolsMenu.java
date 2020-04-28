package org.lemon.frames;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import org.lemon.drawing.PlainDrawingPanel;
import org.lemon.image.ImagePanel;
import org.lemon.image.ImagePanel.PanelMode;
import org.lemon.image.ImageView;
import org.piksel.piksel.PPInternalWindow;

import application.MainBackgroundPane;


public class ToolsMenu extends JInternalFrame implements ActionListener{

	private static final long serialVersionUID = 1L;
	
	private JButton 		brushTool;
	private JButton 		snappingTool;
	
	
	//panels
	JPanel right;
	
	private Component 		target;
	MainBackgroundPane 		parent;
	
	/**
	 * Tools menu for editing all sorts of canvas on application.
	 * <p>
	 * Only ImageView for now.
	 * */
	public ToolsMenu(MainBackgroundPane parent, Color col) {
		
		//this.col = col;
		this.parent = parent;
		
		right = new JPanel();
		
		right.setLayout(new BoxLayout(right, BoxLayout.Y_AXIS));
		
		setLayout(new BoxLayout(getContentPane(), BoxLayout.X_AXIS));
		
		setResizable(true);
		setClosable(false);
		setSize(200, 300);
		setVisible(true);
		setBackground(Color.white);
		setLocation(30, 40);
		
		init();
		initListeners();
		
		Container c = getContentPane();
		c.add(right);
		
		right.add(brushTool);
		right.add(snappingTool);
		
	}
	
	
	/*init all components*/
	private void init() {
		
		//default brush tool
		brushTool = new JButton();
		brushTool.setIcon(new ImageIcon("icons/tools/brush.png"));
		
		//image snapping tool i.e cutter tool
		snappingTool = new JButton();
		snappingTool.setIcon(new ImageIcon("icons/tools/cutter.png"));
		
	}
	
	
	/*init all components listeners*/
	private void initListeners() {
		this.brushTool.addActionListener(this);
		this.snappingTool.addActionListener(this);
	}

	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		ImageView view = null;
		PlainDrawingPanel draw = null;
		PPInternalWindow pixel = null;
		
		if(parent == null)
			return;
		else {
			if(parent.getSelectedFrame() != null)
				target = parent.getSelectedFrame();
			else {
				JOptionPane.showMessageDialog(this, "Choose panel to work with");
				return;
			}
		}
		
		if(target instanceof ImageView) {
			view = (ImageView) target;
			
			System.out.println(view.getImagePanel().getPanelMode());
			ImagePanel pan = view.getImagePanel();
			pan.setPanelMode(PanelMode.CANVAS_MODE);
			pan.revalidate();
			
			System.out.println(view.getImagePanel().getPanelMode());
			return;
		} 
		else if(target instanceof PlainDrawingPanel) {
			draw = (PlainDrawingPanel) target;
			draw.setTitle("New title");
			draw.revalidate();
			return;
		}
		else if(target instanceof PPInternalWindow) {
			pixel = (PPInternalWindow) target;
			pixel.setTitle("Hello shit");
			pixel.revalidate();
			return;
		}
		
	}

}
