package org.lemon.gui;

import java.awt.Dimension;

import javax.swing.JComponent;

public class Gap extends JComponent {
    
    /**
	 * Serial UID
	 */
	private static final long serialVersionUID = 1L;


	/**
     * 
     * Creates filler with minimum size, but expandable infinitely.
     * 
     * */
    public Gap() {
        Dimension min = new Dimension( 0, 0 );
        Dimension max = new Dimension( Integer.MAX_VALUE, Integer.MAX_VALUE );
        setMinimumSize( min );
        setPreferredSize( min );
        setMaximumSize( max );
    }
    
    
    /**
     * 
     * Creates rigid filler.
     * 
     * */
    public Gap( int size ) {
        Dimension dim = new Dimension( size, size );
        setMinimumSize( dim );
        setPreferredSize( dim );
        setMaximumSize( dim );
    }
}
