package org.lemon.gui.menus;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

import javax.swing.JMenu;
import javax.swing.JMenuItem;

import org.lemon.filter.AbstractImageFilter;
import org.lemon.filter.GaussianBlurImageFilter;
import org.lemon.filter.GrayImageFilter;
import org.lemon.filter.SharpImageFilter;
import org.lemon.filter.gui.AbstractFilterPanel;
import org.lemon.filter.gui.FilterPanelWindow;
import org.lemon.filter.gui.PosterizeFilterPanel;
import org.lemon.filter.gui.VanishingPointFilterPanel;
import org.lemon.gui.ImageView;
import org.lemon.gui.Workspace;
import org.lemon.image.LImage;

/**
 * 
 * Filter Menu contains all the image filters.
 * 
 * */
public class FilterMenu extends JMenu implements ActionListener {
	
	/**
	 * Serial UID
	 * */
	private static final long serialVersionUID = 1L;
	
	private JMenuItem findEdgeFilter = null;
	private JMenuItem grayScaleFilter = null;
	private JMenuItem vanishingPtFilter = null;
	private JMenuItem sharpFilter = null;
	private JMenuItem posterizeFilter = null;
	
	private JMenu blurFilter = null;
	private JMenuItem gaussBlur = null;
	
	private Workspace wks = null;
	
	public FilterMenu( Workspace workspace ) {
		this.wks = workspace;
		
		setText( "Filter" );
		init();
		
		blurFilter.add( gaussBlur );
		
		add( grayScaleFilter );
		add( sharpFilter );
		add( blurFilter );
		add( findEdgeFilter );
		add( posterizeFilter );
		add( vanishingPtFilter );
	}
	
	/**
	 * Init the widgets.
	 * */
	private void init() {
		this.findEdgeFilter = new JMenuItem( "Find edges..." );
		findEdgeFilter.addActionListener( this );
		
		this.grayScaleFilter = new JMenuItem( "Grayscale..." );
		grayScaleFilter.addActionListener( this );
		
		this.sharpFilter = new JMenuItem( "Sharp..." );
		sharpFilter.addActionListener( this );
		
		this.blurFilter = new JMenu( "Blur..." );
		this.gaussBlur = new JMenuItem( "Gaussian" );
		gaussBlur.addActionListener( this );
		
		this.posterizeFilter = new JMenuItem( "Posterize..." );
		posterizeFilter.addActionListener( this );
		
		this.vanishingPtFilter = new JMenuItem( "Vanishing Point..." );
		vanishingPtFilter.addActionListener( this );
	}
	
	@Override
	public void actionPerformed( ActionEvent e ) {
		
		Component selected = wks.getSelectedFrame();
		BufferedImage src = null;
		LImage srcL = null;
		
		if( selected == null ) {
			return;
		}
		if( selected instanceof ImageView ) {
			src = ((ImageView) selected).getCurrentImage();
			srcL = new LImage( src );
		}
		
		AbstractImageFilter filter = null;
		
		if( e.getSource() == findEdgeFilter ) {
			
		}
		else if( e.getSource() == grayScaleFilter ) {
			filter = new GrayImageFilter();
			src = filter.filter( srcL ).getAsBufferedImage();
		}
		else if( e.getSource() == sharpFilter ) {
			filter = new SharpImageFilter();
			src = filter.filter( srcL ).getAsBufferedImage();
		}
		else if( e.getSource() == vanishingPtFilter ) {
			new VanishingPointFilterPanel( src );
		}
		else if( e.getSource() == gaussBlur ) {
			filter = new GaussianBlurImageFilter();
			src = filter.filter( srcL ).getAsBufferedImage();
		}
		else if ( e.getSource() == posterizeFilter ) {
			
			//TESTING
			AbstractFilterPanel pan = new PosterizeFilterPanel( (ImageView) selected );
			new FilterPanelWindow( pan );
		}
		
		selected.repaint();
		selected.revalidate();
	}
}
