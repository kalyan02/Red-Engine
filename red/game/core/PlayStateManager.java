package red.game.core;

import java.util.Stack;

public class PlayStateManager {
	protected static Stack<PlayState> gameModes;
	static {
		gameModes = new Stack<PlayState>();
	}
	
	public static void init(PlayState mode) {
		gameModes.push(mode);
	}
	
	public static PlayState activeState() {
		return gameModes.peek();
	}
	
	public static void pop() {
		if( activeState().canChange() )
			gameModes.pop();
	}
	
	public static boolean isLocked = false;
	public static boolean isLocked() {
		return isLocked;
	}
	public static boolean isLocked( boolean locked ) {
		isLocked = locked;
		return isLocked();
	}
	
}
