package org.lemon.gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Ellipse2D;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JDesktopPane;
import javax.swing.JInternalFrame;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;

import org.lemon.gui.layer.FilterLayer;
import org.lemon.gui.layer.ViewLayer;
import org.lemon.gui.node.Node;
import org.lemon.tools.BrushTool;
import org.lemon.tools.LemonTool;
import org.lemon.tools.LemonTool.ToolType;
import org.lemon.tools.brush.BrushToolListener;

public class WorkspaceArena extends JDesktopPane
{
	private static final long serialVersionUID = 1L;
	
	private LemonTool.ToolType globalLemonTool = ToolType.HAND;
	private Color globalLemonColor;

	private Map<NodeView, Layer> viewLayerMapping = new HashMap<>();
	private LayerContainer layerContainer = null;
	
	public WorkspaceArena(LayerContainer layerContainer) {
		this.layerContainer = layerContainer;
		setSize(5000, 5000);
		setBackground(new Color(160, 160, 160));
		setVisible(true);
		addComponentListener(new WorkspaceArenaComponentHandler());
		
		MouseHandler mh = new MouseHandler();
		addMouseListener(mh);
		addMouseMotionListener(mh);
	}
	
	@Override
	public void paintComponent(Graphics g) 
	{
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		final int nodePointSize = 12;
		JInternalFrame frames[] = getAllFrames();
		
		for(JInternalFrame frame: frames)
		{
			final Point framePosition = frame.getLocation();
			final int frameWidth = frame.getWidth();
			// final int frameHeight = frame.getHeight();
			
			final int receiveStartX = framePosition.x - (nodePointSize >> 1);
			final int receiveStartY = framePosition.y + 30;
			int receiveCurrentX = receiveStartX;
			int receiveCurrentY = receiveStartY;
			
			final int sendStartX = framePosition.x + frameWidth - (nodePointSize >> 1);
			final int sendStartY = framePosition.y + 30;
			int sendCurrentX = sendStartX;
			int sendCurrentY = sendStartY;
			
			if(frame instanceof NodeView)
			{
				NodeView view = (NodeView) frame;
				List<Node> senders = view.getSenderNodes();
				if(senders != null)
				{
					for(Node node: senders)
					{
						g2d.setPaint(node.getColor());
						g2d.fill(new Ellipse2D.Double(sendCurrentX, sendCurrentY, nodePointSize, nodePointSize));
						sendCurrentY += nodePointSize + 5;
					}
				}
				
				List<Node> receivers = view.getReceiverNodes();
				if(receivers != null)
				{
					for(Node node: receivers)
					{
						g2d.setPaint(node.getColor());
						g2d.fill(new Ellipse2D.Double(receiveCurrentX, receiveCurrentY, nodePointSize, nodePointSize));
						receiveCurrentY += nodePointSize + 5;
					}
				}
			}
		}
	}

	public void addView(AbstractView view)
	{
		if(view == null) return;
		if(view instanceof ImageView)
		{
			if(globalLemonTool == ToolType.BRUSH)
			{
				new BrushToolListener((ImageView) view, new BrushTool.Builder(((ImageView) view).getCurrentImage().createGraphics(), BrushTool.BrushType.NORMAL).build());
			}
			Layer imageViewLayer = new ViewLayer(view, view.getTitle());
			layerContainer.addLayer(imageViewLayer);
			viewLayerMapping.put((NodeView) view, imageViewLayer);
		}
		else
		{
			if(view instanceof NodeView)
			{
				Layer nodeViewLayer = new FilterLayer(view, view.getTitle());
				layerContainer.addLayer(nodeViewLayer);
				viewLayerMapping.put((NodeView) view, nodeViewLayer);
			}
			else return;
		}
		view.addInternalFrameListener(new ViewActionHandler());
		add(view);
		revalidate();
	}

	private final class ViewActionHandler extends InternalFrameAdapter
	{
		@Override
		public void internalFrameClosing(InternalFrameEvent e) 
		{
			JInternalFrame source = e.getInternalFrame();
			layerContainer.removeLayer(viewLayerMapping.get(source));
			viewLayerMapping.remove(source);
			layerContainer.revalidate();
			revalidate();
		}
	}

	private final class WorkspaceArenaComponentHandler extends ComponentAdapter
	{
		@Override
		public void componentMoved(ComponentEvent e) 
		{
			repaint();
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

	class MouseHandler extends MouseAdapter 
	{
		@Override
		public void mouseReleased(MouseEvent e) 
		{
			super.mouseReleased(e);
			repaint();
		}
	}
}
