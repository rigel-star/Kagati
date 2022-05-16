package org.lemon.gui.menus;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

import javax.swing.JMenu;
import javax.swing.JMenuItem;

import org.lemon.filter.EdgeFindfFilter;
import org.lemon.filter.GaussianBlurImageFilter;
import org.lemon.filter.GrayImageFilter;
import org.lemon.filter.SharpImageFilter;
import org.lemon.filter.WaterRippleImageFilter;
import org.lemon.filter.gui.AbstractFilterPanel;
import org.lemon.filter.gui.FilterPanelWindow;
import org.lemon.filter.gui.PosterizeFilterPanel;
import org.lemon.filter.gui.VanishingPointFilterPanel;
import org.lemon.gui.ImageView;
import org.lemon.gui.WorkspaceArena;
import org.lemon.image.LImage;

public class FilterMenu extends JMenu implements ActionListener {
	private static final long serialVersionUID = 1L;
	
	private JMenuItem findEdgeFilter = null;
	private JMenuItem grayScaleFilter = null;
	private JMenuItem vanishingPtFilter = null;
	private JMenuItem sharpFilter = null;
	private JMenuItem posterizeFilter = null;
	private JMenuItem waterRippleFilter = null;
	
	private JMenu blurFilter = null;
	private JMenuItem gaussBlur = null;
	
	private WorkspaceArena wks = null;
	
	public FilterMenu(WorkspaceArena workspace) {
		this.wks = workspace;
		setText("Filter");
		
		findEdgeFilter = new JMenuItem("Find edges...");
		findEdgeFilter.addActionListener(this);
		
		grayScaleFilter = new JMenuItem("Grayscale...");
		grayScaleFilter.addActionListener(this);
		
		sharpFilter = new JMenuItem("Sharp...");
		sharpFilter.addActionListener(this);
		
		blurFilter = new JMenu("Blur...");
		gaussBlur = new JMenuItem("Gaussian");
		gaussBlur.addActionListener(this);
		blurFilter.add(gaussBlur);
		
		posterizeFilter = new JMenuItem("Posterize...");
		posterizeFilter.addActionListener(this);
		
		vanishingPtFilter = new JMenuItem("Vanishing Point...");
		vanishingPtFilter.addActionListener(this);
		
		waterRippleFilter = new JMenuItem("Water Ripple...");
		waterRippleFilter.addActionListener(this);
		
		add(grayScaleFilter);
		add(sharpFilter);
		add(blurFilter);
		add(findEdgeFilter);
		add(posterizeFilter);
		add(vanishingPtFilter);
		add(waterRippleFilter);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) 
	{
		Component selected = wks.getSelectedFrame();
		if((selected == null) || !(selected instanceof ImageView)) 
			return;

		BufferedImage src = ((ImageView) selected).getCurrentImage();
		LImage srcL = new LImage(src);
		
		if(e.getSource() == findEdgeFilter)
			src = new EdgeFindfFilter().filter(srcL).getAsBufferedImage();
		else if(e.getSource() == grayScaleFilter)
			src = new GrayImageFilter().filter(srcL).getAsBufferedImage();
		else if(e.getSource() == sharpFilter) 
			src = new SharpImageFilter().filter(srcL).getAsBufferedImage();
		else if(e.getSource() == vanishingPtFilter) 
		{
			new VanishingPointFilterPanel(src);
			((ImageView) selected).getImagePanel().repaint();
			return;
		}
		else if(e.getSource() == gaussBlur) 
			src = new GaussianBlurImageFilter().filter(srcL).getAsBufferedImage();	
		else if(e.getSource() == posterizeFilter) 
		{
			AbstractFilterPanel pan = new PosterizeFilterPanel((ImageView) selected);
			new FilterPanelWindow(pan);
			((ImageView) selected).getImagePanel().repaint();
			return;
		}
		else if(e.getSource() == waterRippleFilter)
			src = new WaterRippleImageFilter().filter(srcL).getAsBufferedImage();
		
		((ImageView) selected).getImagePanel().repaint();
	}
}