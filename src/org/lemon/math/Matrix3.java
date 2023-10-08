package org.lemon.math;

public class Matrix3 {
	private double a11;
	private double a12;
	private double a13;
	private double a21;
	private double a22;
	private double a23;
	private double a31;
	private double a32;
	private double a33;
	
	public Matrix3(double a11, double a12, double a13, double a21, double a22, double a23, double a31, double a32,
			double a33) {
		this.a11 = a11;
		this.a12 = a12;
		this.a13 = a13;
		this.a21 = a21;
		this.a22 = a22;
		this.a23 = a23;
		this.a31 = a31;
		this.a32 = a32;
		this.a33 = a33;
	}

	public Matrix3(Matrix3 m) {
		this.a11 = m.a11;
		this.a12 = m.a12;
		this.a13 = m.a13;
		this.a21 = m.a21;
		this.a22 = m.a22;
		this.a23 = m.a23;
		this.a31 = m.a31;
		this.a32 = m.a32;
		this.a33 = m.a33;
	}
	
	// From http://www.dr-lex.be/random/matrix_inv.html
	public void invert() {
		double invDet = 1.0 / determinant();
		double nm00 = a33 * a22 - a32 * a23;
		double nm01 = -(a33 * a12 - a32 * a13);
		double nm02 = a23 * a12 - a22 * a13;
		double nm10 = -(a33 * a21 - a31 * a23);
		double nm11 = a33 * a11 - a31 * a13;
		double nm12 = -(a23 * a11 - a21 * a13);
		double nm20 = a32 * a21 - a31 * a22;
		double nm21 = -(a32 * a11 - a31 * a12);
		double nm22 = a22 * a11 - a21 * a12;
		a11 = nm00 * invDet;
		a12 = nm01 * invDet;
		a13 = nm02 * invDet;
		a21 = nm10 * invDet;
		a22 = nm11 * invDet;
		a23 = nm12 * invDet;
		a31 = nm20 * invDet;
		a32 = nm21 * invDet;
		a33 = nm22 * invDet;
	}

	// From http://www.dr-lex.be/random/matrix_inv.html
	public double determinant() {
		return a11 * (a22 * a33 - a23 * a32) - a12 * (a23 * a31 - a21 * a33) + a13 * (a21 * a32 - a22 * a31);
	}

	public void mul(double factor) {
		a11 *= factor;
		a12 *= factor;
		a13 *= factor;
		a21 *= factor;
		a22 *= factor;
		a23 *= factor;
		a31 *= factor;
		a32 *= factor;
		a33 *= factor;
	}

	public void mul(Matrix3 m) {
		double nm00 = a11 * m.a11 + a12 * m.a21 + a13 * m.a31;
		double nm01 = a11 * m.a12 + a12 * m.a22 + a13 * m.a32;
		double nm02 = a11 * m.a13 + a12 * m.a23 + a13 * m.a33;

		double nm10 = a21 * m.a11 + a22 * m.a21 + a23 * m.a31;
		double nm11 = a21 * m.a12 + a22 * m.a22 + a23 * m.a32;
		double nm12 = a21 * m.a13 + a22 * m.a23 + a23 * m.a33;

		double nm20 = a31 * m.a11 + a32 * m.a21 + a33 * m.a31;
		double nm21 = a31 * m.a12 + a32 * m.a22 + a33 * m.a32;
		double nm22 = a31 * m.a13 + a32 * m.a23 + a33 * m.a33;

		a11 = nm00;
		a12 = nm01;
		a13 = nm02;
		a21 = nm10;
		a22 = nm11;
		a23 = nm12;
		a31 = nm20;
		a32 = nm21;
		a33 = nm22;
	}
	
	public void transform(Point3 p) {
        double x = a11 * p.x + a12 * p.y + a13 * p.z;
        double y = a21 * p.x + a22 * p.y + a23 * p.z;
        double z = a31 * p.x + a32 * p.y + a33 * p.z;
        p.x = x;
        p.y = y;
        p.z = z;
    }
}