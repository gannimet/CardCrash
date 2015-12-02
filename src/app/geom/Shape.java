package app.geom;

import java.awt.Graphics2D;

public abstract class Shape {

	public abstract double area();
	
	public abstract double circumference();
	
	public abstract void draw(Graphics2D g);
	
}
