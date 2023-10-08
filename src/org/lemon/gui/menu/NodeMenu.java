package org.lemon.gui.menu;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JMenu;
import javax.swing.JMenuItem;

import org.lemon.gui.AbstractView;
import org.lemon.gui.BlurFilterNodeView;
import org.lemon.gui.ImageNodeView;
import org.lemon.gui.ImageView;
import org.lemon.gui.TextureNodeView;
import org.lemon.gui.WorkspaceArena;
import org.lemon.gui.node.FilterInputNode;
import org.lemon.gui.node.Node;

public class NodeMenu extends JMenu implements ActionListener {
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
	public void actionPerformed(ActionEvent e) {
		AbstractView view = null;
		if(e.getSource() == blur) {
			view = new BlurFilterNodeView();
		}
		else if(e.getSource() == texture) {
			view = new TextureNodeView();
		}
		else if(e.getSource() == image) {
			view = new ImageNodeView(
				new ArrayList<Node>() {{
					add(new FilterInputNode());
				}}, 
				new ArrayList<>() {{

				}}, 
				(ImageView) workspace.getSelectedFrame()
			);
		}
		workspace.addView(view);
	}	
}