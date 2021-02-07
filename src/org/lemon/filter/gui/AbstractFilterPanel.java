package org.lemon.filter.gui;

import java.awt.Color;
import java.awt.image.BufferedImage;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.border.Border;

import org.lemon.filter.AbstractImageFilter;
import org.lemon.gui.ImageView;
import org.lemon.gui.image.ImagePanel;
import org.lemon.image.LImage;
import org.lemon.lang.NonNull;
import org.lemon.utils.Utils;

/**
 * 
 * Abstract Filter Panel contains the required widgets
 * to make specific filter working.
 * 
 * */
public abstract class AbstractFilterPanel extends JPanel {

	/**
	 * Serial UID
	 */
	private static final long serialVersionUID = 1L;
	
	private LImage src = null;
	private LImage filtered = null;
	
	private ImageView view = null;
	protected AbstractImageFilter filter = null;
	
	/**
	 * Constructs {@code AbstractFilterPanel} with specified 
	 * {@link AbstractImageFilter} and {@link ImageView}.
	 * 
	 * @param f 		{@link AbstractImageFilter}
	 * @param v 		{@link ImageView} to get image from.
	 * */
	public AbstractFilterPanel( @NonNull final AbstractImageFilter f, @NonNull final ImageView v ) {
		
		if ( v == null || f == null )
			throw new NullPointerException( "View and filter can't be null." );
		
		this.filter = f;
		this.view = v;
		
		Border b = BorderFactory.createTitledBorder( BorderFactory.createLineBorder( Color.darkGray ), getPanelName() );
		setBorder( b );
		
		this.src = new LImage( view.getCurrentImage() );
		processFilter();
	}
	
	/**
	 * Change the filter.
	 * 
	 * @param f 	New image filter.
	 * */
	public void setFilter( @NonNull AbstractImageFilter f ) {
		this.filter = f;
	}
	
	/**
	 * Processes the image with specified {@link AbstractImageFilter}.
	 * Making copy of image before processing and replacing 
	 * the non-filtered image with filtered one is done 
	 * by this method internally.
	 * 
	 * @return {@code True} when processing is done. If some 
	 * 			error occured while processing the image, 
	 * 			returns {@code False}.
	 * */
	protected boolean processFilter() {
		if ( filter == null )
			return false;
		
		new Thread( new Runnable() {
			
			@Override
			public void run() {
				BufferedImage copy = Utils.getImageCopy( src.getAsBufferedImage() );
				filtered = filter.filter( new LImage( copy ));
				
				view.getImagePanel().setImage( filtered.getAsBufferedImage() );
				view.getImagePanel().repaint();
				view.getImagePanel().revalidate();
			}
		}).start();
		
		return true;
	}
	
	/**
	 * Overrides current image on {@link ImageView} 
	 * with filtered one. 
	 * */
	protected void override() {
		src = filtered;
		ImagePanel pan = view.getImagePanel();
		pan.setImage( src.getAsBufferedImage() );
		pan.revalidate();
		view.revalidate();
	}
	
	/**
	 * Get {@link AbstractImageFilter} attached with this 
	 * {@code FilterPanel}.
	 * 
	 * @return Filter attached with this panel.
	 * */
	public abstract AbstractImageFilter getFilter();
	
	/**
	 * Get name of the panel.
	 * 
	 * @return Name of panel.
	 * */
	public abstract String getPanelName();
}
