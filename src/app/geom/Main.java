package app.geom;

public class Main {

	public static void main(String[] args) {
		Shape r1 = new Rectangle(5, 2);
		Shape c1 = new Circle(4);
		Shape t1 = new RectangularTriangle(3, 4);
		
		System.out.println("Fläche r1: " + r1.area());
		System.out.println("Umfang r1: " + r1.circumference());
		System.out.println("---");
		System.out.println("Fläche c1: " + c1.area());
		System.out.println("Umfang c1: " + c1.circumference());
		System.out.println("---");
		System.out.println("Fläche t1: " + t1.area());
		System.out.println("Umfang t1: " + t1.circumference());
	}
	
}
