package org.lemon.gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Ellipse2D;
import java.util.List;

import javax.swing.JDesktopPane;
import javax.swing.JInternalFrame;

import org.lemon.gui.node.Node;
import org.lemon.tools.LemonTool;
import org.lemon.tools.LemonTool.ToolType;

public class WorkspaceArena extends JDesktopPane implements ComponentListener 
{
	private static final long serialVersionUID = 1L;
	
	private LemonTool.ToolType globalLemonTool = ToolType.HAND;
	private Color globalLemonColor;
	
	public WorkspaceArena() {
		setSize(5000, 5000);
		setBackground(new Color(160, 160, 160));
		setVisible(true);
		addComponentListener(this);
		
		MouseHandler mh = new MouseHandler();
		addMouseListener(mh);
		addMouseMotionListener(mh);
	}
	
	@Override
	public void paintComponent(Graphics g) 
	{
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		
		final int nodePointSize = 10;
		JInternalFrame frames[] = getAllFrames();
		
		for(JInternalFrame frame: frames)
		{
			final Point framePosition = frame.getLocation();
			final int frameWidth = frame.getWidth();
			final int frameHeight = frame.getHeight();
			
			final int receiveStartX = framePosition.x - (nodePointSize >> 1);
			final int receiveStartY = framePosition.y + (frameHeight >> 1);
			int receiveCurrentX = receiveStartX;
			int receiveCurrentY = receiveStartY;
			
			final int sendStartX = framePosition.x + frameWidth - (nodePointSize >> 1);
			final int sendStartY = framePosition.y + (frameHeight >> 1);
			int sendCurrentX = sendStartX;
			int sendCurrentY = sendStartY;
			
			if(frame instanceof NodeView)
			{
				NodeView view = (NodeView) frame;
				List<Node> senders = view.getSenderNodes();
				for(Node node: senders)
				{
					g2d.setPaint(node.getColor());
					g2d.fill(new Ellipse2D.Double(sendCurrentX, sendCurrentY, nodePointSize, nodePointSize));
					sendCurrentY += nodePointSize + 5;
				}
				
				List<Node> receivers = view.getReceiverNodes();
				for(Node node: receivers)
				{
					g2d.setPaint(node.getColor());
					g2d.fill(new Ellipse2D.Double(receiveCurrentX, receiveCurrentY, nodePointSize, nodePointSize));
					receiveCurrentY += nodePointSize + 5;
				}
			}
		}
	}
	
	public LemonTool.ToolType getGlobalLemonTool()
	{
		return globalLemonTool;
	}
	
	public Color getGlobalLemonColor()
	{
		return globalLemonColor;
	}
	
	public void setGlobalLemonTool(LemonTool.ToolType tool)
	{
		this.globalLemonTool = tool;
	}
	
	public void setGlobalLemonColor(Color color)
	{
		this.globalLemonColor = color;
	}

	@Override
	public void componentResized(ComponentEvent e) 
	{
		
	}
	
	@Override
	public void componentMoved(ComponentEvent e) 
	{
		repaint();
	}
	
	@Override
	public void componentShown(ComponentEvent e) 
	{
		
	}

	@Override
	public void componentHidden(ComponentEvent e) 
	{
		
	}
	
	class MouseHandler extends MouseAdapter 
	{
		
		public MouseHandler() {}
		
		@Override
		public void mousePressed(MouseEvent e) 
		{
			super.mousePressed(e);
		}
		
		@Override
		public void mouseDragged(MouseEvent e) 
		{
			super.mouseDragged(e);
		}
		
		@Override
		public void mouseReleased(MouseEvent e) 
		{
			super.mouseReleased(e);
			repaint();
		}	
	}
}
