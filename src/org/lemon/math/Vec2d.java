package org.lemon.math;

public class Vec2d {
	
	public int x;
	public int y;
	
	public Vec2d(int x, int y){
		this.x = x;
		this.y = y;
	}
	
	
	//adding vectors
	//formulae: new vec2d = ((x1+x1), (y1+y2))
	public Vec2d add(Vec2d v2) {
		int x = this.getX() + v2.getX();
		int y = this.getY() + v2.getY();
		return new Vec2d(x, y);
	}
	
	
	public Vec2d midpoint(Vec2d v2) {
		//add v2 with v1
		//this.add(v2);
		this.x = this.x + v2.x;
		this.y = this.y + v2.y;
		//multiply v1 with 1 and divide by 2
		int x = (1 * this.getX()) / 2;
		int y = (1 * this.getY()) / 2;
		
		return new Vec2d(x, y);
	}
	
	//magnitude of a vector
	//formulae: square root of x1 power 2 + x2 power 2
	public double mag() {
		double mag = Math.sqrt(Math.pow(getX(), 2) + Math.pow(getY(), 2));
		return mag;
	}
	
	//direction of a vector
	//formulae: -> res = y2-y1/x2-x1 -> atan(res) or tan-1(res)
	public double dir(Vec2d v2) {
		double dir = (v2.y - this.getX()) / (v2.x - this.getY());
		return Math.atan(dir);
	}
	
	//dist of two vectors
	//formulae: square root of x2 - x1 power of 2 + y2 - y2 power of 2
	public double dist(Vec2d v2) {
		double dist = Math.sqrt(Math.pow(v2.getX() - this.getX(), 2) + Math.pow((v2.getX() - this.getX()), 2));
		return dist;
	}

	public int getX() {
		return this.x;
	}

	public int getY() {
		return this.y;
	}

}
