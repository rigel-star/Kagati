package org.lemon.filter.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.SwingConstants;

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
			src = ImageIO.read( new File( "C:\\Users\\Ramesh\\Desktop\\opencv\\mack.jpg" ));
		} catch ( IOException ex ) {
			ex.printStackTrace();
		}
		
		final int w = src.getWidth();
		final int h = src.getHeight();
		
		LightImageFilter f = new LightImageFilter( w, h, 50, 100, new Vec2( w >> 1, h >> 1 ));
		ImageView v = new ImageView( src, "Image", null );
		LightFilterPanel2 l2 = new LightFilterPanel2( f, v );
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
	
	public LightFilterPanel2( AbstractImageFilter f, ImageView v ) {
		super( f, v );
		
		LImage src = new LImage( v.getCurrentImage() );
		W = src.width;
		H = src.height;
		
		init();
		
		setPreferredSize( new Dimension( W + 300, H + 50 ));
		setLayout( new BorderLayout( 5, 5 ));
		
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
		
		BorderLayoutJPanel mainPan = new BorderLayoutJPanel( W + 300, H + 50 );
		JPanel center = mainPan.getCenterPanel();
		center.setLayout( new FlowLayout( FlowLayout.LEFT, 2, 2 ));
		center.add( imgHolder );
		
		JPanel right = new JPanel();
		right.setLayout( new BoxLayout( right, BoxLayout.Y_AXIS ));
		right.add( sliderPan );
		right.add( btnPanel );
		
		center.add( right );
		
		add( mainPan, BorderLayout.CENTER );
	}
	
	/**
	 * Initialize the widgets.
	 * */
	private void init() {
		int maxRadi = Math.min( W, H );
		int ori = SwingConstants.HORIZONTAL;
		radiusSlider = new JSlider( ori, 0, maxRadi, ((LightImageFilter)filter).getRadius() );
		radiusSlider.setPreferredSize( new Dimension( 150, 30 ));
		strnthSlider = new JSlider( ori, 0, 200, ((LightImageFilter)filter).getStrength() );
		strnthSlider.setPreferredSize( new Dimension( 150, 30 ));
		
		saveBtn = new JButton( "Save" );
		saveBtn.setPreferredSize( new Dimension( 40, 40 ));
		cancelBtn = new JButton( "Cancel" );
		cancelBtn.setPreferredSize( new Dimension( 40, 40 ));
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
