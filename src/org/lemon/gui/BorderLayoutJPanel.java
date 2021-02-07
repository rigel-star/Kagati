package org.lemon.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.LayoutManager;

import javax.swing.JPanel;

public class BorderLayoutJPanel extends JPanel {

	/**
	 * Serial UID
	 */
	private static final long serialVersionUID = 34532541L;
	
	protected int hgap = 2;
	protected int vgap = 2;
	protected int width = 100;
	protected int height = 100;
	
	private JPanel centerPanel, topPanel, bottomPanel, rightPanel, leftPanel;
	
	public BorderLayoutJPanel( int width, int height ) {
		init();
		setLayout( new BorderLayout( hgap, vgap ));
		setPreferredSize( new Dimension( width, height ));
		add( centerPanel, BorderLayout.CENTER );
		add( topPanel, BorderLayout.NORTH );
		add( bottomPanel, BorderLayout.SOUTH );
		add( rightPanel, BorderLayout.EAST );
		add( leftPanel, BorderLayout.WEST );
	}
	
	/**
	 * Initalize the widgets.
	 * */
	private void init() {
		this.centerPanel = new JPanel();
		this.topPanel = new JPanel();
		this.rightPanel = new JPanel();
		this.bottomPanel = new JPanel();
		this.leftPanel = new JPanel();
	}
	
	@Override
	public void setLayout( LayoutManager mgr ) {
		super.setLayout( new BorderLayout() );
	}
	
	/**
	 * @return Top panel.
	 * */
	public JPanel getTopPanel() {
		return this.topPanel;
	}
	
	/**
	 * @return Bottom panel.
	 * */
	public JPanel getBottomPanel() {
		return this.bottomPanel;
	}
	
	/**
	 * @return Center panel.
	 * */
	public JPanel getCenterPanel() {
		return this.centerPanel;
	}
	
	/**
	 * @return Right panel.
	 * */
	public JPanel getRightPanel() {
		return this.rightPanel;
	}
	
	/**
	 * @return Left panel.
	 * */
	public JPanel getLeftPanel() {
		return this.leftPanel;
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
