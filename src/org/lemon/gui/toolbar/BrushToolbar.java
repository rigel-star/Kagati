package org.lemon.gui.toolbar;

import java.awt.FlowLayout;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JInternalFrame;
import javax.swing.JPanel;

import org.lemon.gui.AbstractToolBar;
import org.lemon.gui.ImageView;
import org.lemon.gui.WorkspaceArena;
import org.lemon.tools.BrushTool;
import org.lemon.tools.BrushTool.BrushType;
import org.lemon.tools.brush.BrushToolListener;

public class BrushToolbar extends AbstractToolBar implements ActionListener {
	private static final long serialVersionUID = 1L;
	
	private WorkspaceArena workspace;
	private JButton normalBrush, softBrush, wobbleBrush, zigzagBrush;
	
	private BrushTool.BrushType currentBrush = BrushType.NORMAL;

	public BrushToolbar() {

	}
	
	public BrushToolbar(final WorkspaceArena workspace) {
		this.workspace = workspace;
		
		normalBrush = new JButton("Normal");
		normalBrush.addActionListener(this);
		
		softBrush = new JButton("Soft");
		softBrush.addActionListener(this);
		
		wobbleBrush = new JButton("Wobble");
		wobbleBrush.addActionListener(this);
		
		zigzagBrush = new JButton("Zigzag");
		zigzagBrush.addActionListener(this);
		
		JPanel bttnPanel = new JPanel();
		bttnPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 2, 2));
		
		bttnPanel.add(normalBrush);
		bttnPanel.add(softBrush);
		bttnPanel.add(wobbleBrush);
		bttnPanel.add(zigzagBrush);
		
		add(bttnPanel);
		
		// setRollover(true);
		// setFloatable(false);
	}
	
	public BrushTool.BrushType getCurrentBrushType()
	{
		return this.currentBrush;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		JInternalFrame frames[] = this.workspace.getAllFrames();
		Object source = e.getSource();
		
		for(JInternalFrame frame: frames) {
			if(frame instanceof ImageView) {
				ImageView view = (ImageView) frame;
				if(source == normalBrush)
					applyBrushToolListener(view, view.getDrawable(), BrushType.NORMAL);
				if(source == softBrush)
					applyBrushToolListener(view, view.getDrawable(), BrushType.SOFT);
				if(source == wobbleBrush)
					applyBrushToolListener(view, view.getDrawable(), BrushType.WOBBLE);
				if(source == zigzagBrush)
					applyBrushToolListener(view, view.getDrawable(), BrushType.ZIGZAG);
			}
		}
	}
	
	private void applyBrushToolListener(ImageView component, Graphics2D context, BrushType type)
	{
		currentBrush = type;
		removeMouseListeners(component);
		
		BrushTool btool = new BrushTool.Builder(context, type).build();
		new BrushToolListener(component, btool);
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