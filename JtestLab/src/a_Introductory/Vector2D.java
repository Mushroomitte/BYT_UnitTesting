package a_Introductory;

public class Vector2D {
	public Integer x, y;
	
	Vector2D(Integer x, Integer y) {
		this.x = x;
		this.y = y;
	}
	
	/* Construct Vector2D from two points */
	Vector2D(Point p1, Point p2) {
		this.x = p2.x - p1.x;
		this.y = p2.y - p1.y;
	}
	
	//Fixed the error with the formula (v.x) -> (v.y)
	public int dotProduct(Vector2D v) {
		return (x * v.x) + (y * v.y);
	}
	
	//
	public boolean isOrthogonalTo(Vector2D v) {
		// Fixed the error in the dotProduct formula
		return (dotProduct(v) == 0);
	}
}
