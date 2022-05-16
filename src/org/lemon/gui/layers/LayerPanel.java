package org.lemon.gui.layers;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import org.lemon.gui.Layer;

public class LayerPanel extends JPanel 
{
	private static final long serialVersionUID = 1L;

	private Layer layer;
	private ImageIcon imageIcon;
	private JTextArea nameField;
	private JLabel imageField;
	
	public LayerPanel(Layer layer)
	{
		this.layer = layer;
		nameField = new JTextArea();
		imageField = new JLabel() 
		{
			private static final long serialVersionUID = 1L;
			
			@Override
			public Dimension getPreferredSize() 
			{
				return new Dimension(50, 50);
			}
		};
		
		setLayout(new BorderLayout());
		add(imageField, BorderLayout.WEST);
		add(nameField, BorderLayout.EAST);
	}
	
	public void setIcon(ImageIcon icon)
	{
		this.imageIcon = icon;
		imageField.setIcon(icon);
	}
	
	public ImageIcon getIcon()
	{
		return imageIcon;
	}
	
	public void setTitle(String title)
	{
		layer.setTitle(title);
		nameField.setText(title);
	}
	
	public String getTitle()
	{
		return layer.getTitle();
	}
	
	public Layer getLayer()
	{
		return layer;
	}
	
	@Override
	public Dimension getPreferredSize() 
	{
		return new Dimension(150, 60);
	}
}
