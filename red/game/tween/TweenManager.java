package red.game.tween;

import java.util.ArrayList;

public class TweenManager {
	static ArrayList<Tween> tweens;
	static {
		tweens = new ArrayList<Tween>();
	}
	public static void tween( Tween tween ) {	
		tweens.add(tween);
	}
	
	public static void tick() {
		
	}
}