package app.geom;

import java.awt.Color;
import java.awt.Graphics2D;

public class Rectangle extends Shape {

	private double width;
	private double height;
	
	public Rectangle(double width, double height) {
		this.width = width;
		this.height = height;
	}
	
	public double getWidth() {
		return width;
	}
	
	public void setWidth(double width) {
		this.width = width;
	}
	
	public double getHeight() {
		return height;
	}
	
	public void setHeight(double height) {
		this.height = height;
	}
	
	@Override
	public double area() {
		return width * height;
	}
	
	@Override
	public double circumference() {
		return 2 * width + 2 * height;
	}

	@Override
	public void draw(Graphics2D g) {
		g.setColor(Color.RED);
		g.drawRect(30, 30, (int) width, (int) height);
	}
	
}
