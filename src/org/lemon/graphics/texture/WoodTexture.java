package org.lemon.graphics.texture;

import java.io.IOException;

import org.lemon.graphics.Texture;
import org.lemon.image.LImage;
import org.lemon.image.LImageIO;

public class WoodTexture extends Texture {
	
	@Override
	public LImage getDrawable() {
		
		LImage img = null;
		try {
			img = LImageIO.read( "textures/wood_txtr.png" );
		} catch( IOException ex ) {
			ex.printStackTrace();
		}
		
		return img;
	}
	
	@Override
	public String getName() {
		return "Wooden";
	}
}
