package red.wordblocks.game.tween;

import android.graphics.Point;
import android.view.animation.Animation;
import android.view.animation.BounceInterpolator;
import red.game.core.PlayStateManager;
import red.game.tween.Tween;
import red.wordblocks.config.GameConfig;
import red.wordblocks.game.core.Tile;

public class TileTween extends Tween {

	Point p_from = new Point();
	Point p_to = new Point();
	Point p_to_pivot = new Point();
	
	Tile target;
	
	public final static int HORIZ = 0;
	public final static int VERT=1;
	
	int dir;
	
	public TileTween(Tile target, Point to, long duration, double steps, int dir) {
		super(target, dir == HORIZ ? target.pivot.x : target.pivot.y, (dir == HORIZ ? to.x*GameConfig.TILE_WIDTH : to.y*GameConfig.TILE_HEIGHT ), duration, steps);
		this.target = target;
		this.dir = dir;
		this.p_to = to;
		this.p_to_pivot.set(to.x*GameConfig.TILE_WIDTH , to.y*GameConfig.TILE_HEIGHT);
	}
	
	@Override
	public void tween() {
		if( dir == HORIZ )
			target.pivot.x = (int) val;
		if( dir == VERT )
			target.pivot.y = (int) val;
		
		super.tween();
	}
	
	@Override
	public void tweenStart() {
		PlayStateManager.isLocked(true);
		super.tweenStart();
	}
	
	@Override
	public void tweenEnd() {
		target.x = p_to.x;
		target.y = p_to.y;
		target.pivot.set( p_to_pivot.x, p_to_pivot.y );
		
		PlayStateManager.isLocked(false);
		super.tweenEnd();
	}

}
