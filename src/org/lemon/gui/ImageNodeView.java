package org.lemon.gui;

import java.util.List;

import javax.swing.JLabel;

import org.lemon.gui.node.Node;

public class ImageNodeView extends NodeView 
{
	private static final long serialVersionUID = 1L;
	
	private ImageView imageView;

	public ImageNodeView(LayerContainer lycont, List<Node> receivers, List<Node> senders, ImageView imageView) 
	{
		super(receivers, senders);
		this.imageView = imageView;
		
		setSize(200, 300);
		setTitle("Image Node");
		setResizable(false);
		setVisible(true);
		setClosable(true);
		setLocation(20, 50);
		
		add(new JLabel("Filter"));
	}
	
	public ImageView getImageView()
	{
		return imageView;
	}
}