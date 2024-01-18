package a_Introductory;

public class Vector2D {
	public Integer x, y;

	Vector2D(Integer x, Integer y) {
		this.x = x;
		this.y = y;
	}

	Vector2D(Point p1, Point p2) {
		this.x = p2.x - p1.x;
		this.y = p2.y - p1.y;
	}

	// TODO - calculation bug
	public int dotProduct(Vector2D v) {
		return (x * v.x) + (y * v.y); // y * v.x --> y * v.y
	}

	// TODO - assertion bug
	public boolean isOrthogonalTo(Vector2D v) {
		return (dotProduct(v) == 0);
	} // -1 --> 0
}
