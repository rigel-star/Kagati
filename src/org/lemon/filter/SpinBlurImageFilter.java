package org.lemon.filter;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;

import org.lemon.image.LImage;

public class SpinBlurImageFilter extends AbstractImageFilter implements BlurFilter {
	
	private float centreX = 0.5f, centreY = 0.5f;
    private float distance;
    private float angle;
    private float rotation;
    private float zoom;
    
    public SpinBlurImageFilter( float distance, float angle, float rotation, float zoom ) {
        this.distance = distance;
        this.angle = angle;
        this.rotation = rotation;
        this.zoom = zoom;
    }
	
	public void setAngle( float angle ) {
		this.angle = angle;
	}
	
	public float getAngle() {
		return angle;
	}
	
	public void setDistance( float distance ) {
		this.distance = distance;
	}
	
	public float getDistance() {
		return distance;
	}
	
	
	public void setRotation( float rotation ) {
		this.rotation = rotation;
	}

	
	public float getRotation() {
		return rotation;
	}
	
	
	public void setZoom( float zoom ) {
		this.zoom = zoom;
	}

	
	public float getZoom() {
		return zoom;
	}
	
	
	public void setCentreX( float centreX ) {
		this.centreX = centreX;
	}
	
	public float getCentreX() {
		return centreX;
	}
	
	public void setCentreY( float centreY ) {
		this.centreY = centreY;
	}
	
	public float getCentreY() {
		return centreY;
	}
	
	public void setCentre(Point2D centre) {
		this.centreX = (float)centre.getX();
		this.centreY = (float)centre.getY();
	}
	
	public Point2D getCentre() {
		return new Point2D.Float( centreX, centreY );
	}
	
    private int log2(int n) {
        int m = 1;
        int log2n = 0;

        while (m < n) {
            m *= 2;
            log2n++;
        }
        return log2n;
    }
    
	@Override
	public LImage filter( LImage limage ) {
		BufferedImage tsrc = limage.getAsBufferedImage();
		BufferedImage dst = new BufferedImage( limage.width, limage.height, tsrc.getType() );
        float cx = (float)tsrc.getWidth() * centreX;
        float cy = (float)tsrc.getHeight() * centreY;
        float imageRadius = (float)Math.sqrt( cx * cx + cy * cy );
        float translateX = (float)(distance * Math.cos( angle ));
        float translateY = (float)(distance * -Math.sin( angle ));
        float scale = zoom;
        float rotate = rotation;
        float maxDistance = distance + Math.abs(rotation * imageRadius) + zoom * imageRadius;
        int steps = log2((int)maxDistance);

		translateX /= maxDistance;
		translateY /= maxDistance;
		scale /= maxDistance;
		rotate /= maxDistance;
		
        if ( steps == 0 ) {
            Graphics2D g = dst.createGraphics();
            g.drawRenderedImage( tsrc, null );
            g.dispose();
            return new LImage( dst );
        }
        
        BufferedImage tmp = LImage.createCompitableDestImage( new LImage( tsrc ), null ).getAsBufferedImage();
        
        for ( int i = 0; i < steps; i++ ) {
            Graphics2D g = tmp.createGraphics();
            g.drawImage( tsrc, null, null );
			g.setRenderingHint( RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON );
			g.setRenderingHint( RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR );
			g.setComposite( AlphaComposite.getInstance( AlphaComposite.SRC_OVER, 0.5f ) );

            g.translate( cx + translateX, cy + translateY );
            g.scale( 1.0001 + scale, 1.0001 + scale );  /* The .0001 works round a bug on Windows 
            												where drawImage throws an ArrayIndexOutofBoundException*/
            if ( rotation != 0 )
                g.rotate( rotate );
            
            g.translate( -cx, -cy );
            g.drawImage( dst, null, null );
            g.dispose();
            
            BufferedImage ti = dst;
            dst = tmp;
            tmp = ti;
            tsrc = dst;

            translateX *= 2;
            translateY *= 2;
            scale *= 2;
            rotate *= 2;
        }
        return new LImage( dst );
	}
	
	@Override
	public LImage blur( LImage src ) {
		return filter( src );
	}
}
