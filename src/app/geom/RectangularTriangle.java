package app.geom;

import java.awt.Color;
import java.awt.Graphics2D;

public class RectangularTriangle extends Shape {

	private double a;
	private double b;
	
	public RectangularTriangle(double a, double b) {
		setA(a);
		setB(b);
	}
	
	public double getA() {
		return a;
	}
	
	public void setA(double a) {
		this.a = a;
	}
	
	public double getB() {
		return b;
	}
	
	public void setB(double b) {
		this.b = b;
	}
	
	@Override
	public double area() {
		return a * b / 2;
	}

	@Override
	public double circumference() {
		double c = Math.sqrt(a * a + b * b);
		return a + b + c;
	}

	@Override
	public void draw(Graphics2D g) {
		g.setColor(Color.BLUE);
		g.drawLine(30, 30, 30 + (int) a, 30); // Seite a
		g.drawLine(30, 30, 30, 30 + (int) b); // Seite b
		g.drawLine(30 + (int) a, 30, 30, 30 + (int) b); // Seite c
	}	
	
}
