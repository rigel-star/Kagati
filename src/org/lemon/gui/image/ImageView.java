package org.lemon.gui.image;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.JInternalFrame;
import javax.swing.border.Border;

import org.lemon.gui.image.menus.ImageViewMenu;
import org.rampcv.utils.Tools;

/**
 * Parent of ImageView is always MainBackgroundPane class. 
 * ImageView is for holding image opened by user in application. 
 * ImageView has one main feature, connection. Connection is basically for connecting 
 * two ImageViews. If two views connected, they can be blended, edited, etc. together.
 * */

public class ImageView extends JInternalFrame implements Cloneable {
	private static final long serialVersionUID = 1L;
	
	private ImageView 				connection;
	
	private ImageView 				self = this;
	
	private ImagePanel 				imgPan = null;
	
	private BufferedImage 			src;
	
	private String 					title = null;
	
	private boolean 				close;
	
	private ImageViewMenu			menu;
	
	/*Connections for this imageview*/
	private Map<String, ImageView> 	connections = new HashMap<String, ImageView>();
	private List<String> 			conOptionsTitles = new ArrayList<String>();
	private List<ImageView> 		conOptionsViews = new ArrayList<ImageView>();
	
	
	private MouseEventsHandler 		meh;
	
	
	/*Note image can't be null*/
	public ImageView(BufferedImage img) throws IOException {
		this(img, null, false, ImagePanel.DEFAULT_MODE);
	}
	
	
	public ImageView(BufferedImage img, String title) throws IOException {
		this(img, title, false, ImagePanel.DEFAULT_MODE);
	}
	
	
	public ImageView(BufferedImage img, boolean closeable) throws IOException {
		this(img, null, closeable, ImagePanel.DEFAULT_MODE);
	}
	
	
	public ImageView(BufferedImage img, int panelMode) throws IOException {
		this(img, null, false, panelMode);
	}
	
	
	public ImageView(BufferedImage img, String title, int panelMode) throws IOException {
		this(img, title, false, panelMode);
	}
	
	
	public ImageView(BufferedImage img, boolean closeable, int panelMode) throws IOException {
		this(img, null, closeable, panelMode);
	}
	
	
	public ImageView(BufferedImage img, String title, boolean closeable) throws IOException {
		this(img, title, closeable, ImagePanel.DEFAULT_MODE);
	}
	
	
	public ImageView(BufferedImage img, String title, boolean closeable, int panelMode)  {
        
		if(img == null)
			throw new NullPointerException("Image can't be null.");
		
		this.src = img;
		this.imgPan = new ImagePanel(img, panelMode);
		this.title = title;
		this.close = closeable;
		
		//border for panel
		Border layoutBorder = BorderFactory.createLineBorder(Color.BLACK, 2);
		
		setLayout(new BorderLayout());
		setBorder(layoutBorder);
		setTitle(this.title);
		setClosable(closeable);
		setVisible(true);
		setSize(new Dimension(img.getWidth(), img.getHeight()));
		setLayout(new BorderLayout());
		add(this.imgPan, BorderLayout.CENTER);
		
		/* Revalidatng simply re-adds the listeners to this ImageView.
		 * I'm calling revalidate here even im not revalidating is cause 
		 * even for the first time i have to first of all add listener
		 * and if all of the listeners removed from this ImageView, on
		 * calling this method, again default mouse listeners will be applied.
		 * BADDDDDDDDD ENGLISSSSSSSSSSHHHHHHHHHH
		 * */
		revalidateListeners();
		
	}
	
	
	/*
	 * Duplicates itself :D
	 * Simply returning super.clone() was causing duplicate not to move from one place to another.
	 * So, have to take this approach of making new ImageView object.
	 * */
	@Override
	public Object clone() throws CloneNotSupportedException {
		var newImg = Tools.copyImage(getImage());
		ImageView duplicate = new ImageView(newImg, getTitle(), getCloseableState(), getImagePanel().getPanelMode());
		return duplicate;
	}
	
	
	
	/**
	 * Revalidate every listener applied to this ImageView
	 * */
	public void revalidateListeners() {
		meh = new MouseEventsHandler();
		getImagePanel().addMouseListener(meh);
		getImagePanel().addMouseMotionListener(meh);
	}
	
	
	
	/**
	 * Connect two ImageView.<p>
	 * @param {@code ImageView} to connect with
	 * @return {@code false} if connectTo is null else {@code true}.
	 * */
	public boolean setConnection(ImageView connectTo) {
		/*set this imageviews connection*/
		this.connection = connectTo;
		return true;
	}
	
	
	/**
	 * Get connected ImageView object.
	 * @return connection
	 * */
	public ImageView getConnection() {
		return this.connection;
	}
	
	
	/**
	 * Set connection options for this ImageView.
	 * @param {{@code List<String>}  of options
	 * */
	public void setConOptions(List<String> optionsTitle, List<ImageView> views) {
		connections.clear();
		this.conOptionsTitles = optionsTitle;
		this.conOptionsViews = views;
		
		for(int i=0; i<views.size(); i++) {
			connections.put(optionsTitle.get(i), views.get(i));
		}
	}
	
	
	/**
	 * Get all the available connections titles for this ImageView.
	 * @return list of connections titles
	 * */
	public List<String> getConOptionsTitles() {
		return this.conOptionsTitles;
	}
	
	
	/**
	 * Get all the available connections of this {@code ImageView}.
	 * @return list of available ImageViews to connect with.
	 * */
	public List<ImageView> getConOptionsViews(){
		return this.conOptionsViews;
	}
	
	
	/**
	 * Sets the custom {@code ImagePanel} object for {@code this} {@code ImageView}.<p>
	 * @param imgPan 	{@code ImagePanel} object.
	 * */
	public void setImagePanel(ImagePanel imgPan) {
		this.imgPan.setImage(imgPan.getImage());
		this.imgPan.setPanelMode(imgPan.getPanelMode());
		revalidate();
	}
	
	
	/**
	 * @return 			{@code ImagePanel}
	 * */
	public ImagePanel getImagePanel() {
		return this.imgPan;
	}
	
	
	/**
	 * @return {@code BufferedImage}
	 * */
	public BufferedImage getImage() {
		return this.src;
	}
	
	
	/**
	 * @return		{@code true} if {@code this ImageView} is closeable <p> else returns {@code false}
	 * */
	public boolean getCloseableState() {
		return this.close;
	}
	
	
	/**
	 * Mouse events handler for ImageView.
	 * */
	private class MouseEventsHandler extends MouseAdapter {
		
		@Override
		public void mouseClicked(MouseEvent e) {
			super.mouseClicked(e);
			
			/*BUTTON3 = Right Mouse Button*/
			if(e.getButton() == MouseEvent.BUTTON3) {
				//show available options
				menu = new ImageViewMenu(self);
				menu.show(self, e.getX(), e.getY());
			}
		}
		
	}
	
	
}


