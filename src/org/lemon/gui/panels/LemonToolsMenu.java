package org.lemon.gui.panels;

import java.awt.Color;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JComponent;
import javax.swing.JInternalFrame;
import javax.swing.JPanel;
import javax.swing.JToolBar;

import org.lemon.gui.drawing.canvas.DrawingPanel;
import org.lemon.gui.drawing.image.DrawingCanvasOnImage;
import org.lemon.gui.image.ImageView;
import org.lemon.gui.toolbars.BrushToolBar;
import org.lemon.tools.BrushTool;
import org.lemon.tools.LemonTool;
import org.lemon.tools.brush.BrushToolAdapter;
import org.lemon.tools.select.PolygonalSelectTool;

import application.ApplicationFrame;
import application.Workspace;


public class LemonToolsMenu extends JInternalFrame implements ActionListener{

	private static final long serialVersionUID = 1L;
	
	
	private JButton						pencilTool;
	private JButton 					brushTool;
	private JButton 					polySnappingTool;
	private JButton						cropTool;
	private JButton						colPickerTool;
	
	
	
	private PolygonalSelectTool 		polyToolListener;
	private DrawingCanvasOnImage		brushToolListener;

	
	
	//panels
	private JPanel 						right;
	
	private ApplicationFrame 			parent;
	private JInternalFrame[]			contexts;
	
	
	/**
	 * All toolbars of application stored in a list.
	 * */
	private Map<JButton, JToolBar> 		toolMap = new HashMap<>();
	
	
	/**
	 * All tools of application stored in a list.
	 * */
	private List<LemonTool> 			tools = new ArrayList<>();
	
	
	/**
	 * Tools menu for editing all sorts of canvas on application.
	 * <p>
	 * Only ImageView for now.
	 * */
	public LemonToolsMenu(ApplicationFrame parent) {
		
		//this.col = col;
		this.parent = parent;
		this.initTools();
		
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
		initToolMap();
		
		Container c = getContentPane();
		c.add(right);
		
	}
	
	
	/*add all buttons (tools) to menu*/
	private void addAll() {
		right.add(createPencilTool());
		right.add(createBrushTool());
		right.add(createPolySelectTool());
		right.add(createCropTool());
		right.add(createColorPickerTool());
	}
	
	
	
	private void initTools() {
		tools.add(new BrushToolAdapter());
	}
	
	
	
	private void initToolMap() {
		toolMap.put(brushTool, new BrushToolBar(parent.getGlobalProperties(), null));
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
	
	
	/*create color picker tool*/
	private JButton createColorPickerTool() {
		this.colPickerTool = new JButton("COLOR");
		
		var color = this.parent.getGlobalProperties().getGLobalColor();
		colPickerTool.setBackground(color);
		colPickerTool.setForeground(color);
		
		return colPickerTool;
	}
	
	
	
	/*init all components listeners*/
	private void initListeners() {
		this.brushTool.addActionListener(this);
		this.polySnappingTool.addActionListener(this);
		this.colPickerTool.addActionListener(this);
	}

	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		ImageView view = null;
		Workspace workspace = parent.getMainScene();
		this.contexts = workspace.getAllFrames();
		
		if(e.getSource() == this.brushTool) {
			
			for(JInternalFrame c: contexts) {
				if(c instanceof ImageView) {
					view = (ImageView) c;
					var pan = view.getImagePanel();
					brushToolListener = new DrawingCanvasOnImage(pan, parent.getGlobalProperties().getGLobalColor());
					ifImageView(pan, brushToolListener);
					view.revalidateListeners();
				} 
			}
		}
		
		else if(e.getSource() == this.polySnappingTool) {
			
			for(JInternalFrame c: contexts) {
				if(c instanceof ImageView) {
					view = (ImageView) c;
					var pan = view.getImagePanel();
					polyToolListener = new PolygonalSelectTool(pan.getImage(), pan);
					ifImageView(pan, polyToolListener);
					view.revalidateListeners();
				}
			}
		}
		
		else if(e.getSource() == this.colPickerTool) {
			pickColor();
		}
		
		parent.revalidate();
		
	}
	
	
	
	/**
	 * Change the global color of application including BrushTool, PencilTool color
	 * */
	private void pickColor() {
		var col = JColorChooser.showDialog(parent, "Color Picker", Color.white);
		parent.getGlobalProperties().setGlobalColor(col);
		colPickerTool.setForeground(col);
		colPickerTool.setBackground(col);
		
		var tool = parent.getTool();
		
		/*if the currently selected tool is brush*/
		if(tool instanceof BrushTool) {
			
			var t = (BrushTool) tool;
			t.setStrokeColor(col);
			
			/*for all the frame of workspace*/
			for(JInternalFrame c: contexts) {
				
				/*if the frame of workspace is ImageView*/
				if(c instanceof ImageView) {
					var view = (ImageView) c;
					var pan = view.getImagePanel();
					pan.getCanvasModeListener().getBrush().setStrokeColor(col);
					pan.revalidate();
				}
				/*else if frame is DrawingPanel*/
				else if(c instanceof DrawingPanel) {
					System.out.println("Drawing panel");
				}
				
			}
			
		}
	}
	
	
	
	/*if the context is imageview*/
	private void ifImageView(JComponent context, Object adp) {
		removePrevListeners(context);
		
		if(adp instanceof DrawingCanvasOnImage) {
			applyBrushTool(context, (DrawingCanvasOnImage) adp);
		}
		else if(adp instanceof PolygonalSelectTool) {
			applyPolySelectTool(context, (PolygonalSelectTool) adp);
		}
		
		context.revalidate();
	}
	
	
	/*apply brush tool to the specific container*/
	private void applyBrushTool(JComponent context, DrawingCanvasOnImage tool) {
		context.addMouseMotionListener(tool);
	}
	
	
	/*apply poly snapping tool to the specific container*/
	private void applyPolySelectTool(JComponent container, PolygonalSelectTool tool) {
		container.addMouseListener(tool);
		container.addMouseMotionListener(tool);
	}
	
	
	/*remove each and every mouse listener which is applied to the specific container*/
	private void removePrevListeners(JComponent container) {
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
