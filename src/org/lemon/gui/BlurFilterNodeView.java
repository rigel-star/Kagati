package org.lemon.gui;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.Stroke;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import org.lemon.filter.GaussianBlurImageFilter;
import org.lemon.gui.node.ColorOutputNode;
import org.lemon.gui.node.FilterInputNode;
import org.lemon.gui.node.FilterOutputNode;

public class BlurFilterNodeView extends NodeView  {
	private JPanel controlPanel = null;
	private JComboBox<String> blurChooser = null;
	private JPanel spinBlurPanel = new JPanel();
	private JPanel blurChooserPanel = new JPanel();
	private Joystick spinBlurJoy = new Joystick( 100 );
	
	public BlurFilterNodeView() {
		super(new ArrayList<>() {{
					add(new FilterInputNode());
				}},
				new ArrayList<>() {{
					add(new FilterOutputNode(new GaussianBlurImageFilter()));
					add(new ColorOutputNode());
				}}
		);

		setTitle("Blur");
		String[] blurs = {
				"Select",
				"Gaussian blur..."
		};
		
		blurChooser = new JComboBox<String>(blurs);
		blurChooser.setPreferredSize( new Dimension( 130, 40 ) );
		
		spinBlurPanel = new JPanel();
		spinBlurPanel.setLayout( new FlowLayout( FlowLayout.LEFT ));
		
		blurChooserPanel = new JPanel();
		blurChooserPanel.setLayout( new FlowLayout(FlowLayout.LEFT) );
		
		controlPanel = new JPanel();
		controlPanel.setLayout( new GridLayout( 2, 1 ));
		
		blurChooserPanel.add(new JLabel("Blurs:"));
		blurChooserPanel.add(blurChooser);
		
		spinBlurPanel.setEnabled(false);
		spinBlurPanel.add(spinBlurJoy);
		
		controlPanel.add(blurChooserPanel);
		controlPanel.add(spinBlurPanel);
		
		JPanel c = getContentPanel();
		c.setLayout(new GridLayout(2, 1));
		c.add(blurChooserPanel);
		c.add(spinBlurPanel);
	}
	
	/**
	 * Joystick class controls the angle of 
	 * rotation for spin blur.
	 * */
	private class Joystick extends JPanel {  

	    /**
		 * Serial UID
		 */
		private static final long serialVersionUID = 1L;
		
		private int dispW = 200;
	    private int dispH = 300;
	    private final Point pos;
	    
	    private double angle = 0.0;
	    
	    private float joySz = 0;

	    public Joystick( float joySize ) {
	    	this.joySz = joySize;
	    	
	        setSize( dispW, dispH );
	        pos = new Point( (dispW >> 1) - (int) joySize >> 1, (dispH >> 1) - (int) joySize >> 1 );
	        //pos = new Point( 0, 0 );
	        SimpleJoystick myJoystick = new SimpleJoystick( 150, pos, 100 );
	        add( myJoystick, BorderLayout.PAGE_END );
	    }
	    
	    class SimpleJoystick extends JPanel {

	        private static final long serialVersionUID = 1L;
	        //Maximum value for full horiz or vert position where centered is 0:
	        private int joyOutputRange;
	        private float joyWidth, joyHeight;
	        private float joyCenterX, joyCenterY;  //Joystick displayed Center
	        //Display positions for text feedback values:
	        private int textHorizPos, textVertPos;
	        private int fontSpace = 12;
	        private float curJoyAngle;    //Current joystick angle
	        private float curJoySize;     //Current joystick size
	        private boolean isMouseTracking;
	        private boolean leftMouseButton;
	        private int mouseX, mouseY;
	        private Stroke lineStroke = new BasicStroke( 10, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND );
	        private final Point position;

	        public SimpleJoystick(final int joyOutputRange, final Point position, final int joySize) {
	            this.joyOutputRange = joyOutputRange;
	            this.position = position;
	            joySz = joySize;
	            joyWidth = joySize;
	            joyHeight = joyWidth;
	            joySz = joyWidth / 2;
	            
	            setPreferredSize( new Dimension((int) joyWidth + 50, (int) joyHeight + 20 )); //+80
	            setBackground( new Color( 226, 226, 226 ));
	            
	            joyCenterX = ( getPreferredSize().width >> 1 );
	            joyCenterY = ( getPreferredSize().height >> 1 );
	            
	            MouseAdapter mouseAdapter = new MouseAdapter() {
	            	
	                @Override
	                public void mouseMoved( final MouseEvent e ) {
	                    mouseCheck( e );
	                }

	                @Override
	                public void mousePressed( final MouseEvent e ) {
	                    leftMouseButton = SwingUtilities.isLeftMouseButton( e );
	                    mouseCheck( e );
	                }
	            };
	            addMouseMotionListener( mouseAdapter );
	            addMouseListener( mouseAdapter );
	        }

	        private void mouseCheck( final MouseEvent e ) {
	            mouseX = e.getX();
	            mouseY = e.getY();
	            float dx = mouseX - joyCenterX;
	            float dy = mouseY - joyCenterY;
	            if (leftMouseButton) {
	                isMouseTracking = true;
	            } else {
	                isMouseTracking = false;
	            }
	            if (isMouseTracking) {
	                curJoyAngle = (float) Math.atan2(dy, dx);
	                curJoySize = (float) Point.distance(mouseX, mouseY,
	                        joyCenterX, joyCenterY);
	            } else {
	                curJoySize = 0;
	            }
	            if (curJoySize > joySz) {
	                curJoySize = joySz;
	            }
	            position.x = (int) (joyOutputRange * (Math.cos(curJoyAngle)
	                    * curJoySize) / joySz);
	            position.y = (int) (joyOutputRange * (-(Math.sin(curJoyAngle)
	                    * curJoySize) / joySz));
	            SwingUtilities.getRoot(SimpleJoystick.this).repaint();
	        }

	        @Override
	        protected void paintComponent( final Graphics g ) {
	            super.paintComponent(g);
	            Graphics2D g2 = (Graphics2D) g;
	            
	            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
	                    RenderingHints.VALUE_ANTIALIAS_ON);
	            g2.setColor(Color.LIGHT_GRAY);
	            g2.fillOval( 20, 20, (int) joyWidth, (int) joyHeight );
	            
	            //rotate and draw joystick line segment:
	            Graphics2D g3 = (Graphics2D) g2.create();
	            g3.setColor(Color.GRAY);
	            g3.translate( 20 + joyWidth / 2, 20 + joyHeight / 2 );
	            g3.fillOval( -10, -10, 20, 20);
	            g3.rotate( curJoyAngle );
	            g3.setStroke( lineStroke );
	            g3.drawLine( 0, 0, (int) curJoySize, 0 );
	            g3.dispose();
	            
	            g2.setColor(Color.GRAY);
	            g3.fillOval( 0, 0, 20, 20);
	            
	            textHorizPos = 20;
	            textVertPos = 20;
	            
	            g2.setColor( Color.red );
	            g2.drawString( "Angle: ", textHorizPos, textVertPos );
	            textHorizPos += ( 4 * fontSpace );
	            
	            //In radians.
	           angle = Math.atan2( position.y, position.x );
	           //In degrees.
	           g2.drawString( String.valueOf( angle * 180 / 3.141592653589 ), textHorizPos, textVertPos );
	        }
	    }
	}
}