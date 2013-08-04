package red.game.wordblocks.util;

import android.graphics.Point;
import red.wordblocks.game.core.Tile;

public class Util {
	public static boolean isGlobalInsideTile(Tile t, Point p) {
		return ( t.x() < p.x && p.x < t.x(t.w) && 
				 t.y() < p.y && p.y < t.y(t.h)  );
	}
	public static boolean isLocalInsideTile(Tile t, Point p) {
		return( t.pivot.x < p.x && p.x < t.pivot.x + t.w &&
				t.pivot.y < p.y && p.y < t.pivot.y + t.h  );
	}
}
