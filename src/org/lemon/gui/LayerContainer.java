package org.lemon.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import org.lemon.gui.layer.LayerList;

public class LayerContainer extends JInternalFrame 
{
	private static final long serialVersionUID = 1L;
		
	private LayerList layerList = new LayerList();
	private JScrollPane scroll = new JScrollPane(layerList);
	
	public LayerContainer()
	{
		setTitle("Layers");
		setClosable(false);
		setBorder(BorderFactory.createLineBorder(Color.black, 1));
		setMaximizable(false);
		setIconifiable(false);
		setLayout(new BorderLayout());
		
		add(scroll);
		add(new BottomMenu(), BorderLayout.SOUTH);
		setVisible(true);
	}
	
	public void addLayer(Layer layer) 
	{
		layerList.addLayer(layer);
		scroll.revalidate();
	}
	
	public void removeLayer(Layer ly) 
	{
		layerList.removeLayer(ly);
	}
	
	public int getLayerCount() 
	{
		return layerList.getLayerCount();
	}
	
	@Override
	public Dimension getPreferredSize() 
	{
		return new Dimension(150, 200);
	}
	
	private class BottomMenu extends JPanel 
	{
		private static final long serialVersionUID = 1L;
		
		private JButton deleteLayerBtn;
		private JButton newLayerBtn;
		
		private BottomMenu() 
		{
			newLayerBtn = new JButton("New");	
			deleteLayerBtn = new JButton("Del");
			deleteLayerBtn.addActionListener(action -> {
				int option = JOptionPane.showConfirmDialog(layerList, "Delete this layer?");
				switch(option)
				{
					case 0: {
						Layer lay = layerList.getSelectedValue();
						JComponent jc = lay.getLayerComponent();
						((JInternalFrame) jc).dispose();
						layerList.removeLayer(lay);
					}
					break;
				}
				revalidate();
			});	
			
			setLayout(new FlowLayout(FlowLayout.CENTER));
			add(newLayerBtn);
			add(deleteLayerBtn);
		}		
	}
}