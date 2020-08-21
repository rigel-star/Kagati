package org.lemon.gui.filter;

import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.JComboBox;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.lemon.gui.FilterController;
import org.lemon.gui.Node;
import org.lemon.math.Vec2d;

public class TextureController extends JInternalFrame implements FilterController {
	private static final long serialVersionUID = 1L;
	
	
	private JPanel editPanel = new JPanel();
	
	private JLabel imgTxt = new JLabel("Image");
	private JComboBox<String> textureChooser = null;
	
	private final int nodeCount = 1;
	private Node imgNode = null;
	private Node[] nodes = new Node[nodeCount];
	
	
	public TextureController() {
		this.init();
		
		var start = new Vec2d(getLocation().x + getWidth(), getLocation().y + (getHeight() / 2));
		this.imgNode = new Node(start, null, this);
		nodes[0] = imgNode;
		
		setTitle("Image Texture");
		setResizable(false);
		setLocation(50, 50);
		setClosable(true);
		setVisible(true);
		
		editPanel.add(textureChooser);
		editPanel.add(imgTxt);
		
		var c = getContentPane();
		
		c.add(editPanel);
		
	}
	
	
	private void init() {
		var options = new String[] {
				"Wooden",
				"Tin"
		};
		
		this.textureChooser = new JComboBox<String>(options);
		
		editPanel.setLayout(new FlowLayout(FlowLayout.TRAILING));
	}
	
	
	
	@Override
	public Node[] getNodes() {
		return nodes;
	}

	
	@Override
	public void updateNodes() {
		this.imgNode.start.x = getLocation().x + getWidth();
		this.imgNode.start.y = getLocation().y + (getHeight() / 2);
	}
	
	
	
	@Override
	public Dimension getPreferredSize() {
		return new Dimension(60, 100);
	}
	

}
