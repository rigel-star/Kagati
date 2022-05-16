package org.lemon.gui.layer;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.lemon.filter.ResizeImageFilter;
import org.lemon.gui.Layer;
import org.lemon.image.LImage;

public class FilterLayerPanel extends JPanel 
{
	private static final long serialVersionUID = 1L;
	
	private FilterLayer layer = null;
	
	private JLabel nameField = null;
	private JLabel imgField = null;
	
	public FilterLayerPanel(FilterLayer layer) 
	{
		this.layer = layer;
		nameField = new JLabel();
		imgField = new JLabel();
		imgField.setPreferredSize( new Dimension( 50, 50 ));
		
		ImageIcon ic = new ImageIcon();
		final int icW = ic.getIconWidth();
		final int icH = ic.getIconHeight();
		BufferedImage ig = new BufferedImage(50, 50, BufferedImage.TYPE_INT_RGB);
		Graphics2D g2 = ig.createGraphics();
		ic.paintIcon(null, g2, 0, 0);
		g2.dispose();
		
		ig = new ResizeImageFilter(50, 50).filter(new LImage(ig)).getAsBufferedImage();
		
		nameField.setText(layer.getTitle());
		imgField.setIcon(new ImageIcon(ig));
		
		setLayout( new BorderLayout());
		add(imgField, BorderLayout.WEST);
		add(nameField, BorderLayout.EAST);
	}
	
	public Layer getLayer() {
		return layer;
	}
}