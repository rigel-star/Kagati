package org.lemon.gui;

import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import org.lemon.filter.ResizeImageFilter;
import org.lemon.gui.image.ImagePanel;
import org.lemon.gui.node.Node;
import org.lemon.image.LImage;

public class ImageNodeView extends NodeView {
	private ImageView imageView;
	private JPanel contentPane;

	public ImageNodeView(List<Node> receivers, List<Node> senders, ImageView imageView) {
		super(receivers, senders);
		this.imageView = imageView;
		this.contentPane = new JPanel();
		contentPane.setLayout(new GridLayout(2, 1));

		final JPanel nodesList = new JPanel();
		nodesList.setLayout(new BoxLayout(nodesList, BoxLayout.Y_AXIS));
		nodesList.add(new JLabel("Filter"));

		contentPane.add(nodesList);
		contentPane.add(new ImagePanel(new ResizeImageFilter(200, 200).filter(new LImage(imageView.getCurrentImage())).getAsBufferedImage()));

		add(contentPane, BorderLayout.CENTER);
		setSize(200, 300);
		setTitle(imageView.getTitle());
		setResizable(false);
		setClosable(true);
		setLocation(20, 50);
		setVisible(true);
	}
	
	public ImageView getImageView() {
		return imageView;
	}
}