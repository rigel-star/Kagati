// SPDX-License-Identifier: MIT
// Copyright (c) 2026 Kagati Foundation

package org.lemon.views.workspace;

import java.awt.BasicStroke;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Line2D;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.swing.JDesktopPane;
import javax.swing.JInternalFrame;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;

import org.lemon.AppContext;
import org.lemon.gui.AbstractView;
import org.lemon.gui.ImageView;
import org.lemon.gui.Layer;
import org.lemon.gui.LayerContainer;
import org.lemon.gui.NodeView;
import org.lemon.gui.ToolBarContainer;
import org.lemon.gui.layer.FilterLayer;
import org.lemon.gui.layer.ViewLayer;
import org.lemon.gui.node.Node;

public class EditorWorkspace extends JDesktopPane implements Node.NodeClickListener, PropertyChangeListener {
	private Map<NodeView, Layer> viewLayerMapping = new HashMap<>();
	private LayerContainer layerContainer = null;
	private ToolBarContainer toolBarContainer;

	private final AppContext ctx;

	public EditorWorkspace(AppContext ctx, LayerContainer layerContainer, ToolBarContainer toolBarContainer) {
		this.ctx = ctx;
		this.ctx.addPropertyChangeListener(this);

		this.layerContainer = layerContainer;
		this.toolBarContainer = toolBarContainer;

		WorkspaceArenaMouseAdapter mouseAdapter = new WorkspaceArenaMouseAdapter();
		addMouseMotionListener(mouseAdapter);
		addMouseListener(mouseAdapter);

		try {
			ImageView testView = new ImageView(ImageIO.read(new File("/Users/rigelstar/Desktop/extra/Test Images/worker.jpeg")), "worker.jpeg");
			addView(testView);
		}
		catch(IOException e) {
			e.printStackTrace();
		}
	}

	private void addFrame(WorkspaceFrame frame) {
		frame.addInternalFrameListener(new InternalFrameAdapter() {
			@Override
			public void internalFrameClosing(InternalFrameEvent e) {
				super.internalFrameClosing(e);
				System.out.println("A frame has been closed!");
			}
		});

		add(frame);
	}

	public void addView(AbstractView view) {
		if(view == null) return;
		if(view instanceof ImageView) {
			Layer imageViewLayer = new ViewLayer(view, view.getTitle());
			layerContainer.addLayer(imageViewLayer);
			viewLayerMapping.put((NodeView) view, imageViewLayer);
		}
		else {
			if(view instanceof NodeView nodeView) {
				Layer nodeViewLayer = new FilterLayer(view, view.getTitle());
				layerContainer.addLayer(nodeViewLayer);
				viewLayerMapping.put(nodeView, nodeViewLayer);
				nodeView.render();
				nodeView.addNodeClickListener(this);
			}
			else return;
		}
		view.addInternalFrameListener(new ViewActionHandler());
		add(view);
		revalidate();
		repaint();
	}

	private final class ViewActionHandler extends InternalFrameAdapter {
		@Override
		public void internalFrameClosing(InternalFrameEvent e) {
			JInternalFrame source = e.getInternalFrame();
			layerContainer.removeLayer(viewLayerMapping.get(source));
			viewLayerMapping.remove(source);
			layerContainer.revalidate();
			revalidate();
		}
	}

	private boolean nodeConnectionStarted = false;
	private int nodeConnectionStartX = 0;
	private int nodeConnectionStartY = 0;
	private int nodeConnectionEndX = 0;
	private int nodeConnectionEndY = 0;
	private Node startingNode, endingNode;
	private List<NodeConnectionInfo> nodeConnectionInfos = new ArrayList<>();

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		g2d.setStroke(new BasicStroke(2));
		if(nodeConnectionStarted) {
			g2d.setColor(startingNode.getColor());
			g2d.draw(new Line2D.Float(
				nodeConnectionStartX, nodeConnectionStartY,
				nodeConnectionEndX, nodeConnectionEndY
			));
		}

		for(NodeConnectionInfo info: nodeConnectionInfos) {
			g2d.setColor(info.startingNode.getColor());
			g2d.draw(new Line2D.Float(info.startingNodeLocation, info.endingNodeLocation));
		}
	}

	private class WorkspaceArenaMouseAdapter extends MouseAdapter {
		@Override
		public void mouseMoved(MouseEvent e) {
			nodeConnectionEndX = e.getX();
			nodeConnectionEndY = e.getY();
			repaint();
		}

		@Override
		public void mouseClicked(MouseEvent e) {
			nodeConnectionStarted = false;
			startingNode = null;
			endingNode = null;
			repaint();
		}
	}

	@Override
	public void nodeClick(Node.NodeClickEvent event) {
		this.ctx.setIsNodeBeingDragged(true);

		if(!nodeConnectionStarted) {
			nodeConnectionStarted = true;
			nodeConnectionStartX = event.getX();
			nodeConnectionStartY = event.getY();
			startingNode = event.getNode();
		}
		else {
			endingNode = event.getNode();
			NodeConnectionInfo info = new NodeConnectionInfo();
			info.startingNode = startingNode;
			info.endingNode = endingNode;
			info.startingNodeLocation = new Point(nodeConnectionStartX, nodeConnectionStartY);
			info.endingNodeLocation = new Point(nodeConnectionEndX, nodeConnectionEndY);
			nodeConnectionInfos.add(info);
			nodeConnectionStarted = false;
		}
	}

	private class NodeConnectionInfo {
		public Point startingNodeLocation;
		public Point endingNodeLocation;
		public Node startingNode;
		public Node endingNode;
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		throw new UnsupportedOperationException("Unimplemented method 'propertyChange'");
	}
}