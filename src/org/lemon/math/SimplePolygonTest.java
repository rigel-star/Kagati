package org.lemon.math;

import java.util.ArrayList;
import java.util.List;

public class SimplePolygonTest {
	
	public static void main( String[] args ) {
		
		List<Vec2> crds = new ArrayList<>();
		
		crds.add( new Vec2( 3, 4 ));
		crds.add( new Vec2( 2, 1 ));
		crds.add( new Vec2( 7, 1 ));
		crds.add( new Vec2( 7, 3 ));
		
		SimplePolygon poly = new SimplePolygon( crds );
		
		System.out.println( poly.area() );
	}
	
}
