package org.lemon.math;

import java.awt.Point;

public class Vec2 {
	
	public double x;
	public double y;
	
	public Vec2( Point point ) {
		this.x = point.x;
		this.y = point.y;
	}
	
	public Vec2( double x, double y ){
		this.x = x;
		this.y = y;
	}
	
	/**
	 * Add two vectors.
	 * @param v2 		Another vector.
	 * */
	public Vec2 add( Vec2 v2 ) {
		double x = this.getX() + v2.getX();
		double y = this.getY() + v2.getY();
		return new Vec2( x, y );
	}
	
	
	/**
	 * Mid point of two vectors.
	 * @param v2 		Another vector.
	 * */
	public Vec2 midpoint( Vec2 v2 ) {
		this.x = this.x + v2.x;
		this.y = this.y + v2.y;
		double x = ( 1 * this.getX()) / 2;
		double y = ( 1 * this.getY()) / 2;
		return new Vec2( x, y );
	}
	
	
	/**
	 * @return Magnitude of a vector.
	 * */
	public double mag() {
		double mag = Math.sqrt(Math.pow(getX(), 2) + Math.pow(getY(), 2));
		return mag;
	}
	
	
	/**
	 * @return Direction of a vector.
	 * */
	public double dir() {
		return Math.atan(this.y / this.x);
	}
	
	
	/**
	 * @param v2 		Another vector.
	 * @return Direction between two vectors.
	 * */
	public double dir(Vec2 v2) {
		
		double dir = (v2.y - this.y) / (v2.x - this.x);
		double finaldir = Math.atan(dir);
		
		return finaldir;
	}
	
	
	/**
	 * @param v2 	Another vector.
	 * @return 		Distance between two vectors.
	 */
	public double dist( Vec2 v2 ) {
		double dist = Math.sqrt( Math.pow( v2.getX() - this.getX(), 2 ) + Math.pow(( v2.getX() - this.getX()), 2  ));
		return dist;
	}

	
	public double getX() {
		return this.x;
	}

	
	public double getY() {
		return this.y;
	}
}
