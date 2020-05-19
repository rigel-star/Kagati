package org.lemon.gui.panels;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import org.lemon.gui.drawing.image.DrawingCanvasOnImage;
import org.lemon.gui.image.ImageView;
import org.lemon.tools.select.PolygonalSelectTool;

import application.MainApplicationScene;


public class LemonToolsMenu extends JInternalFrame implements ActionListener{

	private static final long serialVersionUID = 1L;
	
	
	private JButton						pencilTool;
	private JButton 					brushTool;
	private JButton 					polySnappingTool;
	private JButton						cropTool;
	
	
	
	private PolygonalSelectTool 		polyToolListener;
	private DrawingCanvasOnImage		brushToolListener;

	
	
	//panels
	private JPanel 						right;
	
	private Component 					target;
	MainApplicationScene 				parent;
	
	/**
	 * Tools menu for editing all sorts of canvas on application.
	 * <p>
	 * Only ImageView for now.
	 * */
	public LemonToolsMenu(MainApplicationScene parent, Color col) {
		
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
		
		addAll();
		initListeners();
		
		Container c = getContentPane();
		c.add(right);
		
	}
	
	
	/*add all buttons (tools) to menu*/
	private void addAll() {
		right.add(createPencilTool());
		right.add(createBrushTool());
		right.add(createPolySelectTool());
		right.add(createCropTool());
	}
	
	
	
	/*create pencil tool button*/
	private JButton createPencilTool() {
		this.pencilTool = new JButton();
		pencilTool.setIcon(new ImageIcon("icons/tools/pencil.png"));
		return pencilTool;
	}
	
	
	/*create polygonal selection tool button*/
	private JButton createPolySelectTool() {
		this.polySnappingTool = new JButton();
		polySnappingTool.setIcon(new ImageIcon("icons/tools/cutter.png"));
		return polySnappingTool;
	}
	
	
	/*create brush tool button*/
	private JButton createBrushTool() {
		this.brushTool = new JButton();
		brushTool.setIcon(new ImageIcon("icons/tools/brush.png"));
		return brushTool;
	}
	
	
	/*create crop tool button*/
	private JButton createCropTool() {
		this.cropTool = new JButton();
		cropTool.setIcon(new ImageIcon("icons/tools/crop.png"));
		return cropTool;
	}
	
	
	/*init all components listeners*/
	private void initListeners() {
		this.brushTool.addActionListener(this);
		this.polySnappingTool.addActionListener(this);
	}

	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		ImageView view = null;
		
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
		
		if(e.getSource() == this.brushTool) {
			if(target instanceof ImageView) {
				view = (ImageView) target;
				var pan = view.getImagePanel();
				brushToolListener = new DrawingCanvasOnImage(pan);
				ifImageView(pan, brushToolListener);
				view.revalidateListeners();
				return;
			} 
		}
		
		else if(e.getSource() == this.polySnappingTool) {
			if(target instanceof ImageView) {
				view = (ImageView) target;
				var pan = view.getImagePanel();
				polyToolListener = new PolygonalSelectTool(pan.getImage(), pan);
				ifImageView(pan, polyToolListener);
				view.revalidateListeners();
				return;
			}
		}
		
	}
	
	
	/*if the context is imageview*/
	private void ifImageView(JComponent context, Object adp) {
		removeAllListeners(context);
		
		if(adp instanceof DrawingCanvasOnImage) {
			applyBrushTool(context, (DrawingCanvasOnImage) adp);
		}
		else if(adp instanceof PolygonalSelectTool) {
			applyPolySelectTool(context, (PolygonalSelectTool) adp);
		}
		
		context.revalidate();
	}
	
	
	/*apply brush tool to the specific container*/
	private void applyBrushTool(JComponent container, DrawingCanvasOnImage tool) {
		container.addMouseMotionListener(tool);
	}
	
	
	/*apply poly snapping tool to the specific container*/
	private void applyPolySelectTool(JComponent container, PolygonalSelectTool tool) {
		container.addMouseListener(tool);
		container.addMouseMotionListener(tool);
	}
	
	
	/*remove each and every mouse listener which is applied to the specific container*/
	private void removeAllListeners(JComponent container) {
		var l1 = container.getMouseListeners();
		var l2 = container.getMouseMotionListeners();
		
		for(int i=0; i<l1.length; i++) {
			container.removeMouseListener(l1[i]);
		}
		for(int i=0; i<l2.length; i++) {
			container.removeMouseMotionListener(l2[i]);
		}
	}

}
