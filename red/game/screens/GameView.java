package red.game.screens;

import red.game.core.GameLoop;
import red.game.core.GameStage;
import red.wordblocks.config.GameConfig;
import red.wordblocks.game.core.WordStage;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Debug;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;;

public class GameView extends SurfaceView implements SurfaceHolder.Callback {
	private GameLoop gameLoop;
	public GameStage gameStage;
	
	public GameView(Context context) {
		super(context);
		getHolder().addCallback(this);
		
		gameLoop = new GameLoop( getHolder(), this);
	}
	
	public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
	}
	
	public void surfaceDestroyed(SurfaceHolder holder) {
		boolean retry = true;
		
		System.out.println( "Destroyed!" );
		
		gameLoop.setRunning(false);
		while( retry ) {
			try {
				gameLoop.join();
				retry = false;
			} catch( Exception e ) {
				// bah
			}
		}
		if( GameConfig.DEBUG_PROFILE_ON )
			Debug.stopMethodTracing();
	}
	
	public void surfaceCreated(SurfaceHolder holder) {
		System.out.println( "Created view!" );
		
		if( GameConfig.DEBUG_PROFILE_ON )
			Debug.startMethodTracing("wb");
		
		try {
			gameStage = new WordStage(this);
		} catch( Exception e ) {
			e.printStackTrace();
		}

		
		gameLoop.setRunning(true);
		gameLoop.start();
	}
	
	@Override
	public void onDraw(Canvas canvas) {
		canvas.drawColor(Color.BLACK);

		//gameStage.prepStage(canvas);
		super.onDraw(canvas);
		
		gameStage.drawStage(canvas);
		
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		
		gameStage.onTouch(event);
		
		return true;//super.onTouchEvent(event);
	}
	

}
