package org.lemon.math;

import java.util.List;

public class SimplePolygon {
	
	private List<Vec2> coords;
	private double area = 0.0;
	
	/**
	 * Construct a polygon with given coordinates.
	 * @param coords 	List of points.
	 * */
	public SimplePolygon( List<Vec2> coords ) {
		this.coords = coords;
		
		if( coords.size() == 0 ) {
			throw new IllegalArgumentException( 
					"At least give 4 points to make a polygon. Your points count: " + coords.size() );
		}
		
		calculateArea( coords );
	}
	
	
	/**
	 * Pre calculate the area of of polygon.
	 * @param coords 	Coordinate points.
	 * */
	private void calculateArea( List<Vec2> coords ) {
		
		for( int n = 0; n < coords.size(); n++ ) {
			if( n == coords.size() - 1 ) {
				
				area += ( coords.get( n ).x * coords.get( 0 ).y ) - 
						( coords.get( 0 ).x * coords.get( n ).y ) ;
				break;
			}
			
			area += ( coords.get( n ).x * coords.get( n + 1 ).y ) - 
					( coords.get( n + 1 ).x * coords.get( n ).y ) ;
		}
		
		area /= 2;
	}
	
	
	/**
	 * @return Area of polygon.
	 * */
	public double area() {
		return area;
	}
	
	
	/**
	 * @return List of coordinates of polygon.
	 * */
	public List<Vec2> getCoords() {
		return coords;
	}
}
