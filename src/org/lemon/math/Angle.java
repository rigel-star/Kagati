package org.lemon.math;

import java.awt.Point;

public class Angle {

	public static void main(String[] args) {
		
		
		var origin = new Point(2, 3);
		var dest = new Point(9, 5);
		
		var originVec = new Vec2d(origin.x, origin.y);
		var destVec = new Vec2d(dest);
		
		var dir = destVec.dir(destVec);
		System.out.println(Math.toDegrees(dir));
	}

}
