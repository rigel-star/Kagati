package org.lemon.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import org.lemon.gui.layers.EmptyLayer;
import org.lemon.gui.layers.Layer;
import org.lemon.gui.layers.LayerList;

public class LayerContainer extends JInternalFrame {
	private static final long serialVersionUID = 1L;
	
	
	private LayerList layerList = new LayerList();
	private JScrollPane scroll = new JScrollPane(layerList);
	private Workspace workspace = null;

	
	private Layer background = null;
	
	
	/**
	 * LayerContainer contains all the access to {@code JInternalFrame}'s opened on main {@code Workspace} of application.
	 * Like {@code ImageView}, {@code DrawingPanel} etc.
	 * @param workspace Main {@code Workspace} of this application.
	 * */
	public LayerContainer(Workspace workspace) {
		
		this.workspace = workspace;
		
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
	
	
	public void addLayer(Layer layer) {
		if(layer.getProperty() == Layer.BACKGROUND_LAYER || getLayerCount() == 0) {
			
			if(background != null) {
				background.setTitle("Layer");
			}
			
			background = layer;
			layer.setTitle("Background");
		}
		
		layerList.add(layer);
		scroll.revalidate();
	}
	
	
	public int getLayerCount() {
		return layerList.getLayerCount();
	}
	
	
	@Override
	public Dimension getPreferredSize() {
		return new Dimension(150, 200);
	}
	
	
	/**
	 * Lies in the bottom of LayerContainer. This class contains command like for deleting or copying layers.
	 * */
	private class BottomMenu extends JPanel {
		private static final long serialVersionUID = 1L;
		
		private JButton deleteLayerBtn;
		private JButton copyLayerBtn;
		private JButton newLayerBtn;
		
		
		public BottomMenu() {
			init();
			setLayout(new FlowLayout(FlowLayout.CENTER));
			
			add(newLayerBtn);
			add(copyLayerBtn);
			add(deleteLayerBtn);
		}
		
		
		/*init widgets for bottom menu*/
		private void init() {
			
			this.newLayerBtn = new JButton("New");
			newLayerBtn.addActionListener(action -> {
				layerList.add(new EmptyLayer());
				revalidate();
			});
			
			
			this.copyLayerBtn = new JButton("Cop");
			copyLayerBtn.addActionListener(action -> {
				
			});	
			
			
			this.deleteLayerBtn = new JButton("Del");
			deleteLayerBtn.addActionListener(action -> {
				
				int option = JOptionPane.showConfirmDialog(layerList, "Delete this layer?");
				
				switch(option) {
				
				case 0:{
					Layer lay = layerList.getSelectedValue();
					
					if(lay.getLayerComponent() instanceof ImageView)
						workspace.remove(lay.getLayerComponent());
					
					layerList.remove(lay);
					workspace.revalidate();
				}
				break;
				}
				
				revalidate();
			});
			
			
		}
		
	}
	
	
}
