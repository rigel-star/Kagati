package org.lemon.filter.gui;

import java.awt.GridLayout;

import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.lemon.filter.AbstractImageFilter;
import org.lemon.filter.PosterizeImageFilter;
import org.lemon.gui.ImageView;
import org.lemon.lang.NonNull;

/**
 * 
 * Posterize Filter Panel handles the functions of
 * {@link PosterizeImageFilter}.
 * 
 * */
public class PosterizeFilterPanel extends AbstractFilterPanel {

	/**
	 * Serial UID
	 */
	private static final long serialVersionUID = 1L;
	
	private static JSlider numSlider = null;
	
	public PosterizeFilterPanel( @NonNull final ImageView view ) {
		super( new PosterizeImageFilter(), view );
		this.init();
		
		setLayout( new GridLayout( 2, 1 ));
		add( numSlider );
	}
	
	/**
	 * Initialize the widgets.
	 * */
	private void init() {
		ChangeListener al = new ChangeHandler();
		
		numSlider = new JSlider( JSlider.HORIZONTAL, 1, 15, 15 );
		numSlider.addChangeListener( al );
	}
	
	@Override
	public AbstractImageFilter getFilter() {
		return filter;
	}
	
	@Override
	public String getPanelName() {
		return "Posterize";
	}
	
	/**
	 * Change Listener of {@link PosterizeFilterPanel}.
	 * */
	private class ChangeHandler implements ChangeListener {
		
		@Override
		public void stateChanged( ChangeEvent e ) {
			if ( e.getSource() == numSlider ) {
				((PosterizeImageFilter) filter).setNumLevels( numSlider.getValue() );
				processFilter();
			}
		}
	}
}
