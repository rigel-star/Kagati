package org.lemon.gui;

import java.awt.Color;
import java.awt.Container;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JInternalFrame;
import javax.swing.JPanel;

import org.lemon.gui.image.ImagePanel;
import org.lemon.gui.toolbars.BrushToolbar;
import org.lemon.gui.toolbars.FileToolbar;
import org.lemon.tools.BrushTool;
import org.lemon.tools.BrushTool.BrushType;
import org.lemon.tools.LemonTool.ToolType;
import org.lemon.tools.brush.BrushToolListener;

public class ToolsContainer extends JInternalFrame implements ActionListener {
	private static final long serialVersionUID = 1L;
	
	private Workspace workspace;
	
	private JButton handTool;
	private JButton pencilTool;
	private JButton polySnapTool;
	private JButton brushTool;
	private JButton cropTool;
	private JButton	colPickerTool;
	
	public ToolsContainer(Workspace workspace) {
		this.workspace = workspace;
		
		JPanel right = new JPanel();
		right.setLayout(new BoxLayout(right, BoxLayout.Y_AXIS));
		
		handTool = createToolButton(new ImageIcon("icons/tools/move.png"), this);
		pencilTool = createToolButton(new ImageIcon("icons/tools/pencil.png"), this);
		polySnapTool = createToolButton(new ImageIcon("icons/tools/cutter.png"), this);
		brushTool = createToolButton(new ImageIcon("icons/tools/brush.png"), this);
		cropTool = createToolButton(new ImageIcon("icons/tools/crop.png"), this);
		colPickerTool = createToolButton("Color", this);
		
		colPickerTool.setBackground(Color.BLACK);
		colPickerTool.setForeground(Color.BLACK);
		
		right.add(handTool);
		right.add(pencilTool);
		right.add(brushTool);
		right.add(polySnapTool);
		right.add(cropTool);
		right.add(colPickerTool);
		
		setLayout(new BoxLayout(getContentPane(), BoxLayout.X_AXIS));
		setResizable( true );
		setClosable( false );
		setSize( 200, 300 );
		setVisible( true );
		setBackground( Color.white );
		setLocation( 30, 40 );
		
		Container c = getContentPane();
		c.add( right );
	}
	
	private JButton createToolButton(String text, ActionListener listener)
	{
		JButton button = new JButton(text);
		button.addActionListener(listener);
		return button;
	}
	
	private JButton createToolButton(ImageIcon icon, ActionListener listener)
	{
		JButton button = new JButton(icon);
		button.addActionListener(listener);
		return button;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) 
	{
		JInternalFrame frames[] = this.workspace.getWorkspaceArena().getAllFrames();
		Object source = e.getSource();
		
		for(JInternalFrame frame: frames) {
			if(frame instanceof ImageView) {
				ImageView view = (ImageView) frame;
				ImagePanel panel = view.getImagePanel();
				
				if(source == brushTool)
					applyBrushTool(panel, view.getDrawable(), BrushType.NORMAL);
				else if(source == pencilTool)
					applyBrushTool(panel, view.getDrawable(), BrushType.PENCIL);
				else if(source == handTool)
					applyHandTool(panel);
			}
		}
	}
	
	private void applyHandTool(JComponent component)
	{
		JPanel toolbar = workspace.getToolbarContainer();
		if(toolbar.getComponent(0) instanceof FileToolbar)
			return;
		
		toolbar.remove(0);
		toolbar.add(new FileToolbar(workspace.getWorkspaceArena(), workspace.getLayerContainer()), 0);
		toolbar.revalidate();
		toolbar.repaint();
		
		removeMouseListeners(component);
		workspace.getWorkspaceArena().setGlobalLemonTool(ToolType.HAND);
	}
	
	private void applyBrushTool(JComponent component, Graphics2D context, BrushType type)
	{
		JPanel toolbar = workspace.getToolbarContainer();
		if(toolbar.getComponent(0) instanceof BrushToolbar)
			return;
		
		removeMouseListeners(component);
		
		BrushTool btool = new BrushTool.Builder(context, type).build();
		new BrushToolListener(component, btool);
		
		toolbar.remove(0);
		toolbar.add(new BrushToolbar(workspace.getWorkspaceArena()), 0);
		toolbar.revalidate();
		toolbar.repaint();
		workspace.getWorkspaceArena().setGlobalLemonTool(ToolType.BRUSH);
	}
	
	private void removeMouseListeners(JComponent component)
	{
		for(MouseListener ml: component.getMouseListeners())
		{
			component.removeMouseListener(ml);
		}
		
		for(MouseMotionListener mml: component.getMouseMotionListeners())
		{
			component.removeMouseMotionListener(mml);
		}
	}
}