package org.lemon.math;

public class BasicMath {
	//euclidian distance of two RGB colors.
	public static double dist(double r1, double g1, double b1, double r2, double g2, double b2) {
		var deltaR = r2 - r1;
		var deltaG = g2 - g1;
		var deltaB = b2 - b1;
		return deltaR * deltaR + deltaG * deltaG + deltaB + deltaB;
	}
}