package org.lemon.filter;

import java.awt.Composite;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;

import org.lemon.graphics.ImageGraphics;
import org.lemon.image.LImage;

public class CompositeFilter extends AbstractImageFilter {
	
	private Composite composite = null;
	
	public CompositeFilter( Composite composite ) {
		this.composite = composite;
	}
	
	@Override
	public LImage filter( LImage limage ) {
		throw new 
		IllegalArgumentException( 
				"filter(limage) method is forbidden in CompositeFilter. Use compose(src, dst) instead." 
									);
	}
	
	public LImage compose( LImage src, LImage dst ) {
		
		if( dst == null )
			dst = ImageGraphics.createCompitableDestImage( src, null );
		
		BufferedImage bsrc = src.getAsBufferedImage();
		BufferedImage bdst = dst.getAsBufferedImage();
		
		Graphics2D g = bsrc.createGraphics();
        g.setRenderingHint( RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON );
        g.setRenderingHint( RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR );
        g.setComposite( composite );
        //g.drawRenderedImage( bdst, null );
        g.drawImage( bdst, null, 0, 0 );
        g.dispose();
        
        return new LImage( bsrc );
	}
	
	public void setComposite( Composite c ) {
		this.composite = c;
	}
	
	public Composite getComposite() {
		return composite;
	}
}
