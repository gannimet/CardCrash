package app.geom.ui;

import java.util.ArrayList;
import java.util.List;

import app.geom.Circle;
import app.geom.Rectangle;
import app.geom.RectangularTriangle;
import app.geom.Shape;

public class Main {

	public static void main(String[] args) {
		Shape shape1 = new Rectangle(260, 140);
		Shape shape2 = new Circle(90);
		Shape shape3 = new RectangularTriangle(120, 160);
		
		List<Shape> shapes = new ArrayList<Shape>();
		shapes.add(shape1);
		shapes.add(shape2);
		shapes.add(shape3);
		
		Window w = new Window(shapes);
		w.setVisible(true);
	}
	
}
