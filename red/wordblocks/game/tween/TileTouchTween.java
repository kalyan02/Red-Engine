package red.wordblocks.game.tween;

import red.game.tween.Tween;
import red.wordblocks.game.core.Tile;

public class TileTouchTween extends Tween {
	Tile target;
	double factor;
	public TileTouchTween(Tile target, double factor, long duration, double steps) {
		super(target, factor, 0, duration, steps);
		this.target = target;
		this.factor = factor;
	}
	@Override
	public void tweenStart() {
		super.tweenStart();
	}
	
	@Override
	public void tweenEnd() {
		super.tweenEnd();
	}
	
	@Override
	public void tween() {
		super.tween();
	}
}
