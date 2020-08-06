package org.lemon.gui;

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

import org.lemon.AppGlobalProperties;
import org.lemon.filters.ResizeImage;
import org.lemon.gui.image.ImagePanel;
import org.lemon.gui.image.ImagePanel.PanelMode;
import org.lemon.gui.image.menus.ImageViewMenu;
import org.lemon.utils.Utils;
import org.rampcv.utils.Tools;


/**
 * Parent of ImageView is always MainBackgroundPane class. 
 * ImageView is for holding image opened by user in application. 
 * ImageView has one main feature, connection. Connection is basically for connecting 
 * two ImageViews. If two views connected, they can be blended, edited, etc. together.
 * */
@LemonObject(type = LemonObject.GUI_CLASS)
@Info(author = "Ramesh Poudel",
		date = 2020,
		version = 2)
public class ImageView extends JInternalFrame implements Cloneable {
	private static final long serialVersionUID = 1L;
	
	private ImageView 				connection;
	
	private ImageView 				self = this;
	
	private ImagePanel 				imgPan = null;
	
	private BufferedImage 			src, srcCopy;
	
	private String 					title = null;
	
	private boolean 				close;
	
	private ImageViewMenu			menu;
	
	/*Connections for this imageview*/
	private Map<String, ImageView> 	connections = new HashMap<String, ImageView>();
	private List<String> 			conOptionsTitles = new ArrayList<String>();
	private List<ImageView> 		conOptionsViews = new ArrayList<ImageView>();
	
	
	private MouseEventsHandler 		meh;
	
	private AppGlobalProperties		agp;
	
	private final Dimension MAX_IMG_SIZE = new Dimension(500, 500); 
	
	/**
	 * To check if the {@code ImageView} is showing original image or
	 *  image is resized or changed. If the image size is over than 500X500 then the image
	 *  will be resized to 500X500 otherwise {@code ImageView} will show actual image.
	 * */
	public boolean imageIsActual = true;
	
	
	/*Note image can't be null*/
	public ImageView(BufferedImage img) throws IOException {
		this(img, null, null, false, PanelMode.DEFAULT_MODE);
	}
	
	
	public ImageView(BufferedImage img, String title) throws IOException {
		this(img, null, title, false, PanelMode.DEFAULT_MODE);
	}
	
	
	public ImageView(BufferedImage img, boolean closeable) throws IOException {
		this(img, null, null, closeable, PanelMode.DEFAULT_MODE);
	}
	
	
	public ImageView(BufferedImage img, int panelMode) throws IOException {
		this(img, null, null, false, panelMode);
	}
	
	
	public ImageView(BufferedImage img, String title, int panelMode) throws IOException {
		this(img, null, title, false, panelMode);
	}
	
	
	public ImageView(BufferedImage img, boolean closeable, int panelMode) throws IOException {
		this(img, null, null, closeable, panelMode);
	}
	
	
	public ImageView(BufferedImage img, String title, boolean closeable) throws IOException {
		this(img, null, title, closeable, PanelMode.DEFAULT_MODE);
	}
	
	
	public ImageView(BufferedImage img, AppGlobalProperties agp, String title, boolean closeable, int panelMode)  {
        
		if(img == null)
			throw new NullPointerException("Image can't be null.");
		else {
			this.src = img;
			this.srcCopy = Utils.getImageCopy(src);
			
			var resize = new ResizeImage(srcCopy); 
			
			if(src.getHeight() > MAX_IMG_SIZE.height && src.getWidth() > MAX_IMG_SIZE.width) {
				srcCopy = resize.getImageSizeOf(MAX_IMG_SIZE.width, MAX_IMG_SIZE.height);
			}
			else if(src.getHeight() > MAX_IMG_SIZE.height) {
				srcCopy = resize.getImageSizeOf(src.getWidth(), MAX_IMG_SIZE.height);
			}
			else if(src.getWidth() > MAX_IMG_SIZE.width) {
				srcCopy = resize.getImageSizeOf(MAX_IMG_SIZE.width, src.getHeight());
			}
			
			if(resize.isDone()) {
				srcCopy.getGraphics().dispose();
			}
		}
		
		this.agp = agp;
		this.imgPan = new ImagePanel(srcCopy, agp, panelMode);
		this.title = title;
		this.close = closeable;
		
		//border for panel
		final Border layoutBorder = BorderFactory.createLineBorder(Color.BLACK, 2);
		
		setLayout(new BorderLayout());
		setBorder(layoutBorder);
		setTitle(this.title);
		setClosable(closeable);
		setVisible(true);
		setSize(new Dimension(srcCopy.getWidth(), srcCopy.getHeight()));
		setLayout(new BorderLayout());
		setMaximizable(true);
		setIconifiable(true);
		
		//var scroll = new JScrollPane(imgPan);
		
		add(imgPan, BorderLayout.CENTER);
		
		//add(new ImageZoomAndPan(img), BorderLayout.CENTER);
		
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
		var newImg = Tools.copyImage(getActualImage());
		ImageView duplicate = new ImageView(newImg, this.agp, getTitle(), getCloseableState(), getImagePanel().getPanelMode());
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
	 * Get non-edited or non-transformed original image.
	 * @return {@code BufferedImage}
	 * */
	public BufferedImage getActualImage() {
		return src;
	}
	
	
	/**
	 * Get edited or transformed image.
	 * @return {@code BufferedImage}
	 * */
	public BufferedImage getCurrentImage() {
		return srcCopy;
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


