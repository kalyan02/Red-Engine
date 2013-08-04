package red.wordblocks.game.tween;

import red.game.tween.Tween;
import red.game.wordblocks.util.NumberUtil;
import red.wordblocks.game.core.TileUnderlay;

public class HelperOpacityTween extends Tween {
	TileUnderlay underlay;
	
	public HelperOpacityTween(TileUnderlay underlay, double from, double to, long duration, double steps) {
		super(underlay, from, to, duration, steps);
		this.underlay = underlay;
	}
	
	int ls, ts, bs, rs;
	
	public void reTween(boolean reset_shouldDisable) {
		if( reset_shouldDisable )
			shouldDisable = false;
	}
	public void reTween() {
		step = 0;

		ls = underlay.leftOp;
		bs = underlay.bottomOp;
		rs = underlay.rightOp;
		ts = underlay.topOp;
		
		if( !isStarted ) {
			start();
		}
		else
		if( isFinished ) {
			isFinished = false;
			start();
		}
		
	}
	
	boolean shouldDisable = false;
	public void unTween() {
		shouldDisable = true;
		reTween();
	}
	
	@Override
	public void tween() {
		if( currInterp > 0.9 ) currInterp = 1;
		
		if( step > 3 )
			step = step;
		
		//if( underlay.topOp!= underlay.topOpTarget )
			
			underlay.topOp = (int) NumberUtil.lerp( ts, underlay.topOpTarget, currInterp );
		//if( underlay.bottomOp!= underlay.bottomOpTarget )
			underlay.bottomOp = (int) NumberUtil.lerp( bs, underlay.bottomOpTarget, currInterp );
		//if( underlay.rightOp!= underlay.rightOpTarget )
			underlay.rightOp = (int) NumberUtil.lerp( rs, underlay.rightOpTarget, currInterp );
		//if( underlay.leftOp!= underlay.leftOpTarget )
			underlay.leftOp = (int) NumberUtil.lerp( ls, underlay.leftOpTarget, currInterp );
	}
	
	@Override
	public void tweenEnd() {
		if( shouldDisable ) {
			shouldDisable = false;
			underlay.isActive = false;
		}
	}

}
