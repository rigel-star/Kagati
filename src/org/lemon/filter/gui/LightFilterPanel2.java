package org.lemon.filter.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.lemon.filter.AbstractImageFilter;
import org.lemon.filter.LightImageFilter;
import org.lemon.gui.BorderLayoutJPanel;
import org.lemon.gui.ImageView;
import org.lemon.image.LImage;
import org.lemon.math.Vec2;

public class LightFilterPanel2 extends AbstractFilterPanel {
	
	public static void main( String[] args ) {
		BufferedImage src = null;
		try {
			src = ImageIO.read( new File( "C:\\Users\\Ramesh\\Desktop\\opencv\\astro.jpg" ));
		} catch ( IOException ex ) {
			ex.printStackTrace();
		}
		
		//final int w = src.getWidth();
		//final int h = src.getHeight();
		
		//LightImageFilter f = new LightImageFilter( w, h, 50, 100, new Vec2( w >> 1, h >> 1 ));
		ImageView v = new ImageView(src, "Image");
		LightFilterPanel2 l2 = new LightFilterPanel2( v );
		new FilterPanelWindow( l2 );
	}
	
	/**
	 * Serial UID
	 */
	private static final long serialVersionUID = 1L;
	
	private JSlider radiusSlider = null;
	private JSlider strnthSlider = null;
	
	protected int W = 0;
	protected int H = 0;
	
	private JLabel imgHolder = new JLabel();
	private JButton saveBtn;
	private JButton cancelBtn;
	
	private JCheckBox shadowChkBox = null;
	private boolean shadows = false;
	
	public LightFilterPanel2( ImageView v ) {
		super( new LightImageFilter( v.getCurrentImage().getWidth(), v.getCurrentImage().getHeight(), 
				100, 100, new Vec2( v.getCurrentImage().getWidth() >> 1, 
						v.getCurrentImage().getHeight() >> 1 )), v );
		
		LImage src = new LImage( v.getCurrentImage() );
		W = src.width;
		H = src.height;
		
		init();
		
		setPreferredSize( new Dimension( W + 300, H + 50 ));
		setLayout( new BorderLayout( 5, 5 ));
		
		MouseController mcontrol = new MouseController();
		
		JPanel sliderPan = new JPanel();
		sliderPan.setLayout( new GridLayout( 2, 2, 2, 2 ));
		sliderPan.add( new JLabel( "Radius:" ));
		sliderPan.add( radiusSlider );
		sliderPan.add( new JLabel( "Strength:" ));
		sliderPan.add( strnthSlider );
		
		JPanel btnPanel = new JPanel();
		btnPanel.setLayout( new GridLayout( 2, 1, 2, 2 ));
		btnPanel.add( saveBtn );
		btnPanel.add( cancelBtn );
		
		imgHolder.setIcon( new ImageIcon( src.getAsBufferedImage() ));
		imgHolder.setPreferredSize( new Dimension( W, H ));
		imgHolder.addMouseListener( mcontrol );
		imgHolder.addMouseMotionListener( mcontrol );
		
		BorderLayoutJPanel mainPan = new BorderLayoutJPanel( W + 300, H + 50 );
		JPanel center = mainPan.getCenterPanel();
		center.setLayout( new FlowLayout( FlowLayout.LEFT, 2, 2 ));
		center.add( imgHolder );
		
		JPanel right = new JPanel();
		right.setLayout( new BoxLayout( right, BoxLayout.Y_AXIS ));
		right.add( shadowChkBox );
		right.add( sliderPan );
		right.add( btnPanel );
		
		center.add( right );
		
		add( mainPan, BorderLayout.CENTER );
	}
	
	/**
	 * Initialize the widgets.
	 * */
	private void init() {
		
		ChangeController ccontrol = new ChangeController();
		
		int maxRadi = Math.min( W, H );
		int ori = SwingConstants.HORIZONTAL;
		
		radiusSlider = new JSlider( ori, 0, maxRadi, ((LightImageFilter)filter).getRadius() );
		radiusSlider.setPreferredSize( new Dimension( 150, 30 ));
		radiusSlider.addChangeListener(  ccontrol );
		
		strnthSlider = new JSlider( ori, 0, 200, ((LightImageFilter)filter).getStrength() );
		strnthSlider.setPreferredSize( new Dimension( 150, 30 ));
		strnthSlider.addChangeListener( ccontrol );
		
		shadowChkBox = new JCheckBox( "Render Shadow", false );
		shadowChkBox.addActionListener( a -> {
			
			//toggle the shadow render state
			if ( !shadows ) 
				shadows = true;
			else
				shadows = false;
		});
		
		saveBtn = new JButton( "Save" );
		saveBtn.setPreferredSize( new Dimension( 40, 40 ));
		saveBtn.addActionListener( a -> {
			
		});
		
		cancelBtn = new JButton( "Cancel" );
		cancelBtn.setPreferredSize( new Dimension( 40, 40 ));
		cancelBtn.addActionListener( a -> {
			
		});
	}
	
	/**
	 * Mouse controller for {@link LightFilterPanel2}.
	 * */
	class MouseController extends MouseAdapter {
		
		@Override
		public void mouseDragged( MouseEvent e ) {
			super.mouseDragged( e );
			
			if ( e.getSource() == imgHolder ) {
				LightImageFilter light = (LightImageFilter) filter;
				light.setPosition( new Vec2( e.getX(), e.getY() ) );
				imgHolder.repaint();
			}
		}
		
		@Override
		public void mouseReleased( MouseEvent e ) {
			super.mouseReleased( e );
			
			LImage out = processFilter();
			imgHolder.setIcon( new ImageIcon( out.getAsBufferedImage() ) );
			imgHolder.revalidate();
		}
	}
	
	/**
	 * Value changed controller for {@link LightFilterPanel2}.
	 * */
	class ChangeController implements ChangeListener {

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
			
			LImage out = processFilter();
			imgHolder.setIcon( new ImageIcon( out.getAsBufferedImage() ) );
			imgHolder.revalidate();
		}
	}

	@Override
	public AbstractImageFilter getFilter() {
		return filter;
	}

	@Override
	public String getPanelName() {
		return "Light";
	}
}
