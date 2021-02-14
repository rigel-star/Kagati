package org.lemon.filter.gui;

import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JSlider;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.lemon.filter.AbstractImageFilter;
import org.lemon.filter.LightImageFilter;
import org.lemon.gui.ImageView;
import org.lemon.image.LImage;
import org.lemon.math.Vec2;

/**
 * LightFilterPanel handles the arguments for {@link LightImageFilter}.
 * */
public class LightFilterPanel extends AbstractFilterPanel {
	
	/**
	 * Serial UID
	 */
	private static final long serialVersionUID = 1L;
	
	private static JSlider radiusSlider = null;
	private static JSlider strnthSlider = null;
	private static JSlider xPosSlider = null;
	private static JSlider yPosSlider = null;
	
	private static int W = 0;
	private static int H = 0;
	
	private ImageView view = null;
	
	/**
	 * @param v 	{@link ImageView} containing image.
	 * */
	public LightFilterPanel( ImageView view ) {
		super( new LightImageFilter( view.getCurrentImage().getWidth(), view.getCurrentImage().getHeight(), 
										100, 100, new Vec2( view.getCurrentImage().getWidth() >> 1, 
												view.getCurrentImage().getHeight() >> 1 )), view);
		
		LImage src = new LImage( view.getCurrentImage() );
		W = src.width;
		H = src.height;
		
		this.view = view;
		
		init();
		
		setLayout( new GridLayout( 4, 4, 2, 2 ));
		add( new JLabel( "Strength:" ));
		add( strnthSlider );
		add( new JLabel( "Radius:" ));
		add( radiusSlider );
		add( new JLabel( "X:" ));
		add( xPosSlider );
		add( new JLabel( "Y:" ));
		add( yPosSlider );
	}
	
	/**
	 * Initialize the widgets.
	 * */
	private void init() {
		ChangeHandler chgh = new ChangeHandler();
		
		int maxRadi = Math.min( W, H );
		int ori = SwingConstants.HORIZONTAL;
		
		radiusSlider = new JSlider( ori, 0, maxRadi, ((LightImageFilter) filter).getRadius() );
		radiusSlider.addChangeListener( chgh );
		strnthSlider = new JSlider( ori, 0, 200, ((LightImageFilter) filter).getStrength() );
		strnthSlider.addChangeListener( chgh );
		xPosSlider = new JSlider( ori, 0, W, W >> 1 );
		xPosSlider.addChangeListener( chgh );
		yPosSlider = new JSlider( ori, 0, H, H >> 1 );
		yPosSlider.addChangeListener( chgh );
	}

	@Override
	public AbstractImageFilter getFilter() {
		return filter;
	}

	@Override
	public String getPanelName() {
		return "Light";
	}
	
	/**
	 * Change handler for {@link LightFilterPanel}.
	 * */
	class ChangeHandler implements ChangeListener {

		@Override
		public void stateChanged( ChangeEvent e ) {
			
			if ( e.getSource() == radiusSlider ) {
				int val = radiusSlider.getValue();
				((LightImageFilter) filter).setRadius( val );
			}
			else if ( e.getSource() == strnthSlider ) {
				int val = strnthSlider.getValue();
				((LightImageFilter) filter).setStrength( val );
			}
			else if ( e.getSource() == xPosSlider ) {
				int x = xPosSlider.getValue();
				((LightImageFilter) filter).getPosition().x = x;
			}
			else if ( e.getSource() == yPosSlider ) {
				int y = yPosSlider.getValue();
				((LightImageFilter) filter).getPosition().y = y;
			}
			
			LImage out = processFilter();
			view.getImagePanel().setImage( out.getAsBufferedImage() );
		}
	}
}
