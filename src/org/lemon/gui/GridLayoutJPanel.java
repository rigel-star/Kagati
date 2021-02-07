package org.lemon.gui;

import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JPanel;

public class GridLayoutJPanel extends JPanel {

	/**
	 * Serial UID
	 */
	private static final long serialVersionUID = -9034989348L;
	
	protected int hgap = 2;
	protected int vgap = 2;
	protected int width = 100;
	protected int height = 100;
	
	protected int rows = 0;
	protected int cols = 0;
	
	public GridLayoutJPanel( int width, int height, int rows, int cols ) {
		this.width = width;
		this.height = height;
		this.rows = rows;
		this.cols = cols;
		init();
		setLayout( new GridLayout( rows, cols, width, height));
		setPreferredSize( new Dimension( width, height ));
	}

	/**
	 * Initialize the widgets.
	 * */
	private void init() {
		
	}
	
	/**
	 * Set horizontal gap
	 * */
	public void setHorizontalGap( int hgap ) {
		this.hgap = hgap;
	}
	
	/**
	 * Set vertical gap
	 * */
	public void setVerticalGap( int vgap ) {
		this.vgap = vgap;
	}

}
