package red.game.geom;

import android.graphics.Point;

public class Point2 extends Point {
	public static Point clone(Point p) {
		Point np = new Point();
		np.set( p.x, p.y );
		return np;
	}
	public static Point diff( Point p1, Point p2 ) {
		Point d = new Point(0,0);
		d.x = p1.x - p2.x;
		d.y = p1.y - p2.y;
		return d;
	}
}
