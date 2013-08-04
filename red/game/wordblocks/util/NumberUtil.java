package red.game.wordblocks.util;

public class NumberUtil {
	public static int clamp( int val, int start, int end ) {
		if( val < start ) return start;
		if( val > end   ) return end;
		return val;
	}
	public static double lerp( double from, double to, double frac ) {
		return (from + ((to-from) * frac));
	}
	
	public static int lerp( int from, int to, double frac ) {
		return (int) (from + ((to-from) * frac));
	}
}
