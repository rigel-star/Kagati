package org.lemon.filter.gui;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JDialog;
import javax.swing.WindowConstants;

/**
 * 
 * Filter Panel Window contains {@link AbstractFilterPanel}
 *  of a specific {@link AbstractImageFilter}.
 * 
 * */
public class FilterPanelWindow extends JDialog {
	
	/**
	 * Serial UID
	 */
	private static final long serialVersionUID = 1L;
	
	private static AbstractFilterPanel pan = null;
	
	protected int width = 0;
	protected int height = 0;
	
	public FilterPanelWindow() {
		this( null );
	}
	
	/**
	 * Constrcuts {@code FilterPanelWindow} with specified
	 * {@link AbstractFilterPanel}.
	 * */
	public FilterPanelWindow( AbstractFilterPanel p ) {
		
		if ( p == null )
			throw new NullPointerException( "Specified panel is null." );
		
		pan = p;
		
		Container c = getContentPane();
		c.add( pan );
		
		Dimension afpsize = null;
		if ( width != 0 && height != 0 )
			afpsize = new Dimension( width, height );
		else {
			afpsize = p.getPreferredSize();
			afpsize.height += 40;
			afpsize.width += 40;
		}
		
		setTitle( "Filter" );
		setSize( afpsize.width + 40, afpsize.height + 40 );
		setResizable( false );
		setDefaultCloseOperation( WindowConstants.DISPOSE_ON_CLOSE );
		addWindowListener( new WindowHandler() );
		setVisible( true );
	}
	
	/**
	 * Window handler for {@link FilterPanelWindow}.
	 * */
	static class WindowHandler extends WindowAdapter {
		@Override
		public void windowClosing( WindowEvent e ) {
			super.windowClosing( e );
			pan.override();
		}
	}
}
