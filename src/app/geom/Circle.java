package app.geom;

import java.awt.Color;
import java.awt.Graphics2D;

public class Circle extends Shape {

	private double radius;

	public Circle(double radius) {
		this.radius = radius;
	}
	
	public double getRadius() {
		return radius;
	}
	
	@Override
	public double area() {
		return Math.PI * radius * radius;
	}

	@Override
	public double circumference() {
		return 2 * Math.PI * radius;
	}

	@Override
	public void draw(Graphics2D g) {
		g.setColor(Color.GREEN);
		g.drawOval(30, 30, (int) radius * 2, (int) radius * 2);
	}
	
}
