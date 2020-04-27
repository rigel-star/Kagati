package org.lemon.image;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JInternalFrame;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.border.Border;

import org.lemon.copy.SharePixelsDg;
import org.lemon.image.ImagePanel.PanelMode;
import org.lemon.tools.BrushToolOptions;
import org.lemon.utils.ConnectionManager;

import application.MainBackgroundPane;

public class ImageView extends JInternalFrame {

	private static final long serialVersionUID = 1L;
	
	private ImageView 		connection;
	private ImageView 		self = this;
	private JPopupMenu 		pMenu;
	
	private ImagePanel 		imgPan = null;
	private BufferedImage 	src;
	private String 			title = null;
	private boolean 		close;
	
	/*Connections for this imageview*/
	private Map<String, ImageView> connections = new HashMap<String, ImageView>();
	private List<String> 	conOptionsTitles = new ArrayList<String>();
	private List<ImageView> conOptionsViews = new ArrayList<ImageView>();
	
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
	
	
	public ImageView(BufferedImage img, String title, boolean closeable, int panelMode) throws IOException {
        
		if(img == null)
			throw new NullPointerException("Image can't be null.");
		
		this.src = img;
		this.imgPan = new ImagePanel(img, panelMode);
		this.title = title;
		this.close = closeable;
		
		/*initialize options menu*/
		this.initOptionsMenu();
		
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
		
		MouseEventsHandler meh = new MouseEventsHandler();
		getImagePanel().addMouseListener(meh);
		getImagePanel().addMouseMotionListener(meh);
		
	}
	
	/**
	 * Initialize popup option menu i.e JPopupMenu. 
	 * This will popup when right mouse button clicked on ImageView.
	 * */
	public void initOptionsMenu() {
		this.pMenu = new JPopupMenu("Options");
		
		var connect = new JMenu("Connect with...");
		var brushes = new JMenuItem("Brushes...");
		var options = new JMenu("Options...");
		var delete = new JMenuItem("Delete...");
		
		
		/*editing options after connection*/
		initEditingOptions(options);
		
		/*For each connection option*/
		for(int i=0; i<getConOptionsTitles().size(); i++) {
			var st = this.conOptionsTitles.get(i);
			var item = new JCheckBoxMenuItem(st);
			connect.add(item);
			//connect.setComponentZOrder(item, i);
		}
		
		
		/*On connect items click*/
		for(int i=0;i<connect.getItemCount(); i++) {
			JCheckBoxMenuItem item = (JCheckBoxMenuItem) connect.getItem(i);
			item.addActionListener(action -> {
				
				if(getConnection() != null) {
					JOptionPane.showMessageDialog(self, "Already in connection");
					return;
				}
				
				String itemTitle = item.getText();
				connection = connections.get(itemTitle);
				if(setConnection(connection)) {
					options.setEnabled(true);
					createConnection(self, connection);
				}
				revalidate();
			});
		}
		
		
		
		/*If only this ImageView is connected with another component, set options enabled.*/
		if(getConnection() == null)
			options.setEnabled(false);
		
		
		/*If only this ImageView's mode is Canvas Mode, set brush options enabled.*/
		if(getImagePanel().getPanelMode() == PanelMode.CANVAS_MODE) {
			brushes.addActionListener(action -> {
				Point mouse = MouseInfo.getPointerInfo().getLocation();
				new BrushToolOptions(self, mouse.x, mouse.y);
			});
		} else brushes.setEnabled(false);
		
		
		/*Remove ImageView from scene*/
		delete.addActionListener(action -> {
			dispose();
		});
		
		this.pMenu.add(connect);
		this.pMenu.add(brushes);
		this.pMenu.add(options);
		this.pMenu.add(delete);
	}

	
	
	/*Editing options, those will be available after connection*/
	private void initEditingOptions(JMenu menu) {
		var blend = new JMenuItem("Blend");
		var sharePix = new JMenuItem("Share Pixels");
		
		sharePix.addActionListener(action -> {
			new SharePixelsDg(self.getImage(), connection.getImage());
		});
		
		menu.add(blend);
		menu.add(sharePix);
	}
	
	
	/**
	 * Creates connection between two components
	 * */
	private void createConnection(ImageView start, ImageView end) {
		MainBackgroundPane parent = (MainBackgroundPane) getParent();
		parent.makeConnection(new ConnectionManager.Pair(start, end));
		parent.revalidate();
	}
	
	
	/**
	 * Connect two ImageView to share data.<p>
	 * @param {{@code ImageView} to connect with
	 * @return {@code false} if connectTo is null else {@code true}.
	 * */
	public boolean setConnection(ImageView connectTo) {
		if(connectTo == null)
			return false;	
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
		
		this.initOptionsMenu();
	}
	
	
	/**
	 * Get all the available connections for this ImageView.
	 * */
	public List<String> getConOptionsTitles() {
		return this.conOptionsTitles;
	}
	
	
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
				pMenu.show(self, e.getX(), e.getY());
			}
		}
		
	}
	
	
}


