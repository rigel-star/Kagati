package org.lemon.gui;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.swing.BoxLayout;
import javax.swing.JPanel;

import org.lemon.gui.node.Node;
import org.lemon.gui.node.Node.NodeClickEvent;
import org.lemon.gui.node.Node.NodeClickListener;

public abstract class NodeView extends AbstractView {
	private JPanel contentPanel = new JPanel();
	private NodePanel nodePanel = null;

    private List<Node.NodeClickListener> nodeClickListeners;
    private static final int CONTENT_PANEL_HEIGHT = 300;
	
	public NodeView(List<Node> inputs, List<Node> outputs) {
		int inputNodesCount = inputs == null ? 0 : inputs.size();
		int outputNodesCount = outputs == null ? 0 : outputs.size();
        nodeClickListeners = new ArrayList<>();
		int nodePanelHeight = 20 * Math.max(inputNodesCount, outputNodesCount);
		setSize(200, 300 + nodePanelHeight);
		setResizable(false);
		setClosable(true);
		setLocation(20, 50);
		nodePanel = new NodePanel(inputs, outputs);
		contentPanel.setSize(200, 300);
	}

	public void render() {
		Container root = getContentPane();
		root.setLayout(new BoxLayout(root, BoxLayout.Y_AXIS));
		root.add(contentPanel);
		root.add(nodePanel);
		setVisible(true);
	}

    public void addNodeClickListener(Node.NodeClickListener listener) {
        nodeClickListeners.add(listener);
    }

    public void removeNodeClickListener(Node.NodeClickListener listener) {
        nodeClickListeners.remove(listener);
    }
	
	private class NodePanel extends JPanel {
        public List<Node> inputs;
        public List<Node> outputs;
        private Map<Ellipse2D.Float, Color> nodesMap = new HashMap<>();
        private List<Node> nodes = new ArrayList<>();

        public NodePanel(List<Node> inputs, List<Node> outputs) {
            this.inputs = inputs;
            this.outputs = outputs;
			add(new Gap(10));
            addMouseListener(new NodePanelMouseAdapter());
        }

		@Override
		public Dimension getPreferredSize() {
			return new Dimension(200, 25 * Math.max(inputs.size(), outputs.size()));
		}

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;
            g2d.setFont(new Font("monospace", Font.PLAIN, 12));
            // inputs
            int inputsStart = 0;
            if(inputs != null) {
                for(Node node: inputs) {
                    Color nodeColor = node.getColor();
                    String nodeName = node.getName();
                    g2d.setColor(Color.white);
                    g2d.drawString(nodeName, 15, inputsStart + 10);
                    nodesMap.put(new Ellipse2D.Float(2, inputsStart, 8, 8), nodeColor);
                    nodes.add(node);
                    inputsStart += 25;
                }
            }

            // outputs
            int outputsStart = 0;
            if(outputs != null) {
                for(Node node: outputs) {
                    Color nodeColor = node.getColor();
                    String nodeName = node.getName();
                    g2d.setColor(Color.white);
                    int x = 150 - nodeName.length();
                    g2d.drawString(nodeName, x, outputsStart + 10);
                    nodesMap.put(new Ellipse2D.Float(180, outputsStart, 8, 8), nodeColor);
                    nodes.add(node);
                    outputsStart += 20;
                }
            }

            for(Map.Entry<Ellipse2D.Float, Color> node: nodesMap.entrySet()) {
                g2d.setColor(node.getValue());
                g2d.fill(node.getKey());
            }
        }

        private class NodePanelMouseAdapter extends MouseAdapter {
            @Override
            public void mouseClicked(MouseEvent e) {
                int x = e.getX();
                int y = e.getY();
                Iterator<Ellipse2D.Float> shapes = nodesMap.keySet().iterator();
                int index = 0;
                BREAK_OUT:
                while(shapes.hasNext()) {
                    Ellipse2D.Float shape = shapes.next();
                    if(shape.contains(x, y)) {
                        for(NodeClickListener listener: nodeClickListeners) {
                            Point parentPos = NodeView.this.getLocation();
                            NodeClickEvent event = new NodeClickEvent(nodes.get(index), parentPos.x + x, parentPos.y + CONTENT_PANEL_HEIGHT - y);
                            listener.nodeClick(event);
                            break BREAK_OUT;
                        }
                    }
                    index++;
                }
            }
        }
    }

	public JPanel getContentPanel() {
		return contentPanel;
	}
}