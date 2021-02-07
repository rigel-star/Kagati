package org.lemon.gui.panels;

import java.awt.Color;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.HashMap;
import java.util.Map;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JInternalFrame;
import javax.swing.JPanel;
import javax.swing.JToolBar;

import org.lemon.gui.ColorPicker;
import org.lemon.gui.ImageView;
import org.lemon.gui.Workspace;
import org.lemon.gui.canvas.DrawingCanvasOnImage;
import org.lemon.gui.toolbars.BrushToolbar;
import org.lemon.tools.select.PolygonalSelectTool;

public class LemonToolsMenu extends JInternalFrame implements ActionListener {
	
	/**
	 * Serial UID
	 * */
	private static final long serialVersionUID = 1L;
	
	private JButton						pencilTool;
	private JButton 					brushTool;
	private JButton 					polySnappingTool;
	private JButton						cropTool;
	private JButton						colPickerTool;
	private JButton						handTool;
	
	private PolygonalSelectTool 		polyToolListener;
	private DrawingCanvasOnImage		brushToolListener;
	
	//panels
	private JPanel 						right;
	
	private Workspace 			workspace;
	private JInternalFrame[]			contexts;
	
	/**
	 * Tool bars related to each tool.
	 * */
	private Map<JButton, JToolBar> 		toolMap = new HashMap<>();
	
	public LemonToolsMenu( Workspace workspace ) {
		
		init();
		this.workspace = workspace;
		
		toolMap.put( brushTool, new BrushToolbar(null, null) );
		
		setLayout(new BoxLayout(getContentPane(), BoxLayout.X_AXIS));
		setResizable( true );
		setClosable( false );
		setSize( 200, 300 );
		setVisible( true );
		setBackground( Color.white );
		setLocation( 30, 40 );
		
		addAll();
		initListeners();
		
		Container c = getContentPane();
		c.add( right );
	}
	
	
	/**
	 * Init the widgets.
	 * */
	private void init() {
		
		this.handTool = new JButton();
		handTool.setIcon( new ImageIcon( "icons/tools/move.png" ));
		
		this.right = new JPanel();
		right.setLayout( new BoxLayout( right, BoxLayout.Y_AXIS ) );
		
		this.pencilTool = new JButton();
		pencilTool.setIcon( new ImageIcon( "icons/tools/pencil.png" ));
		
		this.polySnappingTool = new JButton();
		polySnappingTool.setIcon( new ImageIcon( "icons/tools/cutter.png" ));
		
		this.brushTool = new JButton();
		brushTool.setIcon( new ImageIcon( "icons/tools/brush.png" ));
		
		this.cropTool = new JButton();
		cropTool.setIcon( new ImageIcon( "icons/tools/crop.png" ));
		
		this.colPickerTool = new JButton( "COLOR" );
		var color = Color.BLACK;
		colPickerTool.setBackground( color );
		colPickerTool.setForeground( color );
	}
	
	
	/*add all buttons (tools) to menu*/
	private void addAll() {
		right.add( handTool );
		right.add( pencilTool );
		right.add( brushTool );
		right.add( polySnappingTool );
		right.add( cropTool );
		right.add( colPickerTool );
	}
	
	
	/*init all components listeners*/
	private void initListeners() {
		this.handTool.addActionListener(this);
		this.brushTool.addActionListener(this);
		this.polySnappingTool.addActionListener(this);
		this.colPickerTool.addActionListener( this );
	}

	
	@Override
	public void actionPerformed( ActionEvent e ) {
		
		ImageView view = null;
		this.contexts = workspace.getAllFrames();
		
		if( e.getSource() == handTool ) {
			
			for(JInternalFrame c: contexts ) {
				
				if( c instanceof ImageView ) {
					
				}
			}
		}
		
		else if( e.getSource() == brushTool ) {
			
			for( JInternalFrame c: contexts ) {
				if( c instanceof ImageView ) {
					view = (ImageView) c;
					var pan = view.getImagePanel();
					brushToolListener = new DrawingCanvasOnImage(pan, Color.black);
					ifImageView(pan, brushToolListener);
					view.revalidateListeners();
				} 
			}
		}
		
		else if(e.getSource() == polySnappingTool) {
			
			for(JInternalFrame c: contexts) {
				if(c instanceof ImageView) {
					view = (ImageView) c;
					var pan = view.getImagePanel();
					polyToolListener = new PolygonalSelectTool(pan.getImage(), view);
					ifImageView(pan, polyToolListener);
					view.revalidateListeners();
				}
			}
		}
		
		else if(e.getSource() == colPickerTool) {
			pickColor();
		}
		
		workspace.revalidate();
	}
	
	
	/**
	 * Change the global color of application including BrushTool, PencilTool color
	 * */
	private void pickColor() {
		//var col = JColorChooser.showDialog(parent, "Color Picker", Color.white);
		var colPick = new ColorPicker( "Color Picker" );
		var col = colPick.getColor();
		
		if( col != null ) {
			colPickerTool.setForeground( col );
			colPickerTool.setBackground( col );
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
		context.addMouseListener(tool);
	}
	
	
	/*apply poly snapping tool to the specific container*/
	private void applyPolySelectTool(JComponent container, PolygonalSelectTool tool) {
		container.addMouseListener( tool );
		container.addMouseMotionListener( tool );
	}
	
	
	/*remove each and every mouse listener which is applied to the specific container*/
	private void removePrevListeners( JComponent container ) {
		var l1 = container.getMouseListeners();
		var l2 = container.getMouseMotionListeners();
		
		for( MouseListener md: l1 ) {
			container.removeMouseListener( md );
		}
		for( MouseMotionListener md: l2 ) {
			container.removeMouseMotionListener( md );
		}
	}
}
