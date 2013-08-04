package red.game.wordblocks.util;

public class Dir {
	

	public static int LEFT=8,
					  TOP=1,
					  RIGHT=2,
					  BOTTOM=4,
					  NONE=0,
					  ALL=LEFT|TOP|RIGHT|BOTTOM; 

	public int dir=0;
	public Dir() { dir = 0; }
	public Dir(int dir) { this.dir = dir; }
	
	public void set(int d) {
		dir |= d;
	}
	public void unset(int d) {
		dir ^= d;
	}
	public boolean has(int d) {
		return (dir&d)> 0;
	}
	public boolean has() {
		return dir > 0;
	}
	public void reset(int i) {
		dir = i;
	}
	public void reset() { dir = 0; }
}
