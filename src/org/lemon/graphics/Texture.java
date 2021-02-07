package org.lemon.graphics;

import org.lemon.graphics.texture.WoodTexture;
import org.lemon.image.LImage;

public abstract class Texture {
	
	
	/***
	 * Wooden Texture.
	 */
	public static final Texture WOOD = new WoodTexture();
	
	/**
	 * Get drawable graphics attached with texture.
	 * @return Drawable graphics.
	 * */
	public abstract LImage getDrawable();
	
	
	/**
	 * Get name of this texture.
	 * @return Name of this {@link Texture}
	 * */
	public abstract String getName();
	
}
