package org.lemon.gui.texture;

import java.awt.Color;
import java.io.IOException;

import javax.swing.DefaultListModel;
import javax.swing.JList;

import org.lemon.image.LImage;
import org.lemon.image.LImageIO;
import org.lemon.image.Texture;
import org.lemon.image.WoodTexture;

public class TextureList extends JList<TexturePanel> {
	
	/**
	 * Serial UID
	 */
	private static final long serialVersionUID = 1L;
	
	private static DefaultListModel<TexturePanel> model = new DefaultListModel<>();
	private static TextureItemRenderer renderer = new TextureItemRenderer();
	
	
	public TextureList() {
		super( model );
		setCellRenderer( renderer );
		setLayoutOrientation( JList.HORIZONTAL_WRAP );
		setVisibleRowCount( -1 );
		
		renderer.setSelectionBackground( new Color( 240, 240, 240 ));
		
		add( new TexturePanel( new WoodTexture()) );
		add( new TexturePanel( new Texture() {
			
			@Override
			public String getName() {
				return "Pebble";
			}
			
			
			@Override
			public LImage getDrawable() {
				LImage img = null;
				try {
					img = LImageIO.read( "textures/pebble_txtr.jpg" );
				} catch( IOException ex ) {
					ex.printStackTrace();
				}
				
				return img;
			}
		}));
	}
	
	
	public void add( TexturePanel tx ) {
		model.addElement( tx );
	}
	
	
	public void remove( TexturePanel tx ) {
		model.removeElement( tx );
	}
	
	
	public void remove( int index ) {
		model.removeElementAt( index );
	}

	
	public int getTextureCount() {
		return model.size();
	}
}
