package org.lemon.gui;

import org.lemon.image.LImage;

/**
 * 
 * Store the copy of properties of specific {@code ImageView}.
 * 
 * */
public final class ImageViewProperties {

	private String title = null;
	private LImage img = null;
	
	public ImageViewProperties( ImageView view ) {
		this.title = view.getTitle();
		this.img = new LImage( view.getCurrentImage() );
	}
	
	public String getTitle() {
		return title;
	}
	
	public LImage getImage() {
		return img;
	}
}
