package app.geom.ui;

import java.awt.BasicStroke;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;

import app.geom.Shape;

public class Window extends JFrame {

	private static final long serialVersionUID = -6682897080184009986L;

	public Window(List<Shape> shapes) {
		setSize(500, 400);
		setLocation(
			(getToolkit().getScreenSize().width - getWidth()) / 2,
			(getToolkit().getScreenSize().height - getHeight()) / 2
		);
		setTitle("Geometrie");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setContentPane(new GeometryPane(shapes));
	}
	
	class GeometryPane extends JPanel {
		
		private static final long serialVersionUID = -2733718044001545581L;
		private List<Shape> shapes;
		
		public GeometryPane(List<Shape> shapes) {
			this.shapes = shapes;
		}
		
		@Override
		public void paintComponent(Graphics graphics) {
			Graphics2D g = (Graphics2D) graphics;
			
			g.setStroke(new BasicStroke(3));
			
			for (Shape shape : shapes) {
				shape.draw(g);
			}
		}
		
	}

}
