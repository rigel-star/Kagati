package org.lemon.gui.menu;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JMenu;
import javax.swing.JMenuItem;

import org.lemon.filter.GaussianBlurImageFilter;
import org.lemon.gui.AbstractView;
import org.lemon.gui.BlurFilterNodeView;
import org.lemon.gui.ImageNodeView;
import org.lemon.gui.ImageView;
import org.lemon.gui.TextureView;
import org.lemon.gui.WorkspaceArena;
import org.lemon.gui.node.FilterInputNode;
import org.lemon.gui.node.FilterOutputNode;
import org.lemon.gui.node.Node;

public class NodeMenu extends JMenu implements ActionListener 
{
	private static final long serialVersionUID = 1L;
	
	private JMenuItem blur, texture, image;
	private WorkspaceArena workspace;
	
	public NodeMenu(WorkspaceArena workspace) {
		this.workspace = workspace;
		
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
		AbstractView view = null;
		if(e.getSource() == blur) 
		{
			view = new BlurFilterNodeView(new ArrayList<>(),
					new ArrayList<>() {{
						add(new FilterOutputNode(new GaussianBlurImageFilter()));
					}}
				);
		}
		else if(e.getSource() == texture) 
		{
			view = new TextureView();
		}
		else if(e.getSource() == image)
		{
			view = new ImageNodeView(new ArrayList<Node>() {{
				add(new FilterInputNode());
			}}, null, (ImageView) workspace.getSelectedFrame());
		}
		workspace.addView(view);
	}	
}