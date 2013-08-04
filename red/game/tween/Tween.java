package red.game.tween;

import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.BounceInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.view.animation.Transformation;
import android.view.animation.TranslateAnimation;
import red.game.display.DisplayComponent;

public class Tween implements Runnable {
	public boolean isFinished;
	public boolean canTick;
	public boolean isStarted;
	
	public Object target;
	
	public double from, to, val, steps;
	public long duration, durationPerTick;
	
	protected double step, delta;
	
	protected Animation anim;
	protected double change;
	
	protected double currInterp=0;
	
	ITweenEventHandler tweenHandler=null;
	
	/**
	 * 
	 * @param target The target
	 * @param from 	 from what value
	 * @param to	 to what value
	 * @param duration in milli seconds (1000 = 1 second)
	 * @param steps
	 */
	public Tween( Object target, double from, double to, long duration, double steps) {
		step = 0;
		this.target = target;
		this.from = from;
		this.to = to;
		this.change = to - from;
		this.duration = duration;
		this.steps = steps;
		this.isFinished = false;
		this.isStarted = false;
				
		init();
	}
	
	public void setEventListener(ITweenEventHandler handler) {
		tweenHandler = handler;
	}
	
	public void init() {
		durationPerTick = (long) Math.floor( duration/steps );
		delta = (to - from)/steps;
		val = from;
	}
	
	/**
	 * Tick( interpolation fraction [0..1)
	 * @param f 0 to 1
	 */
	public void tick(float f) {
		step++;
		currInterp = f;
		val = change * f + from;
		
		if( step >= 0 ) {
			isStarted = true;
		}
		if( step >= steps )
			isFinished = true;
	}
	
	public void run() {
		Thread t = Thread.currentThread();
		tweenStart();
		
		long startTime = System.currentTimeMillis();
		long endTime = startTime + duration;
		
		Interpolator interp = new DecelerateInterpolator(4);
		
		while(!isFinished) {
			try {
				long currTime = System.currentTimeMillis();
				//if( currTime > endTime ) { step = steps; }
				float interpTime = (float)(currTime - startTime) / duration;
				tick( interp.getInterpolation(interpTime));
				tween();
				
				t.sleep(durationPerTick);
				
				// If more than 40% error, then reduce duration per tick by 30%
				long errTicks = System.currentTimeMillis() - currTime;
				if( errTicks > durationPerTick * 1.4 ) {
					durationPerTick *= 0.7;
				}
			} catch (InterruptedException e) {
				tweenEnd();
			}
		}
		tweenEnd();
		
	}
	
	public void tween() { if(tweenHandler != null) tweenHandler.tween(this); }
	public void tweenEnd() { if(tweenHandler != null) tweenHandler.tweenEnd(this); }
	public void tweenStart() { if(tweenHandler != null) tweenHandler.tweenStart(this); }
	
	public void start() {
		Thread t = new Thread(this);
		t.start();
		isStarted = true;
	}
}