package org.lemon.gui.menu;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JComponent;
import javax.swing.JInternalFrame;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

import org.lemon.filter.GaussianBlurImageFilter;
import org.lemon.gui.BlurFilterNodeView;
import org.lemon.gui.ImageNodeView;
import org.lemon.gui.ImageView;
import org.lemon.gui.Layer;
import org.lemon.gui.LayerContainer;
import org.lemon.gui.TextureView;
import org.lemon.gui.WorkspaceArena;
import org.lemon.gui.layer.FilterLayer;
import org.lemon.gui.node.FilterReceiverNode;
import org.lemon.gui.node.FilterSenderNode;

public class NodeMenu extends JMenu implements ActionListener {
	private static final long serialVersionUID = 1L;
	
	private JMenuItem blur, texture, image;
	private WorkspaceArena workspace;
	private LayerContainer layerContainer;
	
	public NodeMenu(WorkspaceArena workspace, LayerContainer layerContainer) {
		this.workspace = workspace;
		this.layerContainer = layerContainer;
		
		setText("Node");
		
		blur = new JMenuItem("Blur") ;
		texture = new JMenuItem("Texture");
		image = new JMenuItem("Image");
		
		blur.addActionListener(this);
		add(blur);
		
		texture.addActionListener(this);
		add(texture);
		
		image.addActionListener(this);
		add(image);
	}

	@Override
	public void actionPerformed(ActionEvent e) 
	{
		JComponent view = null;
		Layer layer = null;
		
		if(e.getSource() == blur) 
		{
			view = new BlurFilterNodeView(layerContainer, 
					new ArrayList<>(),
					new ArrayList<>() {
						private static final long serialVersionUID = 1L;
						{
							add(new FilterSenderNode(new GaussianBlurImageFilter()));
						}
					}
				);
			layer = new FilterLayer(view, "Blur Node");
		}
		else if(e.getSource() == texture) 
		{
			view = new TextureView(layerContainer);
			layer = new FilterLayer(view, "Texture Node");
		}
		else if(e.getSource() == image)
		{
			JInternalFrame selected = workspace.getSelectedFrame();
			if(selected != null && (selected instanceof ImageView))
			{
				view = new ImageNodeView(layerContainer, 
						new ArrayList<>() 
						{
							private static final long serialVersionUID = 1L;
							{
								add(new FilterReceiverNode());
							}
						}, 
					new ArrayList<>(), (ImageView) selected);
			}
			else return;
		}
		
		if(layer != null)
		{
			layerContainer.addLayer(layer);
			layerContainer.revalidate();
		}
		
		workspace.add(view);
		workspace.revalidate();
	}	
}