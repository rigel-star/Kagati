package org.lemon.gui.node;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.Stroke;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import org.lemon.filter.BlurFilter;
import org.lemon.filter.GaussianBlurImageFilter;
import org.lemon.filter.SpinBlurImageFilter;
import org.lemon.gui.ImageView;
import org.lemon.gui.Layer;
import org.lemon.gui.LayerContainer;
import org.lemon.gui.Node;
import org.lemon.gui.NodePt;
import org.lemon.gui.NodePt.NodePtType;
import org.lemon.gui.image.ImagePanel;
import org.lemon.image.LImage;
import org.lemon.lang.LemonObject;
import org.lemon.lang.NonNull;
import org.lemon.math.Vec2;
import org.lemon.tools.select.PolygonalSelectTool;

@LemonObject( type = LemonObject.GUI_CLASS )
public class BlurFilterNodeComponent extends NodeComponent implements SenderNode, ActionListener {
	
	/**
	 * Serial UID
	 * */
	private static final long serialVersionUID = 1L;
	
	private NodePt[] nodes = new NodePt[1];
	private NodePt imgNode = null;
	
	private JPanel controlPanel = null;
	
	private JLabel blurChooserTxt = new JLabel( "Blurs:" );
	private JComboBox<String> blurChooser = null;
	
	private JPanel spinBlurPanel = new JPanel();
	private JPanel blurChooserPanel = new JPanel();
	
	private Joystick spinBlurJoy = new Joystick( 100 );
	
	private final String GAUSS = "Gaussian Blur";
	private final String MOTION = "Motion Blur";
	private final String SPIN = "Spin Blur";
	private final String BOX = "Box Blur";
	
	private String choosenBlur = GAUSS;
	
	public BlurFilterNodeComponent( @NonNull final LayerContainer lycont ) {
		super( lycont );
		init();
		setSize( 200, 300 );
		setTitle( "Blur" );
		setResizable( false );
		setVisible( true );
		setClosable( true );
		setLocation( 20, 50 );
		
		var start = new Point( getLocation().x + this.getWidth(), getLocation().y + (this.getHeight() / 2) );
		imgNode = new NodePt( new Vec2( start ), null, this, NodePtType.SENDER );
		nodes[0] = imgNode;
		
		blurChooserPanel.add( blurChooserTxt );
		blurChooserPanel.add( blurChooser );
		
		spinBlurPanel.setEnabled( false );
		spinBlurPanel.add( spinBlurJoy );
		
		controlPanel.add( blurChooserPanel );
		controlPanel.add( spinBlurPanel );
		
		Container c = getContentPane();
		c.add( controlPanel );
	}
	
	/**
	 * Initialize the widgets.
	 * */
	private void init() {
		
		String[] blurs = {
				"Select",
				GAUSS,
				BOX,
				MOTION,
				SPIN
		};
		
		blurChooser = new JComboBox<String>( blurs );
		blurChooser.addActionListener( this );
		blurChooser.setPreferredSize( new Dimension( 130, 40 ) );
		
		spinBlurPanel = new JPanel();
		spinBlurPanel.setLayout( new FlowLayout( FlowLayout.LEFT ));
		
		blurChooserPanel = new JPanel();
		blurChooserPanel.setLayout( new FlowLayout(FlowLayout.LEFT) );
		
		controlPanel = new JPanel();
		controlPanel.setLayout( new GridLayout( 2, 1 ));
	}
	
	/**
	 * Init the currently selected blur method.
	 * */
	private void startBlur() {
		
		switch ( choosenBlur ) {
		
		case SPIN: {
			/*adding extra options for spin blur*/
			if ( !spinBlurPanel.isEnabled() )
				spinBlurPanel.setEnabled( true );
			
			applyBlur( choosenBlur );
			break;
		}
		
		case MOTION:
		case BOX:
		case GAUSS: {
			spinBlurPanel.setEnabled( false );
			applyBlur( choosenBlur );
			break;
		}
		
		default: {
			break;
		}
		
		}
		
		controlPanel.revalidate();
		revalidate();
	}
	
	private BufferedImage img = null;
	private LImage limg = null;
	private Component view = null;
	
	/**
	 * Apply blur to an image.
	 * 
	 * @param blur 		Specific blur.
	 * @return
	 * */
	private void applyBlur( String blur ) {
		
		if( imgNode.getConnections().size() == 0 ) {
			return;
		}
		
		for( Node fc: imgNode.getConnections() ) {	
			
			if( fc instanceof ImageView ) {
				view = ( (ImageView) fc ).getImagePanel();
				img = ( (ImageView) fc ).getImagePanel().getImage();
				limg = new LImage( img );
			}
			
			new Thread( new Runnable() {
				
				@Override
				public void run() {
					
					BlurFilter blurFilter = null;
					
					switch( blur ) {
					
					case BOX: {
						spinBlurPanel.setEnabled( false );
						break;
					}
					
					case GAUSS: {
						spinBlurPanel.setEnabled( false );
						blurFilter = new GaussianBlurImageFilter();
						doBlur( blurFilter );
						//limg = blurFilter.blur( limg );
						break;
					}
					
					case SPIN: {
						if ( !spinBlurPanel.isEnabled() )
							spinBlurPanel.setEnabled( true );
						
						blurFilter = new SpinBlurImageFilter( 0, (float) spinBlurJoy.getAngle(), .2f, 0 );
						doBlur( blurFilter );
						//limg = blurFilter.blur( limg );
						break;
					}
					}
				}
			}).start();
		}
		view.revalidate();
	}
	
	/**
	 * Start to do blur.
	 * */
	private void doBlur( BlurFilter bf ) {
		
		ImagePanel panel = (ImagePanel) view;
		
		if( panel.hasAreaSelected() ) {		
			
			if( panel.getCurrentMouseListener() instanceof PolygonalSelectTool ) {
				img = ((PolygonalSelectTool) panel.getCurrentMouseListener()).getSelectedAreaImage();
				img = bf.blur( new LImage( img )).getAsBufferedImage();
				Graphics2D g = panel.getImage().createGraphics();
				g.drawImage( img, 0, 0, null );
				g.dispose();
				panel.repaint();
				return;
			}
		}
		else {
			img = panel.getImage();
		}
		
		img = bf.blur( new LImage( img )).getAsBufferedImage();
		panel.setImage( img );
		panel.repaint();
	}
	
	@Override
	public Dimension getPreferredSize() {
		return new Dimension( 200, 140 );
	}
	
	@Override
	public NodePt[] getSenderNodePts() {
		return nodes;
	}
	
	@Override
	public void updateSenderNodePts() {
		imgNode.start.x = getLocation().x + getWidth();
		imgNode.start.y = getLocation().y + ( getHeight() / 2 );
	}
	
	@Override
	public NodeType getNodeType() {
		return NodeType.SENDER;
	}

	@Override
	public void actionPerformed( ActionEvent e ) {
		
		if( e.getSource() == blurChooser ) {
			choosenBlur = (String) blurChooser.getSelectedItem();
			startBlur();
		}
		
		spinBlurPanel.repaint();	
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
	           applyBlur( choosenBlur );
	        }
	    }
	    
	    public double getAngle() {
	    	return angle;
	    }
	}

	@Override
	public ImageIcon getNodeIcon() {
		return new ImageIcon( "icons/layer/blur.png" );
	}
}
